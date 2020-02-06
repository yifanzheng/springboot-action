package com.example.elasticsearch.api;

/**
 * ElasticSearchDocument
 *
 * @author star
 */
public class ElasticSearchDocument<T> {

    private String id;

    private T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
