package com.soft.mapp.basecenter.handler;

import com.soft.mapp.basecenter.controller.ITaskController;
import com.soft.mapp.basecenter.domain.task.OrdersVo;
import com.soft.mapp.basecenter.services.ITaskService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "任务接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class TaskController implements ITaskController {
    @Autowired
    ITaskService taskService;

    @ApiOperation(value = "查询任务", notes = "根据id查询任务")
    @ApiImplicitParam(paramType = "path", name = "id", value = "任务id", required = true)
    @GetMapping("/api/task/{id}")
    public String getInfoById(Integer id) {
        return null;
    }

    @ApiOperation(value = "删除任务", notes = "删除任务信息")
    @RequestMapping(value = "/api/task/deleteByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    public ServerResponse<?> deleteByExample(@RequestParam("ids")  List<Integer> taskIds) {
        int  count =taskService.deleteByExample(taskIds);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "添加任务", notes = "添加任务信息")
    @RequestMapping(value = "/api/task/addInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    public ServerResponse<?> addInfo(@RequestBody OrdersVo taskVo) {
        int  count =taskService.addInfo(taskVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("下发任务失败");
    }

    @Override
    @ApiOperation(value = "编辑任务", notes = "编辑任务信息")
    @RequestMapping(value = "/api/task/updateByExample", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=3) //排序
    public ServerResponse<?> updateByExample(@RequestBody OrdersVo taskVo) {
        int  count =taskService.updateByExampleSelective(taskVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "查询任务信息", notes = "根据参数条件分页查询")
    @RequestMapping(value = "/api/task/getPageBySelective", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序
    public ServerResponse<?> getPageBySelective(OrdersVo taskVo, Integer pageSize, Integer pageNum) {
        CommonPage<OrdersVo> list=taskService.getPageBySelective(taskVo,pageSize,pageNum);
        return  ServerResponse.createBySuccess(list);
    }
}
