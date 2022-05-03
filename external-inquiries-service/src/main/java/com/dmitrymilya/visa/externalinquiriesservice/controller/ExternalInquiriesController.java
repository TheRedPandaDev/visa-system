package com.dmitrymilya.visa.externalinquiriesservice.controller;

import com.dmitrymilya.visa.externalinquiriesservice.facade.ExternalInquiriesFacade;
import com.dmitrymilya.visa.shared.dto.inquiries.ExternalInquiriesRequestDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExternalInquiriesController {

    private final ExternalInquiriesFacade externalInquiriesFacade;

    @PostMapping("/inquire")
    public ResponseEntity<List<ExternalInquiryResponseDto>> makeExternalInquiries(
            @RequestBody ExternalInquiriesRequestDto externalInquiriesRequestDto
    ) {
        return ResponseEntity.ok(externalInquiriesFacade.makeExternalInquiries(externalInquiriesRequestDto));
    }

}
