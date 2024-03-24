package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.UserRole;
import com.soft.mapp.basecenter.domain.loginVo.UserRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IUserRoleDao {

    int addUserRole(UserRole userRole);
    int deleteByExample(UserRoleQuery example);

}
