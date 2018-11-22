package com.example.servlet;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * 使用Java语言实现tomcat服务,加载servlet
 *
 * @author kevin
 **/
public class TomcatManager {

    private static final int SERVER_PORT = 8090;

    private static final String CONTEXT_PATH = "/src";

    private static final String SERVLET_NAME = "indexServlet";

    public static void main(String[] args) throws LifecycleException {
        // 创建tomcat服务器
        Tomcat tomcat = new Tomcat();
        // 设置端口
        tomcat.setPort(SERVER_PORT);
        // 是否设置自动部署
        tomcat.getHost().setAutoDeploy(false);
        // 创建servlet上下文对象
        StandardContext context = new StandardContext();
        // 设置上下文地址
        context.setPath(CONTEXT_PATH);
        // 监听上下文
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // tomcat添加上下文
        tomcat.getHost().addChild(context);

        // 添加servlet
        tomcat.addServlet(CONTEXT_PATH, SERVLET_NAME, new IndexServlet());
        // 在上下文添加servlet映射
        context.addServletMappingDecoded("/index", SERVLET_NAME);
        // 开启tomcat
        tomcat.start();
        System.out.println("tomcat 启动成功！");
        // 开启异步接收请求
        tomcat.getServer().await();
    }

}
