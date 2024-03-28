package com.soft.mapp.basecenter.impl.services;

import com.soft.mapp.basecenter.dao.ISysLogDao;
import com.soft.mapp.basecenter.domain.SysLog.SysLogPo;
import com.soft.mapp.basecenter.domain.SysLog.SysLogQuery;
import com.soft.mapp.basecenter.domain.SysLog.SysLogVo;
import com.soft.mapp.basecenter.handler.CommonBusiness;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.ISysLogService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    ISysLogDao sysLogDao;

    @Override
    public String delLogInfo(List<String> ids) {
        int count = 0;
        SysLogQuery sysLogQuery = new SysLogQuery();
        SysLogQuery.Criteria criteria = sysLogQuery.createCriteria();
        List<Integer> idsInt = new ArrayList<Integer>();
        for (String str : ids) {
            idsInt.add(ConvertUtils.toInt(str));
        }
        criteria.andIdIn(idsInt);
        count = sysLogDao.deleteByExample(sysLogQuery);
        if (count == 0) {
            return "删除log错误";
        }
        return null;
    }

    @Override
    public String addLogInfo(SysLogPo sysLogPo) {
        int count = 0;
        count = sysLogDao.addInfo(sysLogPo);
        if (count == 0) {
            return "添加log错误";
        }
        return null;
    }
    @Override
    public CommonPage<SysLogPo> getLogInfo(Integer pageSize, Integer pageNum, Integer sort, SysLogVo sysLogVo) {
        SysLogQuery sysLogQuery = new SysLogQuery();
        SysLogQuery.Criteria criteria = sysLogQuery.createCriteria();
        if (StringUtil.isNotEmpty(sysLogVo.getLogType())) {
            criteria.andLogTypeEqualTo(sysLogVo.getLogType());
        }
        if (StringUtil.isNotEmpty(sysLogVo.getMsg())) {
            criteria.andMsgLike(sysLogVo.getMsg());
        }
        if (StringUtil.isNotEmpty(sysLogVo.getUserName())) {
            criteria.andUserNameLike(sysLogVo.getUserName());
        }
        //操作时间
        if(StringUtil.isNotEmpty(sysLogVo.getCreateDateFrom()) && StringUtil.isNotEmpty(sysLogVo.getCreateDateTo()) ){
            criteria.andCreateTimeBetween(sysLogVo.getCreateDateFrom(),sysLogVo.getCreateDateTo());
        }

        if (StringUtil.isNotEmpty(sysLogVo.getMId()) && !CommonBusiness.isAdmin()) {
            criteria.andMIdEqualTo(sysLogVo.getMId());
        }
        if (StringUtil.isNotEmpty(sysLogVo.getType()) && !"0".equals(sysLogVo.getType())) {
            criteria.andTypeEqualTo(sysLogVo.getType());
        }
        if (StringUtil.isNotEmpty(sysLogVo.getOperationStartTime()) && StringUtil.isNotEmpty(sysLogVo.getOperationEndTime())) {
            criteria.andCreateTimeBetween(sysLogVo.getOperationStartTime(), sysLogVo.getOperationEndTime());
        }
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            sysLogQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        if (null != sort) {
            //排序 0，升序，1，降序
            String sortStr = "create_time";
            if (sort == 0) {
                sortStr = "create_time desc";
            }
            sysLogQuery.setOrderByClause(sortStr);
        }
        List<?> list = sysLogDao.getPageSysLog(sysLogQuery);
        List<SysLogPo> sysLogPoList = (List<SysLogPo>) list.get(0);
        Long total = ((List<Long>) list.get(1)).get(0);
        return CommonPage.restPage(sysLogPoList, pageNum, pageSize, total);
    }
}
