package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.Menu;
import com.soft.mapp.basecenter.domain.loginVo.MenuDTO;
import com.soft.mapp.basecenter.domain.loginVo.MenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IMenuDao {
    Menu findByMenuId(Integer menuId);

    List<MenuDTO> findList();
    List<MenuDTO> findByLikeMenuName(String menuName);
    List<MenuDTO> findByUserId(Integer userId);
    int insertSelective(MenuDTO record);
    int updateByExampleSelective(@Param("record") MenuDTO record, @Param("example") MenuQuery example);
    int deleteByExample(MenuQuery example);
    List<?> selectByExample(MenuQuery example);
}
