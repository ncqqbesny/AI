package com.soft.mapp.basecenter.dao;


import java.util.List;

import com.soft.mapp.basecenter.domain.task.OrdersPo;
import com.soft.mapp.basecenter.domain.task.TaskQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface ITaskDao {	
     int addInfo(OrdersPo orderPo);
     int updateByExampleSelective(@Param("record") OrdersPo record, @Param("example") TaskQuery example);
     int deleteByExample(TaskQuery example);
     List<?> getPageInfo(TaskQuery taskQuery);
     OrdersPo selectByReqCode(String reqCode);
     Long  getOrderId();
}
