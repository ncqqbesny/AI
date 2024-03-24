package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.loginVo.RoleDTO;
import com.soft.mapp.basecenter.domain.loginVo.RoleVo;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface IRoleService {
    List<RoleDTO> findByUserid(Integer userId);
    List<User>   findUserById(Integer id);
    int addInfo(RoleVo user);

    int updateByExampleSelective(RoleVo user);

    int deleteByExample(List<Integer> ids);

    CommonPage<RoleVo> getPageBySelective(RoleVo user, Integer pageSize,
                                          Integer pageNum);


}
