package com.soft.mapp.basecenter.impl.controller;

import com.soft.mapp.basecenter.controller.IMenuController;
import com.soft.mapp.basecenter.domain.loginVo.MenuDTO;
import com.soft.mapp.basecenter.domain.loginVo.MenuVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IMenuService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "菜单页面接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class MenuController implements IMenuController {
    @Autowired
    IMenuService menuService;


    @ApiOperation(value = "查询用户", notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true)
    @GetMapping("/api/menu/getInfoById/{id}")
    public ServerResponse<?> getInfoById(@PathVariable Integer id) {
        List<MenuDTO> menuDTOS=menuService.findByUserid(id);
        if(null==menuDTOS || menuDTOS.size()==0){
            return ServerResponse.createByErrorMessage("没有查到菜单数据");
        }
        return ServerResponse.createBySuccess(menuDTOS);
    }

    @ApiOperation(value = "删除菜单页面", notes = "删除菜单信息")
    @RequestMapping(value = "/api/menu/deleteByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="删除菜单页面", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> deleteByExample(@RequestParam("ids") List<Integer> menuIds) {
        int  count =menuService.deleteByExample(menuIds);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "添加菜单页面", notes = "添加菜单页面信息")
    @RequestMapping(value = "/api/menu/addInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    public ServerResponse<?> addInfo(@RequestBody MenuVo menuvo) {
        boolean isExistFlag=menuService.IsExistInfoName(menuvo.getMenuName());
        if(isExistFlag){
            return ServerResponse.createByErrorMessage("不能添加相同名称页面");
        }
        int  count =menuService.addInfo(menuvo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加菜单页面失败");
    }

    @Override
    @ApiOperation(value = "编辑菜单页面", notes = "编辑菜单页面信息")
    @RequestMapping(value = "/api/menu/updateByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    public ServerResponse<?> updateByExample(@RequestBody MenuVo menuvo) {
        int  count =menuService.updateByExampleSelective(menuvo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    @Override
    @ApiOperation(value = "查询菜单页面信息", notes = "根据参数条件分页查询")
    @RequestMapping(value = "/api/menu/getPageBySelective", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序

    public ServerResponse<?> getPageMenuBySelective(MenuVo menuvo, Integer pageSize, Integer pageNum) {
        CommonPage<MenuVo> list=menuService.getPageBySelective(menuvo,pageSize,pageNum);
        return  ServerResponse.createBySuccess(list);
    }
}
