package com.dmitrymilya.visa.caseresolutionservice.dao;

import com.dmitrymilya.visa.caseresolutionservice.entity.UserTaskEntity;
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

    @Insert("insert into user_task(id, visa_case_id, is_resolved) " +
            "values (#{id}, #{visaCaseId}, #{isResolved})")
    @SelectKey(resultType = Long.class, keyProperty = "id", before = true,
            statement = "select nextval('user_task_id_seq')")
    void insert(UserTaskEntity userTaskEntity);

    List<UserTaskEntity> getUserTasksForResolution(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getUserTasksForResolutionCount();

    List<UserTaskEntity> getResolvedUserTasks(@Param("limit") Integer limit, @Param("offset") Integer offset);

    Long getResolvedUserTasksCount();

    @Update("update user_task " +
            "set is_resolved = true, comment_value = #{comment}, resolved_dttm = now() " +
            "where id = #{userTaskId}")
    void updateUserTaskResolved(@Param("userTaskId") Long userTaskId, @Param("comment") String comment);

}
