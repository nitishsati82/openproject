package com.example.profile.dbconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource")
public class DbConfiguration {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	public String getDriverClassName() {
		return driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		System.out.println("DB connection for DEV - H2");
		System.out.println(driverClassName);
		System.out.println(url);
		return "DB connection for DEV - H2";
	}
	
	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		System.out.println("DB Connection to TEST - LOW Performance Instance");
		System.out.println(driverClassName);
		System.out.println(url);
		return "DB connection for TEST";
	}
	
	@Profile("prod")
	@Bean
	public String prodDatabaseConnection() {
		System.out.println("DB Connection to RDS_PROD - High Performance Instance");
		System.out.println(driverClassName);
		System.out.println(url);
		return "DB connection for PROD";
	}
}
