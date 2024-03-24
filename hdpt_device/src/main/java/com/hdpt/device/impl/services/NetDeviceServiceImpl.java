package com.hdpt.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.hdpt.device.dao.INetDeviceDao;
import com.hdpt.device.dao.ISysTokenDao;
import com.hdpt.device.dao.IUserDao;
import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceQuery;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.domain.loginVo.SysToken;
import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.handler.UserInfoContext;
import com.hdpt.device.impl.controller.HardCabQueryController;
import com.hdpt.device.services.INetDeviceQueryService;
import com.hdpt.device.services.INetDeviceService;
import com.hdpt.device.services.IShiroService;
import com.hdpt.device.utils.DateUtils;
import com.hdpt.device.utils.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NetDeviceServiceImpl implements INetDeviceService {

    @Autowired
    INetDeviceDao netDeviceDao;


    @Override
    public String addInfo(NetDeviceDTO netDeviceDTO) {
        return null;
    }

    @Override
    public String updateByExampleSelective(List<NetDeviceVo> saveListInfo) {
        for (NetDeviceVo netDeviceVo : saveListInfo) {
            if(isExistDeviceMac(netDeviceVo)){
                return "存在别的相同MAC地址不能保存";
            }
            NetDeviceDTO record = new NetDeviceDTO();
            NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
            NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
            if (StringUtil.isNotEmpty(netDeviceVo.getId())) {
                criteria.andIdEqualTo(netDeviceVo.getId());
            }
            BeanUtils.copyProperties(netDeviceVo, record);
            int count = netDeviceDao.updateByExampleSelective(record,netDeviceQuery);
            if (count == 0) {
                return "没有可更新数数据";
            }
        }
        return null;
    }
    private final static Logger log = LoggerFactory.getLogger(NetDeviceServiceImpl.class);

    @Override
    public String delInfo(List<Integer> ids) {
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
        if (CollectionUtil.isNotEmpty(ids)) {
            criteria.andIdIn(ids);
        }
        int count = netDeviceDao.deleteByExample(netDeviceQuery);
        if (count == 0) {
            return "没有数据可删除";
        }
        return null;
    }

    @Override
    public String insertOrUpdate(String data) {
        return null;
    }

    @Override
    public String saveListInfo(List<NetDeviceVo> saveListInfo) {

        for (NetDeviceVo netDeviceVo : saveListInfo) {
            if(isExistDeviceSn(netDeviceVo)){
                return "存在相同设备编号不能保存";
            }
            NetDeviceDTO record = new NetDeviceDTO();
            NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
            NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
            if (StringUtil.isNotEmpty(netDeviceVo.getAcIp())) {
                criteria.andAcIpLike("%" + netDeviceVo.getAcIp() + "%");
            }
            if (StringUtil.isNotEmpty(netDeviceVo.getAddress())) {
                criteria.andAddressLike("%" + netDeviceVo.getAddress() + "%");
            }
            if (StringUtil.isNotEmpty(netDeviceVo.getAcMac())) {
                criteria.andAcMacLike("%" + netDeviceVo.getAcMac() + "%");
            }
            if (StringUtil.isNotEmpty(netDeviceVo.getId())) {
                criteria.andIdEqualTo(netDeviceVo.getId());
            }
            List<NetDeviceDTO> netDeviceDTOS = netDeviceDao.findList(netDeviceQuery);
            BeanUtils.copyProperties(netDeviceVo, record);
            if (CollectionUtil.isNotEmpty(netDeviceDTOS) && netDeviceDTOS.size() > 0) {
                NetDeviceDTO netDeviceDTO = netDeviceDTOS.get(0);
                record.setAcMac(netDeviceDTO.getAcMac());
                record.setAcDeviceSn(netDeviceDTO.getAcDeviceSn());
                record.setId(netDeviceDTO.getId());
            }
            int count = netDeviceDao.insertOrUpdate(record);
            if (count == 0) {
                return "没有可更新数数据";
            }
        }
        return null;
    }

    /**
     * 存在相同设备编号
     * @param netDeviceVo
     * @return
     */
    private  boolean isExistDeviceSn(NetDeviceVo netDeviceVo){
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
        if (StringUtil.isNotEmpty(netDeviceVo.getAcDeviceSn()) && (null==netDeviceVo.getId() || 0==netDeviceVo.getId())) {
            criteria.andAcDeviceSnEqualTo(netDeviceVo.getAcDeviceSn());
            List<NetDeviceDTO> netDeviceDTOS = netDeviceDao.findList(netDeviceQuery);
            if (CollectionUtil.isNotEmpty(netDeviceDTOS) && netDeviceDTOS.size() > 0) {
                return  true;
            }
        }
        return false;
    }

    /**
     * 存在相同设备MAC
     * @param netDeviceVo
     * @return
     */
    private  boolean isExistDeviceMac(NetDeviceVo netDeviceVo){
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
        if (StringUtil.isNotEmpty(netDeviceVo.getAcMac()) && null!=netDeviceVo.getId() ) {
            criteria.andAcMacEqualTo(netDeviceVo.getAcMac());
            criteria.andIdNotEqualTo(netDeviceVo.getId());
            List<NetDeviceDTO> netDeviceDTOS = netDeviceDao.findList(netDeviceQuery);
            if (CollectionUtil.isNotEmpty(netDeviceDTOS) && netDeviceDTOS.size() > 0) {
                return  true;
            }
            return false;
        }else {
            return false;
        }
    }

    @Override
    public String saveExcelInfo(List<NetDeviceVo> saveListInfo,String remark) {
        List<NetDeviceDTO> list=new ArrayList<>();
        String msg="";
        User context = UserInfoContext.getUser();
        List<String> macs=new ArrayList<>();
        for (NetDeviceVo netDeviceVo : saveListInfo) {
            netDeviceVo.setOutTime(DateUtils.getDateByString(netDeviceVo.getOutDate(), "yyyy.MM.dd"));
            netDeviceVo.setMId(context.getMId());
            netDeviceVo.setRemark(context.getUsername() + "在" + remark);
            List<String> macList=getMac(netDeviceVo.getAcMac(),msg);
            if(StringUtil.isNotEmpty(msg)){
                return msg;
            }
            if(CollectionUtil.isEmpty(macList)) {
                NetDeviceDTO netDeviceDTO = new NetDeviceDTO();
                BeanUtils.copyProperties(netDeviceVo, netDeviceDTO);

                netDeviceDTO.setAcMac("UUID_"+UUID.randomUUID().toString().replace("-",""));
                list.add(netDeviceDTO);
                continue;
            }
            for(String str:macList){
                NetDeviceDTO netDeviceDTO1 = new NetDeviceDTO();
                BeanUtils.copyProperties(netDeviceVo, netDeviceDTO1);
                netDeviceDTO1.setAcMac(str);
                macs.add(str);
                list.add(netDeviceDTO1);
            }
        }
        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(NetDeviceDTO::getAcMac, Collectors.counting()));
        List<NetDeviceDTO> listall=new ArrayList<>();
        for(String key:map.keySet()){
            Long value = map.get(key);
            if(value>=2){
             List<NetDeviceDTO> list1= list.stream().filter(netDeviceDTO ->key.equals(netDeviceDTO.getAcMac())).collect(Collectors.toList());
             listall.addAll(list1);
            }
        }
        if(CollectionUtil.isNotEmpty(listall)) {
            return "重复的macList:" + JSON.toJSONString(listall);
        }
        List<NetDeviceDTO> upateList=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(macs)) {
                NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
                NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
                criteria.andAcMacIn(macs);
                List<NetDeviceDTO> netDeviceDTOS = netDeviceDao.findList(netDeviceQuery);
                if (CollectionUtil.isNotEmpty(netDeviceDTOS)) {
                    for (NetDeviceDTO item : netDeviceDTOS) {
                        //存在的mac进行更新
                        Optional<NetDeviceDTO> netDeviceDTOOptional = list.stream().filter(netDeviceDTO2 -> item.getAcMac().equals(netDeviceDTO2.getAcMac())).findFirst();
                        NetDeviceDTO netDeviceDTO = netDeviceDTOOptional.get();
                        netDeviceDTO.setRemark(DateUtils.getYYYYMMDDHHMMSSDate(new Date()) + "_导入更新," + netDeviceDTOS.get(0).getRemark());
                        upateList.add(netDeviceDTO);
                        //清除已更新的数据
                        list.remove(netDeviceDTO);

                    }
                }
        }
        try {
            if(CollectionUtil.isNotEmpty(upateList)){
                int count=netDeviceDao.updateBatchByNative(upateList);
                if(count==0){
                    log.info("saveExcelInfo-导入更新excel没有数据");
                }
            }
            if(CollectionUtil.isNotEmpty(list)){
                boolean flag=netDeviceDao.saveBatchByNative(list);
                if(!flag){
                    log.info("saveExcelInfo-导入excel程序错误"+list);
                }
            }

        }catch (Exception e){
            log.info("saveExcelInfo 导入异常"+e);
            msg="导入程序执行错误，请检查数据";
        }
        return msg;
    }
    private List<String> getMac(String str,String msg) {
        List<String> stringList=new ArrayList<>();
        if(StringUtil.isEmpty(str)){
            return stringList;
        }
        String [] strs=str.split("/");
        for(int j=0;j<strs.length;j++) {
            String item=strs[j].replace("\n","");
            int strlenth = item.length();
            String firstItem=strs[0].replace("\n","");
            if(strlenth<7 && firstItem.length()>=12){
                item=firstItem.substring(0,firstItem.length()-strlenth)+item;
                strlenth = item.length();
            }
            if(item.length()>13){
                log.info("getMac--没有导入mac(超过了13位）:"+item);
                msg="导入mac(超过了数据位）:"+item;
                continue;
            }
            int blankcount = 0;
            if (strlenth <= 2) {
                blankcount = 0;
            } else {
                blankcount = strlenth % 2 > 0 ? strlenth / 2 : item.length() / 2 - 1; //需要加空格数量
            }
            if (blankcount > 0 && !item.contains("-") && item.length()>=12) {
                for (int i = 0; i < blankcount; i++) {
                    item = item.substring(0, (i + 1) * 2 + i) + "-" + item.substring((i + 1) * 2 + i, strlenth + i);
                }
            }
            stringList.add(item);
        }
        return stringList;
    }

    @Override
    public String netDevUpInfo(String data) {
        return null;
    }

}
