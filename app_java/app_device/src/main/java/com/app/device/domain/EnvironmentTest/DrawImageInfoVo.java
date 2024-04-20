package com.app.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DrawImageInfoVo {
    @ApiModelProperty(value = "设备编号--序列号")
    private String deviceSn;
    @ApiModelProperty(value = "名称")
    private String sceneName;
    @ApiModelProperty(value = "角色")
    private String administrator;
    @ApiModelProperty(value = "二维码URL")
    private String qrCodeUrl;

    @ApiModelProperty(value = "是否报警")
    private boolean isAlarm;
    @ApiModelProperty(value = "数据明细信息")
    private List<SceneItemDeviceDataVO> deviceDataVoList;

}
