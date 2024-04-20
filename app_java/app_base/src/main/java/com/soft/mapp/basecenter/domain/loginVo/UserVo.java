package com.soft.mapp.basecenter.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soft.mapp.basecenter.modules.system.CustomTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "用户基本实体")
public  class UserVo extends  UserInfoVo {
    @ApiModelProperty(value = "用户id")
    @CustomTag(desc = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名(必填项)")
    @CustomTag(desc = "用户名")
    private  String username;
    @ApiModelProperty(value = "姓名(必填项)" )
    @CustomTag(desc = "姓名")
    private  String name;
    @ApiModelProperty(value = "用户类型：1、网页注册用户，4，小程序注册用户，5、公众号注册用户")
    @CustomTag(desc = "用户类型")
    private  String userType;
    @ApiModelProperty(value = "状态 1正常，2停用")
    @CustomTag(desc = "状态")
    private  String userStatus;
    @ApiModelProperty(value = "邮箱")
    @CustomTag(desc = "邮箱")
    private  String email;
    @ApiModelProperty(value = "联系电话 (必填项)")
    @CustomTag(desc = "联系电话")
    private  String telephone;
    @ApiModelProperty(value = "密码")
    @CustomTag(desc = "密码")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "项目id")
    private Integer mId;
    @ApiModelProperty(value = "项目名称")
    @CustomTag(desc = "项目名称")
    private String mName;
    @ApiModelProperty(value = "公司地址")
    private  String companyAddress;
    @ApiModelProperty(value = "公司电话")
    private  String companyTel;
    @ApiModelProperty(value = "公司邮箱")
    private  String companyEmail;
    @ApiModelProperty(value = "微信用户ID")
    private String  openId;
    @ApiModelProperty(value = "昵称")
    private String  nickName;
    @ApiModelProperty(value = "微信头像url")
    private String  wxHeadurl;
    @ApiModelProperty(value = "微信统一ID")
    private String  unionid;
    @ApiModelProperty(value = "项目编号集合")
    @CustomTag(desc = "项目编号集合")
    private List<Integer> mIds;
    @ApiModelProperty(value = "角色编号集合")
    private List<Integer> roleIds;
    @ApiModelProperty(value = "角色集合")
    private List<RoleDTO> roles;
    @ApiModelProperty(value = "备注")
    private String  remark;
}