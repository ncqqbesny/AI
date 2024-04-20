package com.app.device.impl.services;

import com.app.device.dao.ISysTokenDao;
import com.app.device.dao.IUserDao;
import com.app.device.domain.loginVo.SysToken;
import com.app.device.domain.loginVo.User;
import com.app.device.services.IShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroServiceImpl implements IShiroService {
    @Autowired
    IUserDao userDao;
    @Autowired
    ISysTokenDao sysTokenDao;
    //@Value("${exprie.hours}")
    private String hours;

    /**
     * 根据username查找用户
     *
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }

    //12小时后过期
    private static int EXPIRE = 3600 * 12;


    @Override
    public SysToken findByToken(String accessToken) {
        return sysTokenDao.findByToken(accessToken);
    }

    @Override
    public User findByUserId(Integer userId) {
        return userDao.selectByuserId(userId);
    }
}
