package com.hdpt.device.domain.Cabinet;

import java.util.ArrayList;
import java.util.List;

public class CabinetConfigQuery {
    protected String orderByClause;
    protected String pageNumAndSize;
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CabinetConfigQuery() {
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
            addCriterion("config_id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("config_id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("config_id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andCabinetIdNotIn(List<Integer> values) {
            addCriterion("cabinet_id not in", values, "id");
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

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("config_id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_id not between", value1, value2, "id");
            return (Criteria) this;
        }


        public Criteria andIdIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("config_id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andCabinetNoEqualTo(String value) {
            addCriterion("cabinet_no =", value, "CabinetNo");
            return (Criteria) this;
        }
        public Criteria andCabinetNoLike(String value) {
            addCriterion("cabinet_no like", value, "CabinetNo");
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

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "Name");
            return (Criteria) this;
        }
        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "Name");
            return (Criteria) this;
        }
        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value){
            addCriterion("content =", value, "Content");
            return (Criteria) this;
        }
        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "Content");
            return (Criteria) this;
        }
        public Criteria andConfigTypeEqualTo(String value){
            addCriterion("config_type =", value, "ConfigType");
            return (Criteria) this;
        }
        public Criteria andModuleTypeEqualTo(String value){
            addCriterion("module_type =", value, "moduleType");
            return (Criteria) this;
        }
        public Criteria andAddressEqualTo(String value) {
            addCriterion("dev_addr =", value, "address");
            return (Criteria) this;
        }
        public Criteria andPortEqualTo(String value) {
            addCriterion("comm_name =", value, "port");
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