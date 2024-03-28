package com.hdpt.device.domain.Device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "灭火器上传信息实体")
public class FireUpVo {
    @ApiModelProperty(value = "设备序列号")
    private String serialNo     ;
    @ApiModelProperty(value = "环境温度")
    private String temperature     ;
    @ApiModelProperty(value = "环境湿度")
    private String humidity     ;
    @ApiModelProperty(value = "倾斜角度")
    private String angle     ;
    @ApiModelProperty(value = "模块电量")
    private String power     ;
    @ApiModelProperty(value = "地理位置信息")
    private String geo     ;
    @ApiModelProperty(value = "表头图片")
    private String img     ;
    @ApiModelProperty(value = "采集时间")
    private String collectTime     ;
    @ApiModelProperty(value = "通讯心跳")
    private String keepalive     ;

}
