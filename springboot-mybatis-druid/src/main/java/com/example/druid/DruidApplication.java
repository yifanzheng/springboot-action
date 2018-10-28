package com.example.druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动器
 *
 * @author kevin
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example.druid.mapper")
public class DruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(DruidApplication.class, args);
	}
}
