package com.hdpt.device.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.hdpt.device.controller.IHardCabController;
import com.hdpt.device.domain.Cabinet.CabinetSaveVO;
import com.hdpt.device.domain.system.AutoLog;
import com.hdpt.device.services.IHardwareCabService;
import com.hdpt.device.utils.ServerResponse;
import com.hdpt.device.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "器具柜接入接口")//描述UserController的信息
@RequestMapping("/qjg")
@ApiSort(value = 5)
public class HardCabController implements IHardCabController {

    @Autowired
    IHardwareCabService hardwareCabService;
    private final static Logger log = LoggerFactory.getLogger(HardCabController.class);


    //990202143	保存IP地址	保存IP地址	主柜《系统配置》
    @Override
    @ApiOperation(value = "保存器具柜相应信息", notes = "添加和编辑器具柜信息")
    @RequestMapping(value = "/saveCabinetInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    @AutoLog(value ="保存器具柜相应信息", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> saveCabinet(@RequestBody CabinetSaveVO cabinetSaveVO) {
        String msg = hardwareCabService.saveCabinet(cabinetSaveVO);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "删除器具柜信息", notes = "按id批量删除器具柜信息")
    @RequestMapping(value = "/delLogInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    @AutoLog(value ="删除器具柜信息", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> delCabInfo(@RequestBody List<Integer> ids) {
        String msg = hardwareCabService.delInfo(ids);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

}
