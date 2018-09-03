package com.newegg.core.service.config;

import com.newegg.core.service.domain.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionConfig {

    /**
     * 页面找不到
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData notFound(NoHandlerFoundException exception) {
        ResponseData responseData = new ResponseData(false,404,exception.getMessage(),null);
        return responseData;
    }

    /**
     * 请求方式不受支持
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseData methodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        ResponseData responseData = new ResponseData(false,405,exception.getMessage(),null);
        return responseData;
    }


    /**
     * 统一的5xx错误
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseData defaultException(Exception exception) {
        ResponseData responseData = new ResponseData(false,500,exception.getMessage(),null);
        return responseData;
    }

}
