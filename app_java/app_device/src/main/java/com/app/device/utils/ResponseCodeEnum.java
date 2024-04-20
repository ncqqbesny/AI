package com.app.device.utils;
/**
 * 返回结果枚举类.<br>
 * @version 1.0.0 2021年1月21日<br>
 * @see
 * @since JDK 1.5.0
 */
public enum ResponseCodeEnum {

	/**
	 * 成功标识.
	 */
	SUCCESS(200, "成功"),

	/*******************************************
	 * 5开头为参数缺失问题
	 *******************************************************/
	/**
	 * 参数丢失.
	 */
	ER_PARAM(5001, "部分参数为空，或缺失参数"),
	/**
	 * 枚举值缺失.
	 */
	ENUM_MISS(5002, "枚举值缺失"),
	/**
	 * 参数丢失.
	 */
	LOSE_PARAM(5003, "校验失败：xx不能为空"),

	/**
	 * id不存在.
	 */
	ID_MISS(5004, "id缺失"),
	/**
	 * busiTypeGid,imageTypeGid不存在 或者 输入有误.
	 */
	ERROR_NOPARAMGID(5005, "busiTypeGid,imageTypeGid不存在 或者 输入有误"),
	/**
	 * "影像id或水印图片为空".
	 */
	IMAGEID_OR_IMAGE_MISS(5006, "影像id或水印图片为空"),

	/**
	 * 影像id（imageId）不存在.
	 */
	IMAGEID_MISS(5008, "影像id（imageId）缺失"),
	/**
	 * 业务key不存在.
	 */
	YWKEY_MISS(5009, "业务key缺失"),
	/**
	 * 业务key和bizId全部缺失.
	 */
	YWKEY_BIZID_MISS(5010, "业务key和bizId全部缺失"),

	/**
	 * sysCode不能为空.
	 */
	SYSCODE_MISS(5011, "sysCode不能为空"),

	/**
	 * ywkey和bizId不能同时为空.
	 */
	YWKEY_BIZID_SHOULD_HAVE_ONE(5012, "ywkey和bizId不能同时为空"),
	/**
	 * 原始业务类型bizType.
	 */
	NOT_BIZTYPE(5013, "原始业务类型bizType不能为空"),
	/**
	 * sysCode,ywkey,indexName都不能为空.
	 */
	NOT_SYSCODE_YWKEY_INDEXNAME_DATATYPE(5014, "sysCode,ywkey,indexName都不能为空"),
	
	/**
	 * ecmDataContext参数缺失.
	 */
	LOSE_ECMDATACONTEXT(5015, "ecmDataContext参数缺失"),
	
	/**
	 * 未有变更信息，无需再次保存.
	 */
	LOSE_KONG(5016, "未有变更信息，无需再次保存"),

	/*******************************************
	 * 6开头为资源不存在问题
	 *******************************************************/
	/**
	 * 无效文件流.
	 */
	NO_IMAGE_FILE(6000, "无效文件"),

	/**
	 * 影像资源不存在.
	 */
	IMAGE_NOTFOUND(6001, "影像资源不存在"),

	/**
	 * 无相关影像信息，请检查参数信息.
	 */
	NOT_IMAGE_INFO(6002, "无相关影像信息，请检查参数信息"),

	/**
	 * 系统未记录此业务，请检查业务信息是否正确.
	 */
	NOT_SAVE_BUSINESS(6003, "系统未记录此业务，请检查业务信息是否正确"),
	/**
	 * 参数丢失.
	 */
	YWKEY_ERROR(6004, "ywKey与实际存储不一致，请检查参数信息"),
	/**
	 * 第三方中sysCode,typeNo不存在 或 输入有误.
	 */
	ERROR_SYSCODE_TYPENO(6005, "第三方中sysCode,typeNo不存在 或 输入有误"),
	/**
	 * 枚举值不存在.
	 */
	ENUM_NOT_FIND(6006, "枚举值不存在"),
	/**
	 * id不存在.
	 */
	ID_NOT_FIND(6007, "id不存在"),
	/**
	 * 没有有效的文件.
	 */
	NO_VALID_DOC(6008, "没有有效的文件"),

