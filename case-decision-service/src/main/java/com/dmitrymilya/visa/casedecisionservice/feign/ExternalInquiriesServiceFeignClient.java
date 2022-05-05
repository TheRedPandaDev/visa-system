package com.dmitrymilya.visa.casedecisionservice.feign;

import com.dmitrymilya.visa.shared.dto.inquiries.ExternalInquiriesRequestDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "external-inquiries-service", url = "${feign.gateway-service:http://localhost:2023/external-inquiries-service}")
public interface ExternalInquiriesServiceFeignClient {

    @PostMapping("/inquire")
    List<ExternalInquiryResponseDto> makeExternalInquiries(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ExternalInquiriesRequestDto externalInquiriesRequestDto
    );

}
