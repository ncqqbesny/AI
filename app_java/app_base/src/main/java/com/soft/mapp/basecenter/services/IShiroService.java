package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.loginVo.SysToken;
import com.soft.mapp.basecenter.domain.loginVo.User;

import java.util.Map;

public interface IShiroService {

    User findByUsername(String username);
    Map<String, Object> createToken(Integer userId);
    void logout(String token);
    SysToken findByToken(String accessToken);
    User findByUserId(Integer userId);
    //根据userName,email,phone 查询
    User findByLoginWhere(String userUniqueness);

}
