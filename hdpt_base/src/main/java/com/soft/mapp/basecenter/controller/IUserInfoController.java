package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.loginVo.UserInfoVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface IUserInfoController extends IRestService {
    ServerResponse<?>  getInfoByUserId(Integer userId);
    ServerResponse<?>  deleteInfoById(List<Integer> ids);
    ServerResponse<?>  addUserInfo(UserInfoVo userInfoVo);
    ServerResponse<?>  updateInfo(UserInfoVo userInfoVo);
    ServerResponse<?>  getPageByInfo(UserInfoVo userInfoVo, Integer pageSize,
                                    Integer pageNum);
}
