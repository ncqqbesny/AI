package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IAgvSysLogDao {

    List<?> getPageRole(RoleQuery roleQuery);

}
