package com.soft.mapp.basecenter.impl.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.soft.mapp.basecenter.controller.IUserInfoController;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IUserInfoService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "用户公司接口") //描述UserController的信息
@RequestMapping("/hdptbase")
public class UserInfoController implements IUserInfoController {
    private static Logger log = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    IUserInfoService userInfoService;
    @Override
    @ApiOperation(value = "用户ID查询公司信息", notes = "根据用户ID查询公司信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true)
    @GetMapping("/api/userInfo/query/{id}")
    @AutoLog(value = "用户ID查询公司信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?>  getInfoByUserId(@PathVariable Integer id) {
        List<UserInfoDTO> userInfoDTOList=userInfoService.selectByuserId(id);
        if(null==userInfoDTOList || userInfoDTOList.size()==0 ){
            return ServerResponse.createByErrorMessage("没有查到相应的数据");
        }
         return ServerResponse.createBySuccess(userInfoDTOList);
    }
    @Override
    @ApiOperation(value = "删除用户", notes = "删除公司相关信息")
    @RequestMapping(value = "/api/userInfo/deleteByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="删除用户", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> deleteInfoById(@RequestParam("ids") List<Integer> ids) {
        int  count =userInfoService.deleteByExample(ids);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "添加用户公司", notes = "添加一个用户和相关公司信息")
    @RequestMapping(value = "/api/userInfo/addUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    @AutoLog(value ="添加用户公司", operateType = 1, logType = 1,paramType = 0)
    public ServerResponse<?> addUserInfo(@RequestBody UserInfoVo userInfoVo) {
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        BeanUtil.copyProperties(userInfoVo,userInfoDTO);
        int count = userInfoService.addInfo(userInfoDTO);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加公司失败");
    }

    @Override
    @ApiOperation(value = "修改用户公司", notes = "根据传入ID的修改公司信息")
    @RequestMapping(value = "/api/userInfo/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    @AutoLog(value ="修改用户公司", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> updateInfo(@RequestBody UserInfoVo userInfoVo) {
        log.info("修改用户公司："+userInfoVo);
        int count = userInfoService.updateByExampleSelective(userInfoVo);
        if (count > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("修改用户公司失败");
    }
    @Override
    @ApiOperation(value = "查询公司列表信息", notes = "根据传入的用户信息分页查询公司信息")
    @RequestMapping(value = "/api/userInfo/getPageByInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序
    @AutoLog(value = "查询公司列表信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getPageByInfo(UserInfoVo userInfoVo, Integer pageSize, Integer pageNum) {
        log.info("查询公司列表："+userInfoVo);
        CommonPage<UserInfoDTO> list = userInfoService.getPageUser(userInfoVo, pageSize, pageNum);
        return ServerResponse.createBySuccess(list);
    }
}
