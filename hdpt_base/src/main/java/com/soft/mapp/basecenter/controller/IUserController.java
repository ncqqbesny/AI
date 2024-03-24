package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface IUserController  extends IRestService {
    ServerResponse<?>  getUserById(Integer id);
    ServerResponse<?>  deleteUserById(List<Integer> userIds);
    ServerResponse<?> addUser(UserVo user);
    ServerResponse<?> regUser(UserVo user);
    ServerResponse<?>  updateUser(UserVo user);
    ServerResponse<?> getPageByUser(UserVo userVo, Integer pageSize,
                                    Integer pageNum);
}
