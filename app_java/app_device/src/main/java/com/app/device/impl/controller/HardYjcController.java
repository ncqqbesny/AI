package com.app.device.impl.controller;

import com.app.device.controller.IHardYjcController;
import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.services.IHardwareYjcService;
import com.app.device.type.OpTypeEnum;
import com.app.device.utils.CommonPage;
import com.app.device.utils.ServerResponse;
import com.app.device.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
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
@Api(tags = "应急仓操作接口")//描述UserController的信息
@RequestMapping("/yjc")
@ApiSort(value = 5)
public class HardYjcController implements IHardYjcController {

    @Autowired
    IHardwareYjcService hardwareYjcService;
    private final static Logger log = LoggerFactory.getLogger(HardYjcController.class);
    @Override
    @ApiOperation(value = "硬件操作", notes = "按点按制编号操作硬件，具体参照点位配置表")
    @RequestMapping(value = "/operBitCtrl", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> operBitCtrl(@RequestBody List<BitCtrlVo> param) {
        String msg = hardwareYjcService.saveBitCtrl(param, OpTypeEnum.manual.name());
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "保存点位控制信息", notes = "批量添加和编辑点位控制信息")
    @RequestMapping(value = "/saveBitCtrlListInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 8) //排序
    public ServerResponse<?> saveListInfo(@RequestBody  List<BitCtrlVo> bitCtrlVos) {
        String msg = hardwareYjcService.saveListInfo(bitCtrlVos);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "删除点位控制信息", notes = "按id批量删除点位控制信息")
    @RequestMapping(value = "/delBitCtrlInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> delInfo(@RequestBody List<Integer> ids) {
        String msg = hardwareYjcService.delInfo(ids);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "分页获得位控制信息", notes = "分页查询获得位控制信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页数量", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "Query", dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序 1，升序，0，降序", required = true, paramType = "Query", dataType = "Integer")
    })
    @RequestMapping(value = "/getBitCtrlPageList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    public ServerResponse<?> getBitCtrlPageList(Integer pageSize, Integer pageNum, Integer sort, BitCtrlVo bitCtrlVo) {
        CommonPage<BitCtrlDTO> list = hardwareYjcService.getBitCtrlPageList(pageSize,pageNum,sort,bitCtrlVo);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    @ApiOperation(value = "获得仓操作返回信息", notes = "获得仓操作返回信息")
    @RequestMapping(value = "/getBitCtrlList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    public ServerResponse<?> getBitCtrlList(BitCtrlVo bitCtrlVo) {
        if(StringUtil.isEmpty(bitCtrlVo.getDeviceSn())){
            return ServerResponse.createByErrorMessage("参数没有输入设备编号或序列号");
        }
        return  ServerResponse.createBySuccess(hardwareYjcService.getBitCtrlList(bitCtrlVo));
    }

    @ApiOperation(value = "应急联测试", notes = "获得仓操作返回信息")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    public ServerResponse<?> test(BitCtrlVo bitCtrlVo) {
          /* Map<String,String> htMap=new HashMap<>();
        htMap.put("cabinetNo","Cab1");
        htMap.put("stationNo","1");
        htMap.put("devType","1");
        htMap.put("addr","1");
        htMap.put("humidity", ConvertUtils.toString(82.9));
        htMap.put("temperature",ConvertUtils.toString(90.9));
        String msg=hardwareService.handleYjcAi(htMap,"%H%");
        msg=hardwareService.handleYjcAi(htMap,"%T%");*/
        //String msg=hardwareYjcService.batchTempDiDoLogic();
        log.info("test info 开始==");
        //log.error("test error 错误===");
        //String msg=faceService.delFaceLogRecords();
        //String msg=warningService.HandlerWarning();
        //QueueHolder.get().offer(NettyMsgModel.create("87654321", "Hello World!"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //QueueHolder.get().offer(NettyMsgModel.create("87654321", "Hello World Too!"));
        return ServerResponse.createBySuccess("msg");
    }
}
