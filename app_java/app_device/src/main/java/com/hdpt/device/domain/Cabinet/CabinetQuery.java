package com.hdpt.device.domain.Cabinet;

import com.hdpt.device.domain.hdUp.NetDeviceQuery;

import java.util.ArrayList;
import java.util.List;

public class CabinetQuery {
    protected String orderByClause;
    protected String pageNumAndSize;
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CabinetQuery() {
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
            addCriterion("cabinet_id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("cabinet_id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andMidIn(List<Integer> values) {
            addCriterion("m_id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("cabinet_id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("cabinet_id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("cabinet_id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cabinet_id not between", value1, value2, "id");
            return (Criteria) this;
        }


        public Criteria andIdIsNull() {
            addCriterion("cabinet_id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("cabinet_id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("cabinet_id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andSlaveIdNotIn(List<Integer> values) {
            addCriterion("slave_id not in", values, "slaveIds");
            return (Criteria) this;
        }
        public Criteria andCabinetNoNotIn(List<String> values) {
            addCriterion("cabinet_no not in", values, "cabinetNos");
            return (Criteria) this;
        }
        public Criteria andCabinetNoEqualTo(String value) {
            addCriterion("cabinet_no =", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andCabinetNoNotNull(String value) {
            addCriterion("cabinet_no <>", value, "CabinetNoNull");
            return (Criteria) this;
        }
        public Criteria andCabinetNoLike(String value) {
            addCriterion("cabinet_no like", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andMacLike(String value) {
            addCriterion("mac like", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andCabdescLike(String value) {
            addCriterion("cabdesc like", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andCabinetNoNotLike(String value) {
            addCriterion("cabinet_no not like", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andCabinetNoNotEqualTo(String value) {
            addCriterion("cabinet_no <>", value, "CabinetNo");
            return (Criteria) this;
        }

        public Criteria andStationNoEqualTo(String value) {
            addCriterion("station_no =", value, "StationNo");
            return (Criteria) this;
        }
        public Criteria andStationNoLike(String value) {
            addCriterion("station_no like", value, "StationNo");
            return (Criteria) this;
        }
        public Criteria andStationNoNotEqualTo(String value) {
            addCriterion("station_no <>", value, "stationNo");
            return (Criteria) this;
        }

        public Criteria andCabinetStatusEqualTo(String value){
            addCriterion("cabinet_status =", value, "CabinetStatus");
            return (Criteria) this;
        }
        public Criteria andParentEqualTo(Integer Value) {
            addCriterion("(Exists(SELECT 1 FROM merchant WHERE m_id=cabinet.m_id AND parent_m_id=", Value, "parentMid");
            return (Criteria) this;
        }
        public Criteria andOrMidEqualTo(Integer Value) {
            addCriterion("Exists(SELECT 1 FROM merchant WHERE m_id=cabinet.m_id AND m_id=", Value, "parentMid1");
            addCriterion(")","增加括号");
            addCriterion(")","增加括号");
            addCriterion(")","增加括号");
            return (Criteria) this;
        }
        public Criteria andIsMainEqualTo(Integer value){
            addCriterion("is_main =", value, "IsMain");
            return (Criteria) this;
        }
        public Criteria andWarningTimeEqualTo(String value){
            addCriterion("warning_time =", value, "WarningTime");
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
        public Criteria andCabinetNameLike(String value) {
            addCriterion("cabinet_name like", value, "cabinetName");
            return (Criteria) this;
        }
        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }
        public Criteria andBatchNoEqualTo(String value){
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }
        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
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