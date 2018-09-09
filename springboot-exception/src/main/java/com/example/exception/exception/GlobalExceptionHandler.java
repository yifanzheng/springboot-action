package com.example.exception.exception;

import com.example.exception.pojo.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.Element;
import java.util.Objects;

/**
 * 全局异常捕获
 *
 * @author kevin
 * @date 2018-09-07 21:08
 **/
//@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_VIEW="errors";

    /*@ExceptionHandler(value = ArithmeticException.class)
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception",e);
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.setViewName(ERROR_VIEW);
        return modelAndView;
    }

    @ExceptionHandler(value = NullPointerException.class)
    public JsonResponse ajaxErrorHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        return JsonResponse.errorException(e.getMessage());
    }*/

    @ExceptionHandler(Exception.class)
    public Object errorHandler(HttpServletRequest request,HttpServletResponse response,Exception e){
        e.printStackTrace();
        if(isAjax(request)){
            return JsonResponse.errorException(e.getMessage());
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("exception",e);
            modelAndView.addObject("url",request.getRequestURL());
            modelAndView.setViewName(ERROR_VIEW);
            return modelAndView;
        }
    }

    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With")!=null
                && Objects.equals(request.getHeader("X-Requested-With").toString(),"XMLHttpRequest"));
    }


}
