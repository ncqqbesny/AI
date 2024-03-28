package com.soft.mapp.basecenter.impl.controller;

import com.soft.mapp.basecenter.domain.loginVo.LoginDTO;
import com.soft.mapp.basecenter.domain.loginVo.RoleDTO;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.impl.services.CountService;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IRoleService;
import com.soft.mapp.basecenter.services.IShiroService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.JedisUtil;
import com.soft.mapp.basecenter.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "系统登录接口")
public class ShiroController {

    private final IShiroService shiroService;

    public ShiroController(IShiroService shiroService) {
        this.shiroService = shiroService;
    }

    @Autowired
    IRoleService roleService;
    @Autowired
    private CountService countService;

    /**
     * 登录
     */
    @ApiOperation(value = "登陆", notes = "参数:用户名 密码")
    @PostMapping("/hdptbase/api/user/login")
    @AutoLog(value ="登陆", operateType = 2, logType = 1,paramType = 0)
    public Map<String, Object> login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("status", 400);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        String password = loginDTO.getPassword();
        countService.incr(1);
        JedisUtil.set("onlineNum",ConvertUtils.toString(countService.getOnlineCnt()));
        //用户信息
        User user= new User();
        try {
            user = shiroService.findByLoginWhere(loginDTO.getUsername());
        }catch (Exception e){
            result.put("status", 400);
            result.put("msg", "登录账号有多个");
            return  result;
        }
        //账号不存在、密码错误
        if (user == null || user.getPassword()==null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到数据库
            result = shiroService.createToken(user.getUserId());
            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("id", StringUtil.toString(user.getUserId()));
            userMap.put("name", user.getUsername());
            //获得角色
            List<RoleDTO> roles = roleService.findByUserid(user.getUserId());
            Map<String,String> rolesMap = new HashMap<>();
            if (roles != null && !roles.isEmpty()) {
                for (RoleDTO role : roles) {
                    rolesMap.put("roleId", ConvertUtils.toString(role.getRoleId()));
                    rolesMap.put("roleName",role.getRoleName());
                }
            }
            if (null != rolesMap && !rolesMap.isEmpty()) {
                userMap.put("roles", rolesMap);
            }
            result.put("result",userMap);
            result.put("code", 200);
            result.put("msg", "登陆成功");
        }
        return result;
    }

    /**
     * 退出
     */
    @ApiOperation(value = "登出", notes = "参数:token")
    @PostMapping("/sys/logout")
    @AutoLog(value ="登出", operateType = 2, logType = 1,paramType = 2)
    public Map<String, Object> logout(@RequestHeader("token") String token) {
        Map<String, Object> resultLogout = new HashMap<>();
        shiroService.logout(token);
        resultLogout.put("status", 200);
        resultLogout.put("msg", "您已安全退出系统");
        countService.incr(-1);
        JedisUtil.set("onlineNum",ConvertUtils.toString(countService.getOnlineCnt()));
        return resultLogout;
    }

}
