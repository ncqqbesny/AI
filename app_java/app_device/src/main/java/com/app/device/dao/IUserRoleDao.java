package com.app.device.dao;

import com.app.device.domain.loginVo.UserRole;
import com.app.device.domain.loginVo.UserRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IUserRoleDao {

    int addUserRole(UserRole userRole);
    int deleteByExample(UserRoleQuery example);
}
