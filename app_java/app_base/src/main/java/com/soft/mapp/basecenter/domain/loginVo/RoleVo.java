package com.soft.mapp.basecenter.domain.loginVo;

import com.soft.mapp.basecenter.modules.system.CustomTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo  extends RoleDTO{
    @ApiModelProperty(value = "菜单 （添加编辑不用）")
    @CustomTag(desc = "菜单")
    private  List<Menu> menus;
    @ApiModelProperty(value = "菜单名字 （添加编辑不用）")
    @CustomTag(desc = "菜单名字")
    private String menuName ;
    @ApiModelProperty(value = "路径名（添加编辑不用）")
    @CustomTag(desc = "路径名")
    private String staticRouter;
    @ApiModelProperty(value = "查询多项目")
    @CustomTag(desc = "查询多项目")
    private List<Integer> mIds;

}
