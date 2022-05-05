package com.dmitrymilya.visa.visaissueservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.dmitrymilya.visa.visaissueservice.dao", "com.dmitrymilya.visa.shared.dao"})
public class VisaIssueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisaIssueServiceApplication.class, args);
	}

}
