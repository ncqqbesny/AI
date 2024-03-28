package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.task.OrdersVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface ITaskController extends IRestService {
    String getInfoById(Integer id);
    ServerResponse<?>  deleteByExample(List<Integer> roleIds);
    ServerResponse<?> addInfo(OrdersVo orderVo);
    ServerResponse<?>  updateByExample(OrdersVo orderVo);
    ServerResponse<?> getPageBySelective(OrdersVo orderVo, Integer pageSize,
                                         Integer pageNum);
}
