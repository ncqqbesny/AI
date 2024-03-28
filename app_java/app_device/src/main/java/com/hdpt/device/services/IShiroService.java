package com.hdpt.device.services;



import com.hdpt.device.domain.loginVo.SysToken;
import com.hdpt.device.domain.loginVo.User;

import java.util.Map;

public interface IShiroService {

    User findByUsername(String username);
    SysToken findByToken(String accessToken);
    User findByUserId(Integer userId);

}
