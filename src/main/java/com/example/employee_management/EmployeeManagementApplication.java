package com.example.employee_management;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("employee");
		cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES));
		return cacheManager;
	}

	@Scheduled(fixedRate = 30000)
	public void systemRunning() {
		System.out.println("system running");
	}
}
