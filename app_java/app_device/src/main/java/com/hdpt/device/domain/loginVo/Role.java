package com.hdpt.device.domain.loginVo;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private Integer roleId;
    private String roleName;
    /**
     * 菜单ID
     */
    private String menuIds;
    private List<Permission> permissions;
}
