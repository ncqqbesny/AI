package com.soft.mapp.basecenter.domain.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrdersBean implements Comparable<OrdersBean> {

	@JsonProperty("jobId")
	private String jobId; // 作业编号，同一个作业编号的多个指令必须由一个AGV执行
	
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

	@Override
	public int compareTo(OrdersBean o) {
		// TODO Auto-generated method stub
//		return o.priority.compareTo(o.priority);
		return Integer.valueOf(o.priority) >= Integer.valueOf(this.priority) ? 1 : -1;
//		return o.priority.reversedComparator(this.priority);

	}
}
