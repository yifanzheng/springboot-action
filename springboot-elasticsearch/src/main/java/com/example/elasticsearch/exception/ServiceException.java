package com.example.elasticsearch.exception;

import org.springframework.http.HttpStatus;

/**
 * ServiceException
 *
 * @author star
 */
public class ServiceException extends BaseException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
