package com.soft.mapp.basecenter.domain.task;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqOrdersBean {

	@JsonProperty("reqCode")
	private String reqCode; // 请求编号，每个请求要唯一编号，请求重复发送，编号不变

	@JsonProperty("orders")
	private List<OrdersBean> orders;

}
