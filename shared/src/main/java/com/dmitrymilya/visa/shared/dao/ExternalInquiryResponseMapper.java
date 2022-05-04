package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.ExternalInquiryResponseEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExternalInquiryResponseMapper {

    @Insert("insert into external_inquiry_response(id, from_value, inquiry_response_status, answer, visa_case_id) " +
            "values (#{id}, #{from}, #{inquiryResponseStatus}, #{answer}, #{visaCaseId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('external_inquiry_response_id_seq')")
    void insert(ExternalInquiryResponseEntity externalInquiryResponseEntity);

}
