package com.example.manager;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * tomcat服务器
 *
 * @author kevin
 **/
public class TomcatAppManager {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        // 设置服务器端口
        tomcat.setPort(9090);
        // 读取项目路径，加载静态资源
        StandardContext context = (StandardContext) tomcat.addWebapp("/",
                new File("src/main").getAbsolutePath());
        // 禁止重新载入
        context.setReloadable(false);
        // class文件读取地址
        File addWebInfoClass = new File("target/classes");
        // 创建webroot
        WebResourceRoot resourceRoot = new StandardRoot(context);
        // tomcat内部读取class文件
        resourceRoot.addPreResources(
                new DirResourceSet(resourceRoot, "/WEB-INF/class", addWebInfoClass.getAbsolutePath(),"/"));
        // 启动Tomcat
        tomcat.start();
        // 开启异步等待请求执行
        tomcat.getServer().await();


    }
}
