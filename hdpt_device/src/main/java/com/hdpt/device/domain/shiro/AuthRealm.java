package com.hdpt.device.domain.shiro;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;

import com.hdpt.device.dao.ISysLogDao;
import com.hdpt.device.domain.SysLog.SysLogPo;
import com.hdpt.device.domain.loginVo.Permission;
import com.hdpt.device.domain.loginVo.Role;
import com.hdpt.device.domain.loginVo.SysToken;
import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.handler.UserInfoContext;
import com.hdpt.device.services.IShiroService;
import com.hdpt.device.type.BaseConst;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IShiroService shiroService;
    @Autowired
    private ISysLogDao sysLogDao;
    /**
     * 授权 获取用户的角色和权限
     *@param
     *@return org.apache.shiro.authz.AuthorizationInfo
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        User user = (User) principals.getPrimaryPrincipal();
        //Integer userId = user.getUserId();
        //2.添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //2.1添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                //2.1.1添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    /**
     * 认证 判断token的有效性
     *@param  [token]
     *@return org.apache.shiro.authc.AuthenticationInfo
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取token，既前端传入的token
        String accessToken = (String) token.getPrincipal();
        //1. 根据accessToken，查询用户信息
        SysToken tokenEntity = shiroService.findByToken(accessToken);
        //2. token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            /*SysLogPo sysLogPo=new SysLogPo();
            sysLogPo.setLogType(BaseConst.LOG_OPTYPE_ADD);
            sysLogPo.setType(BaseConst.LOG_TYPE_ERROR);
            sysLogPo.setMsg("token失效，请重新登录");
            sysLogPo.setParam(accessToken);
            sysLogPo.setReturnValue(JSON.toJSONString(tokenEntity));
            sysLogPo.setModule("token校验");
            sysLogPo.setMethod("doGetAuthenticationInfo");
            sysLogPo.setIp(getUrlandIp().get("ip"));
            sysLogPo.setUrl(getUrlandIp().get("url"));
            sysLogService.addLogInfo(sysLogPo);*/
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        User user = shiroService.findByUserId(tokenEntity.getUserId());
        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (user == null) {
            SysLogPo sysLogPo=new SysLogPo();
            sysLogPo.setLogType(BaseConst.LOG_OPTYPE_ADD);
            sysLogPo.setType(BaseConst.LOG_TYPE_ERROR);
            sysLogPo.setMsg("用户不存在!");
            sysLogPo.setParam(accessToken);
            sysLogPo.setReturnValue(JSON.toJSONString(tokenEntity));
            sysLogPo.setModule("token校验");
            sysLogPo.setMethod("doGetAuthenticationInfo");
            sysLogPo.setIp(getUrlandIp().get("ip"));
            sysLogPo.setUrl(getUrlandIp().get("url"));
            sysLogDao.addInfo(sysLogPo);
            throw new UnknownAccountException("用户不存在!");
        }
        Map<String,String> map=getUrlandIp();
        user.setIp(map.get("ip"));
        user.setUrl(map.get("url"));
        //5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, this.getName());
        //保存用户
        UserInfoContext.setUser(user);
        return info;
    }

    /**
     * 获得ip和url
     * @return
     */
    public Map<String,String> getUrlandIp(){
        Map<String,String> map=new HashMap<>();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String clientIP = ServletUtil.getClientIP(request);
        map.put("ip",clientIP);
        map.put("url",request.getRequestURI());
        return map;
    }



}