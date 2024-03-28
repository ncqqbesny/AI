package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.loginVo.RoleVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface IRoleController extends IRestService {
    ServerResponse<?> getUserInfoById(Integer id);
    ServerResponse<?>  deleteByExample(List<Integer> roleIds);
    ServerResponse<?> addInfo(RoleVo roleVo);
    ServerResponse<?>  updateByExample(RoleVo roleVo);
    ServerResponse<?> getPageBySelective(RoleVo roleVo, Integer pageSize,
                                         Integer pageNum);
}
