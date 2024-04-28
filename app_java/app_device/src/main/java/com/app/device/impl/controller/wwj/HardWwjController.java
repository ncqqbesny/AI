package com.app.device.impl.controller.wwj;

import com.app.device.controller.wwj.IHardWwjController;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.services.wwj.IHardwareWwjService;
import com.app.device.utils.ServerResponse;
import com.app.device.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = "业务WW操作接口")//描述UserController的信息
@RequestMapping("/wwj")
@ApiSort(value = 5)
public class HardWwjController implements IHardWwjController {

    @Autowired
    IHardwareWwjService hardwareWwjService;
    private final static Logger log = LoggerFactory.getLogger(HardWwjController.class);


    @ApiOperation(value = "WWJ测试", notes = "wwj测试")
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

    @Override
    @ApiOperation(value = "打开端口", notes = "按点按制编号操作硬件，具体参照点位配置表")
    @RequestMapping(value = "/openHardPort", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> openHardPort() {
        String msg=hardwareWwjService.openHardPort();
        return ServerResponse.createBySuccess(msg);
    }

    @Override
    @ApiOperation(value = "接收数据并处理", notes = "查看接收数据并进行处理")
    @RequestMapping(value = "/revPortInfoAndProc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order = 30) //排序
    public ServerResponse<?> getPortInfoAndProc() {
        Map<String,Object> map=hardwareWwjService.getPortInfoAndProc();
        return ServerResponse.createBySuccess(map);
    }

    @Override
    @ApiOperation(value = "硬件控制打开与关闭", notes = "打开与控制硬件操作")
    @RequestMapping(value = "/openAndCloseHard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> openAndCloseHard(Integer cent) {
        String msg=hardwareWwjService.openAndCloseHard(cent);
        if(StringUtil.isNotEmpty(msg)){
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
}
