package com.soft.mapp.basecenter.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soft.mapp.basecenter.domain.Wechat.WxUserDTO;
import com.soft.mapp.basecenter.modules.system.CustomTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(value = "用户公司实体")
public  class UserInfoVo  extends WxUserDTO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    @CustomTag(desc = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "公司名称 必填项")
    @CustomTag(desc = "公司名称")
    private  String companyName;
    @ApiModelProperty(value = "地址")
    @CustomTag(desc = "地址")
    private  String address;
    @ApiModelProperty(value = "状态 1正常，0停用")
    @CustomTag(desc = "状态")
    private  String status;
    @ApiModelProperty(value = "联系电话")
    @CustomTag(desc = "联系电话,不能超过11位数字，校验为数字")
    private  String telephone;
    @ApiModelProperty(value = "邮箱，需要对邮箱进行校验")
    @CustomTag(desc = "邮箱")
    private  String email;
    @ApiModelProperty(value = "备注")
    @CustomTag(desc = "备注")
    private  String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}