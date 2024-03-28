package com.soft.mapp.basecenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.mapp.basecenter.domain.Wechat.WxUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IWxUserDao extends BaseMapper<WxUserDTO> {

    /**
     * 自定义执行SQL
     * @param sql
     * @return
     */
    List<Map> mySelect(String sql);

    @Select("select count(1) from wx_user where wx_user_id = ${wxUserId} ")
    public int selectColumn(@Param("wxUserId") String wxUserId);
}
