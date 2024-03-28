package com.soft.mapp.basecenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.mapp.basecenter.domain.Wechat.WxUserSyncTaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IWxUserSyncTaskDao extends BaseMapper<WxUserSyncTaskDTO> {

    /**
     * 自定义执行SQL
     * @param sql
     * @return
     */
    List<Map> mySelect(String sql);

    @Select("select count(1) from wx_user_sync_task where wx_appid = ${wxAppid} ")
    public int selectColumn(@Param("wxAppid") String wxAppid);
}
