package com.soft.mapp.basecenter.domain.loginVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "用户基本实体")
public  class UserQueryVo extends  User {
    @ApiModelProperty(value = "角色编号集合")
    private List<Integer> roleIds;
}