package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.loginVo.UserInfoDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface IUserInfoService {


    List<UserInfoDTO> selectByuserId(Integer userId);

    int addInfo(UserInfoDTO userInfo);

    int updateByExampleSelective(UserInfoVo userInfo);

    int deleteByExample(List<Integer> mids);

    CommonPage<UserInfoDTO> getPageUser(UserInfoVo userInfo, Integer pageSize,
                                        Integer pageNum);

}
