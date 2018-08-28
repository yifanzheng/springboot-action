package com.newegg.core.service.domain;

/**
 * 响应体中的数据
 */

public class ResponseData {
    private Boolean status;
    private int code;
    private String message;
    private Object data;
    private Page page;

    public ResponseData() {
    }

    public ResponseData(Boolean status, int code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(Boolean status, int code, String message, Object data, Page page) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
