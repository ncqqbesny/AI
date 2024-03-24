package com.soft.mapp.basecenter.impl.services;

import com.soft.mapp.basecenter.dao.ISysTokenDao;
import com.soft.mapp.basecenter.dao.IUserDao;
import com.soft.mapp.basecenter.domain.loginVo.SysToken;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.services.IShiroService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.TokenGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private  static int EXPIRE = 3600 * 12;

    @Override
    /**
     * 生成一个token
     *@param  [userId]
     *@return Result
     */
    public Map<String, Object> createToken(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        //生成一个token
        String token = TokenGenerator.getYRandomString(50);
        //当前时间
        Date now = new Date();
        //过期时间
        if(!StringUtils.isBlank(hours)&&StringUtils.isNumeric(hours)){
            EXPIRE=3600* ConvertUtils.toInt(hours);
        }
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        SysToken tokenEntity = sysTokenDao.findByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            sysTokenDao.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            sysTokenDao.updateByUserId(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", EXPIRE);
        return result;
    }

    @Override
    public void logout(String token) {
        SysToken byToken = findByToken(token);
        //生成一个token
        token = TokenGenerator.getYRandomString(50);
        //修改token
        SysToken tokenEntity = new SysToken();
        tokenEntity.setUserId(byToken.getUserId());
        tokenEntity.setToken(token);
        sysTokenDao.updateByUserId(tokenEntity);
    }

    @Override
    public SysToken findByToken(String accessToken) {
        return sysTokenDao.findByToken(accessToken);
    }

    @Override
    public User findByUserId(Integer userId) {
        return userDao.selectByuserId(userId);
    }

    @Override
    public User findByLoginWhere(String userUniqueness) {
        User user = userDao.findByLoginWhere(userUniqueness);
        return user;
    }
}
