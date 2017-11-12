package com.example.webclientdemo;

import com.example.webclientdemo.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebclientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebclientDemoApplication.class, args);
	}
}
