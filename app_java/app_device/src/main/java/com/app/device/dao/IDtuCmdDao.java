package com.app.device.dao;

import com.app.device.domain.Wwj.DtuCmdPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IDtuCmdDao extends BaseMapper<DtuCmdPO> {
}
