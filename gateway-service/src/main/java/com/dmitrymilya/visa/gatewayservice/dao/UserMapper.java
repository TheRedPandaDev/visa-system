package com.dmitrymilya.visa.gatewayservice.dao;

import com.dmitrymilya.visa.gatewayservice.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user_table WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Insert("INSERT INTO user_table(username, password) VALUES (#{username}, #{password})")
    void saveUser(User user);

}
