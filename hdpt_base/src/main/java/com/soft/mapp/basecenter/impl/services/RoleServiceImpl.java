package com.soft.mapp.basecenter.impl.services;

import com.soft.mapp.basecenter.dao.IMenuDao;
import com.soft.mapp.basecenter.dao.IRoleDao;
import com.soft.mapp.basecenter.dao.IUserDao;
import com.soft.mapp.basecenter.domain.loginVo.*;
import com.soft.mapp.basecenter.handler.CommonBusiness;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.IRoleService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleDao roleDao;
    @Autowired
    IMenuDao menuDao;
    @Autowired
    IUserDao userDao;


    @Override
    public List<RoleDTO> findByUserid(Integer userId) {

        return roleDao.findByUserid(userId);
    }

    @Override
    public List<User> findUserById(Integer id) {
        List<User> users = userDao.findByRoleId(id);
        return users;
    }

    @Override
    public int addInfo(RoleVo role) {
        RoleDTO roleDTO=new RoleDTO();
        BeanUtils.copyProperties(role,roleDTO);
        return roleDao.addRole(roleDTO);
    }

    @Override
    public int updateByExampleSelective(RoleVo role) {
        RoleDTO roleDTO=new RoleDTO();
        BeanUtils.copyProperties(role,roleDTO);
        RoleQuery roleQuery = new RoleQuery();
        RoleQuery.Criteria criteria = roleQuery.createCriteria();
        if(null!=roleDTO.getRoleId() ){
            criteria.andIdEqualTo(roleDTO.getRoleId());
        }
        return roleDao.updateByExampleSelective(roleDTO,roleQuery);
    }

    @Override
    public int deleteByExample(List<Integer> ids) {
        RoleQuery roleQuery = new RoleQuery();
        if(null!=ids && ids.size()>0){
            roleQuery.createCriteria().andIdIn(ids);
        }
        return roleDao.deleteByExample(roleQuery);
    }

    @Override
    public CommonPage<RoleVo> getPageBySelective(RoleVo role, Integer pageSize, Integer pageNum) {
        RoleQuery roleQuery = new RoleQuery();
        RoleQuery.Criteria criteria = roleQuery.createCriteria();
        if(null!=role.getRoleId() ){
            criteria.andIdEqualTo(role.getRoleId());
        }
        if(StringUtils.isNotEmpty(role.getRoleName())){
            criteria.andRoleNameLike( role.getRoleName());
        }
        if(StringUtils.isNotEmpty(role.getDescription())){
            criteria.andDescriptionLike( role.getDescription());
        }
        if(!CollectionUtils.isEmpty(role.getMIds())){
            criteria.andMidIn(role.getMIds());
        }
        if(StringUtils.isNotEmpty(role.getRoleStatus())){
            criteria.andRoleStatusEqualTo(role.getRoleStatus());
        }
        if(null!=role.getmId() && !CommonBusiness.isAdmin()){
            roleQuery.or().andParentEqualTo(role.getmId());
            roleQuery.or().andOrMidEqualTo(role.getmId());
        }
        roleQuery.setOrderByClause("create_time desc");
        List<RoleDTO> roleDTOS=getRoleByMenu(role);
        if(null!=roleDTOS && roleDTOS.size()>0){
            List<Integer> ids=new ArrayList<>();
            for(RoleDTO roleDTO:roleDTOS){
               ids.add(roleDTO.getRoleId());
            }
            criteria.andIdIn(ids);
        }
        //增加分页
        if(null!=pageNum && null!=pageSize ){
            String  fromRow= ConvertUtils.toString((pageNum-1)*pageSize);
            roleQuery.setPageNumAndSize(fromRow+","
                    +ConvertUtils.toString(pageSize));
        }
        List<RoleVo>  roleVos=new ArrayList<>();
        List<?> listAndTotal=roleDao.getPageRole(roleQuery);
        List<RoleDTO> roleDTOs=(List<RoleDTO>)listAndTotal.get(0);
        Long total=((List<Long>) listAndTotal.get(1)).get(0);
        for (RoleDTO roleDTO:roleDTOs){
            RoleVo roleVo=new RoleVo();
            BeanUtils.copyProperties(roleDTO,roleVo);
            if(null!=roleVo.getMenuIds()) {
                List<Menu> roles = getMenusByRole(roleVo.getMenuIds());
                roleVo.setMenus(roles); //获得菜单信息
            }
            roleVos.add(roleVo);
        }
        //List<UserVo>  userVos= JSONArray.parseArray(JSON.toJSONString(userDTOs), UserVo.class);
        return  CommonPage.restPage(roleVos, pageNum, pageSize, total);
    }

    /**
     * 根据角色菜单列表值获得菜单信息
     * @param roleMenuIds
     * @return
     */
    private List<Menu> getMenusByRole(String roleMenuIds){
        List<Menu> menus=new ArrayList<>();
        MenuQuery menuQuery=new MenuQuery();
        if(StringUtils.isNotEmpty(roleMenuIds)){
            List<Integer> ids=new ArrayList<>();
            String []  strs=roleMenuIds.split(",");
            for(String str:strs){
              if(StringUtil.isNumeric(str)){
                  ids.add(Integer.valueOf(str));
              }
            }
            if(ids.size()!=0){
                menuQuery.createCriteria().andIdIn(ids);
            }
        }
        List<?> listAndTotal=menuDao.selectByExample(menuQuery);
        menus=(List<Menu>)listAndTotal.get(0);
        return  menus;
    }

    /**
     * 根据菜单页面查找角色
     * @param roleVo
     * @return
     */
    private List<RoleDTO>  getRoleByMenu(RoleVo roleVo){
        List<RoleDTO> roleDTOS=new ArrayList<>();
        if(StringUtils.isNotEmpty(roleVo.getMenuName()) || StringUtils.isNotEmpty(roleVo.getStaticRouter()) ){
          roleDTOS=roleDao.getRoleByMenu(roleVo);
        }
        return  roleDTOS;
    }
}
