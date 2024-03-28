package com.soft.mapp.basecenter.domain.task;

import java.util.ArrayList;
import java.util.List;

public class TaskQuery {
    protected String orderByClause;

    protected String pageNumAndSize;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskQuery() {
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
        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
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
        public Criteria andReqCodeNotEqualTo(String value) {
            addCriterion("req_code <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeGreaterThan(String value) {
            addCriterion("req_code >", value, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeGreaterThanOrEqualTo(String value) {
            addCriterion("req_code >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeLessThan(String value) {
            addCriterion("req_code <", value, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeLessThanOrEqualTo(String value) {
            addCriterion("req_code <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeIn(List<String> values) {
            addCriterion("req_code in", values, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeNotIn(List<String> values) {
            addCriterion("req_code not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeBetween(String value1, String value2) {
            addCriterion("req_code between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andReqCodeNotBetween(String value1, String value2) {
            addCriterion("req_code not between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andReqCodeLike(String value) {
            addCriterion("req_code like", value, "reqCode");
            return (Criteria) this;
        }

        public Criteria andReqCodeIsNull() {
            addCriterion("req_code is null");
            return (Criteria) this;
        }

        public Criteria andReqCodeIsNotNull() {
            addCriterion("req_code is not null");
            return (Criteria) this;
        }

        public Criteria andReqCodeEqualTo(String value) {
            addCriterion("req_code =", value, "reqCode");
            return (Criteria) this;
        }
        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andIsParent() {
            addCriterion("parent_id is  null");
            return (Criteria) this;
        }
        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id=", value, "parentId");
            return (Criteria) this;
        }
        public Criteria andIsChildren() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }
        public Criteria andToLike(String value) {
            addCriterion("to like", value, "to");
            return (Criteria) this;
        }
        public Criteria andJobIdEqualTo(String value) {
            addCriterion("job_id =", value, "jobId");
            return (Criteria) this;
        }
        public Criteria andJobIdLike(String value) {
            addCriterion("job_id like", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }
        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotLike(String value) {
            addCriterion("job_id not like", value, "jobId");
            return (Criteria) this;
        }
        public Criteria andFormEqualTo(String value){
            addCriterion("from =", value, "from");
            return (Criteria) this;
        }
        public Criteria andFormLike(String value){
            addCriterion("from  like", value, "from");
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