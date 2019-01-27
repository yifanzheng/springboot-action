package com.example.springboot.timer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在代码中需要动态获取spring管理的bean
 * 目前遇到的主要有两种场景：
 * 1.在工具类中需要调用某一个Service完成某一个功能;
 * 2.在实现了Runnable接口的任务类中需要调用某一个Service完成run方法中的功能。
 *
 * @author Star.Y.Zheng
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        ApplicationContextUtils.applicationContext = applicationContext;
    }

    /***
     * 根据一个bean的类型相应的bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }

}
