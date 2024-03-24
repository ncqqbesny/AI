package com.hdpt.device.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 接口返回结构.<br>
 * @version 1.0.0 2021年1月21日<br>
 * @see
 * @param <T> 数据类型.
 * @since JDK 1.5.0
 */
@SuppressWarnings("serial")
// 序列化时忽略null属性
@ApiModel(description = "响应数据")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ServerResponseNet<T> implements Serializable {
	/**
	 * 状态码.
	 */
	@ApiModelProperty(value = "状态码")
	private Integer code;

	/**
	 * 返回信息.
	 */
	@ApiModelProperty(value = "提示信息")
	private String msg;

	/**
	 * .
	 * @param newCode .
	 */
	private ServerResponseNet(final int newCode) {
		this.code = newCode;
	}
	/**
	 * .
	 * @param newCode .
	 * @param newMsg .
	 */
	private ServerResponseNet(final int newCode, final String newMsg) {
		this.code = newCode;
		this.msg = newMsg;
	}

	/**
	 * .
	 * @param newCode .
	 * @param newVersion .
	 * @param newMsg .
	 * @param newData .
	 */
	private ServerResponseNet(final int newCode, final String newVersion, final String newMsg, final T newData) {
		this.code = newCode;
		this.msg = newMsg;
	}

	/**
	 * .
	 * @param newCode .
	 * @param newVersion .
	 * @param newMsg .
	 */
	private ServerResponseNet(final int newCode, final String newVersion, final String newMsg) {
		this.code = newCode;
		this.msg = newMsg;

	}

	/**
	 * .
	 * @return .
	 */
	public int getCode() {
		return code;
	}

	/**
	 * .
	 * @return .
	 */
	public String getMsg() {
		return msg;
	}


	/**
	 * 返回传入枚举项对应code何msg.
	 * @param rc .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByResponse(final ResponseCodeEnum rc) {
		return new ServerResponseNet<T>(rc.getCode(), CommonConst.ONE_VERSION, rc.getMsg());
	}

	/**
	 * .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccess() {
		return createByResponse(ResponseCodeEnum.SUCCESS);
	}

	/**
	 * 返回成功数据 .
	 * @param <T> .
	 * @param data 数据
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createSuccessData(final T data) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), CommonConst.ONE_VERSION,
				ResponseCodeEnum.SUCCESS.getMsg(), data);
	}

	/**
	 * .
	 * @param msg .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccessMessage(final String msg) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), CommonConst.ONE_VERSION, msg);
	}

	/**
	 * .
	 * @param data .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccess(final T data) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), CommonConst.ONE_VERSION,
				ResponseCodeEnum.SUCCESS.getMsg(), data);
	}

	/**
	 * .
	 * @param msg .
	 * @param data .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccess(final String msg, final T data) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), CommonConst.ONE_VERSION, msg, data);
	}

	/**
	 * .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByError() {
		return new ServerResponseNet<T>(ResponseCodeEnum.ER_PARAM.getCode(), CommonConst.ONE_VERSION,
				ResponseCodeEnum.ER_PARAM.getMsg());
	}

	/**
	 * 参数丢失专用返回.
	 * @param <T> .
	 * @param errorMessage .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByErrorMessage(final String errorMessage) {
		return new ServerResponseNet<T>(ResponseCodeEnum.ER_PARAM.getCode(), CommonConst.ONE_VERSION, errorMessage);
	}

	/**
	 * .
	 * @param errorCode .
	 * @param errorMessage .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByErrorCodeMessage(final int errorCode, final String errorMessage) {
		return new ServerResponseNet<T>(errorCode, CommonConst.ONE_VERSION, errorMessage);
	}

	// 新增版本支持
	/**
	 * .
	 * @param <T> .
	 * @param version .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccessAndVersion(final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), version);
	}

	/**
	 * .
	 * @param msg .
	 * @param version .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccessMessageAndVersion(final String msg, final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), version, msg);
	}

	/**
	 * .
	 * @param data .
	 * @param version .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccessAndVersion(final T data, final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), version, ResponseCodeEnum.SUCCESS.getMsg(),
				data);
	}

	/**
	 * .
	 * @param msg .
	 * @param data .
	 * @param <T> .
	 * @param version .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createBySuccessAndVersion(final String msg, final T data,
                                                                     final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.SUCCESS.getCode(), version, msg, data);
	}

	/**
	 * .
	 * @param version .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByErrorAndVersion(final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.ER_PARAM.getCode(), version, ResponseCodeEnum.ER_PARAM.getMsg());
	}

	/**
	 * .
	 * @param errorMessage .
	 * @param version .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByErrorMessageAndVersion(final String errorMessage,
                                                                          final String version) {
		return new ServerResponseNet<T>(ResponseCodeEnum.ER_PARAM.getCode(), version, errorMessage);
	}

	/**
	 * .
	 * @param errorCode .
	 * @param errorMessage .
	 * @param version .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createByErrorCodeMessageAndVersion(final int errorCode,
                                                                              final String errorMessage, final String version) {
		return new ServerResponseNet<T>(errorCode, version, errorMessage);
	}

	/**
	 * .
	 * @param errorMessage .
	 * @param <T> .
	 * @return .
	 */
	public static <T> ServerResponseNet<T> createLoseMessage(final String errorMessage) {
		return new ServerResponseNet<T>(ResponseCodeEnum.LOSE_PARAM.getCode(), CommonConst.ONE_VERSION, errorMessage);
	}

	/**
	 * 获取内部错误返回值.
	 * @param <T> 返回数据类型
	 * @param msg 提示信息
	 * @return .
	 */
	public static <T> ServerResponseNet<T> getInternalErrorMsg(final String msg) {
		return new ServerResponseNet<T>(ResponseCodeEnum.INTERNAL_ERROR.getCode(), CommonConst.ONE_VERSION, msg);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ServerResponse [");
		if (code != null) {
			builder.append("code=").append(code).append(", ");
		}
		if (msg != null) {
			builder.append("msg=").append(msg).append(", ");
		}

		builder.append("]");
		return builder.toString();
	}

	/**
	 * 专门给第三方系统转换专用.
	 * @param result 结果.
	 * @return  .
	 */
	public static <T> ServerResponseNet<T> createResponse(final ServerResponseNet<?> result) {
		return new ServerResponseNet<T>(result.getCode(),  result.getMsg());
	}
}
