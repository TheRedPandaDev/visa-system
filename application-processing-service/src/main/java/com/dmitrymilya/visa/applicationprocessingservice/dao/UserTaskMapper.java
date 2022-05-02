package com.dmitrymilya.visa.applicationprocessingservice.dao;

import com.dmitrymilya.visa.applicationprocessingservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.shared.model.DecisionEnum;
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

    @Insert("insert into user_task(id, visa_application_id, decision) " +
            "values (#{id}, #{visaApplicationId}, #{decision})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('user_task_id_seq')")
    void insert(UserTaskEntity userTaskEntity);

    List<UserTaskEntity> getUserTasksForDecision(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getUserTasksForDecisionCount();

    @Update("update user_task " +
            "set decision = #{decision} " +
            "where id = #{userTaskId} and decision is null")
    void updateDecision(@Param("userTaskId") Long userTaskId, DecisionEnum decision);

    Optional<UserTaskEntity> getUserTaskById(@Param("id") Long id);

}
