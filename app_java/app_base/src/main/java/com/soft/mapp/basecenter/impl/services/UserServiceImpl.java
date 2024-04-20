package com.soft.mapp.basecenter.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.soft.mapp.basecenter.dao.*;
import com.soft.mapp.basecenter.domain.Wechat.WxUserDTO;
import com.soft.mapp.basecenter.domain.loginVo.*;
import com.soft.mapp.basecenter.domain.merchant.MerchantPo;
import com.soft.mapp.basecenter.domain.merchant.MerchantQuery;
import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import com.soft.mapp.basecenter.handler.CommonBusiness;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.handler.UserInfoContext;
import com.soft.mapp.basecenter.services.IMenuService;
import com.soft.mapp.basecenter.services.ISysParamService;
import com.soft.mapp.basecenter.services.IUserService;
import com.soft.mapp.basecenter.services.IWechatService;
import com.soft.mapp.basecenter.type.UserTypeEnum;
import com.soft.mapp.basecenter.type.WxUserTypeEnum;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.StringUtil;
import com.soft.mapp.basecenter.utils.ToolsUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    IRoleDao roleDao;
    @Autowired
    IMenuDao menuDao;
    @Autowired
    IUserDao userDao;
    @Autowired
    IUserRoleDao userRoleDao;
    @Autowired
    IMerchantDao merchantDao;
    @Autowired
    IMenuService menuService;
    @Autowired
    IUserInfoDao userInfoDao;
    @Autowired
    ISysParamDao sysParamDao;
    @Autowired
    IWxUserDao wxUserDao;
    @Autowired
    IWechatService wechatService;
    @Autowired
    ISysParamService sysParamService;

    @Value("${file.image.path}")
    public String imagePath;


    @Override
    public Map<String, Object> getUserInfo() {
        User user = UserInfoContext.getUser();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);

        //角色菜单
        List<RoleDTO> roles = roleDao.findByUserid(user.getUserId());
        getRoles(roles, user);  //角色
        //getMenuName(roles, result); //菜单
        result.put("user", user);
        return result;
    }

    @Override
    public UserQueryVo selectByuserId(Integer userId) {
        UserQueryVo userQueryVo=new UserQueryVo();
        User user = userDao.selectByuserId(userId);
        if (null == user) {
            return userQueryVo;
        }
        List<RoleDTO> roles = roleDao.findByUserid(user.getUserId());
        getRoles(roles, user);  //角色
        List<Integer> roleIds=new ArrayList<>();
        BeanUtils.copyProperties(user,userQueryVo);
        for(RoleDTO roleDTO:roles){
            roleIds.add(roleDTO.getRoleId());
        }
        userQueryVo.setRoleIds(roleIds);
        return userQueryVo;

    }

    @Override
    public int addUser(UserVo user) {
        List<User> users = findExistUser(user);
        //已存在用户不能注册
        if (!CollectionUtils.isEmpty(users)) {
            return 0;
        }
        setUserDefaultVaule(user);
        //先保存用户关系
        String msg = saveUserCorrelation(user);
        if (StringUtils.isNotEmpty(msg)) {
            return 0;
        }
        return 1;
    }

    //判断是否有相同用户名、EMAIL. 电话
    @Override
    public List<User> findExistUser(UserVo user) {
        //判断是否有相同用户名、EMAIL. 电话
        UserQuery userQuery = new UserQuery();
        if (StringUtils.isNotEmpty(user.getUsername()) && (null == user.getUserId() || user.getUserId() == 0)) {
            userQuery.createCriteria().andUserNameEqualTo(user.getUsername().trim());
        }
        if (null != user.getUserId() && user.getUserId() != 0) {
            userQuery.createCriteria().andIdEqualTo(user.getUserId());
        }
        //有openid按openid优先查询
        if (StringUtils.isEmpty(user.getOpenId())) {
            if (StringUtils.isNotEmpty(user.getTelephone())) {
                userQuery.createCriteria().andTelephoneEqualTo(user.getTelephone());
            }
        } else {
            userQuery.createCriteria().andOpenIdEqualTo(user.getOpenId());
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            userQuery.createCriteria().andEmailEqualTo(user.getEmail());
        }
        List<User> existUsers = userDao.findListByWhere(userQuery);
        if (!CollectionUtils.isEmpty(existUsers)) {
            return existUsers;
        }
        //再用电话号码查询一次
        UserQuery userQuery1 = new UserQuery();
        List<User> existUsers1=new ArrayList<>();
        if (StringUtils.isNotEmpty(user.getTelephone())) {
            userQuery1.createCriteria().andTelephoneEqualTo(user.getTelephone());
            existUsers1 = userDao.findListByWhere(userQuery1);
        }
        if (!CollectionUtils.isEmpty(existUsers1)) {
            return existUsers1;
        }
        return null;
    }

    /**
     * 设为默认值
     *
     * @param userVo
     */
    private void setUserDefaultVaule(UserVo userVo) {
        if (StringUtils.isEmpty(userVo.getPassword())) {
            //默认123456
            userVo.setPassword("CV1620%35%32%33%38%39%36%18");
        }
        if (userVo.getMIds() == null || userVo.getMIds().size() == 0) {
            //默认为平台
            userVo.setMIds(new ArrayList<>());
        }
    }

    @Override
    public int updateByExampleSelective(UserVo user) {
        setUserDefaultVaule(user);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        String msg = saveUserCorrelation(user);
        if (StringUtils.isNotEmpty(msg)) {
            return 0;
        }
        return 1;
    }

    /**
     * 保存用户相的关系
     *
     * @param user
     * @return
     */
    private String saveUserCorrelation(UserVo user) {
        List<User> findUsers = findExistUser(user);
        String msg="";
        //user.setMId(null);
        //保存项目mid的判断
        if (!CollectionUtils.isEmpty(findUsers) && StringUtil.isEmpty(user.getMId())) {
            user.setMId(findUsers.get(0).getMId());
            user.setOpenId(findUsers.get(0).getOpenId());
        }

        //保存用户信息
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if (!CollectionUtils.isEmpty(findUsers)) {
            UserQuery userQuery = new UserQuery();
            UserQuery.Criteria criteria = userQuery.createCriteria();
            criteria.andIdEqualTo(findUsers.get(0).getUserId());
            userDTO.setUserId(findUsers.get(0).getUserId());
            user.setUserId(findUsers.get(0).getUserId());
            if (StringUtils.isEmpty(user.getUserType())) {
                user.setUserType(findUsers.get(0).getUserType());
            }
            int count = userDao.updateByExampleSelective(userDTO, userQuery);
        } else {
            if (StringUtils.isEmpty(userDTO.getName()) || StringUtil.isEmpty(userDTO.getUsername())) {
                userDTO.setName("张三");
                userDTO.setUsername(ToolsUtils.bitRandom(6));
            }
            int count = userDao.addUser(userDTO);
            //返回添加在userid
            if (count > 0) {
                user.setUserId(userDTO.getUserId());
            }
        }
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        //保存角色关系
        String msg1=installAndUpddateRoleByUser(user);
        //微信用户信息保存
        msg = saveWxUser(user);
        if (StringUtil.isNotEmptyString(msg)) {
            log.info("updateByExampleSelective--保存微信信息失败==" + msg);
            return "保存微信信息失败";
        }
        //保存公司信息
        msg = saveUserInfo(user);
        if (StringUtil.isNotEmptyString(msg)) {
            log.info("updateByExampleSelective--保存公司失败关系失败==" + msg);
            return "保存公司失败关系失败";
        }
        return "";
    }

    /**
     * 保存微信用户信息
     *
     * @param user
     * @return
     */
    private String saveWxUser(UserVo user) {
        //微信用户信息保存
        if (StringUtils.isNotEmpty(user.getOpenId())) {
            WxUserDTO wxUserDTO = new WxUserDTO();
            BeanUtils.copyProperties(user, wxUserDTO);
            if (user.getUserType().equals(UserTypeEnum.getValue("小程序注册用户"))) {
                wxUserDTO.setType(WxUserTypeEnum.getValue("小程序注册用户"));
            }
            if (user.getUserType().equals(UserTypeEnum.getValue("公从号注册用户"))) {
                wxUserDTO.setType(WxUserTypeEnum.getValue("公从号注册用户"));
            }
            if (StringUtils.isNotEmpty(user.getTelephone())) {
                wxUserDTO.setPhone(user.getTelephone());
            }
            QueryWrapper<WxUserDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(wxUserDTOQueryWrapper -> wxUserDTOQueryWrapper.eq("open_id", wxUserDTO.getOpenId()));
            List<WxUserDTO> list = wxUserDao.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                int count = wxUserDao.update(wxUserDTO, queryWrapper);
                if (count == 0) {
                    return "更新微信用户信息失败";
                }
            } else {
                int count = wxUserDao.insert(wxUserDTO);
                if (count == 0) {
                    return "保存微信用户信息失败";
                }
            }
        }
        return "";
    }

    /**
     * 保存公司信息
     *
     * @param user
     * @return
     */
    private String saveUserInfo(UserVo user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        List<UserInfoDTO> userinfo = userInfoDao.selectByuserId(user.getUserId());
        int count = 0;
        if (!CollectionUtils.isEmpty(userinfo)) {
            if (StringUtils.isEmpty(user.getCompanyAddress())) {
                user.setCompanyAddress(userinfo.get(0).getAddress());
            }
            if (StringUtils.isEmpty(user.getCompanyTel())) {
                user.setCompanyTel(userinfo.get(0).getTelephone());
            }
            if (StringUtils.isEmpty(user.getCompanyEmail())) {
                user.setCompanyEmail(userinfo.get(0).getEmail());
            }
            if (StringUtils.isEmpty(user.getCompanyName())) {
                user.setCompanyName(userinfo.get(0).getCompanyName());
            }
            userInfoDTO.setCompanyName(user.getCompanyName());
            userInfoDTO.setAddress(user.getCompanyAddress());
            userInfoDTO.setTelephone(user.getCompanyTel());
            userInfoDTO.setEmail(user.getCompanyEmail());
            UserInfoQuery userInfoQuery = new UserInfoQuery();
            userInfoQuery.createCriteria().andUserIdEqualTo(user.getUserId());
            count = userInfoDao.updateByExampleSelective(userInfoDTO, userInfoQuery);
        } else {
            if (StringUtils.isEmpty(user.getCompanyName())) {
                userInfoDTO.setCompanyName("默认公司");
            } else {
                userInfoDTO.setCompanyName(user.getCompanyName());
            }
            userInfoDTO.setAddress(user.getCompanyAddress());
            userInfoDTO.setTelephone(user.getCompanyTel());
            userInfoDTO.setEmail(user.getCompanyEmail());
            count = userInfoDao.addUserInfo(userInfoDTO);
        }
        if (count == 0) {
            return "没有添加上公司信息";
        }
        return "";
    }

    /**
     * 保存角色和关系
     *
     * @param user
     * @return
     */
    private String installAndUpddateRoleByUser(UserVo user) {

        if(CollectionUtil.isEmpty(user.getRoleIds())){
            return "没有角色信息，不需要绑定";
        }
        String sql="SELECT  * FROM user_role u  WHERE u.user_id="+user.getUserId() ;
        Map<String, Object> roleUserMap = SqlRunner.db().selectOne(sql);
        //应急人员
        if (roleUserMap != null && roleUserMap.get("role_id") != null  ) {
            String updateSql="update user_role u set u.role_id="+user.getRoleIds().get(0)+" WHERE u.user_id="+user.getUserId() ;
            boolean exec = SqlRunner.db().update(updateSql);
        }else{
            UserRole userRole=new UserRole();
            userRole.setRoleId(user.getRoleIds().get(0));
            userRole.setUserId(user.getUserId());
            userRoleDao.addUserRole(userRole);
        }

        return "";
    }

    /**
     * 使用用户保存项目信息
     *
     * @param user
     * @return
     */
    private String installAndUpdateMerchantByUser(UserVo user) {
        MerchantPo merchantPo = new MerchantPo();
        merchantPo.setIsParent(1);
        merchantPo.setMStatus("1");
        MerchantQuery merchantQuery = new MerchantQuery();
        List<MerchantPo> list = new ArrayList<>();
        if (null != user.getMId()) {
            merchantQuery.createCriteria().andIdEqualTo(user.getMId());
            list = merchantDao.findListByWhere(merchantQuery);
        }
        if (!CollectionUtils.isEmpty(list)) {
            //复制参数信息
            BeanUtils.copyProperties(user, merchantPo);
            merchantPo.setMId(list.get(0).getMId());
            if (StringUtils.isNotEmpty(user.getOpenId()) && StringUtil.isEmpty(list.get(0).getWxSmallQRCodeUrl())) {
                merchantPo.setWxSmallQRCodeUrl(wechatService.getWxSmarlQrcCodeUrl(merchantPo.getMId()));
            }
            //添回更新项目
            merchantDao.insertOrUpdate(merchantPo);
            //添加时获得添加的mid
            user.setMId(list.get(0).getMId());
        } else {
            if (StringUtils.isEmpty(user.getMName()) && StringUtil.isNotEmpty(user.getUsername())) {
                user.setMName(user.getUsername() + "_主项目");
                merchantPo.setMCode(ToolsUtils.bitRandom(6));
                merchantPo.setMDesc("手机号：" + user.getTelephone() + ",管理默认主项目");
                merchantPo.setMName("管理项目");
            } else {
                user.setMName("主项目");
                merchantPo.setMCode(ToolsUtils.bitRandom(6));
                merchantPo.setMDesc("手机号：" + user.getTelephone() + ",管理默认主项目");
                merchantPo.setMName("管理项目");
            }

            merchantPo.setMId(null);
            //添回更新项目
            merchantDao.insertOrUpdate(merchantPo);
            //添加时获得添加的mid
            user.setMId(merchantPo.getMId());
            if (StringUtils.isNotEmpty(user.getOpenId())) {
                merchantPo.setWxSmallQRCodeUrl(wechatService.getWxSmarlQrcCodeUrl(merchantPo.getMId()));
                merchantDao.insertOrUpdate(merchantPo);
            }
        }
        return "";
    }


    //删除用户角色关系
    private void delUserRole(UserVo userVo) {
        List<Integer> ids = new ArrayList<>();
        ids.add(userVo.getUserId());
        if (null == ids) {
            return;
        }
        UserRoleQuery userRoleQuery = new UserRoleQuery();
        userRoleQuery.createCriteria().andUserIdIn(ids);
        userRoleDao.deleteByExample(userRoleQuery);
    }

    /**
     * 添加用户角色关系
     *
     * @param
     */
    private String addUserRoleAndUserInfo(UserVo user) {
        List<Integer> roleDTOS = user.getRoleIds();
        if (null == roleDTOS || roleDTOS.size() == 0) {
            //添加默认角色
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setmId(user.getMId());
            if (StringUtils.isNotEmpty(user.getName())) {
                roleDTO.setRoleName(user.getName() + "_manger");
            } else {
                roleDTO.setRoleName(user.getUsername() + "_manger");
            }
            roleDTO.setDescription(user.getUsername() + "_管理员");
            roleDTO.setRoleStatus("1");

            //排除不要显示菜单内容
            StringBuilder outMenuSb = new StringBuilder();
            List<SysPramPo> sysPramPos = sysParamDao.getSysParamByTypeAndCode("menu", "out_menu");
            if (!CollectionUtils.isEmpty(sysPramPos)) {
                for (SysPramPo item : sysPramPos) {
                    outMenuSb.append(item.getParamVal() + ";");
                }
            } else {
                outMenuSb.append("系统日志");
            }
            String menuids = menuService.findMenusIds(outMenuSb.toString());
            roleDTO.setMenuIds(menuids);
            //1、网络设备管理、2、器具柜设备管理
            if ("1".equals(user.getUserType())) {
                roleDTO.setMenuIds(menuService.findMenusIds("网络"));
            }
            if ("2".equals(user.getUserType())) {
                roleDTO.setMenuIds(menuService.findMenusIds("器具柜"));
            }
            roleDao.addRole(roleDTO);
            if (roleDTOS != null) {
                roleDTOS.add(roleDTO.getRoleId());
            } else {
                roleDTOS = new ArrayList<>();
                roleDTOS.add(roleDTO.getRoleId());
            }
        }
        if (null == user.getUserId()) {
            return "没有添加好用户";
        }
        for (Integer roleId : roleDTOS) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(roleId);
            int count = userRoleDao.addUserRole(userRole);
            if (count == 0) {
                return "没有添加上角色关系";
            }
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        List<UserInfoDTO> userinfo = userInfoDao.selectByuserId(user.getUserId());
        int count = 0;
        if (!CollectionUtils.isEmpty(userinfo)) {
            if (StringUtils.isEmpty(user.getCompanyAddress())) {
                user.setCompanyAddress(userinfo.get(0).getAddress());
            }
            if (StringUtils.isEmpty(user.getCompanyTel())) {
                user.setCompanyTel(userinfo.get(0).getTelephone());
            }
            if (StringUtils.isEmpty(user.getCompanyEmail())) {
                user.setCompanyEmail(userinfo.get(0).getEmail());
            }
            if (StringUtils.isEmpty(user.getCompanyName())) {
                user.setCompanyName(userinfo.get(0).getCompanyName());
            }
            userInfoDTO.setCompanyName(user.getCompanyName());
            userInfoDTO.setAddress(user.getCompanyAddress());
            userInfoDTO.setTelephone(user.getCompanyTel());
            userInfoDTO.setEmail(user.getCompanyEmail());
            UserInfoQuery userInfoQuery = new UserInfoQuery();
            userInfoQuery.createCriteria().andUserIdEqualTo(user.getUserId());
            count = userInfoDao.updateByExampleSelective(userInfoDTO, userInfoQuery);
        } else {
            userInfoDTO.setAddress(user.getCompanyAddress());
            userInfoDTO.setTelephone(user.getCompanyTel());
            userInfoDTO.setEmail(user.getCompanyEmail());
            count = userInfoDao.addUserInfo(userInfoDTO);
        }
        if (count == 0) {
            return "没有添加上公司信息";
        }
        return "";
    }

    @Override
    public int deleteByExample(List<Integer> ids) {
        UserQuery userQuery = new UserQuery();
        if (null != ids && ids.size() > 0) {
            userQuery.createCriteria().andIdIn(ids);
            //清除角色關系
            UserRoleQuery userRoleQuery = new UserRoleQuery();
            userRoleQuery.createCriteria().andUserIdIn(ids);
            userRoleDao.deleteByExample(userRoleQuery);
            UserInfoQuery userInfoQuery = new UserInfoQuery();
            userInfoQuery.createCriteria().andUserIdIn(ids);
            userInfoDao.deleteByExample(userInfoQuery);
        }

        return userDao.deleteByExample(userQuery);
    }

    @Override
    public CommonPage<UserVo> getPageUser(UserVo user, Integer pageSize,
                                          Integer pageNum) {
        List<UserVo> userVos = new ArrayList<>();
        List<?> listAndTotal = findUsers(user, pageSize, pageNum);
        List<UserDTO> userDTOs = (List<UserDTO>) listAndTotal.get(0);
        Long total = ((List<Long>) listAndTotal.get(1)).get(0);
        for (UserDTO userDTO : userDTOs) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userDTO, userVo);
            if (null != userDTO.getCreateTime()) {
                userVo.setCreateTime(userDTO.getCreateTime());
            }
            if (null != userDTO.getUpdateTime()) {
                userVo.setUpdateTime(userDTO.getUpdateTime());
            }
            List<RoleDTO> roles = roleDao.findByUserid(userDTO.getUserId());
            userVo.setRoles(roles);
            userVos.add(userVo);
        }
        //List<UserVo>  userVos= JSONArray.parseArray(JSON.toJSONString(userDTOs), UserVo.class);
        return CommonPage.restPage(userVos, pageNum, pageSize, total);
    }

    public List<?> findUsers(UserVo user, Integer pageSize,
                             Integer pageNum) {
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        if (null != user.getUserId()) {
            criteria.andIdEqualTo(user.getUserId());
        }
        if (StringUtils.isNotEmpty(user.getName())) {
            criteria.andNameLike("%" + user.getName() + "%");
        }
        if (StringUtils.isNotEmpty(user.getUsername())) {
            criteria.andUserNameLike("%" + user.getUsername() + "%");
        }
        if (StringUtil.isNotEmpty(user.getUserStatus())) {
            criteria.andUserStatusEqualTo(user.getUserStatus());
        }
        //页面查询
        if (StringUtil.isNotEmpty(user.getMId()) && !CommonBusiness.isAdmin() && pageNum != null) {
            criteria.andParentEqualTo(user.getMId());
            UserQuery.Criteria criteria1 = userQuery.createCriteria();
            criteria1.andMidEqualTo(user.getMId());
            userQuery.or(criteria1);
        }
        //单独查询
        if (StringUtil.isNotEmpty(user.getMId()) && pageNum == null) {
            criteria.andParentEqualTo(user.getMId());
            UserQuery.Criteria criteria1 = userQuery.createCriteria();
            criteria1.andMidEqualTo(user.getMId());
            userQuery.or(criteria1);
        }
        if (!CollectionUtils.isEmpty(user.getMIds()) && !CommonBusiness.isAdmin()) {
            criteria.andMidIn(user.getMIds());
        }
        List<Integer> ids = new ArrayList<>();
        if (null != user.getRoleIds() && user.getRoleIds().size() > 0) {
            List<Integer> roles = user.getRoleIds();
            for (Integer role : roles) {
                List<User> users = userDao.findByRoleId(role);
                if (!CollectionUtils.isEmpty(users)) {
                    for (User user1 : users) {
                        ids.add(user1.getUserId());
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        //增加分页
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            userQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        List<?> listAndTotal = userDao.getPageUser(userQuery);
        return listAndTotal;
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
            Role role = new Role();

            if (StringUtil.isEmpty(roleDTO.getRoleName())) {
                continue;
            }
            BeanUtils.copyProperties(roleDTO, role);
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
