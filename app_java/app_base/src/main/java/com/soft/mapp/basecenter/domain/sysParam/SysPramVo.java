package com.soft.mapp.basecenter.domain.sysParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysPramVo  {


    /**
     * 项目ID
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

    private Date createTime;

    private Date updateTime;
    /**
     * 1:用户可见 2：用户不可见  3：用户可改
     */
    private String status;
}