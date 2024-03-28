package com.hdpt.device.domain.loginVo;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.hdpt.device.domain.system.CustomTag;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class RoleDTO {
    @ApiModelProperty(value = "角色id")
    @CustomTag(desc = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "角色名称")
    @CustomTag(desc = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色描述")
    @CustomTag(desc = "角色描述")
    private String description;
    @ApiModelProperty(value = "项目id")
    @CustomTag(desc = "项目id")
    private Integer mId;
    @ApiModelProperty(value = "角色状态（1启用，0停用）")
    @CustomTag(desc = "角色状态")
    private String roleStatus;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date updateTime;
    /**
     * 菜单ID
     */
    private String menuIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
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

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}
