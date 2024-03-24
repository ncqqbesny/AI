package com.soft.mapp.basecenter.impl.services;

import cn.hutool.core.bean.BeanUtil;
import com.soft.mapp.basecenter.dao.*;
import com.soft.mapp.basecenter.domain.loginVo.*;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.IUserInfoService;
import com.soft.mapp.basecenter.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    IRoleDao roleDao;
    @Autowired
    IMenuDao menuDao;
    @Autowired
    IUserDao userDao;
    @Autowired
    IUserRoleDao userRoleDao;
    @Autowired
    IUserInfoDao userInfoDao;
    @Override
    public List<UserInfoDTO> selectByuserId(Integer userId) {
        List<UserInfoDTO> userInfos=userInfoDao.selectByuserId(userId);
        return userInfos;

    }

    @Override
    public int addInfo(UserInfoDTO userInfoDTO) {
        int count = userInfoDao.addUserInfo(userInfoDTO);
        return count;
    }

    @Override
    public int updateByExampleSelective(UserInfoVo userInfoVo) {
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        BeanUtil.copyProperties(userInfoVo,userInfoDTO);
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        UserInfoQuery.Criteria criteria = userInfoQuery.createCriteria();
        if (null != userInfoDTO.getUserId() ) {
            criteria.andUserIdEqualTo(userInfoDTO.getUserId());
        }
        if (null != userInfoDTO.getId()) {
            criteria.andIdEqualTo(userInfoDTO.getId());
        }
        return userInfoDao.updateByExampleSelective(userInfoDTO, userInfoQuery);
    }

    @Override
    public int deleteByExample(List<Integer> ids) {
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        if (null != ids && ids.size() > 0) {
            userInfoQuery.createCriteria().andIdIn(ids);
        }
        return userInfoDao.deleteByExample(userInfoQuery);
    }

    @Override
    public CommonPage<UserInfoDTO> getPageUser(UserInfoVo userInfo, Integer pageSize,
                                               Integer pageNum) {
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        BeanUtil.copyProperties(userInfo,userInfoDTO);
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        UserInfoQuery.Criteria criteria = userInfoQuery.createCriteria();
        if (null != userInfo.getUserId()) {
            criteria.andUserIdEqualTo(userInfo.getUserId());
        }
        if (null != userInfo.getId()) {
            criteria.andIdEqualTo(userInfo.getId());
        }
        if (StringUtils.isNotEmpty(userInfo.getCompanyName())) {
            criteria.andCompanyNameLike("%" + userInfo.getCompanyName() + "%");
        }
        if (StringUtils.isNotEmpty(userInfo.getAddress())) {
            criteria.andAddressLike("%" + userInfo.getAddress() + "%");
        }
        if (StringUtil.isNotEmpty(userInfo.getStatus())) {
            criteria.andStatusEqualTo(userInfo.getStatus());
        }
        List<?> listAndTotal=userInfoDao.getPageUser(userInfoQuery);
        List<UserInfoDTO> userInfoDTOList=(List<UserInfoDTO>)listAndTotal.get(0);
        Long total=((List<Long>) listAndTotal.get(1)).get(0);
        return CommonPage.restPage(userInfoDTOList, pageNum, pageSize, total);
    }

    /**
     * 获得角色名称
     *
     * @param user
     */
    private void getRoles(List<RoleDTO> roles, User user) {
        List<Role> rolesStrings = new ArrayList<Role>();
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }
        for (RoleDTO roleDTO : roles) {
            Role role=new Role();

            if (StringUtil.isEmpty(roleDTO.getRoleName())) {
                continue;
            }
            BeanUtils.copyProperties(roleDTO,role);
            rolesStrings.add(role);

        }
        user.setRoles(rolesStrings);
    }

    /**
     * 获得菜单名
     *
     * @param result
     */
    private void getMenuName(List<RoleDTO> roles, Map<String, Object> result) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        Map<String, String> mapFirstPage = new HashMap<String, String>();
        mapFirstPage.put("name", "index");
        mapList.add(mapFirstPage);
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }
        for (RoleDTO role : roles) {
            if (StringUtil.isEmpty(role.getMenuIds())) {
                continue;
            }
            for (String menuId : role.getMenuIds().split(",")) {
                Map<String, String> map = new HashMap<String, String>();
                if (!StringUtil.isNumeric(menuId)) {
                    continue;
                }
                Menu menu = menuDao.findByMenuId(Integer.valueOf(menuId));
                if (null == menu) {
                    continue;
                }
                map.put("name", menu.getStaticRouter());
                mapList.add(map);

            }
        }
        result.put("permissions", mapList);
    }

}
