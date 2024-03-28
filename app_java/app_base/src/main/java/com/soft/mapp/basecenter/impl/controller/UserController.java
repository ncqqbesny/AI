package com.soft.mapp.basecenter.impl.controller;

import com.soft.mapp.basecenter.controller.IUserController;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IUserService;
import com.soft.mapp.basecenter.type.UserTypeEnum;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户中心接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class UserController implements IUserController {
    @Autowired
    IUserService userService;
    private static final String sessionKey = "login";

    @ApiOperation(value = "查询登录用户信息", notes = "根据token查询用户")
    @PostMapping("/api/user/info")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> map = userService.getUserInfo();
        return map;
    }

    @ApiOperation(value = "根据ID查询用户", notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true)
    @GetMapping("/user/query/{id}")
    @AutoLog(value = "根据ID查询用户", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getUserById(@PathVariable Integer id) {
        User user=userService.selectByuserId(id);
        if(null==user ){
            return ServerResponse.createByError();
        }
         return ServerResponse.createBySuccess(user);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户信息")
    @RequestMapping(value = "/api/user/deleteByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="删除用户", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> deleteUserById(@RequestParam("ids") List<Integer> ids) {
        int  count =userService.deleteByExample(ids);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "注册用户", notes = "注册一个用户，传入用户名和性别")
    @RequestMapping(value = "/api/user/regUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    @AutoLog(value ="注册用户", operateType = 1, logType = 1,paramType = 0)
    public ServerResponse<?> regUser(@RequestBody UserVo userVo) {
        int count = userService.addUser(userVo);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("注册用户失败");
    }

    @Override
    @ApiOperation(value = "添加用户", notes = "添加一个用户，传入用户名和性别11")
    @RequestMapping(value = "/api/user/addInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    @AutoLog(value ="添加用户", operateType = 1, logType = 1,paramType = 0)
    public ServerResponse<?> addUser(@RequestBody UserVo userVo) {
        userVo.setUserType(UserTypeEnum.getValue("网页注册用户"));
        int count = userService.addUser(userVo);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加用户失败");
    }

    @Override
    @ApiOperation(value = "修改用户", notes = "根据传入的用户信息修改用户")
    @RequestMapping(value = "/api/user/updateUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="修改用户公司", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> updateUser(@RequestBody UserVo userVo) {
        userVo.setUserType(UserTypeEnum.getValue("网页注册用户"));
        int count = userService.updateByExampleSelective(userVo);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("修改用户失败");
    }
    @Override
    @ApiOperation(value = "查询用户列表信息", notes = "根据传入的用户信息分页查询")
    @RequestMapping(value = "/api/user/getPageByUser", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序
    @AutoLog(value = "查询用户列表信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getPageByUser(UserVo userVo, Integer pageSize, Integer pageNum) {
        CommonPage<UserVo> list = userService.getPageUser(userVo, pageSize, pageNum);
        return ServerResponse.createBySuccess(list);
    }
    @GetMapping("/ignore")
    @ApiIgnore
    public void ignoreMethod() {
    }
}
