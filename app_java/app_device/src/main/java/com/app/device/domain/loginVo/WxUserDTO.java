package com.app.device.domain.loginVo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@ApiModel(value = "微信用户实体DTO")
@Data
@TableName("wx_user")
public  class WxUserDTO {
    @ApiModelProperty(value = "用户id")
    @TableField(value = "wx_user_id")
    private Integer wxUserId;
    @ApiModelProperty(value = "昵称")
    @TableField(value = "nick_name")
    private String  nickName;
    @ApiModelProperty(value = "微信头像url")
    @TableField(value = "head_url")
    private String  wxHeadurl;
    @ApiModelProperty(value = "微信用户ID")
    @TableField(value = "open_id")
    private String  openId;
    @ApiModelProperty(value = "姓名")
    @TableField(value = "user_name")
    private  String userName;
    @ApiModelProperty(value = "手机号")
    @TableField(value = "phone")
    private  String phone;
    @ApiModelProperty(value = "名片")
    @TableField(value = "card_url")
    private  String cardUrl;
    @ApiModelProperty(value = "认证状态1认证通过2认证未通过3未认证4待审核")
    @TableField(value = "auth_state")
    private  String authState;
    @ApiModelProperty(value = "是否认证")
    @TableField(value = "is_auth")
    private  String isAuth;
    @ApiModelProperty(value = "是否关注公众号1是2否")
    @TableField(value = "is_follow")
    private  String isFollow;
    @ApiModelProperty(value = "项目名称")
    @TableField(value = "m_name")
    private String mName;
    @ApiModelProperty(value = "用户状态， 0 停用  1启用")
    @TableField(value = "status")
    private String status;
    @ApiModelProperty(value = "申请认证时间")
    @TableField(value = "apply_time")
    private Date  applyTime;
    @ApiModelProperty(value = "用户状态， 0 停用  1启用")
    @TableField(value = "用户同步wxappid")
    private String wxAppid;
    @ApiModelProperty(value = "微信用户性别（1男2女）")
    @TableField(value = "sex")
    private String sex;
    @ApiModelProperty(value = "微信城市")
    @TableField(value = "city")
    private String city;
    @ApiModelProperty(value = "微信省份")
    @TableField(value = "province")
    private String province;
    @ApiModelProperty(value = "微信国家")
    @TableField(value = "country")
    private String country;
    @ApiModelProperty(value = "同步用户状态（1未同步，2同步中，3同步成功）")
    @TableField(value = "sync_state")
    private String syncState;
    @ApiModelProperty(value = "")
    @TableField(value = "同步数据id")
    private String taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "apply_time")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}