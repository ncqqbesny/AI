package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.domain.loginVo.UserDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserQuery;
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

    User findByLoginWhere(String userUniqueness);

    List<User> findByRoleId(Integer roleId);

    List<User> findListByWhere(UserQuery example);

    int addUser(UserDTO userDTO);

    int updateByExampleSelective(@Param("record") UserDTO record, @Param("example") UserQuery example);

    int deleteByExample(UserQuery example);

    List<?> getPageUser(UserQuery UserQuery);


}
