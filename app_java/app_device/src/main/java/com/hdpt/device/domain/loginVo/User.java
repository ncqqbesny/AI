package com.hdpt.device.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "用户实体基础")
public  class User {

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private  String username;
    @ApiModelProperty(value = "姓名")
    private  String name;
    @ApiModelProperty(value = "项目ID")
    private Integer mId;
    @ApiModelProperty(value = "邮箱")
    private  String email;
    @ApiModelProperty(value = "用户类型：1、网络设备管理、2、器具柜设备管理",required = true)
    private  String userType;
    @ApiModelProperty(value = "联系电话")
    private  String telephone;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "状态，1正状，0停止")
    private String userStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date update_time;
    @ApiModelProperty(value = "ip")
    private  String ip;
    @ApiModelProperty(value = "微信用户ID")
    private String  openId;
    @ApiModelProperty(value = "昵称")
    private String  nickName;
    @ApiModelProperty(value = "微信头像url")
    private String  wxHeadurl;
    @ApiModelProperty(value = "微信统一ID")
    private String  unionId;
    private  String url;
    private Long total;
    private List<Role> roles;

}