package com.hdpt.device.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.hdpt.device.controller.IHardCabQueryController;
import com.hdpt.device.domain.Cabinet.CabinetDTO;
import com.hdpt.device.domain.Cabinet.CabinetQueryVo;
import com.hdpt.device.domain.Cabinet.CabinetVo;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.domain.system.AutoLog;
import com.hdpt.device.services.IHardwareCabQueryService;
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

@RestController
@Api(tags = "器具柜查询接口")//描述UserController的信息
@RequestMapping("/qjg")
@ApiSort(value = 5)
public class HardCabQueryController implements IHardCabQueryController {

    @Autowired
    IHardwareCabQueryService hardwareCabQueryService;
    private final static Logger log = LoggerFactory.getLogger(HardCabQueryController.class);


    @Override
    @ApiOperation(value = "分页获得器具柜设备信息", notes = "根据查询条件分页获得器具柜数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页数量", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序 0，降序，1，升序", required = true, paramType = "Query", dataType = "Integer")
    })
    @RequestMapping(value = "/getPageCabList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    @AutoLog(value = "分页获得器具柜设备信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getPageCabList(Integer pageSize, Integer pageNum, Integer sort, CabinetQueryVo cabinetQueryVo) {
        CommonPage<CabinetVo> list = hardwareCabQueryService.getPageCabList(pageSize, pageNum, sort,cabinetQueryVo);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    @ApiOperation(value = "ID获得器具柜信息", notes = "根批量id获得器具柜信息")
    @RequestMapping(value = "/getCabListById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "批量id编号", paramType = "Query", dataType = "String")
    })
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> getCabListByIds(@RequestParam("ids") List<Integer> ids) {
        return ServerResponse.createBySuccess(hardwareCabQueryService.selectByIds(ids));
    }

    @Override
    @ApiOperation(value = "项目获得器具柜信息", notes = "根批量项目id获得器具柜信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mIds", value = "批量项目id编号", paramType = "Query", dataType = "String")
    })
    @RequestMapping(value = "/getCabListByMids", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getCabListByMids(@RequestParam("mIds") List<Integer> mIds) {
        return ServerResponse.createBySuccess(hardwareCabQueryService.selectByMids(mIds));
    }

    @Override
    @ApiOperation(value = "条件获得器具柜信息", notes = "根据查询条件获得器具柜信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "NetDeviceVo", value = "查询条件", paramType = "Query", dataType = "String")
    })
    @RequestMapping(value = "/getCabList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 2) //排序
    public ServerResponse<?> getCabList(CabinetQueryVo cabinetQueryVo) {
        return  ServerResponse.createBySuccess(hardwareCabQueryService.findList(cabinetQueryVo));
    }

}
