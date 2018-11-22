package com.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 配置springMvc dispatcherServlet
 *
 * @author kevin
 **/
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 加载根配置信息
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * springMVC 加载配置信息
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * springMvc拦截url映射
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        // 拦截所有请求
        return new String[]{"/"};
    }
}
