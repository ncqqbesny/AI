package com.hdpt.device.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hdpt.device.controller.INetDeviceController;
import com.hdpt.device.controller.INetDeviceQueryController;
import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.domain.system.AutoLog;
import com.hdpt.device.services.INetDeviceQueryService;
import com.hdpt.device.services.INetDeviceService;
import com.hdpt.device.utils.CommonPage;
import com.hdpt.device.utils.ServerResponse;
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
@Api(tags = "网络设备查询接口")//描述UserController的信息
@RequestMapping("/hdptdevice")
public class NetDeviceQueryController implements INetDeviceQueryController {
    private final static Logger log = LoggerFactory.getLogger(NetDeviceQueryController.class);
    @Autowired
    INetDeviceQueryService netDeviceQueryService;

    @Override
    @ApiOperation(value = "分页获得网络设备信息", notes = "分页查询获得网络设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页数量", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序 1，升序，0，降序", required = true, paramType = "Query", dataType = "Integer")
    })
    @RequestMapping(value = "/getNetDevicePageList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    @AutoLog(value = "分页获得网络设备信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getNetDevicePageList(Integer pageSize, Integer pageNum, Integer sort, NetDeviceVo netDeviceVo) {
        CommonPage<NetDeviceDTO> list = netDeviceQueryService.getPageList(pageSize, pageNum, sort, netDeviceVo);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    @ApiOperation(value = "ID获得网络设备信息", notes = "根批量id获得网络设备信息")
    @RequestMapping(value = "/getNetDeviceListById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "批量id编号", paramType = "Query", dataType = "String")
    })
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> getNetDeviceListByIds(@RequestParam("ids") List<Integer> ids) {
        return ServerResponse.createBySuccess(netDeviceQueryService.selectByIds(ids));
    }

    @Override
    @ApiOperation(value = "项目获得网络设备信息", notes = "根批量项目id获得网络设备信息")
    @RequestMapping(value = "/getNetDeviceListByMids", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mIds", value = "批量项目id编号(必填项）", paramType = "Query", dataType = "String", required = false),
            @ApiImplicitParam(name = "deviceTypes", value = "设备类型集合(1.无线接入控制器、2.无线AP,3.4G路由网关,4.5G路由网关，6灭火器,7终端)", paramType = "Query", dataType = "String", required = false)

    })
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getNetDeviceListByMids(@RequestParam(value="mIds",required=true) List<Integer> mIds,@RequestParam(value="deviceTypes",required=false) List<String> deviceTypes) {
        return ServerResponse.createBySuccess(netDeviceQueryService.selectByMids(mIds,deviceTypes));
    }

    @Override
    @ApiOperation(value = "条件获得网络设备信息", notes = "根据查询条件获得网络设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "NetDeviceVo", value = "查询条件", paramType = "Query", dataType = "String")
    })
    @RequestMapping(value = "/getNetDeviceList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getNetDeviceList(NetDeviceVo netDeviceVo) {
        return ServerResponse.createBySuccess(netDeviceQueryService.findList(netDeviceVo));
    }
}
