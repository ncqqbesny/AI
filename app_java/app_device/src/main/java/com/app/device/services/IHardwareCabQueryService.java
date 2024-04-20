package com.app.device.services;

import com.app.device.domain.Cabinet.CabinetQueryVo;
import com.app.device.domain.Cabinet.CabinetVo;
import com.app.device.utils.CommonPage;

import java.util.List;

public interface IHardwareCabQueryService {
    // 99020100获得柜信息
    public CommonPage<CabinetVo> getPageCabList(Integer pageSize, Integer pageNum, Integer sort, CabinetQueryVo cabinetQueryVo);
    List<CabinetVo> selectByIds(List<Integer> ids);
    List<CabinetVo> findList(CabinetQueryVo cabinetVo);
    List<CabinetVo> selectByMids(List<Integer> mIds);
}
