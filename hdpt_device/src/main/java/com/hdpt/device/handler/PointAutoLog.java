package com.hdpt.device.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hdpt.device.dao.ISysLogDao;
import com.hdpt.device.domain.SysLog.SysLogVo;
import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.domain.system.AutoLog;
import com.hdpt.device.domain.system.LogParam;
import com.hdpt.device.type.BaseConst;
import com.hdpt.device.type.LogConst;
import com.hdpt.device.utils.ConvertUtils;
import com.hdpt.device.utils.DateUtils;
import com.hdpt.device.utils.Parser;
import com.hdpt.device.utils.StringUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class PointAutoLog {
    private static Logger log = LoggerFactory.getLogger(PointAutoLog.class);
    @Autowired
    private ISysLogDao sysLogDao;

    @Pointcut("@annotation(com.hdpt.device.domain.system.AutoLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        //log.info("开始日志处理-->"+result+"---point:"+point);
        saveSysLog(point, result);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogVo sysLog = new SysLogVo();
        AutoLog syslog = method.<AutoLog>getAnnotation(AutoLog.class);
        try {
            Object[] args = joinPoint.getArgs();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            Map<String, Object> resultMap = getResultMap(result);
            String logContent = getlogContent(syslog, args, methodName, sysLog);
            sysLog.setModule(getModuleName(joinPoint.getTarget().getClass()));
            setJsonParam(args,  sysLog);
            sysLog.setLogType(syslog.logType());
            sysLog.setType(syslog.operateType());
            sysLog.setMethod(className + "." + methodName + "()");
            User context = UserInfoContext.getUser();
            //log.info("日志处理 context-->"+context+"---sysLog:"+sysLog);
            if (context != null) {
                sysLog.setUserId(context.getUserId());
                sysLog.setUserName(context.getUsername());
                sysLog.setIp(context.getIp());
                sysLog.setUrl(context.getUrl());
                sysLog.setMId(context.getMId());
            }
            sysLog.setCreateTime(new Date());
            sysLog.setEventType(BaseConst.LOG_EVENT_TYPE_OP);
            //log.info("日志处理0 logContent-->"+logContent+"---sysLog:"+JSON.toJSONString(sysLog));
            logContent = logContent + getResultContent(syslog, resultMap, sysLog);
            //log.info("日志处理1 logContent-->"+logContent+"---sysLog:"+JSON.toJSONString(sysLog));
            if(StringUtil.isNotEmptyString(logContent)) {
                sysLog.setMsg(logContent.replace("'", ""));
            }
            this.sysLogDao.addInfo(sysLog);
            //log.info("日志处理结束");
        } catch (Exception e) {
            this.log.error("saveSysLog error---->" + e.getMessage());
        }
    }

    /**
     * 设置参数值，让没有错误实体类，进行设置参数值
     * @param args
     * @param sysLog
     */
    private void setJsonParam(Object[] args, SysLogVo sysLog) {
        boolean flag = false;
        String paramStr="";
        for (int i = 0; i < args.length; i++) {
            if (args[i].toString().contains("error")) {
                flag = true;
            } else {
                try {
                    paramStr=paramStr+JSON.toJSONString(args[i])+";";
                }catch (Exception e){
                    log.info("转换对象Json参数错误："+args[i]);
                }
                sysLog.setParam(paramStr);
            }
        }
        if (!flag) {
            sysLog.setParam(JSON.toJSONString(args));
        }
    }
    /**
     * 获得注解名称
     *
     * @param cl1
     * @return
     * @throws ClassNotFoundException
     */
    private String getModuleName(Class cl1) throws ClassNotFoundException {
        String moduleName = "";
        //获取RequestMapping注解
        Api anno = (Api) cl1.getAnnotation(Api.class);
        //获取类注解的value值
        if (anno.tags().length > 0) {
            moduleName = anno.tags()[0];
        }
        if(StringUtil.isNotEmptyString(moduleName)){
            moduleName=moduleName.replace("接口","模块");
        }
        return moduleName;
    }

    private String getResultContent(AutoLog syslog, Map<String, Object> resultMap, SysLogVo sysLog) {
        StringBuffer sb = new StringBuffer();
        if (null != resultMap && null != resultMap.get("code") && "200"
                .equalsIgnoreCase(resultMap.get("code").toString())) {
            sb.append(";操作成功");
        }
        if (null != resultMap) {
            String result = JSON.toJSONString(resultMap);
            sysLog.setReturnValue(result);
            if (resultMap.size() > 0) {
                sb.append(";详细看返回值");
            } else {
                sb.append(";操作错误");
            }
        }
        String returnString = getResultContent1(syslog, resultMap, sysLog);
        sb.append(returnString);
        return sb.toString();
    }

    private String getResultContent1(AutoLog syslog, Map<String, Object> resultMap, SysLogVo sysLog) {
        StringBuffer sb = new StringBuffer();
        if (null != resultMap && null != resultMap.get("code") &&
                !"200".equalsIgnoreCase(resultMap.get("code").toString())) {
            if (syslog.logType() == 1 && null != resultMap.get("data")) {
                String result = JSON.toJSONString(resultMap.get("data"));
                sb.append(";返回错误详细查返回值" );
                sysLog.setReturnValue(result);
            }
            if (syslog.operateType() == 2)
                sb.append(";添加错误");
        }
        if (null == resultMap)
            sb.append(";操作错误");
        return sb.toString();
    }

    private String getlogContent(AutoLog syslog, Object[] args, String methodName, SysLogVo sysLog) {
        String logContent = "";
        try {
            if (null != syslog && null != args) {
                //实体类
                if (syslog.paramType() == 0)
                    logContent = getContext(syslog, args);
                //指定实体类 1logParam
                if (syslog.paramType() == BaseConst.LOG_PARAMTYPE_JSONOBJECT)
                    logContent = getMapContext(syslog, args);
                //多参数 2 logConst翻译
                if (syslog.paramType() == BaseConst.LOG_PARAMTYPE_TEXT)
                    logContent = getTextContext(syslog, args, methodName, sysLog);
                //数组 3 参数
                if (syslog.paramType() == BaseConst.LOG_PARAMTYPE_LISTMAP)
                    logContent = getMapContextByListEnty(syslog, args);
            }
        } catch (Exception e) {
            this.log.error("getlogContent error---->" + e.getMessage());
        }
        return logContent;
    }

    private Map<String, Object> getResultMap(Object result) {
        Map<String, Object> resultMaps = new HashMap<String, Object>();
        Field[] resultFields = result.getClass().getDeclaredFields();
        try {
            for (int j = 0; j < resultFields.length; j++) {
                ReflectionUtils.makeAccessible(resultFields[j]);
                String name = new String(resultFields[j].getName());
                Object value = resultFields[j].get(result);
                resultMaps.put(name, value);
            }
        } catch (Exception e) {
            this.log.error("获取日志" + e.getMessage());
        }
        return resultMaps;
    }

    private String getContext(AutoLog syslog, Object[] args) {
        StringBuffer sb = new StringBuffer();
        String operTypeName = getOperateType(syslog.operateType());
        String comtype = getSplitTypeName(syslog, args);
        sb.append(comtype + ":" + operTypeName + "参数->");
        for (int i = 0; i < args.length; i++) {
            Map<String, String> map = Parser.getAllDesc(args[i]);
            for (String m : map.keySet()) {
                Object vaule = getFieldValueByName(m, args[i]);
                if (null == vaule)
                    continue;
                String mapVaule = ConvertUtils.toString(vaule);
                if (StringUtil.isNotEmptyString(mapVaule) && mapVaule.startsWith("{") && mapVaule.indexOf(":") != -1) {
                    JSONObject json = JSONObject.parseObject(mapVaule);
                    mapVaule = json.getString("ecp_file_content");
                }
                if (null == vaule || StringUtil.isEmpty(mapVaule))
                    continue;
                if (((String) map.get(m)).contains("时间") && vaule.getClass().getName().indexOf("Date") > -1)
                    mapVaule = DateUtils.shortDate2Str((Date) vaule);
                sb.append((String) map.get(m) + ":" + mapVaule + ",");
            }
        }
        String context = sb.toString().substring(0, sb.toString().length() - 1);
        return context;
    }

    private String getTextContext(AutoLog syslog, Object[] args, String methodName, SysLogVo sysLog) {
        StringBuffer sb = new StringBuffer();
        String operTypeName = getOperateType(syslog.operateType());
        String comtype = getSplitTypeName(syslog, args);
        String[] paramNames = getLogConst(methodName);
        sb.append(comtype + ":" + operTypeName + "参数->");
        for (int i = 0; i < paramNames.length; i++) {
            String textVaule = JSON.toJSONString(args[i]);
            if (textVaule.equals("{}")) {
                continue;
            }
            sb.append(paramNames[i] + ":" + textVaule + ",");
        }
        String context = sb.toString().substring(0, sb.toString().length() - 1);
        //sysLog.setParam(context);
        return context;
    }
    private String[] getLogConst(String methodName) {
        String[] staticVaule = new String[0];
        LogConst logConst = new LogConst();
        Class<?> logClass = logConst.getClass();
        Field[] declaredFields = logClass.getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                ReflectionUtils.makeAccessible(field);
                if (Modifier.isStatic(field.getModifiers()) && field.getName().equalsIgnoreCase(methodName))
                    staticVaule = (String[]) field.get(logClass);
            }
        } catch (Exception e) {
            this.log.error(e.getMessage());
        }
        return staticVaule;
    }

    private String getMapContext(AutoLog syslog, Object[] args) {
        StringBuffer sb = new StringBuffer();
        String operTypeName = getOperateType(syslog.operateType());
        String comtype = getSplitTypeName(syslog, args);
        sb.append(comtype + ":" + operTypeName + "参数->");
        Map<String, String> map = Parser.getAllDesc(new LogParam());
        for (String m : map.keySet()) {
            for (int i = 0; i < args.length; i++) {
                String jsonParamString = JSON.toJSONString(args[i]);
                if (jsonParamString.indexOf(m) > -1 && jsonParamString.indexOf("\",") > -1) {
                    JSONObject respObject = JSON.parseObject(jsonParamString);
                    JSONArray params = respObject.getJSONArray("data");
                    if (null != params) {
                        List<JSONObject> paramList = params.toJavaList(JSONObject.class);
                        String mapVaule = "";
                        for (JSONObject paramJson : paramList) {
                            mapVaule = (String) paramJson.get(m);
                            if (m.equals("year"))
                                mapVaule = mapVaule.substring(0, BaseConst.LOG_JSONPARAM_YEAR);
                            String sContext = (String) map.get(m) + ":" + mapVaule + ",";
                            if (sb.toString().indexOf(sContext) != -1)
                                continue;
                            sb.append((String) map.get(m) + ":" + mapVaule + ",");
                        }
                    } else {
                        int subStar = jsonParamString.indexOf(m) + m.length() + BaseConst.LOG_JSONPARAM_SUBSTAR;
                        int endStar = jsonParamString.indexOf(m) + jsonParamString.length() - subStar + BaseConst.LOG_JSONPARAM_ENDSTAR;
                        String mapVaule = jsonParamString.substring(subStar, endStar);
                        if (mapVaule.indexOf("\",") != -1)
                            mapVaule = mapVaule.substring(0, mapVaule.indexOf("\","));
                        if (m.equals("year"))
                            mapVaule = mapVaule.substring(0, BaseConst.LOG_JSONPARAM_YEAR);
                        sb.append((String) map.get(m) + ":" + mapVaule + ",");
                    }
                }
            }
        }
        String context = sb.toString().substring(0, sb.toString().length() - 1);
        if (context.length() > 1999)
            context = context.substring(0, 1999);
        return context;
    }

    private String getMapContextByListEnty(AutoLog syslog, Object[] args) {
        StringBuffer sb = new StringBuffer();
        String operTypeName = getOperateType(syslog.operateType());
        String comtype = getSplitTypeName(syslog, args);
        sb.append(comtype + ":" + operTypeName + "的内容->");
        for (int j = 0; j < args.length; j++) {
            List list = (List) args[j];
            if (null != list) {
                String paramStr = getMapContextByListEnty1(syslog, args, list);
                sb.append(paramStr);
            }
        }
        String context = sb.toString().substring(0, sb.toString().length() - 1);
        return context;
    }

    private String getMapContextByListEnty1(AutoLog syslog, Object[] args, List list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Map<String, String> map = Parser.getAllDescByArgs(args);
            if (obj != null && map.size() == 0) {
                sb.append("id:" + obj.toString() + ";");
            }
            for (String m : map.keySet()) {
                Object vaule = getFieldValueByName(m, obj);
                if (null == vaule)
                    continue;
                String mapVaule = ConvertUtils.toString(vaule);
                if (StringUtil.isNotEmptyString(mapVaule) && mapVaule.startsWith("{") && mapVaule.indexOf(":") != -1) {
                    JSONObject json = JSONObject.parseObject(mapVaule);
                    mapVaule = json.getString("ecp_file_content");
                }
                if (null == vaule || StringUtil.isEmpty(mapVaule))
                    continue;
                if (((String) map.get(m)).contains("时间"))
                    mapVaule = DateUtils.shortDate2Str((Date) vaule);
                sb.append((String) map.get(m) + ":" + mapVaule + ",");
            }
        }
        return sb.toString();
    }

    private String getSplitTypeName(AutoLog syslog, Object[] args) {
        String[] typeName = syslog.value().split("/");
        String splitTypeName = "";
        try {
            for (int i = 0; i < args.length; i++) {
                Field[] fields = args[i].getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    ReflectionUtils.makeAccessible(fields[j]);
                    String name = new String(fields[j].getName());
                    Object value = fields[j].get(args[i]);
                    splitTypeName = getSplitTypeNameDetail(name, typeName, value);
                    if (StringUtils.isNotEmpty(splitTypeName))
                        return splitTypeName;
                }
            }
        } catch (Exception e) {
            this.log.error(e.getMessage());
        }
        return syslog.value();
    }

    private String getSplitTypeNameDetail(String name, String[] typeName, Object value) {
        String typeNameVaule = "";
        if (typeName.length <= 0 || StringUtil.isEmpty(name) || null == value)
            return typeNameVaule;
        if (name.equalsIgnoreCase("docInd") && value.toString().equalsIgnoreCase("1"))
            typeNameVaule = typeName[0];
        if (name.equalsIgnoreCase("docInd") && value.toString().equalsIgnoreCase("0"))
            typeNameVaule = typeName[1];
        if (name.equalsIgnoreCase("isMyBorrow") && value.toString().equalsIgnoreCase("true"))
            typeNameVaule = typeName[0];
        if (name.equalsIgnoreCase("isMyBorrow") && value.toString().equalsIgnoreCase("false"))
            typeNameVaule = typeName[1];
        return typeNameVaule;
    }

    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[0]);
            Object value = method.invoke(o, new Object[0]);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    private String getOperateType(int operateType) {
        if (operateType == 2)
            return BaseConst.LOG_OP_QUERY;
        if (operateType == 1)
            return BaseConst.LOG_OP_ADD;
        if (operateType == BaseConst.LOG_OPTYPE_UPDATE)
            return BaseConst.LOG_OP_UPDATE;
        if (operateType == BaseConst.LOG_OPTYPE_DEL)
            return BaseConst.LOG_OP_DEL;
        return BaseConst.LOG_OP_QUERY;
    }
}


