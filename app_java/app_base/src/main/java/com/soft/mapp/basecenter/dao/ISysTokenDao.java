package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.SysToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ISysTokenDao {
    /**
     * 查找token
     * @param userId
     * @return
     */
    SysToken findByUserId(@Param("userId") Integer userId);

    /**
     * 保存token
     * @param tokenEntity
     */
    void insert (SysToken tokenEntity);

    /**
     * 保存token
     * @param tokenEntity
     */
    void updateByUserId (SysToken tokenEntity);

    /**
     * 查找token信息
     * @param accessToken
     * @return
     */
    SysToken findByToken(@Param("token") String token);
}
