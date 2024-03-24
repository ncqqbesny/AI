package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;
import java.util.Map;

public interface IUserService {
    Map<String, Object> getUserInfo();

    User selectByuserId(Integer userId);

    int addUser(UserVo user);
    //是否存在 User
    List<User> findExistUser(UserVo userVo);

    int updateByExampleSelective(UserVo user);

    int deleteByExample(List<Integer> mids);

    CommonPage<UserVo> getPageUser(UserVo user, Integer pageSize,
                                   Integer pageNum);
    List<?>  findUsers(UserVo user, Integer pageSize,
                              Integer pageNum);
}
