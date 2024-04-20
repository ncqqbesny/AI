package com.app.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.app.device.dao.ICabinetDao;
import com.app.device.domain.Cabinet.CabinetDTO;
import com.app.device.domain.Cabinet.CabinetQuery;
import com.app.device.domain.Cabinet.CabinetQueryVo;
import com.app.device.domain.Cabinet.CabinetVo;
import com.app.device.handler.CommonBusiness;
import com.app.device.services.IHardwareCabQueryService;
import com.app.device.utils.CommonPage;
import com.app.device.utils.ConvertUtils;
import com.app.device.utils.DateUtils;
import com.app.device.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HardwareCabQueryService implements IHardwareCabQueryService {

    @Autowired
    ICabinetDao cabinetDao;

    private final static Logger log = LoggerFactory.getLogger(HardwareCabQueryService.class);

    @Override
    public CommonPage<CabinetVo> getPageCabList(Integer pageSize, Integer pageNum, Integer sort, CabinetQueryVo cabinetQueryVo) {
        CabinetQuery cabinetQuery = getCabinetQuery(pageSize, pageNum, sort, cabinetQueryVo);
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            cabinetQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        if (null != sort) {
            //排序 0，升序，1，降序
            //排序 0，降序，1，升序
            String sortStr = "if(`cabinet_status` is NULL OR `cabinet_status`='' ,'0',`cabinet_status`),create_time";
            if (sort == 0) {
                sortStr = "if(`cabinet_status` is NULL OR `cabinet_status`='' ,'0',`cabinet_status`) desc,create_time desc";
            }
            cabinetQuery.setOrderByClause(sortStr);
        }
        List<?> list = cabinetDao.getPageByExample(cabinetQuery);
        List<CabinetDTO> cabinetDTOS = (List<CabinetDTO>) list.get(0);
        Long total = ((List<Long>) list.get(1)).get(0);
        return CommonPage.restPage(getCabinetVoByDTO(cabinetDTOS), pageNum, pageSize, total);
    }

    private CabinetQuery getCabinetQuery(Integer pageSize, Integer pageNum, Integer sort, CabinetQueryVo cabinetQueryVo) {
        CabinetQuery cabinetQuery = new CabinetQuery();
        CabinetQuery.Criteria criteria = cabinetQuery.createCriteria();
        if (StringUtil.isNotEmpty(cabinetQueryVo.getProvince())) {
            criteria.andProvinceLike(cabinetQueryVo.getProvince());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getCity())) {
            criteria.andCityLike(cabinetQueryVo.getCity());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getArea())) {
            criteria.andAreaLike(cabinetQueryVo.getArea());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getCabinetName())) {
            criteria.andCabinetNameLike(cabinetQueryVo.getCabinetName());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getMac())) {
            criteria.andMacLike(cabinetQueryVo.getMac());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getIsMain())) {
            criteria.andIsMainEqualTo(cabinetQueryVo.getIsMain());
        }

        if (StringUtil.isNotEmpty(cabinetQueryVo.getIp())) {
            criteria.andIpLike(cabinetQueryVo.getIp() );
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getCabinetNo())) {
            criteria.andCabinetNoLike(cabinetQueryVo.getCabinetNo());
        }
        if (StringUtil.isNotEmpty(cabinetQueryVo.getCabdesc())) {
            criteria.andCabdescLike(cabinetQueryVo.getCabdesc());
        }
        if(StringUtil.isNotEmpty(cabinetQueryVo.getCabinetStatus())){
            criteria.andCabinetStatusEqualTo(cabinetQueryVo.getCabinetStatus());
        }
        if(StringUtil.isNotEmpty(cabinetQueryVo.getBatchNo())){
            criteria.andBatchNoEqualTo(cabinetQueryVo.getBatchNo());
        }
        if(StringUtil.isNotEmpty(cabinetQueryVo.getMId()) && !CommonBusiness.isAdmin()){
            CabinetQuery cabinetQuery1 = new CabinetQuery();
            CabinetQuery.Criteria criteria1 = cabinetQuery1.createCriteria();
            criteria.andParentEqualTo(cabinetQueryVo.getMId());
            criteria1.andOrMidEqualTo(cabinetQueryVo.getMId());
            cabinetQuery.or(criteria1);
        }
        criteria.andCabinetNoNotNull("");
        return cabinetQuery;
    }

    private List<CabinetVo> getCabinetVoByDTO(List<CabinetDTO> list) {
        List<CabinetVo> cabinetVos = new ArrayList<>();
        for (CabinetDTO cabinetDTO : list) {
            CabinetVo cabinetVo = new CabinetVo();
            BeanUtils.copyProperties(cabinetDTO, cabinetVo);
            if (null != cabinetDTO.getRunTime()) {
                cabinetVo.setWorkLineTime(DateUtils.getDatePoor(new Date(), cabinetDTO.getRunTime()));
            } else {
                cabinetVo.setWorkLineTime(DateUtils.getDatePoor(new Date(), cabinetDTO.getCreateTime()));
            }
            cabinetVos.add(cabinetVo);
        }
        return cabinetVos;
    }

    @Override
    public List<CabinetVo> selectByIds(List<Integer> ids) {
        CabinetQuery cabinetQuery = new CabinetQuery();
        CabinetQuery.Criteria criteria = cabinetQuery.createCriteria();
        if (CollectionUtil.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        return getCabinetVoByDTO(cabinetDao.findListByWhere(cabinetQuery));
    }

    @Override
    public List<CabinetVo> findList(CabinetQueryVo cabinetVo) {
        CabinetQuery cabinetQuery =getCabinetQuery(null,null,null,cabinetVo);
        return getCabinetVoByDTO(cabinetDao.findListByWhere(cabinetQuery));
    }

    @Override
    public List<CabinetVo> selectByMids(List<Integer> mIds) {
        CabinetQuery cabinetQuery = new CabinetQuery();
        CabinetQuery.Criteria criteria = cabinetQuery.createCriteria();
        criteria.andMidIn(mIds);
        return getCabinetVoByDTO(cabinetDao.findListByWhere(cabinetQuery));
    }


}
