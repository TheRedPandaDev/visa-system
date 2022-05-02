package com.dmitrymilya.visa.applicationprocessingservice.dao;

import com.dmitrymilya.visa.applicationprocessingservice.entity.VisitAddressEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisitAddressMapper {

    @Insert("insert into visit_address(id, region, city, visa_application_id) " +
            "values (#{id}, #{region}, #{city}, #{visaApplicationId})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('visit_address_id_seq')")
    void insert(VisitAddressEntity visitAddressEntity);

}
