package com.example.service;

import org.springframework.stereotype.Service;

/**
 * 业务层
 *
 * @author kevin
 **/
@Service
public class IndexService {

    public String getContent() {
        return "spring boot 底层实现原理！";
    }
}
