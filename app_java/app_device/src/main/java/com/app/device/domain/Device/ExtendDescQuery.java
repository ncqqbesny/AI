package com.app.device.domain.Device;

import java.util.ArrayList;
import java.util.List;

public class ExtendDescQuery {
    protected String orderByClause;
    protected String pageNumAndSize;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtendDescQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public String getPageNumAndSize() {
        return pageNumAndSize;
    }

    public void setPageNumAndSize(String pageNumAndSize) {
        this.pageNumAndSize = pageNumAndSize;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, String noInfo) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition, noInfo));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andGidLessThan(String value) {
            addCriterion("GID <", value, "GID");
            return (Criteria) this;
        }

        public Criteria andGidLessThanOrEqualTo(String value) {
            addCriterion("GID <=", value, "GID");
            return (Criteria) this;
        }

        public Criteria andGidIn(List<String> values) {
            addCriterion("GID in", values, "GID");
            return (Criteria) this;
        }
        public Criteria andMidIn(List<String> values) {
            addCriterion("MID in", values, "mId");
            return (Criteria) this;
        }
        public Criteria andMidEqualTo(Integer value) {
            addCriterion("mid =", value, "mid");
            return (Criteria) this;
        }
        public Criteria andParentEqualTo(Integer Value) {
            addCriterion("(Exists(SELECT 1 FROM merchant WHERE m_id=net_device.m_id AND parent_m_id=", Value, "parentMid");

            return (Criteria) this;
        }
        public Criteria andOrMidEqualTo(Integer Value) {
            addCriterion("Exists(SELECT 1 FROM merchant WHERE m_id=net_device.m_id AND m_id=", Value, "parentMid1");
            addCriterion(")","增加括号");
            addCriterion(")","增加括号");
            addCriterion(")","增加括号");
            return (Criteria) this;
        }
        public Criteria andGidNotIn(List<String> values) {
            addCriterion("GID not in", values, "GID");
            return (Criteria) this;
        }


        public Criteria andLastTimeBetween(String value1, String value2) {
            addCriterion("LAST_TIME between", value1, value2, "lastTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }



        public Criteria andGidIsNull() {
            addCriterion("GID is null");
            return (Criteria) this;
        }

        public Criteria andGidIsNotNull() {
            addCriterion("GID is not null");
            return (Criteria) this;
        }

        public Criteria andGidEqualTo(String value) {
            addCriterion("GID =", value, "GID");
            return (Criteria) this;
        }
        public Criteria andGidNotEqualTo(String value) {
            addCriterion("GID <>", value, "GID");
            return (Criteria) this;
        }
        public Criteria andDeviceTypeGidEqualTo(String value) {
            addCriterion("DEVICETYPEGID =", value, "deviceTypeGid");
            return (Criteria) this;
        }
        public Criteria andDeviceTypeGidNotEqualTo(String value) {
            addCriterion("DEVICETYPEGID <>", value, "deviceTypeGid");
            return (Criteria) this;
        }
        public Criteria andDeviceTypeGidIsNotNull() {
            addCriterion("DEVICETYPEGID is not null");
            return (Criteria) this;
        }


        public Criteria andExtDataModuleEqualTo(String value) {
            addCriterion("EXTDATAMODULE =", value, "extDataModule");
            return (Criteria) this;
        }

        public Criteria andDetailLevelEqualTo(String value) {
            addCriterion("DETAILLEVEL =", value, "detailLevel");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeIn(List<String> values) {
            addCriterion("DEVICETYPECODE in", values, "DEVICETYPECODE");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeNotIn(List<String> values) {
            addCriterion("DEVICETYPECODE not in", values, "DEVICETYPECODE");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeBetween(String value1, String value2) {
            addCriterion("DEVICETYPECODE between", value1, value2, "deviceTypeCode");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeNotBetween(String value1, String value2) {
            addCriterion("DEVICETYPECODE not between", value1, value2, "deviceTypeCode");
            return (Criteria) this;
        }


        public Criteria andDeviceTypeCodeIsNull() {
            addCriterion("DEVICETYPECODE is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeIsNotNull() {
            addCriterion("DEVICETYPECODE is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeEqualTo(String value) {
            addCriterion("DEVICETYPECODE =", value, "deviceTypeCode");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeCodeLike(String value) {
            addCriterion("DEVICETYPECODE like", value, "deviceTypeCode");
            return (Criteria) this;
        }
        public Criteria andCatalogCodeLike(String value) {
            addCriterion("CATALOGCODE like", value, "CATALOGCODE");
            return (Criteria) this;
        }
        public Criteria andDescCodeLike(String value) {
            addCriterion("DESCCODE like", value, "DESCCODE");
            return (Criteria) this;
        }
        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }
        public Criteria andAcDeviceNameLike(String value) {
            addCriterion("ac_device_name like", value, "acDeviceName");
            return (Criteria) this;
        }
        public Criteria andModelLike(String value) {
            addCriterion("model like", value, "model");
            return (Criteria) this;
        }
        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("device_type  =", value, "deviceType");
            return (Criteria) this;
        }
        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("device_type in", values, "deviceType");
            return (Criteria) this;
        }
        public Criteria andAcDeviceHwLike(String value) {
            addCriterion("ac_device_hw like", value, "acDeviceHw");
            return (Criteria) this;
        }
        public Criteria andAcDeviceSwLike(String value) {
            addCriterion("ac_device_sw like", value, "acDeviceSw");
            return (Criteria) this;
        }

        public Criteria andAcMacEqualTo(String value) {
            addCriterion("ac_mac =", value, "ac_mac");
            return (Criteria) this;
        }

        public Criteria andAcMacLike(String value) {
            addCriterion("ac_mac like", value, "ac_mac");
            return (Criteria) this;
        }
        public Criteria andAcMacIn(List<String> values) {
            addCriterion("ac_mac in", values, "ac_mac");
            return (Criteria) this;
        }
        public Criteria andAcMacNotIn(List<String> values) {
            addCriterion("ac_mac not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andAcMacNotEqualTo(String value) {
            addCriterion("ac_mac <>", value, "ac_mac");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }
        public Criteria andAcIpLike(String value) {
            addCriterion("ac_ip like", value, "acIP");
            return (Criteria) this;
        }
        public Criteria andAcMacNotLike(String value) {
            addCriterion("ac_mac not like", value, "ac_mac");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean noInfo;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isNoInfo() {
            return noInfo;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, String noInfo) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noInfo = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}