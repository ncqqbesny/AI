package com.app.device.domain.BitCtrl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "设备点位配置参数")
public class BitCtrlVo  extends BitCtrlDTO{
    @ApiModelProperty(value = "硬件地址List")
    private List<Integer> addresses;
    @ApiModelProperty(value = "控制类型List 0.DO,1.DI 2.温度，3湿度")
    private List<Integer> ctrlTypes;
    @ApiModelProperty(value = "控制数字List，例如1=DI1,2=net_deviceDO2")
    private List<Integer> ctrlNums;
    @ApiModelProperty(value = "信号位List")
    private List<String> signalingBits;

}
