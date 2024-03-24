package com.soft.mapp.basecenter.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;


@ApiModel(value = "项目实体")
public  class MerchantDTO implements Serializable {

    @ApiModelProperty(value = "项目id")
    private Integer  mId;
    @ApiModelProperty(value = "父项目ID")
    private Integer  parentMid;
    @ApiModelProperty(value = "是否为父项目（0无，1有）")
    private Integer  isParent;
    @ApiModelProperty(value = "项目编号")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private String  createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private String  updateTime;
    @ApiModelProperty(value = "功能Id")
    private String  funcListId;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getParentMid() {
        return parentMid;
    }

    public void setParentMid(Integer parentMid) {
        this.parentMid = parentMid;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getmBanner() {
        return mBanner;
    }

    public void setmBanner(String mBanner) {
        this.mBanner = mBanner;
    }

    public BigDecimal getmBalance() {
        return mBalance;
    }

    public void setmBalance(BigDecimal mBalance) {
        this.mBalance = mBalance;
    }

    public String getmLogo() {
        return mLogo;
    }

    public void setmLogo(String mLogo) {
        this.mLogo = mLogo;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFuncListId() {
        return funcListId;
    }

    public void setFuncListId(String funcListId) {
        this.funcListId = funcListId;
    }

    public String getWxSmallQRCodeUrl() {
        return wxSmallQRCodeUrl;
    }

    public void setWxSmallQRCodeUrl(String wxSmallQRCodeUrl) {
        this.wxSmallQRCodeUrl = wxSmallQRCodeUrl;
    }
}