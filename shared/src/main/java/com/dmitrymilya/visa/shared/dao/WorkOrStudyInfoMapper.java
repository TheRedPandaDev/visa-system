package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.WorkOrStudyInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkOrStudyInfoMapper {

    @Insert("insert into work_or_study_info(id, organization, job_title, contact_info_id, address_id) " +
            "values (#{id}, #{organization}, #{jobTitle}, #{contactInfoId}, #{addressId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('work_or_study_info_id_seq')")
    void insert(WorkOrStudyInfoEntity workOrStudyInfoEntity);

}
