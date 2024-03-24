package com.soft.mapp.basecenter.impl.controller;

import com.soft.mapp.basecenter.controller.ISysParamController;
import com.soft.mapp.basecenter.domain.sysParam.SysPramVo;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.ISysParamService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "系统管理接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class SysParamController implements ISysParamController {
    @Autowired
    ISysParamService sysParamService;
    @Override
    @ApiOperation(value = "类型查询参数", notes = "根据参数类型查询参数据")
    @PostMapping("/api/sysparam/getSysParamByType")
    @AutoLog(value = "类型查询参数", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getSysParamByType(@RequestParam(value = "paramType") String paramType) {
        List<SysPramVo> sysParamVos =sysParamService.getSysParamByType(paramType);
        return  ServerResponse.createBySuccess(sysParamVos);
    }

    @Override
    @ApiOperation(value = "类型和编号参数查询", notes = "根据参数类型和编号查询")
    @PostMapping("/api/sysparam/getSysParamByTypeAndCode")
    @AutoLog(value = "类型和编号参数查询", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getSysParamByTypeAndCode(@RequestParam("paramType") String paramType, @RequestParam("paramCode") String  paramCode) {
        List<SysPramVo> sysParamVos =sysParamService.getSysParamByTypeAndCode(paramType,paramCode);
        return  ServerResponse.createBySuccess(sysParamVos);
    }

}
