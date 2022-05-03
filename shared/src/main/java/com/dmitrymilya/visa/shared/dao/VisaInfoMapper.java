package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.VisaInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisaInfoMapper {

    @Insert("insert into visa_info(id, category_type, entry_type, valid_from, valid_to) " +
            "values (#{id}, #{category}, #{entryType}, #{validFrom}, #{validTo})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('visa_info_id_seq')")
    void insert(VisaInfoEntity visaInfoEntity);

}
