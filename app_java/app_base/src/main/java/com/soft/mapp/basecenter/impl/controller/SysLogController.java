package com.soft.mapp.basecenter.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.soft.mapp.basecenter.controller.ISysLogController;
import com.soft.mapp.basecenter.domain.SysLog.SysLogPo;
import com.soft.mapp.basecenter.domain.SysLog.SysLogVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.ISysLogService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.soft.mapp.basecenter.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "系统日志接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class SysLogController implements ISysLogController {
    @Autowired
    ISysLogService sysLogService;
    //99020147	删除操作日志
    @Override
    @ApiOperation(value = "删除操作日志", notes = "99020147:主柜《标签、操作、报警日志》-删除操作日志")
    @RequestMapping(value = "/delLogInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> delLogInfo(@RequestBody List<String> ids) {
        String msg = sysLogService.delLogInfo(ids);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @ApiOperation(value = "添加操作日志", notes = "990201470:主柜《标签、操作、报警日志》-添加操作日志")
    @Override
    @RequestMapping(value = "/addLogInfo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<?> addLogInfo(@RequestBody SysLogPo sysLogPo) {
        String msg = sysLogService.addLogInfo(sysLogPo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
    @Override
    @ApiOperation(value = "获取日志列表", notes = "990202146:获取标签、操作、报警日志主柜《标签、操作、报警日志》")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页数量", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序 1、升序，0，降序", required = true, paramType = "Query", dataType = "Integer")
    })
    @RequestMapping(value = "/getLogInfo", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<?> getLogInfo(Integer pageSize, Integer pageNum, Integer sort, SysLogVo sysLogVo) {
        CommonPage<SysLogPo> list = sysLogService.getLogInfo(pageSize, pageNum, sort, sysLogVo);
        return ServerResponse.createBySuccess(list);
    }

}
