package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.loginVo.UserDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IUserInfoDao {

    List<UserInfoDTO> selectByuserId(Integer userId);

    List<UserInfoDTO> findList();

    List<UserInfoDTO> findByCompanyName(String companyName);

    int addUserInfo(UserInfoDTO userInfoDTO);

    int updateByExampleSelective(@Param("record") UserInfoDTO record, @Param("example") UserInfoQuery example);

    int deleteByExample(UserInfoQuery example);

    List<UserDTO> getPageUser(UserInfoQuery userInfoQuery);
}
