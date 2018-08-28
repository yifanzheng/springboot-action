package com.newegg.core.service.domain;

import java.util.List;

public class PageHelper {
    //如果访问的页数不存在则返回值为null
    public static <T extends Record> List<T> page(List<T> records, Integer limit, Integer page) {
        int total=records.size();
        List<T> list= null;
        int start=limit*(page-1);
        if(start<=total)
            list = records.subList(limit*(page-1), (limit*page)>total?total:(limit*page));
        return list;
    }
}
