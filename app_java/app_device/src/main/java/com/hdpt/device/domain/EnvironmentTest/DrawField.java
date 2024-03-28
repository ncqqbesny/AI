package com.hdpt.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.*;

@Data
public class DrawField {
    @ApiModelProperty(value = "名称")
    private String key;
    @ApiModelProperty(value = "值")
    private  String value;
    @ApiModelProperty(value = "颜色")
    private Point point;
    @ApiModelProperty(value = "类别")
    private  String type;
    @ApiModelProperty(value = "类别")
    private  Integer qrCodeSize;
    @ApiModelProperty(value = "类别")
    private  Integer qrCodeX;
    @ApiModelProperty(value = "类别")
    private  Integer qrCodeY;
    @ApiModelProperty(value = "类别")
    private  String qrCodeDesc;
    @ApiModelProperty(value = "类别")
    private  String qrCodeContent;




}
