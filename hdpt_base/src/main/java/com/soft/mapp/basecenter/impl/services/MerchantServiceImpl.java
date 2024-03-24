package com.soft.mapp.basecenter.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.soft.mapp.basecenter.dao.IMerchantDao;
import com.soft.mapp.basecenter.dao.IUserDao;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.domain.loginVo.UserDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.domain.merchant.MerchantPo;
import com.soft.mapp.basecenter.domain.merchant.MerchantQuery;
import com.soft.mapp.basecenter.domain.merchant.MerchantVo;
import com.soft.mapp.basecenter.handler.CommonBusiness;
import com.soft.mapp.basecenter.handler.CommonPage;
import com.soft.mapp.basecenter.services.IMerchantService;
import com.soft.mapp.basecenter.services.IUserService;
import com.soft.mapp.basecenter.services.IWechatService;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantServiceImpl implements IMerchantService {
    @Autowired
    IMerchantDao merchantDao;
    @Autowired
    IUserService userService;
    @Autowired
    IUserDao userDao;
    @Autowired
    IWechatService wechatService;
    @Override
    public int addMerchant(MerchantVo merchantVo) {

        MerchantPo merchantPo=new MerchantPo();
        BeanUtils.copyProperties(merchantVo,merchantPo);
        if(null==merchantPo.getIsParent()){
            merchantPo.setIsParent(0);
        }
       return merchantDao.addMerchant(merchantPo);

    }

    @Override
    public int updateMerchant(MerchantVo merchantVo) {
        MerchantPo merchantPo=new MerchantPo();
        BeanUtils.copyProperties(merchantVo,merchantPo);
        MerchantQuery merchantQuery = new MerchantQuery();
        MerchantQuery.Criteria criteria = merchantQuery.createCriteria();
        merchantPo.setUpdateTime(DateUtils.getYYYYMMDDHHMMSSDate(new Date()));
        if(merchantVo.getMId()!=null ){
            criteria.andIdEqualTo(merchantVo.getMId());
            return merchantDao.updateByExampleSelective(merchantPo,merchantQuery);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteMerchant(List<Integer> mids) {
        MerchantQuery merchantQuery = new MerchantQuery();
        if(null!=mids &&mids.size()>0){
            merchantQuery.createCriteria().andIdIn(mids);
        }
        return merchantDao.deleteByExample(merchantQuery);
    }

    @Override
    public List<MerchantVo> findList(MerchantVo merchantVo) {
        CommonPage<MerchantVo> page=getInfoBySelect(merchantVo,null,null);
        return page.getList();
    }

    @Override
    public CommonPage<MerchantVo>  getPageMerchant(MerchantVo merchantVo, Integer pageSize, Integer pageNum) {
       // PageHelper.startPage(pageNum, pageSize);
        return getInfoBySelect(merchantVo,pageSize,pageNum);
    }

    /**
     * 按条件查询
     * @param merchantVo
     * @return
     */
    private CommonPage<MerchantVo> getInfoBySelect(MerchantVo merchantVo,Integer pageSize, Integer pageNum){
        MerchantQuery merchantQuery = new MerchantQuery();
        MerchantQuery.Criteria criteria = merchantQuery.createCriteria();
        if(merchantVo.getMId()!=null && !CommonBusiness.isAdmin()){
            criteria.andParentEqualTo(merchantVo.getMId());
            MerchantQuery.Criteria criteria1 = merchantQuery.createCriteria();
            criteria1.andIdEqualTo(merchantVo.getMId());
            merchantQuery.or(criteria1);
        }
        if(StringUtils.isNotEmpty(merchantVo.getMName())){
            criteria.andmNameLike(merchantVo.getMName());
        }
        if(StringUtils.isNotEmpty(merchantVo.getMStatus())){
            criteria.andmStatusEqualTo(merchantVo.getMStatus());
        }
        if(StringUtils.isNotEmpty(merchantVo.getDevice())){
            criteria.andDeviceEqualTo(merchantVo.getDevice());
        }
        if(StringUtils.isNotEmpty(merchantVo.getAddress())){
            criteria.andmAddressLike(merchantVo.getAddress());
        }
        if(StringUtils.isNotEmpty(merchantVo.getMCode())){
            criteria.andmCodeLike(merchantVo.getMCode());
        }
        //查找is_parent=0 父项目
        if(null!=merchantVo.getParentMid() && 0!=merchantVo.getParentMid()) {
            criteria.andIsChildren(merchantVo.getParentMid());
        }
        if(null!=merchantVo.getIsParent() && 0==merchantVo.getIsParent()){
            criteria.andIsParent();
        }
        if(null!=merchantVo.getIsParent() && 1==merchantVo.getIsParent()){
            criteria.andNotIsParent();
        }
        if(StringUtils.isNotEmpty(merchantVo.getUserId())&&!CommonBusiness.isAdmin() ){
            Integer parentMid=getParentMidsByUserId(merchantVo.getUserId());
            //admin mid=null 查看所有
            if(parentMid!=null) {
                criteria.andIsChildren(parentMid);
            }
        }
        merchantQuery.setOrderByClause("create_time desc,m_id asc");
        if(null!=pageNum && null!=pageSize ){
         String  fromRow= ConvertUtils.toString((pageNum-1)*pageSize);
        merchantQuery.setPageNumAndPageSize(fromRow+","
                +ConvertUtils.toString(pageSize));
        }
        List<?> listAndTotal=merchantDao.getPageMerchant(merchantQuery);
        List<MerchantPo> list=(List<MerchantPo>)listAndTotal.get(0);
        Long total=((List<Long>) listAndTotal.get(1)).get(0);
        List<MerchantVo> datas=new ArrayList<MerchantVo>();
        if(null!=list){
            for(MerchantPo item:list){
                MerchantVo merchant=new MerchantVo();
                BeanUtils.copyProperties(item,merchant);
                if(StringUtils.isEmpty(item.getWxSmallQRCodeUrl())){
                    String qrcCodeUrl= wechatService.getWxSmarlQrcCodeUrl(item.getMId());
                    merchant.setWxSmallQRCodeUrl(qrcCodeUrl);
                    updateMerchant(merchant);
                }
                Map<String,String> map= getDeviceTypeByMid(merchant.getMId());
                merchant.setDeviceType(map);
                //merchant.setMUserName(UserInfoContext.getUser().getName());
                List<MerchantPo> children= getMerchantChildList(item.getMId());
                merchant.setChildren(children);
                merchant.setMUserName(getUserNamesByMid(merchant.getMId()));
                datas.add(merchant);
            }
        }
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=1;
        }
        return  CommonPage.restPage(datas, pageNum, pageSize, total);
    }

    /**
     * 根据mid获得设备类型
     * @param mid
     * @return
     */
    private Map<String,String> getDeviceTypeByMid(Integer mid){
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        //查项目的智能设备
        String sql="SELECT typecode,devicetypename from t_device_type a WHERE  exists(SELECT 1 FROM t_device t WHERE t.MID="+mid+" and t.DEVICETYPECODE=a.typeCode) order by a.TYPEORDER asc";
        List<Map<String, Object>> sqlMapList= SqlRunner.db().selectList(sql);
        if(CollectionUtil.isNotEmpty(sqlMapList)){
            for(Map<String,Object> item:sqlMapList){
               map.put(item.get("typecode").toString(),item.get("devicetypename").toString());
            }
        }
        //查项目的网络设备
        String sql2="SELECT ac_device_name from net_device a WHERE  a.m_id="+mid;
        List<Map<String, Object>> sqlMapList1= SqlRunner.db().selectList(sql2);
        if(CollectionUtil.isNotEmpty(sqlMapList1)){
            for(Map<String,Object> item:sqlMapList1){
                map.put("0100","网络设备");
            }
        }
        String sql3="SELECT cabinet_name from cabinet a WHERE  a.m_id="+mid;
        List<Map<String, Object>> sqlMapList2= SqlRunner.db().selectList(sql3);
        if(CollectionUtil.isNotEmpty(sqlMapList2)){
            for(Map<String,Object> item:sqlMapList1){
                map.put("0104","器具柜设备");
            }
        }
        return map;
    }

    private String getUserNamesByMid(Integer mId){
        String userNames="";
        UserVo user=new UserVo();
        user.setMId(mId);
        List<?> listAndTotal1 =userService.findUsers(user,null,null);
        if(listAndTotal1==null || listAndTotal1.size()==0 ){
            return  userNames;
        }
        List<UserDTO> userDTOs = (List<UserDTO>) listAndTotal1.get(0);
        if(userDTOs==null || userDTOs.size()==0 ){
            return  userNames;
        }
        for (UserDTO userDTO:userDTOs){
            userNames=userNames+userDTO.getName()+";";
        }
        if(StringUtils.isNotEmpty(userNames)){
            userNames=userNames.substring(0,userNames.length()-1);
        }
        return userNames;
    }

    private Integer getParentMidsByUserId(String userId){
        List<Integer> mIds=new ArrayList<>();
        User user=userDao.selectByuserId(ConvertUtils.toInt(userId));
        //没有mid时，查询所有记录信息
        if(null==user || null==user.getMId()){
            return  null;
        }
        return  user.getMId();
    }

    /**
     * 获得子信息
     * @param mid
     * @return
     */
    private  List<MerchantPo> getMerchantChildList(Integer mid){
        MerchantQuery merchantQuery = new MerchantQuery();
        MerchantQuery.Criteria criteria = merchantQuery.createCriteria();
        criteria.andIsChildren(mid);
        criteria.andNotIsParent();
        List<?> listAndTotal=merchantDao.getPageMerchant(merchantQuery);
        List<MerchantPo> list=(List<MerchantPo>)listAndTotal.get(0);
        return  list;
    }
    @Override
    public boolean IsExistMerchanName(String name) {
        boolean flag=false;
        MerchantQuery merchantQuery = new MerchantQuery();
        if(StringUtils.isNotEmpty(name)){
            merchantQuery.createCriteria().andmNameEqualTo(name);
        }
        List<?> listAndTotal=merchantDao.getPageMerchant(merchantQuery);
        List<MerchantPo> merchantPoList=(List<MerchantPo>)listAndTotal.get(0);
        if(merchantPoList!=null && merchantPoList.size()>0){
            flag=true;
        }
        return flag;
    }
}
