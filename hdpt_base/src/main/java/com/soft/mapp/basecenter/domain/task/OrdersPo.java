package com.soft.mapp.basecenter.domain.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


public class OrdersPo implements Comparable<OrdersPo> {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("parentId")
    private Integer parentId;
    @JsonProperty("reqCode")
    private String reqCode; // 请求编号
    @JsonProperty("taskJobId")
    private String taskJobId; // 作业编号，同一个作业编号的多个指令必须由一个AGV执行

    @JsonProperty("orderId")
    private String orderId; // 指令编号

    @JsonProperty("orderType")
    private String orderType; // 指令类型, carry搬运，move行走，charge充电

    @JsonProperty("from")
    private String from; // 搬运或行走指令起点(如果起点为空，则agv当前位置为起点)

    @JsonProperty("to")
    private String to; // 搬运或行走指令终点

    @JsonProperty("isDrop")
    private String isDrop; // 到达终点是否放货，1：放货 0：不放货

    @JsonProperty("isLastOrder")
    private String isLastOrder;// 是否作业最后一个指令，1：是 0：不是；如果是最后一个指令，完成该指令后，释放AGV。

    @JsonProperty("priority")
    private String priority; // 指令优先级,1~5级，值越大，优先级越高

    private String jobStatus; // 作业状态1：执行中；0待执行;-1 执行失败;2关联任务待执行
    private String carNo; // 执行任务的小车编号
    private boolean hasJobID; // 执行中和待执行任务中是否有相同的作业编号
    private String taskID; // 任务编号
    private long taskTime; // mes下发任务时间 任务超过一天 就删除
    private String jobIDAGV; // AGV 任务ID
    private String agvID; // 执行任务的AGV

    @JsonProperty("createTime")
    private Date createTime;

    @JsonProperty("updateTime")
    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public String getReqCode() {
        return reqCode;
    }


    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }


    public String getTaskJobId() {
        return taskJobId;
    }


    public void setTaskJobId(String taskJobId) {
        this.taskJobId = taskJobId;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getOrderType() {
        return orderType;
    }


    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    public String getTo() {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }


    public String getIsDrop() {
        return isDrop;
    }


    public void setIsDrop(String isDrop) {
        this.isDrop = isDrop;
    }


    public String getIsLastOrder() {
        return isLastOrder;
    }


    public void setIsLastOrder(String isLastOrder) {
        this.isLastOrder = isLastOrder;
    }


    public String getPriority() {
        return priority;
    }


    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getJobStatus() {
        return jobStatus;
    }


    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }


    public String getCarNo() {
        return carNo;
    }


    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }


    public boolean isHasJobID() {
        return hasJobID;
    }


    public void setHasJobID(boolean hasJobID) {
        this.hasJobID = hasJobID;
    }


    public String getTaskID() {
        return taskID;
    }


    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }


    public long getTaskTime() {
        return taskTime;
    }


    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }


    public String getJobIDAGV() {
        return jobIDAGV;
    }


    public void setJobIDAGV(String jobIDAGV) {
        this.jobIDAGV = jobIDAGV;
    }


    public String getAgvID() {
        return agvID;
    }


    public void setAgvID(String agvID) {
        this.agvID = agvID;
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


    @Override
    public int compareTo(OrdersPo o) {
        // TODO Auto-generated method stub
//		return o.priority.compareTo(o.priority);
        return Integer.valueOf(o.priority) >= Integer.valueOf(this.priority) ? 1 : -1;
//		return o.priority.reversedComparator(this.priority);

    }
}
