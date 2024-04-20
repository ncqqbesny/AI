package com.app.device.impl.controller;

import com.app.device.utils.JedisUtil;
import com.app.device.utils.ListUtils;
import com.app.device.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.app.device.controller.IHardQueryScreenController;
import com.app.device.domain.queryScreen.DeviceAreaVo;
import com.app.device.domain.queryScreen.DeviceNameVo;
import com.app.device.domain.queryScreen.DeviceProvinceVo;
import com.app.device.domain.queryScreen.DeviceType;
import com.app.device.handler.CommonBusiness;
import com.app.device.services.IHardwareCabQueryService;
import com.app.device.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "数据大屏询接口")//描述UserController的信息
@RequestMapping("/dataScreen")
@ApiSort(value = 5)
public class HardQueryScreenController implements IHardQueryScreenController {

    @Autowired
    IHardwareCabQueryService hardwareCabQueryService;
    private final static Logger log = LoggerFactory.getLogger(HardQueryScreenController.class);


    @Override
    @ApiOperation(value = "获得设备分布数据", notes = "获得不同省的设备分布数据")
    @RequestMapping(value = "/getProvinceDeviceNum", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mId", value = "用户绑定的项目id", paramType = "Query", dataType = "Integer" ,required = true)
    })
    @ResponseBody
    @ApiOperationSupport(order = 1) //排序
    public ServerResponse<?> getProvinceDeviceNum(Integer mId)  {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localMacAddress =inetAddress.getHostName();
            List<DeviceProvinceVo>  list= JedisUtil.getList(localMacAddress+"_deviceProvince",DeviceProvinceVo.class);
            if(!CommonBusiness.isAdmin()) {
                CollectionUtils.filter(list, deviceProvinceVo -> {
                    if (deviceProvinceVo.getMId() == mId) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
            return ServerResponse.createBySuccess(list);
        }catch (IOException e){
            return ServerResponse.createByErrorMessage("数据读取错误");
        }
    }

    @Override
    @ApiOperation(value = "获得设备地图数据", notes = "获得最小单位区的设备数量")
    @RequestMapping(value = "/getAreaDeviceNum", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mId", value = "用户绑定的项目id", paramType = "Query", dataType = "Integer",required = true)
    })
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getAreaDeviceNum(Integer mId)  {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localMacAddress =inetAddress.getHostName();
            List<DeviceAreaVo>  list=JedisUtil.getList(localMacAddress+"_deviceArea",DeviceAreaVo.class);
            if(!CommonBusiness.isAdmin()) {
                CollectionUtils.filter(list, deviceAreaVo -> {
                    if (deviceAreaVo.getMId() == mId) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
            return ServerResponse.createBySuccess(list);
        }catch (IOException e){
            return ServerResponse.createByErrorMessage("数据读取错误");
        }

    }

    @Override
    @ApiOperation(value = "获得在线和离线状态数量", notes = "获得所有设备（网络设备、器具柜设备）在线和离线状态数量")
    @RequestMapping(value = "/getStatusDeviceNum", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mId", value = "用户绑定的项目id", paramType = "Query", dataType = "Integer",required = true)
    })
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> getStatusDeviceNum(Integer mId) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localMacAddress =inetAddress.getHostName();
            Map<String,Object> map=JedisUtil.getMap(localMacAddress+"_lineStatus",String.class,Object.class);
            return ServerResponse.createBySuccess(map);
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("数据读取错误");
        }

    }
    @Override
    @ApiOperation(value = "获得设备类型名称数量", notes = "获得设备类型名称数量的由大到小排名")
    @RequestMapping(value = "/getDeviceTypeNum", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mId", value = "用户绑定的项目id", paramType = "Query", dataType = "Integer",required = true)
    })
    @ResponseBody
    @ApiOperationSupport(order = 4) //排序
    public ServerResponse<?> getDeviceTypeNum(Integer mId) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localMacAddress =inetAddress.getHostName();
            List<DeviceType>  list=JedisUtil.getList(localMacAddress+"_deviceType",DeviceType.class);
            if(!CommonBusiness.isAdmin()) {
                CollectionUtils.filter(list, item -> {
                    if (item.getMId() == mId) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
            return ServerResponse.createBySuccess(list);
        }catch (IOException e){
            return ServerResponse.createByErrorMessage("数据读取错误");
        }
    }


    @Override
    @ApiOperation(value = "条件查询最新设备的状态和位置", notes = "设备最新的更新的状态和位置,参数控制显示记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "displayLine", value = "多少条数据", paramType = "Query", dataType = "String"),
            @ApiImplicitParam(name = "mId", value = "用户绑定的项目id", paramType = "Query", dataType = "Integer",required = true)
    })
    @RequestMapping(value = "/getDeviceAreaStatus", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 5) //排序
    public ServerResponse<?> getDeviceAreaStatus(Integer displayLine,Integer mId) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localMacAddress =inetAddress.getHostName();
            List<DeviceNameVo>  list=JedisUtil.getList(localMacAddress+"_deviceName",DeviceNameVo.class);
            ListUtils.sortList(list, "updateTime", false);
            if(!CommonBusiness.isAdmin()) {
                CollectionUtils.filter(list, item -> {
                    if (item.getMId() == mId) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
            if(list==null || list.size()==0){
                return ServerResponse.createByErrorMessage("没有数据");
            }
            List<DeviceNameVo> newList = list.subList(0, displayLine);
            return ServerResponse.createBySuccess(newList);
        }catch (IOException e){
            return ServerResponse.createByErrorMessage("数据读取错误");
        }

    }

}
