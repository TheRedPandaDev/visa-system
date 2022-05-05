package com.dmitrymilya.visa.visaissueservice.dao;

import com.dmitrymilya.visa.visaissueservice.entity.UserTaskEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserTaskMapper {

    @Insert("insert into user_task(id, visa_case_id, is_issued) " +
            "values (#{id}, #{visaCaseId}, #{isIssued})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('user_task_id_seq')")
    void insert(UserTaskEntity userTaskEntity);

    List<UserTaskEntity> getUserTasksForIssuing(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getUserTasksForIssuingCount();

    @Update("update user_task " +
            "set is_issued = true " +
            "where id = #{userTaskId}")
    void updateUserTaskIssued(@Param("userTaskId") Long userTaskId);

    Optional<UserTaskEntity> getUserTaskForIssuingById(@Param("id") Long id);

}
