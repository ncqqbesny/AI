package com.soft.mapp.basecenter.impl.controller;

import com.soft.mapp.basecenter.controller.IRoleController;
import com.soft.mapp.basecenter.domain.loginVo.RoleVo;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IRoleService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "角色管理接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class RoleController implements IRoleController {
    @Autowired
    IRoleService roleService;


    @ApiOperation(value = "查询角色的用户", notes = "根据角色id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "角色id", required = true)
    @GetMapping("/api/role/{id}")
    public ServerResponse<?> getUserInfoById(@PathVariable Integer id) {
        List<User> userList=roleService.findUserById(id);
        if(null==userList || userList.size()==0 ){
            return ServerResponse.createByErrorMessage("没有查到用户数据");
        }
        return ServerResponse.createBySuccess(userList);
    }

    @ApiOperation(value = "删除角色", notes = "删除角色信息")
    @RequestMapping(value = "/api/role/deleteByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="删除角色", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> deleteByExample(@RequestParam("ids")  List<Integer> roleIds) {
        int  count =roleService.deleteByExample(roleIds);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "添加角色", notes = "添加角色信息")
    @RequestMapping(value = "/api/role/addInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    public ServerResponse<?> addInfo(@RequestBody RoleVo roleVo) {
        int  count =roleService.addInfo(roleVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加菜单页面失败");
    }

    @Override
    @ApiOperation(value = "编辑角色", notes = "编辑角色信息")
    @RequestMapping(value = "/api/role/updateByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="编辑角色", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> updateByExample(@RequestBody RoleVo roleVo) {
        int  count =roleService.updateByExampleSelective(roleVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "查询角色信息", notes = "根据参数条件分页查询")
    @RequestMapping(value = "/api/role/getPageBySelective", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序
    @AutoLog(value = "查询角色信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getPageBySelective(RoleVo roleVo, Integer pageSize, Integer pageNum) {
        CommonPage<RoleVo> list=roleService.getPageBySelective(roleVo,pageSize,pageNum);
        return  ServerResponse.createBySuccess(list);
    }
}
