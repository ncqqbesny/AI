package com.soft.mapp.basecenter.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "项目实体")
public  class MerchantVo {

    @ApiModelProperty(value = "项目id")
    private Integer  mId;
    @ApiModelProperty(value = "用户编号" )
    private String  mCode;
    @ApiModelProperty(value = "父项目ID")
    private Integer  parentMid;
    @ApiModelProperty(value = "是否有父项目（0无，1有）")
    private Integer  isParent;
    @ApiModelProperty(value = "用户id" )
    private String  userId;
    @ApiModelProperty(value = "名称" )
    private String  mName;
    @ApiModelProperty(value = "所属用户姓名" )
    private String  mUserName;
    @ApiModelProperty(value = "接入设备设备,qjg(器具柜），ac(网络管理）")
    private String  device;
    @ApiModelProperty(value = "banner")
    private String  mBanner;
    @ApiModelProperty(value = "余额")
    private BigDecimal mBalance;
    @ApiModelProperty(value = "logo")
    private String  mLogo;
    @ApiModelProperty(value = "描述")
    private String  mDesc;
    @ApiModelProperty(value = "状态（1正常，2删除）",required = true)
    private String  mStatus;
    @ApiModelProperty(value = "地址")
    private String  address;
    @ApiModelProperty(value = "微信小程序二维码")
    private String  wxSmallQRCodeUrl;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private String  createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private String  updateTime;
    @ApiModelProperty(value = "子项目")
    private List<MerchantPo> children;
    @ApiModelProperty(value = "设备类型")
    private Map<String,String> deviceType;
}