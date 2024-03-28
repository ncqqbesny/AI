package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.loginVo.MenuDTO;
import com.soft.mapp.basecenter.domain.loginVo.MenuVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface IMenuService {
    List<MenuDTO> findByUserid(Integer userId);

    int addInfo(MenuVo menuVo);

    int updateByExampleSelective(MenuVo menuVo);

    int deleteByExample(List<Integer> ids);

    CommonPage<MenuVo> getPageBySelective(MenuVo menuVo, Integer pageSize,
                                          Integer pageNum);

    /**
     * 逗号分隔
     * @return
     */
    String  findMenusIds(String outMenuName);

    String  findExistMenusIds(String menuName);

    boolean IsExistInfoName (String name);
}
