package com.dmitrymilya.visa.applicationprocessingservice.dao;

import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisaApplicationMapper {

    @Insert("insert into visa_application(id, attached_photo, applicant_info_id, visa_info_id) " +
            "values (#{id}, #{attachedPhoto}, #{applicantInfoId}, #{visaInfoId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('visa_application_id_seq')")
    void insert(VisaApplicationEntity visaApplicationEntity);

}
