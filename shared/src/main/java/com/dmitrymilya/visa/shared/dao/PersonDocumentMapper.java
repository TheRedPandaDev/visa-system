package com.dmitrymilya.visa.shared.dao;

import com.dmitrymilya.visa.shared.entity.PersonDocumentEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonDocumentMapper {

    @Insert("insert into person_document(id, doc_no, series_code, issue_date, valid_from, valid_to, issuer) " +
            "values (#{id}, #{docNo}, #{seriesCode}, #{issueDate}, #{validFrom}, #{validTo}, #{issuer})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('person_document_id_seq')")
    void insert(PersonDocumentEntity personDocumentEntity);

}
