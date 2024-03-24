package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.loginVo.MenuVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface IMenuController extends IRestService {
    ServerResponse<?> getInfoById(Integer id);
    ServerResponse<?>  deleteByExample(List<Integer> roleIds);
    ServerResponse<?> addInfo(MenuVo menuvo);
    ServerResponse<?>  updateByExample(MenuVo menuvo);
    ServerResponse<?> getPageMenuBySelective(MenuVo menuvo, Integer pageSize,
                                         Integer pageNum);
}
