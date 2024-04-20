package com.app.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.device.dao.INetDeviceDao;
import com.app.device.domain.hdUp.NetDeviceDTO;
import com.app.device.domain.hdUp.NetDeviceQuery;
import com.app.device.domain.hdUp.NetDeviceVo;
import com.app.device.utils.*;
import com.app.device.handler.CommonBusiness;
import com.app.device.services.INetDeviceQueryService;
import com.app.device.services.INetDeviceService;
import com.app.device.type.BaseConst;
import com.app.device.type.DeviceTypeEnum;
import com.app.device.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NetDeviceQueryServiceImpl implements INetDeviceQueryService {
    private final static Logger log = LoggerFactory.getLogger(NetDeviceQueryServiceImpl.class);
    @Autowired
    INetDeviceDao netDeviceDao;

    @Override
    public List<NetDeviceDTO> selectByIds(List<Integer> ids) {
        NetDeviceQuery netDeviceQuery=new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria=netDeviceQuery.createCriteria();
        if(CollectionUtil.isNotEmpty(ids)){
            criteria.andIdIn(ids);
        }
        return netDeviceDao.findList(netDeviceQuery);
    }

    @Override
    public List<NetDeviceDTO> findList(NetDeviceVo netDeviceVo) {
        NetDeviceQuery netDeviceQuery=getNetQuery(null,null,null,netDeviceVo);
        NetDeviceQuery.Criteria criteria=netDeviceQuery.createCriteria();
        return netDeviceDao.findList(netDeviceQuery);
    }

    @Override
    public List<NetDeviceDTO> selectByMids(List<Integer> mIds,List<String> deviceTypes) {
        NetDeviceQuery netDeviceQuery=new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria=netDeviceQuery.createCriteria();
        if(CollectionUtil.isNotEmpty(mIds)){
            criteria.andMidIn(mIds);
        }
        if(CollectionUtil.isNotEmpty(deviceTypes)){
            criteria.andDeviceTypeIn(deviceTypes);
            if(deviceTypes.contains(ConvertUtils.toString(DeviceTypeEnum.terminal.ordinal()))){
                criteria.andStaSignalIsNotNull();
            }
        }
        List<Map> mapList=getSystemParam("terminal_sort_order");
        boolean  calculateFlag=false;
        for(Map map:mapList) {
            String paramCode=ConvertUtils.toString(map.get("param_code"));
            String paramName=ConvertUtils.toString(map.get("param_name"));
            String paramVal=ConvertUtils.toString(map.get("param_val"));
            if(paramCode.equalsIgnoreCase("d") && paramVal.equals("1") ){
                calculateFlag=true;
            }
        }
        List<NetDeviceDTO> netDeviceDTOList=netDeviceDao.findList(netDeviceQuery);
        for(NetDeviceDTO netDeviceDTO:netDeviceDTOList){
            if(netDeviceDTO.getDeviceType().equals(ConvertUtils.toString(DeviceTypeEnum.terminal.ordinal()))) {
                Integer staOrderInt = 0;
                //按计算公试所得距离
                if(calculateFlag){
                    //按FSPL= 20 * log10(d) + 20 * log10(f) + 20 * log10(4 * π / c)-Gt-Gr 计算得距离
                    //c=c为光速（约3 * 10^8 m/s）
                    staOrderInt=getCalculateSortOrder(netDeviceDTO);
                }else{ //按实测试距离所得
                    staOrderInt=getActualTestSortOrder(netDeviceDTO,mapList);
                }
                netDeviceDTO.setDisplaySortorder(staOrderInt);
                if(staOrderInt>0){
                    NetDeviceQuery netDeviceQuery1=new NetDeviceQuery();
                    netDeviceQuery1.createCriteria().andIdEqualTo(netDeviceDTO.getId());
                    int count=netDeviceDao.updateByExampleSelective(netDeviceDTO,netDeviceQuery1);
                    log.info("selectByMids--计算标志--"+calculateFlag+"--保存数据--"+netDeviceDTO);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(netDeviceDTOList)) {
            return netDeviceDTOList.stream().sorted(Comparator.comparing(NetDeviceDTO::getDisplaySortorder)).collect(Collectors.toList());
        }else{
            return netDeviceDTOList;
        }
    }

    /**
     * 根据计算公试所得按FSPL= 20 * log10(d) + 20 * log10(f) + 20 * log10(4 * π / c)-Gt-Gr 计算得距离
     * @param netDeviceDTO
     * @return
     */
    private  Integer getCalculateSortOrder(NetDeviceDTO netDeviceDTO){
        Integer result=20;
        if(StringUtil.isEmpty(netDeviceDTO.getParentId())){
            return result;
        }
        NetDeviceDTO netDevice=netDeviceDao.selectById(netDeviceDTO.getParentId());
        //获得上ap 的f频率Frequency:
        Integer f=0;
        //获得ap 的增益Gt Transmitter Gain (dB)
        Integer Gt=0;
        Float Gr=0f;
        if(StringUtil.isJSON(netDevice.getApRadio())){
            JSONObject jsonObjectAll= JSON.parseObject(netDevice.getApRadio());
            JSONObject jsonObject=jsonObjectAll.getJSONObject("mode_2.4g");
            f=ConvertUtils.toInt(jsonObject.getString("bandwidth").replace("MHZ",""));
            Gt=ConvertUtils.toInt(jsonObject.getString("antennagain").replace("dbi",""));
        }
        //GR值目前是没有暂用db进行计算
        //if(null!=netDeviceDTO.getStaSignal()) {
          //  Gr = netDeviceDTO.getStaSignal();
        //}

        Double d=(20*f/100+20*3.14159265359*4/300000000-Gt-Gt)/20;
        if(d<=20){
            result=20;
        }
        if(d>20 && d<=40){
            result=40;
        }
        if(d>40 && d<=60){
            result=60;
        }
        if(d>60 ){
            result=80;
        }
        // 3.14159265359*4/300000000=4.18879E-08
        //c=c为光速（约3 * 10^8 m/s）
        //获得终端的Gr Receiver Gain (dB): 信号强度绝对值
        return  result;
    }



    /**
     * 根实实测试获得距离，20，40，60，80，米距离
     * @param netDeviceDTO
     * @return
     */
    private  Integer getActualTestSortOrder(NetDeviceDTO netDeviceDTO,List<Map> mapList){
        Integer result=20;
        for(Map map:mapList) {
            String paramCode=ConvertUtils.toString(map.get("param_code"));
            String paramName=ConvertUtils.toString(map.get("param_name"));
            String paramVal=ConvertUtils.toString(map.get("param_val"));
            if(StringUtil.isNumeric(paramCode) && paramName.contains("米")){
                String [] paramVals=paramVal.split(",");
                Float minBitStr=0f;
                Float maxBitStr=0f;
                if(paramVals.length>1){
                    minBitStr=Math.abs(ConvertUtils.toFloat(paramVals[0]));
                    maxBitStr=Math.abs(ConvertUtils.toFloat(paramVals[1]));
                }
                Float staSignal=Math.abs(netDeviceDTO.getStaSignal());
                if(staSignal<=maxBitStr && staSignal>minBitStr){
                    result=ConvertUtils.toInt(paramCode);
                    break;
                }
            }
        }
        return  result;
    }

    /**
     * 获得终端参数配置信息
     * @param paramType
     * @return
     */
    public List<Map> getSystemParam(String paramType){
        StringBuilder sb=new StringBuilder();
        sb.append("select * from sys_param where 1=1 ");
        if(StringUtil.isNotEmpty(paramType)){
            sb.append("and param_type='"+paramType+"'");
        }
        List<Map> mapList=netDeviceDao.mySelect(sb.toString());
        return  mapList;
    }

    /**
     * 获得灭火器的配置文件
     * @return
     */
    public Map<String,String> getFirexConfig(){
        List<Map> mapList=getSystemParam("firex_Identify_config");
        Map<String,String> fileParamMap=new HashMap<>();
        for(Map map:mapList) {
            String paramCode= ConvertUtils.toString(map.get("param_code"));
            String paramVal=ConvertUtils.toString(map.get("param_val"));
            if(paramCode.equalsIgnoreCase("text_path") ){
                fileParamMap.put("filePath",paramVal);
            }
            if(paramCode.equalsIgnoreCase("in_filename") ){
                fileParamMap.put("inFileName",paramVal);
            }
            if(paramCode.equalsIgnoreCase("pic_path") ){
                fileParamMap.put("picFilePath",paramVal);
            }
            if(paramCode.equalsIgnoreCase("out_filename")){
                fileParamMap.put("out_filename",paramVal);
            }
            if(paramCode.equalsIgnoreCase("token_url")){
                fileParamMap.put("token_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("device_list_url")){
                fileParamMap.put("device_list_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("request_url")){
                fileParamMap.put("request_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("appid")){
                fileParamMap.put("appid",paramVal);
            }
            if(paramCode.equalsIgnoreCase("accesskey")){
                fileParamMap.put("accesskey",paramVal);
            }
            if(paramCode.equalsIgnoreCase("remote_url")){
                fileParamMap.put("remote_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("subscribe_url")){
                fileParamMap.put("subscribe_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("callback_url")){
                fileParamMap.put("callback_url",paramVal);
            }
            if(paramCode.equalsIgnoreCase("host_url")){
                fileParamMap.put("host_url",paramVal);
            }

        }
        return fileParamMap;
    }

    @Override
    public Map<String, Object> savePicAndTextFile(Map<String,String> fileParamMap) {
        Map<String, Object> resultMap=new HashMap<>();
        resultMap.putAll(fileParamMap);
        resultMap.put("code","200");
        resultMap.put("msg","保存成功");
        int startIndex=0;
        if(fileParamMap.get("picFilePath").lastIndexOf("\\")!=-1){
            startIndex=fileParamMap.get("picFilePath").lastIndexOf("\\");
        }
        if(fileParamMap.get("picFilePath").lastIndexOf("/")!=-1){
            startIndex=fileParamMap.get("picFilePath").lastIndexOf("/");
        }
        //生成文件名称已uuid命名
        //SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //String prefixFile = sf.format(new Date());
        //String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileNewName =fileParamMap.get("fileName");
        //写入相对图片读取路径文件名
        String writeFileName="./"+fileParamMap.get("picFilePath").substring(startIndex+1, fileParamMap.get("picFilePath").length())+"/"+fileNewName;
        //保存图片
        String jpgFile= PicUtils.base64ToJpg(fileParamMap.get("picFilePath"),fileNewName,fileParamMap.get("jpgBase64"));
        String jpgFileBak=PicUtils.base64ToJpg(fileParamMap.get("picFilePath"),BaseConst.File_IMG_BAK+fileNewName,fileParamMap.get("jpgBase64"));
        if(StringUtil.isNotEmpty(jpgFile)){
            resultMap.put("writePicFilePath",jpgFile);
            //对图片进行镜像反转
            //PicUtils.ImageMirror(jpgFile,jpgFile);
        }else{
            resultMap.put("msg","保存图片失败");
            resultMap.put("code","400");
        }
        if(StringUtil.isNotEmpty(jpgFile)){
            resultMap.put("writePicFilePathBak",jpgFileBak);
            //对图片进行镜像反转
            //PicUtils.ImageMirror(jpgFile,jpgFile);
        }else{
            resultMap.put("msg","保存备份图片失败");
            resultMap.put("code","400");
        }
        //保存文件 JpgIn.txt 文件
        List<String> srcList=new ArrayList<>();
        srcList= FileUtils.readFileData(fileParamMap.get("inFileName"),fileParamMap.get("filePath"),"UTF-8");
        srcList.add(writeFileName);
        boolean writeFlag= FileUtils.writeFile(srcList,fileParamMap.get("inFileName"),fileParamMap.get("filePath"),"UTF-8");
        resultMap.put("writeFileFlag",writeFlag);
        if (!writeFlag){
            resultMap.put("msg","保存文件失败");
            resultMap.put("code","400");
        }
        resultMap.remove("jpgBase64");
        return resultMap;
    }

    @Override
    public CommonPage<NetDeviceDTO> getPageList(Integer pageSize, Integer pageNum, Integer sort, NetDeviceVo netDeviceVo) {
        NetDeviceQuery netDeviceQuery = getNetQuery(pageSize,pageNum,sort,netDeviceVo);
        List<?> list = netDeviceDao.getPageByExample(netDeviceQuery);
        List<NetDeviceDTO> NetDeviceDTOS = (List<NetDeviceDTO>) list.get(0);
        Long total = ((List<Long>) list.get(1)).get(0);
        return CommonPage.restPage(NetDeviceDTOS, pageNum, pageSize, total);
    }

    /**
     * 获得查询条件，后续可以增加
     * @param pageSize
     * @param pageNum
     * @param sort
     * @param netDeviceVo
     * @return
     */
    private  NetDeviceQuery  getNetQuery(Integer pageSize, Integer pageNum, Integer sort, NetDeviceVo netDeviceVo){
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
        //设备编号
        if(StringUtil.isNotEmpty(netDeviceVo.getAcDeviceSn())){
            criteria.andAcDeviceSnLike(netDeviceVo.getAcDeviceSn());
        }
        //省
        if(StringUtil.isNotEmpty(netDeviceVo.getProvince())){
            criteria.andProvinceLike(netDeviceVo.getProvince());
        }
        //市
        if(StringUtil.isNotEmpty(netDeviceVo.getCity())){
            criteria.andCityLike(netDeviceVo.getCity());
        }
        //区
        if(StringUtil.isNotEmpty(netDeviceVo.getArea())){
            criteria.andAreaLike(netDeviceVo.getArea());
        }
        //设备名称
        if(StringUtil.isNotEmpty(netDeviceVo.getAcDeviceName())){
            criteria.andAcDeviceNameLike(netDeviceVo.getAcDeviceName());
        }

        //设备型号
        if(StringUtil.isNotEmpty(netDeviceVo.getModel())){
            criteria.andModelLike(netDeviceVo.getModel());
        }

        //设备类型
        if(StringUtil.isNotEmpty(netDeviceVo.getDeviceType())){
            criteria.andDeviceTypeEqualTo(netDeviceVo.getDeviceType());
        }

        //在线状态
        if(StringUtil.isNotEmpty(netDeviceVo.getStatus())){
            criteria.andStatusEqualTo(netDeviceVo.getStatus());
        }
        //绑定时间
        if(StringUtil.isNotEmpty(netDeviceVo.getBindDateFrom()) && StringUtil.isNotEmpty(netDeviceVo.getBindDateTo()) ){
            criteria.andBindTimeBetween(netDeviceVo.getBindDateFrom(),netDeviceVo.getBindDateTo());
        }
        //出厂时间
        if(StringUtil.isNotEmpty(netDeviceVo.getOutDateFrom()) && StringUtil.isNotEmpty(netDeviceVo.getOutDateTo()) ){
            criteria.andOutTimeBetween(netDeviceVo.getOutDateFrom(),netDeviceVo.getOutDateTo());
        }
        //硬件版本
        if(StringUtil.isNotEmpty(netDeviceVo.getAcDeviceHw())){
            criteria.andAcDeviceHwLike(netDeviceVo.getAcDeviceHw());
        }
        //软件版本
        if(StringUtil.isNotEmpty(netDeviceVo.getAcDeviceSw())){
            criteria.andAcDeviceSwLike(netDeviceVo.getAcDeviceSw());
        }

        if(StringUtil.isNotEmpty(netDeviceVo.getAcIp())){
            criteria.andAcIpLike(netDeviceVo.getAcIp());
        }
        if(StringUtil.isNotEmpty(netDeviceVo.getAddress())){
            criteria.andAddressLike(netDeviceVo.getAddress());
        }
        if(StringUtil.isNotEmpty(netDeviceVo.getAcMac())){
            criteria.andAcMacLike(netDeviceVo.getAcMac());
        }
        if(StringUtil.isNotEmpty(netDeviceVo.getAcDeviceSn())){
            criteria.andAcDeviceSnLike(netDeviceVo.getAcDeviceSn());
        }
        if(StringUtil.isNotEmpty(netDeviceVo.getMId()) && !CommonBusiness.isAdmin()){
            NetDeviceQuery netDeviceQuery1 = new NetDeviceQuery();
            NetDeviceQuery.Criteria criteria1 = netDeviceQuery1.createCriteria();
            criteria.andParentEqualTo(netDeviceVo.getMId());
            criteria1.andOrMidEqualTo(netDeviceVo.getMId());
            netDeviceQuery.or(criteria1);
        }

        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            netDeviceQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        if (null != sort) {
            //排序 0，降序，1，升序
            String sortStr = "if(`status` is NULL OR `status`='' ,'0',`status`),create_time";
            if (sort == 0) {
                sortStr = "if(`status` is NULL OR `status`='' ,'0',`status`) desc,create_time desc";
            }
            netDeviceQuery.setOrderByClause(sortStr);
        }
        return netDeviceQuery;
    }

}
