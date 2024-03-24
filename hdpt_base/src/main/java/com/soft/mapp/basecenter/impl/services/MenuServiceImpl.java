package com.soft.mapp.basecenter.impl.services;

import com.soft.mapp.basecenter.dao.IMenuDao;
import com.soft.mapp.basecenter.domain.loginVo.Menu;
import com.soft.mapp.basecenter.domain.loginVo.MenuDTO;
import com.soft.mapp.basecenter.domain.loginVo.MenuQuery;
import com.soft.mapp.basecenter.domain.loginVo.MenuVo;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.IMenuService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    IMenuDao menuDao;


    @Override
    public List<MenuDTO> findByUserid(Integer userid) {

        return menuDao.findByUserId(userid);
    }

    @Override
    public int addInfo(MenuVo menuVo) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuVo, menuDTO);
        return menuDao.insertSelective(menuDTO);
    }

    @Override
    public int updateByExampleSelective(MenuVo menuVo) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuVo, menuDTO);
        MenuQuery menuQuery = new MenuQuery();
        if (null != menuVo.getMenuId()) {
            menuQuery.createCriteria().andIdEqualTo(menuVo.getMenuId());
        }
        return menuDao.updateByExampleSelective(menuDTO, menuQuery);
    }

    @Override
    public int deleteByExample(List<Integer> ids) {
        MenuQuery menuQuery = new MenuQuery();
        if (null != ids) {
            menuQuery.createCriteria().andIdIn(ids);
        }
        return menuDao.deleteByExample(menuQuery);
    }

    @Override
    public CommonPage<MenuVo> getPageBySelective(MenuVo menuVo, Integer pageSize, Integer pageNum) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuVo, menuDTO);
        MenuQuery menuQuery = new MenuQuery();
        MenuQuery.Criteria criteria = menuQuery.createCriteria();
        if (null != menuDTO.getMenuId()) {
            criteria.andIdEqualTo(menuDTO.getMenuId());
        }
        if (StringUtils.isNotEmpty(menuDTO.getMenuName())) {
            criteria.andmenuNameLike("%" + menuDTO.getMenuName() + "%");
        }
        if (StringUtils.isNotEmpty(menuDTO.getStaticRouter())) {
            criteria.andStaticRouterLike("%" + menuDTO.getStaticRouter() + "%");
        }
        if (StringUtils.isNotEmpty(menuDTO.getStatus())) {
            criteria.andStatusEqualTo(menuDTO.getStatus());
        }
        menuQuery.setOrderByClause("create_time desc,menu_id asc");
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            menuQuery.setPageNumAndPageSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        List<?> listAndTotal = menuDao.selectByExample(menuQuery);
        List<MenuDTO> menus = (List<MenuDTO>) listAndTotal.get(0);
        Long total = ((List<Long>) listAndTotal.get(1)).get(0);
        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        for (MenuDTO menu : menus) {
            MenuVo menuvo = new MenuVo();
            BeanUtils.copyProperties(menu, menuvo);
            menuVos.add(menuvo);
        }
        return CommonPage.restPage(menuVos, pageNum, pageSize, total);
    }

    /**
     * 获得菜单的id数据
     *
     * @param outMenuName 排除菜单名
     * @return
     */
    @Override
    public String findMenusIds(String outMenuName) {
        StringBuilder sb = new StringBuilder();
        List<MenuDTO> menus = menuDao.findList();
        for (MenuDTO menuDTO : menus) {
            if (StringUtils.isNotEmpty(outMenuName) && outMenuName.indexOf(menuDTO.getMenuName()) != -1) {
                continue;
            }
            sb.append(menuDTO.getMenuId() + ",");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    public String findExistMenusIds(String menuName) {
        StringBuilder sb = new StringBuilder();
        List<MenuDTO> menus = menuDao.findList();
        for (MenuDTO menuDTO : menus) {
            if (StringUtils.isNotEmpty(menuName) && menuName.indexOf(menuDTO.getMenuName()) != -1) {
                sb.append(menuDTO.getMenuId() + ",");
                if (!sb.toString().contains(menuDTO.getParentMenuId() + ",")) {
                    sb.append(menuDTO.getParentMenuId() + ",");
                }
            }
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    public boolean IsExistInfoName(String name) {
        boolean flag = false;
        MenuQuery menuQuery = new MenuQuery();
        if (StringUtils.isNotEmpty(name)) {
            menuQuery.createCriteria().andmNameEqualTo(name);
        }
        List<?> listAndTotal = menuDao.selectByExample(menuQuery);
        List<Menu> menus = (List<Menu>) listAndTotal.get(0);
        if (null != menus && menus.size() > 0) {
            flag = true;
        }
        return flag;
    }
}
