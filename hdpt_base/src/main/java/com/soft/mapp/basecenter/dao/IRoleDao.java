package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IRoleDao {

    RoleDTO selectByRoleId(Integer roleId);

    List<RoleDTO> findList();

    RoleDTO findByRoleName(String RoleName);

    List<RoleDTO> findByUserid(Integer userId);

    int addRole(RoleDTO role);

    int updateByExampleSelective(@Param("record") RoleDTO record, @Param("example") RoleQuery example);

    int deleteByExample(RoleQuery example);
    int deleteByUserId(Integer  userId);
    List<?> getPageRole(RoleQuery roleQuery);
    List<RoleDTO> getRoleByMenu(@Param("record") RoleVo record);

    List<RoleDTO> findListByWhere(RoleQuery example);

}
