package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.AddressEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AddressMapper {

    @Insert("insert into address(id, region, city, street, house, section, building, apartment) " +
            "values (#{id}, #{region}, #{city}, #{street}, #{house}, #{section}, #{building}, #{apartment})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('visa_application_id_seq')")
    void insert(AddressEntity addressEntity);

}
