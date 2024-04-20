package com.app.device.services;



import com.app.device.domain.loginVo.SysToken;
import com.app.device.domain.loginVo.User;

import java.util.Map;

public interface IShiroService {

    User findByUsername(String username);
    SysToken findByToken(String accessToken);
    User findByUserId(Integer userId);

}
