package com.soft.mapp.basecenter.domain.sysParam;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class SysPramPo {
    /**
     * ID
     */
    private Integer id;
    /**
     * 项目ID
     */
    private Integer mId;
    /**
     * 参数类型
     */
    private String paramType;
    /**
     * 参数代码
     */
    private String paramCode;
    private String paramName;
    private String paramVal;
    /**
     * 是否可用(1:不可用,0:可用，默认为可用0)
     */
    private String isValid;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date updateTime;
    /**
     * 1:用户可见 2：用户不可见  3：用户可改
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}