package com.app.device.impl.controller;

import com.app.device.controller.ISmartDeviceQueryController;
import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.system.AutoLog;
import com.app.device.services.IDeviceExtendService;
import com.app.device.utils.CommonPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.app.device.services.IDeviceService;
import com.app.device.services.INetDeviceQueryService;
import com.app.device.utils.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Slf4j
@RestController
@Api(tags = "智能设备查询接口")//描述UserController的信息
@RequestMapping("/smartdevice")
public class SmartDeviceQueryController implements ISmartDeviceQueryController {
    private final static Logger log = LoggerFactory.getLogger(SmartDeviceQueryController.class);
    @Autowired
    IDeviceExtendService deviceExtendService;
    @Autowired
    IDeviceService deviceService;

    @Override
    @ApiOperation(value = "分页获得智能设备信息", notes = "分页查询获得智能设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页数量", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序 1，升序，0，降序", required = true, paramType = "Query", dataType = "Integer")
    })
    @RequestMapping(value = "/getSmartDevicePageList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    @AutoLog(value = "分页获得智能设备信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getSmartDevicePageList(Integer pageSize, Integer pageNum, Integer sort, DeviceVo deviceVo) {
        CommonPage<?> list=new CommonPage<>();
        try {
            list = deviceService.getPageList(pageSize, pageNum, sort, deviceVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    @ApiOperation(value = "GID获得智能设备信息", notes = "根批量Gid获得智能设备信息")
    @RequestMapping(value = "/getSmartDeviceListByGids", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gids", value = "批量id编号", paramType = "Query", dataType = "String")
    })
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> getSmartDeviceListByGids(@RequestParam("gids") List<String> gids) {
        try {
            return ServerResponse.createBySuccess(deviceService.getSmartDeviceListByGids(gids));
        } catch (Exception e) {
            log.info("程序执行异常===="+e);
            return ServerResponse.createByErrorMessage("程序执行异常");
        }
    }

    @Override
    @ApiOperation(value = "项目获得智能设备信息", notes = "根批量项目id获得智能设备信息")
    @RequestMapping(value = "/getSmartDeviceListByMids", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mIds", value = "批量项目id编号(必填项）", paramType = "Query", dataType = "String", required = false),
            @ApiImplicitParam(name = "deviceTypeCode", value = "设备类型编号，详细可查设备类型表，例如0101 ", paramType = "Query", dataType = "String", required = false)

    })
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getSmartDeviceListByMids(@RequestParam(value="mIds",required=true) List<String> mIds,@RequestParam(value="deviceTypeCode",required=false) List<String> deviceTypeCodes) {
        try {
            return ServerResponse.createBySuccess(deviceService.getSmartDeviceListByMids(mIds,deviceTypeCodes));
        } catch (Exception e) {
            log.info("getSmartDeviceListByMids程序执行异常===="+e);
            return ServerResponse.createByErrorMessage("程序执行异常");
        }
    }

    @Override
    @ApiOperation(value = "条件获得智能设备信息", notes = "根据查询条件获得智能设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SmartDeviceVo", value = "查询条件", paramType = "Query", dataType = "String")
    })
    @RequestMapping(value = "/getSmartDeviceList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getSmartDeviceList(DeviceVo deviceVo) {
        try {
            return ServerResponse.createBySuccess(deviceService.getSmartDeviceListBySearch(deviceVo));
        } catch (Exception e) {
            log.info("getSmartDeviceList程序执行异常===="+e);
            return ServerResponse.createByErrorMessage("程序执行异常");
        }
    }
}
