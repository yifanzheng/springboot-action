package com.example.elasticsearch.dto;

import java.util.List;

/**
 * ReportDataDTO
 *
 * @author star
 */
public class SoftwareReportDataDTO {

    private String code;

    private String functionId;

    private long totalViews;

    private List<ReportDataDTO> views;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    public List<ReportDataDTO> getViews() {
        return views;
    }

    public void setViews(List<ReportDataDTO> views) {
        this.views = views;
    }
}
