package com.soft.mapp.basecenter.domain.Wechat;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("wx_user")
@Data
public class WxUserDTO {
    @ApiModelProperty(value = "设备分类CODE,必填项")
    @TableId(value="wx_user_id",type = IdType.AUTO)
    @TableField(value = "wx_user_id",fill = FieldFill.INSERT)
    private Integer wxUserId;
    @ApiModelProperty(value = "昵称")
    @TableField(value = "nick_name")
    private String nickName;
    @ApiModelProperty(value = "类别：默认null或1为小程序，2.公众号")
    @TableField(value = "type")
    private String type;
    @ApiModelProperty(value = "头像")
    @TableField(value = "head_url")
    private String wxHeadurl;
    @ApiModelProperty(value = "微信用户ID")
    @TableField(value = "open_id")
    private String openId;
    @ApiModelProperty(value = "微信统一ID")
    @TableField(value = "unionid")
    private String  unionid;
    @ApiModelProperty(value = "登录名")
    @TableField(value = "user_name")
    private String username;
    @ApiModelProperty(value = "手机号")
    @TableField(value = "phone")
    private String phone;
    @ApiModelProperty(value = "名片")
    @TableField(value = "card_url")
    private String cardUrl;
    @ApiModelProperty(value = "认证状态1认证通过2认证未通过3未认证4待审核")
    @TableField(value = "auth_state")
    private String authState;
    @ApiModelProperty(value = "是否认证1是2否")
    @TableField(value = "is_auth")
    private String isAuth;
    @ApiModelProperty(value = "是否关注公众号1是2否")
    @TableField(value = "is_follow")
    private Integer isFollow;
    @ApiModelProperty(value = "初次注册项目称")
    @TableField(value = "m_name")
    private String mName;
    @ApiModelProperty(value = "用户状态， 0 停用  1启用")
    @TableField(value = "status")
    private String status;
    @ApiModelProperty(value = "申请认证时间")
    @TableField(value = "apply_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date applyTime;
    @ApiModelProperty(value = "用户同步wxappid")
    @TableField(value = "wx_appid")
    private String wxAppid;
    @ApiModelProperty(value = "微信用户性别（1男2女）")
    @TableField(value = "sex")
    private String sex;
    @ApiModelProperty(value = "微信省份")
    @TableField(value = "province")
    private String province;
    @ApiModelProperty(value = "微信城市")
    @TableField(value = "city")
    private String city;
    @ApiModelProperty(value = "微信国家")
    @TableField(value = "country")
    private String country;
    @ApiModelProperty(value = "同步用户状态（1未同步，2同步中，3同步成功）")
    @TableField(value = "sync_state")
    private String syncState;
    @ApiModelProperty(value = "同步数据id")
    @TableField(value = "task_id")
    private String taskId;
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
 
 
}