	/**
	 * 影像分类停用 或者 查无此影像分类.
	 */
	IMAGE_NOTFOUND_FAIL(6011, "影像分类停用 或者 查无此影像分类"),
	/**
	 * 已有sysCode,typeNo,不可重复出现.
	 */
	IMAGE_FOUND_FAILAGAIN(6012, "已有sysCode,typeNo,不可重复出现，或输入参数有误"),

	/**
	 * 已有sysCode,typeNo,不可重复出现.
	 */
	IMAGE_NOTFOUND_FAILID(6014, "输入参数有误,删除失败"),
	/**
	 * 已有busiTypeGid,imageTypeGid,不可重复出现.
	 */
	IMAGE_FOUND_REF_FAILAGAIN(6015, "已有busiTypeGid,imageTypeGid,不可重复出现，或输入参数有误"),

	/**
	 * 部分传入影像id不存在.
	 */
	ER_SOME_RESID_NOT_EXIST(6017, "部分传入影像id不存在"),

	/**
	 * 未开启业务分类.
	 */
	BUSI_NOTOPEN(6018, "未开启业务分类"),
	/**
	 * 无附件.
	 */
	NO_ATTACHMENT(6019, "无附件"),

	/**
	 * 所属影像分类下没有必采影像分类项.
	 */
	NO_NEED_TO_FILL_IN(6020, "所属影像分类下没有必采影像分类项"),

	/**
	 * 无相关影像预览结果.
	 */
	NOT_IMAGE_VIEW_INFO(6021, "无相关影像预览结果"),

	/**
	 * 未设置影像业务类型和影像分类的关系.
	 */
	NOT_SET_IMAGE_BUSITYPE(6022, "未设置影像业务类型和影像分类的关系"),

	/**
	 * 影像系统无ywKey或bizId对应记录.
	 */
	NOT_EXIST_YWKEY_OR_BIZID(6023, "影像系统无ywKey或bizId对应记录"),

	/**
	 * 影像系统无bizId和ywKey对应对应记录.
	 */
	NOT_EXIST_YWKEY_AND_BIZID(6024, "影像系统无ywKey和bizId对应记录"),
	
	/**
	 * 影像系统无sysCode和bizId对应对应记录.
	 */
	NOT_SYSCODE_BIZID(6025, "影像系统无sysCode和bizId对应记录"),
	
	/**
	 * tk不可为空值.
	 */
	NOT_TK(6026, "tk不可为空值"),
	
	/**
	 * typeNo不能为空.
	 */
	NO_TYPENO(6027, "typeNo不能为空"),

	/*******************************************
	 * 7开头为未通过校验问题
	 *******************************************************/

	/**
	 * .
	 */
	NOT_LOGIN(7000, "系统未登陆"),
	/**
	 * 该文件名中包含了 /\":|*?<> 特殊字符， 不允许上传.
	 */
	FILENAME_CHECK_SENSITIVE(7001, "该文件名中包含了  /\":|*?<>%# 特殊字符， 不允许上传"),

	/**
	 * 文件名称校验失败.
	 */
	FILENAME_CHECK_FAIL(7002, "文件名称校验失败："),

	/**
	 * 该批次文件上传总数大于设置值，不允许上传.
	 */
	FILE_CHECK_COUNT_FAIL(7003, "该批次文件上传总数大于设置值，不允许上传"),

	/**
	 * 该批次文件上传总大小大于设置值，不允许上传.
	 */
	FILE_CHECK_COUNT_SIZE_FAIL(7004, "该批次文件上传总大小大于设置值，不允许上传"),

