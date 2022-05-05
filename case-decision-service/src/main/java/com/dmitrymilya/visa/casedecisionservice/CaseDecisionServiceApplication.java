package com.dmitrymilya.visa.casedecisionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan({"com.dmitrymilya.visa.casedecisionservice.dao", "com.dmitrymilya.visa.shared.dao"})
@EnableFeignClients
public class CaseDecisionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseDecisionServiceApplication.class, args);
	}

}
