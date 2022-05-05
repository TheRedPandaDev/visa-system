package com.dmitrymilya.visa.casedecisionservice.dao;

import com.dmitrymilya.visa.casedecisionservice.entity.UserTaskEntity;
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

    @Insert("insert into user_task(id, visa_case_id, decision) " +
            "values (#{id}, #{visaCaseId}, #{decision})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('user_task_id_seq')")
    void insert(UserTaskEntity userTaskEntity);

    List<UserTaskEntity> getUserTasksForDecision(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getUserTasksForDecisionCount();

    List<UserTaskEntity> getUserTasksForInquiries(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getUserTasksForInquiriesCount();

    @Update("update user_task " +
            "set decision = #{decision} " +
            "where id = #{userTaskId} and decision is null")
    void updateDecision(@Param("userTaskId") Long userTaskId, DecisionEnum decision);

    @Update("update user_task " +
            "set ready_for_decision = true " +
            "where id = #{userTaskId}")
    void makeReadyDecision(@Param("userTaskId") Long userTaskId);

    Optional<UserTaskEntity> getUserTaskForDecisionById(@Param("id") Long id);

    Optional<UserTaskEntity> getUserTaskForInquiriesById(@Param("id") Long id);

}
