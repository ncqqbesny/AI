package com.soft.mapp.basecenter.impl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.soft.mapp.basecenter.controller.IMerchantController;
import com.soft.mapp.basecenter.domain.merchant.MerchantVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.modules.system.AutoLog;
import com.soft.mapp.basecenter.services.IMerchantService;
import com.soft.mapp.basecenter.utils.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "项目管理接口")//描述UserController的信息
@RequestMapping("/hdptbase")
public class MerchantController implements IMerchantController {
    @Autowired
    IMerchantService merchantService;

    @Override
    @ApiOperation(value = "分页查询项目信息", notes = "根据参数条件分页查询")
    @RequestMapping(value = "/api/merchant/getPageMerchant", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=1) //排序
    @AutoLog(value = "分页查询项目信息", operateType = 2, logType = 1,paramType = 2)
    public ServerResponse<?> getPageMerchant(MerchantVo merchantVo, Integer pageSize, Integer pageNum) {
        CommonPage<MerchantVo> list=merchantService.getPageMerchant(merchantVo,pageSize,pageNum);
        return  ServerResponse.createBySuccess(list);
    }

    @Override
    @ApiOperation(value = "添加项目", notes = "添加项目信息")
    @RequestMapping(value = "/api/merchant/addMerchant", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    public ServerResponse<?> addMerchant(@RequestBody MerchantVo merchantVo) {
        boolean isExistMerchant=merchantService.IsExistMerchanName(merchantVo.getMName());
        if(isExistMerchant){
            return ServerResponse.createByErrorMessage("不能添加相同名称项目");
        }
        int  count =merchantService.addMerchant(merchantVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加项目失败");
    }

    @Override
    @ApiOperation(value = "编辑项目", notes = "编辑项目信息")
    @RequestMapping(value = "/api/merchant/updateMerchant", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    @AutoLog(value ="编辑项目", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> updateMerchant(@RequestBody MerchantVo merchantVo) {
        int  count =merchantService.updateMerchant(merchantVo);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @ApiOperation(value = "删除项目", notes = "删除项目信息")
    @RequestMapping(value = "/api/merchant/deleteMerchant", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order=2) //排序
    @AutoLog(value ="删除项目", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> deleteMerchant(@RequestParam("mids") List<Integer> mids) {
        int  count =merchantService.deleteMerchant(mids);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    @ApiOperation(value = "查询项目信息", notes = "根据参数条件查询所有项目信息")
    @RequestMapping(value = "/api/merchant/findList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperationSupport(order=5) //排序
    public ServerResponse<?> findList(MerchantVo merchantVo) {
        List<MerchantVo> list=merchantService.findList(merchantVo);
        return  ServerResponse.createBySuccess(list);
    }
}
