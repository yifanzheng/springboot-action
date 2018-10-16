package com.example.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开启spring的缓存功能
 * @author kevin
 */
@SpringBootApplication
@EnableCaching
public class EhcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhcacheApplication.class, args);
	}
}
