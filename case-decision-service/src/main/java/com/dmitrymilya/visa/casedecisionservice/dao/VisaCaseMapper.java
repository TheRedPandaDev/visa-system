package com.dmitrymilya.visa.casedecisionservice.dao;

import com.dmitrymilya.visa.casedecisionservice.entity.VisaCaseEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisaCaseMapper {

    @Insert("insert into visa_case(id, attached_photo, applicant_info_id, visa_info_id) " +
            "values (#{id}, #{attachedPhoto}, #{applicantInfoId}, #{visaInfoId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('visa_case_id_seq')")
    void insert(VisaCaseEntity visaCaseEntity);

}
