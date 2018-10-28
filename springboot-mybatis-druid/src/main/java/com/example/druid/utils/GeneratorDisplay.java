package com.example.druid.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用Mapper生成器
 *
 * @author kevin
 **/
public class GeneratorDisplay {

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();

        // 创建逆向工程配置文件
        File configFile = new File("generatorConfig.xml");
        // 配置解析器
        ConfigurationParser parser = new ConfigurationParser(warnings);
        // 解析配置文件
        Configuration configuration = parser.parseConfiguration(configFile);
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(configuration, shellCallback, warnings);
        generator.generate(null);


    }

    public static void main(String[] args) {
        try {
            new GeneratorDisplay().generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
