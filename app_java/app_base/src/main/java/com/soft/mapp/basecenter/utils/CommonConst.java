package com.soft.mapp.basecenter.utils;
/**
 * 通用常量类.<br>
 * @see 
 * @since JDK 1.5.0
 */
public final class CommonConst {
	
	/**
	 * .
	 */
	private CommonConst() { }

    /**
	 * 1.0.0 版本号.
	 */
	public static final String ONE_VERSION = "1.0.0";
	
	/**
     * 文件名称和格式分隔符.
     */
    public static final String FILE_SPLIT = ".";
    
    /**
     * 标签分隔符.
     */
    public static final String TAGS_SPLIT = "\\.";
    
    /**
     * 字符串分隔符.
     */
    public static final String STR_SPLIT = ",";
    
    /**
     * 字符串分隔符.
     */
	public static final String STR_SPLIT_OF_VERTICAL = "\\|";
    
	/**
     * YG.
     */
    public static final String YG = "YG";
	
    /**
     * GRIS.
     */
    public static final String GRIS = "GRIS";
    
    /**
     * ecm 上下文信息.
     */
    public static final String ECM_DATACONTEXT = "ecmDataContext";
    
    /**
     * 年月日时间格式.
     */
    public static final String Y_M_D_FORMAT = "yyyy-MM-dd";
    
    /**
     * 年月日时间格式.
     */
    public static final String YMD_FORMAT = "yyyyMMdd";
    
    /**
	 * 特殊字符匹配.
	 */
	public static final String SPECIAL_CHARACTERS = 
		"[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";
	
	/**
	 * utf-8 编码格式.
	 */
	public static final String UTF8 = "UTF-8";
	
	/**
	 * PNG 输出格式.
	 */
	public static final String PNG_OUTPUT_FORMAT = "PNG";
	
	/**
	 * jpg 输出格式.
	 */
	public static final String JPG_OUTPUT_FORMAT = "JPG";
	
	/**
	 * 默认生成二维码大小.
	 */
	public static final int QRCODE_SIZE = 300;
	
	/** 常量-ENABLE,是否启用. */
	public static final String ENABLE = "1";
	
	/**
	 * 连字符切割.
	 */
	public static final String HYPHENSSPLIT = "~";
	
	/**
	 * 影像链上验证地址.
	 */
	public static final String UPCHAIN_VALIDATE_URL = "/eas/mapp/entire/query/verify/verifyProfiles";
	
	/**
	 * 半小时.
	 */
	public static final int HALF_AN_HOUR  = 3600;
	
	/**
	 * ip 地址匹配.
	 */
	public static final String PATTERN_IP = "(\\d*\\.){3}\\d*";
	
	/**
	 * 档案归档前缀.
	 */
	public static final String ARCHIVES_FRONT = "SAC~";
	
	/**
	 * 远光影像编号规则.
	 * YG_系统标识_数据类型_业务类型id/管控类型id_业务id_影像业务key.
	 */
	public static final String YG_IMAGE_CODE = "YG_%s_%s_%s_%s_%s";
}
