package com.hdpt.device.dao;

import com.hdpt.device.domain.loginVo.UserRole;
import com.hdpt.device.domain.loginVo.UserRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IUserRoleDao {

    int addUserRole(UserRole userRole);
    int deleteByExample(UserRoleQuery example);
}
