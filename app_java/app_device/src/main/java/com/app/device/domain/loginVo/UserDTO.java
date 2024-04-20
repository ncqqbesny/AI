package com.app.device.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(value = "用户实体DTO")
public  class UserDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户类型：1、网络设备管理、2、器具柜设备管理",required = true)
    private  String userType;
    @ApiModelProperty(value = "用户名")
    private  String username;
    @ApiModelProperty(value = "姓名")
    private  String name;
    @ApiModelProperty(value = "状态 1正常，2停用")
    private  String userStatus;
    @ApiModelProperty(value = "邮箱")
    private  String email;
    @ApiModelProperty(value = "联系电话")
    private  String telephone;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "项目ID")
    private Integer mId;
    @ApiModelProperty(value = "微信用户ID")
    private String  openId;
    @ApiModelProperty(value = "昵称")
    private String  nickName;
    @ApiModelProperty(value = "微信头像url")
    private String  wxHeadurl;
    @ApiModelProperty(value = "微信统一ID")
    private String  unionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWxHeadurl() {
        return wxHeadurl;
    }

    public void setWxHeadurl(String wxHeadurl) {
        this.wxHeadurl = wxHeadurl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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
}