	/**
	 * 单文件大小大于设置值，不允许上传.
	 */
	FILE_CHECK_SIZE_FAIL(7005, "单文件大小大于设置值，不允许上传:"),
	/**
	 * 特征码校验失败：xxx 存在重复文件.
	 */
	CHECK_SIGNATURECODE_FAIL(7006, "特征码校验失败："),
	/**
	 * 票据封面传入的字段信息最大不允许超过8个.
	 */
	COVER_KEYVVALUE_COUNT_LIMIT(7007, "票据封面传入的字段信息最大不允许超过8个"),
	/**
	 * 验证失败.
	 */
	VERIFY_FAILED(7008, "验证失败"),

	/**
	 * 上传成功，识别失败返回空.
	 */
	ORCODE_NOFAILED(7009, "上传成功，附件imageGid:%s 识别失败返回空"),
	/** 此附件未上链. */
	NOT_ON_CHAIN(7010, "此附件未签名"),
	/**
	 * 未设置暗水印支持类型.
	 */
	BLIND_WATER_SUPPORT_TYPE_MISS(7011, "未设置暗水印支持类型"),
	/**
	 * "非暗水印支持类型".
	 */
	NO_BLIND_WATER_SUPPORT_TYPE(7012, "非暗水印支持类型"),

	/**
	 * 数据被锁定，删除失败.
	 */
	BUSI_ISLOCKED(7013, "数据被锁定，删除失败"),
	/**
	 * 业务数据被锁定，上传失败.
	 */
	BUSI_SC_ISLOCKED(7014, "业务数据被锁定，上传失败"),
	
	/**
	 * ocr识别信息不存在.
	 */
	OCR_INFO_NOTFOUND(7015, "没有识别数据"),
	/**
	 * 业务数据被锁定，操作失败.
	 */
	BUSI_CZ_ISLOCKED(7016, "业务数据被锁定，操作失败"),
	
	/**
	 * 获取的base64流大小不能超过5M.
	 */
	SUM_CG(7017, "需要获取的base64流大小不能超过5M"),
	/**
	 * 输入影像分类名称过长.
	 */
	IMAGE_LD(7018, "输入影像分类名称过长(不可超过50字符)"),
	/**
	 * 同一ywkey对应多条记录或同sysCode，bizId对应多条记录.
	 */
	BUSI_NON_UNIQUENESS(7019, "ywkey与sysCode，bizId对应多条记录，请检查数据。"),
	/*******************************************
	 * 8开头为系统内部问题
	 *******************************************************/

	/** 程序运行内部错误. */
	INTERNAL_ERROR(8000, "程序运行内部错误"),
	/**
	 * 生成票据封面失败.
	 */
	CREATE_PDF_COVER_FAIL(8001, "生成票据封面失败"),
	/**
	 * 生成二维码失败.
	 */
	CREATE_QRCODE_FAIL(8002, "生成二维码失败"),
	/**
	 * 验证服务异常.
	 */
	VALIDATE_SERVICE_ERROR(8003, "验证服务异常"),

	/**
	 * 影像客户端无运行中.
	 */
	IMAGEWEB_NOT_RUNNING(8004, "影像客户端没有在运行中"),

	/**
	 * 系统重定向失败.
	 */
	IMAGEWEB_REDIRECT_ERROR(8005, "系统重定向失败"),
	/**
	 * 文件名异常.
	 */
	FILE_NAME_ERROR(8006, "文件名异常"),
	/**
	 * 预览或下载添加水印错误！.
	 */
	ADD_WATER_ERROR(8007, "预览或下载添加水印错误！");

	/**
	 * 状态码.
	 */
	private Integer code;

	/**
	 * 信息.
	 */
	private String msg;

	/**
	 * 构造器.
	 * @param newCode .
	 * @param newMsg .
	 */
    ResponseCodeEnum(final Integer newCode, final String newMsg) {
		this.code = newCode;
		this.msg = newMsg;
	}

	/**
	 * 获取code.
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 获取msg.
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

}