package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.task.OrdersPo;
import com.soft.mapp.basecenter.domain.task.OrdersVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface ITaskService {
    List<OrdersPo> findByUserid(Integer userId);

    int addInfo(OrdersVo task);

    int updateByExampleSelective(OrdersVo task);

    int deleteByExample(List<Integer> ids);

    CommonPage<OrdersVo> getPageBySelective(OrdersVo user, Integer pageSize,
                                            Integer pageNum);

}
