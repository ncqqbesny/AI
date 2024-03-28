package com.hdpt.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.hdpt.device.dao.ICabinetDao;
import com.hdpt.device.domain.Cabinet.CabinetDTO;
import com.hdpt.device.domain.Cabinet.CabinetQuery;
import com.hdpt.device.domain.Cabinet.CabinetSaveVO;
import com.hdpt.device.domain.hdUp.NetDeviceQuery;
import com.hdpt.device.impl.controller.HardwareRevController;
import com.hdpt.device.services.IHardwareCabService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareCabService implements IHardwareCabService {
    private final static Logger log = LoggerFactory.getLogger(HardwareCabService.class);
    @Autowired
    ICabinetDao cabinetDao;

    @Override
    public String saveCabinet(CabinetSaveVO cabinetSaveVO) {
        CabinetDTO cabinetDTO = new CabinetDTO();
        BeanUtils.copyProperties(cabinetSaveVO, cabinetDTO);
        int count = cabinetDao.insertOrUpdate(cabinetDTO);
        if (count == 0) {
            log.info("init save cabinet:" + cabinetDTO);
            return "没有数据保存到";
        }
        return null;
    }

    @Override
    public String delInfo(List<Integer> ids) {
        CabinetQuery cabinetQuery = new CabinetQuery();
        CabinetQuery.Criteria criteria = cabinetQuery.createCriteria();
        if(CollectionUtil.isNotEmpty(ids)){
            criteria.andIdIn(ids);
        }
        int count=cabinetDao.deleteByExample(cabinetQuery);
        if(count==0){
            return "没有数据可删除";
        }
        return null;
    }


}
