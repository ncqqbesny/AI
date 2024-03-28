package com.soft.mapp.basecenter.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class MenuDTO {
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId ;
    @ApiModelProperty(value = "菜单名字")
    private String menuName ;
    @ApiModelProperty(value = "上级菜单ID")
    private Integer parentMenuId;
    @ApiModelProperty(value = "角色菜单类型（a一级菜单，b二级菜单，c三级菜单）id")
    private String type;
    @ApiModelProperty(value = "路径名")
    private String staticRouter;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "跳转url")
    private String skipUrl;
    @ApiModelProperty(value = "排序")
    private Integer orderNum;
    @ApiModelProperty(value = "是否是外部地址0是，1否")
    private String isOutUrl;
    @ApiModelProperty(value = "菜单状态 1正常，2下架")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStaticRouter() {
        return staticRouter;
    }

    public void setStaticRouter(String staticRouter) {
        this.staticRouter = staticRouter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getIsOutUrl() {
        return isOutUrl;
    }

    public void setIsOutUrl(String isOutUrl) {
        this.isOutUrl = isOutUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
