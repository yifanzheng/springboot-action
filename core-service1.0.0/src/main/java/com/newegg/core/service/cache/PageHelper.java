package com.newegg.core.service.cache;

import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Record;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class PageHelper {
    public static <T extends Record> ArrayList<T> page(ArrayList<T> records, Integer limit, Integer page) {
        //TODO
        return null;
    }
}
