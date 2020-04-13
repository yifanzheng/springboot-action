package com.example.elasticsearch.service;

import com.example.elasticsearch.api.ElasticSearchRestAPI;
import com.example.elasticsearch.dto.ReportDataDTO;
import com.example.elasticsearch.dto.SoftwareReportDataDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReportService
 * <p>
 * 用于查询统计报告
 *
 * @author star
 */
@Service
public class SoftwareReportService {

    @Autowired
    private ElasticSearchRestAPI elasticSearchRestAPI;


    /**
     * 搜索软件访问统计报告
     *
     * @param startTimestamp 开始时间
     * @param endTimestamp 结束时间
     * @param intervalMinutes 间隔分钟数
     * @param topNum 前N条数据
     * @return 返回软件访问统计报告集合
     */
    public List<SoftwareReportDataDTO> searchSoftwareReport(long startTimestamp, long endTimestamp, int intervalMinutes, int topNum) {
        SearchSourceBuilder searchSourceBuilder =
                this.newSearchSourceBuilderForReport(startTimestamp, endTimestamp, intervalMinutes, topNum);

        return this.searchTopSoftwareReportData(searchSourceBuilder);

    }

    /**
     * 搜索前N条软件访问统计报告
     *
     * @param sourceBuilder 搜索条件构造器
     * @return 返回软件统计报告集合
     */
    private List<SoftwareReportDataDTO> searchTopSoftwareReportData(SearchSourceBuilder sourceBuilder) {
        // 搜索
        Aggregations aggs = elasticSearchRestAPI.searchAggs(sourceBuilder, "report_software_*");
        if (aggs == null) {
            return Collections.emptyList();
        }
        Aggregation topStatAgg = aggs.get("topData");
        if (!(topStatAgg instanceof Terms)) {
            return Collections.emptyList();
        }
        Terms terms = (Terms) topStatAgg;
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        if (CollectionUtils.isEmpty(buckets)) {
            return Collections.emptyList();
        }
        // 解析成统计数据
        return buckets.stream().map(e -> {
            // 分组key
            String keyString = e.getKeyAsString();
            Long totalViews = 0L;
            // 总访问量
            Aggregation totalSpeedAgg = e.getAggregations().get("totalViews");
            if (totalSpeedAgg instanceof ParsedSum) {
                ParsedSum sum = (ParsedSum) totalSpeedAgg;
                totalViews = new Double(sum.getValue()).longValue();
            }

            // 解析按时间聚合的统计数据
            Aggregation statAgg = e.getAggregations().get("views");
            List<ReportDataDTO> reportDataList = this.parseStatAggregation(statAgg);

            String[] keys = keyString.split("#");

            // 生成软件功能访问量统计报告
            SoftwareReportDataDTO dataDTO = new SoftwareReportDataDTO();
            dataDTO.setCode(keys[0]);
            dataDTO.setFunctionId(keys[1]);
            dataDTO.setTotalViews(totalViews);
            dataDTO.setViews(reportDataList);

            return dataDTO;
        }).collect(Collectors.toList());
    }

    private List<ReportDataDTO> parseStatAggregation(Aggregation aggregation) {
        if (!(aggregation instanceof ParsedDateHistogram)) {
            return Collections.emptyList();
        }
        ParsedDateHistogram histogram = (ParsedDateHistogram) aggregation;
        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
        if (CollectionUtils.isEmpty(buckets)) {
            return Collections.emptyList();
        }
        // 解析成统计数据
        return buckets.stream().map(e -> {
            Long timestamp = ((ZonedDateTime) e.getKey()).toEpochSecond() * 1000;
            Long views = 0L;
            // 访问量
            Aggregation speedAgg = e.getAggregations().get("views");
            if (speedAgg instanceof ParsedSum) {
                ParsedSum sum = (ParsedSum) speedAgg;
                views = new Double(sum.getValue()).longValue();
            }
            ReportDataDTO reportDataDTO = new ReportDataDTO();
            reportDataDTO.setTimestamp(timestamp);
            reportDataDTO.setViews(views);

            return reportDataDTO;
        }).collect(Collectors.toList());
    }


    /**
     * 构建获取前N条统计数据的搜索条件
     *
     * @param startTimestamp  开始时间
     * @param endTimestamp    结束时间
     * @param intervalMinutes 间隔分钟数
     * @param topNum          前多少条数据
     * @return 返回搜索条件构造器
     */
    private SearchSourceBuilder newSearchSourceBuilderForReport(long startTimestamp, long endTimestamp, int intervalMinutes, int topNum) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 搜索条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("reportTimestamp")
                        .gte(startTimestamp)
                        .lte(endTimestamp));
        searchSourceBuilder.query(queryBuilder);

        // 根据软件code和functionId进行分组统计
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("topData")
                .script(new Script("doc['code.keyword'].value+'#'+doc['functionId.keyword'].value"))
                .subAggregation(AggregationBuilders.sum("totalViews").field("view"))
                .subAggregation(
                        AggregationBuilders.dateHistogram("views")
                                .field("reportTimestamp")
                                .fixedInterval(DateHistogramInterval.minutes(intervalMinutes))
                                .minDocCount(0)
                                .extendedBounds(new ExtendedBounds(startTimestamp, endTimestamp))
                                .subAggregation(
                                        AggregationBuilders.sum("views").field("view")
                                )
                )
                .order(BucketOrder.aggregation("totalViews", false))
                .size(topNum);
        searchSourceBuilder.aggregation(aggregationBuilder);

        return searchSourceBuilder;
    }

}
