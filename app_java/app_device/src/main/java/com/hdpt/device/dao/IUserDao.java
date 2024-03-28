package com.hdpt.device.dao;

import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.domain.loginVo.UserDTO;
import com.hdpt.device.domain.loginVo.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IUserDao {

    User selectByuserId(Integer userId);

    List<User> findList();

    User findByUsername(String username);

    List<User> findByRoleId(Integer roleId);

    int addUser(UserDTO userDTO);

    int updateByExampleSelective(@Param("record") UserDTO record, @Param("example") UserQuery example);

    int deleteByExample(UserQuery example);

    List<?> getPageUser(UserQuery UserQuery);
}
