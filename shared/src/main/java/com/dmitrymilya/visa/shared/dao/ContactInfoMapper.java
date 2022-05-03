package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.ContactInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ContactInfoMapper {

    @Insert("insert into contact_info(id, email, phone_number) " +
            "values (#{id}, #{email}, #{phoneNumber})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('contact_info_id_seq')")
    void insert(ContactInfoEntity contactInfoEntity);

}
