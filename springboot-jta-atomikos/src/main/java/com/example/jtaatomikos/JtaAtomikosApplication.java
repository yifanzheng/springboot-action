package com.example.jtaatomikos;

import com.example.jtaatomikos.config.datasource.DataSourceConfiguration1;
import com.example.jtaatomikos.config.datasource.DataSourceConfiguration2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import sun.awt.SunHints;

@SpringBootApplication
// @EnableConfigurationProperties(value = {DataSourceConfiguration1.class, DataSourceConfiguration2.class})
public class JtaAtomikosApplication {

	public static void main(String[] args) {
		SpringApplication.run(JtaAtomikosApplication.class, args);
	}
}
