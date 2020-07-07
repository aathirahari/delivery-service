package com.eatza.deliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.eatza.deliveryservice.config.JwtFilter;


@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class DeliveryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryserviceApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean jwtFilterBean() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/delivery/closeOrder/*");

		return registrationBean;
	}
}
