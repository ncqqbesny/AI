package com.soft.mapp.basecenter.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "项目实体")
public  class MerchantPo implements Serializable {

    @ApiModelProperty(value = "项目id")
    private Integer  mId;
    @ApiModelProperty(value = "父项目ID")
    private Integer  parentMid;
    @ApiModelProperty(value = "是否为父项目（0无，1有）")
    private Integer  isParent;
    @ApiModelProperty(value = "项目编号" )
    private String  mCode;
    @ApiModelProperty(value = "名称")
    private String  mName;
    @ApiModelProperty(value = "接入设备")
    private String  device;
    @ApiModelProperty(value = "banner")
    private String  mBanner;
    @ApiModelProperty(value = "余额")
    private BigDecimal mBalance;
    @ApiModelProperty(value = "logo")
    private String  mLogo;
    @ApiModelProperty(value = "描述")
    private String  mDesc;
    @ApiModelProperty(value = "状态")
    private String  mStatus;
    @ApiModelProperty(value = "地址")
    private String  address;
    @ApiModelProperty(value = "微信小程序二维码")
    private String  wxSmallQRCodeUrl;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private String  createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private String  updateTime;
    @ApiModelProperty(value = "功能Id")
    private String  funcListId;

}