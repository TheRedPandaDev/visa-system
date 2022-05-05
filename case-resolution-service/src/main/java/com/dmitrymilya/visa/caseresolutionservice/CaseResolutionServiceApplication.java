package com.dmitrymilya.visa.caseresolutionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.dmitrymilya.visa.caseresolutionservice.dao", "com.dmitrymilya.visa.shared.dao"})
public class CaseResolutionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseResolutionServiceApplication.class, args);
	}

}
