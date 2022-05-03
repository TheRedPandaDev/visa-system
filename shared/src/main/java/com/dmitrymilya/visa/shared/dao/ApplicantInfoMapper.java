package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApplicantInfoMapper {

    @Insert("insert into applicant_info(id, sex, birth_country, birth_date, last_name, first_name, middle_name, " +
            "citizenship, contact_info_id, address_id, person_document_id, work_or_study_info_id) " +
            "values (#{id}, #{sex}, #{birthCountry}, #{birthDate}, #{lastName}, #{firstName}, #{middleName}, " +
            "#{citizenship}, #{contactInfoId}, #{addressId}, #{personDocumentId}, #{workOrStudyInfoId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('applicant_info_id_seq')")
    void insert(ApplicantInfoEntity applicantInfoEntity);

}
