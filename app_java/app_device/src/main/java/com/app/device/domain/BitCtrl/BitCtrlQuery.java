package com.app.device.domain.BitCtrl;

import java.util.ArrayList;
import java.util.List;

public class BitCtrlQuery {
    protected String orderByClause;
    protected String pageNumAndSize;
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BitCtrlQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
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
    public String getPageNumAndSize() {
        return pageNumAndSize;
    }
    public void setPageNumAndSize(String pageNumAndSize) {

        this.pageNumAndSize = pageNumAndSize;
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
        protected void addCriterion(String condition,String noInfo) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition,noInfo));
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

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andCabinetNoIn(List<String> values) {
            addCriterion("cabinet_no  in", values, "cabinetNos");
            return (Criteria) this;
        }
        public Criteria andCabinetNoEqualTo(String value) {
            addCriterion("cabinet_no =", value, "stationNo");
            return (Criteria) this;
        }
        public Criteria andCabinetNoNotIn(List<String> values) {
            addCriterion("cabinet_no not in", values, "cabinetNos");
            return (Criteria) this;
        }
        public Criteria andStationNoNotEqualTo(String value) {
            addCriterion("station_no <>", value, "stationNo");
            return (Criteria) this;
        }

        public Criteria andStationNoEqualTo(String value) {
            addCriterion("station_no =", value, "stationNo");
            return (Criteria) this;
        }
        public Criteria andStationNoIn(List<String> values) {
            addCriterion("station_no  in", values, "stationNos");
            return (Criteria) this;
        }
        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
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
        public Criteria andDeviceSnEqualTo(String value) {
            addCriterion("device_sn =", value, "deviceSn");
            return (Criteria) this;
        }
        public Criteria andDeviceSnIsNull() {
            addCriterion("device_sn =''");
            return (Criteria) this;
        }
        public Criteria andCtrlNoEqualTo(String value) {
            addCriterion("ctrl_no =", value, "ctrlNo");
            return (Criteria) this;
        }
        public Criteria andCtrlNoIn(List<String> values) {
            addCriterion("ctrl_no in", values, "ctrNos");
            return (Criteria) this;
        }
        public Criteria andCtrlNoLike(String value) {
            addCriterion("ctrl_no like", value, "ctrlNo");
            return (Criteria) this;
        }
        public Criteria andCtrlNoNotLike(String value) {
            addCriterion("ctrl_no not like", value, "ctrlNo");
            return (Criteria) this;
        }
        public Criteria andCtrlNoNotEqualTo(String value) {
            addCriterion("ctrl_no <>", value, "ctrlNo");
            return (Criteria) this;
        }

        public Criteria andSignalingBitEqualTo(String value) {
            addCriterion("signaling_bit =", value, "signalingBit");
            return (Criteria) this;
        }
        public Criteria andSignalingBitLike(String value) {
            addCriterion("signaling_bit like", value, "signalingBit");
            return (Criteria) this;
        }
        public Criteria andSignalingBitNotEqualTo(String value) {
            addCriterion("signaling_bit <>", value, "signalingBit");
            return (Criteria) this;
        }
        public Criteria andSignalingBitIn(List<String> values) {
            addCriterion("signaling_bit in", values, "signalingBits");
            return (Criteria) this;
        }
        public Criteria andTypeDescEqualTo(String value){
            addCriterion("type_desc =", value, "typeDesc");
            return (Criteria) this;
        }
        public Criteria andTypeDescLike(String value) {
            addCriterion("type_desc like", value, "typeDesc");
            return (Criteria) this;
        }
        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }
        public Criteria andStatusEqualTo(Integer value){
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andTypeEqualTo(Integer value){
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }
        public Criteria andCtrlModeEqualTo(Integer value) {
            addCriterion("ctrl_mode =", value, "ctrlMode");
            return (Criteria) this;
        }
        public Criteria andAddressTypeEqualTo(Integer value) {
            addCriterion("address_type =", value, "addressType");
            return (Criteria) this;
        }
        public Criteria andAddressEqualTo(Integer value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressIn(List<Integer> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }
        public Criteria andCtrlTypeEqualTo(Integer value) {
            addCriterion("ctrl_type =", value, "ctrlType");
            return (Criteria) this;
        }
        public Criteria andCtrlTypeIn(List<Integer> values) {
            addCriterion("ctrl_type in", values, "ctrlTypes");
            return (Criteria) this;
        }
        public Criteria andCtrlNumEqualTo(Integer value) {
            addCriterion("ctrl_num =", value, "ctrlNum");
            return (Criteria) this;
        }
        public Criteria andCtrlNumIn(List<Integer> values) {
            addCriterion("ctrl_num in", values, "ctrlNums");
            return (Criteria) this;
        }
        public Criteria andRequestFlagEqualTo(Integer value) {
            addCriterion("request_flag =", value, "requestFlag");
            return (Criteria) this;
        }
        public Criteria andRequestNoEqualTo(String value) {
            addCriterion("request_no =", value, "requestNo");
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
        protected Criterion(String condition,String noInfo) {
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