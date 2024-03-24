package com.hdpt.device.domain.hdUp;

import java.util.ArrayList;
import java.util.List;

public class NetDeviceQuery {
    protected String orderByClause;
    protected String pageNumAndSize;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NetDeviceQuery() {
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

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }
        public Criteria andMidIn(List<Integer> values) {
            addCriterion("m_id in", values, "mId");
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
        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBindTimeBetween(String value1, String value2) {
            addCriterion("bind_time between", value1, value2, "bindTime");
            return (Criteria) this;
        }
        public Criteria andOutTimeBetween(String value1, String value2) {
            addCriterion("out_time between", value1, value2, "outTime");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }
        public Criteria andStaSignalNotEqualTo(Integer value) {
            addCriterion("sta_signal <>", value, "id");
            return (Criteria) this;
        }
        public Criteria andStaSignalIsNotNull() {
            addCriterion("sta_signal is not null");
            return (Criteria) this;
        }
        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnLessThan(String value) {
            addCriterion("ac_device_sn <", value, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnLessThanOrEqualTo(String value) {
            addCriterion("ac_device_sn <=", value, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnIn(List<String> values) {
            addCriterion("ac_device_sn in", values, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnNotIn(List<String> values) {
            addCriterion("ac_device_sn not in", values, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnBetween(String value1, String value2) {
            addCriterion("ac_device_sn between", value1, value2, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnNotBetween(String value1, String value2) {
            addCriterion("ac_device_sn not between", value1, value2, "acDeviceSn");
            return (Criteria) this;
        }


        public Criteria andAcDeviceSnIsNull() {
            addCriterion("ac_device_sn is null");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnIsNotNull() {
            addCriterion("ac_device_sn is not null");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnEqualTo(String value) {
            addCriterion("ac_device_sn =", value, "acDeviceSn");
            return (Criteria) this;
        }

        public Criteria andAcDeviceSnLike(String value) {
            addCriterion("ac_device_sn like", value, "acDeviceSn");
            return (Criteria) this;
        }
        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }
        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
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