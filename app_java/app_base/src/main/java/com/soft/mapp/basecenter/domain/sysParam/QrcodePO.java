package com.soft.mapp.basecenter.domain.sysParam;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "二维码实体")
public class QrcodePO {
    /**
     * 内容
     */
    private String content;
    /**
     * 二维码图片地址
     */
    private String qrcodePath;
    /** 图片名称 */
    private String fileName;
    /**
     * logo图片地址
     */
    private String logoPath;
    /**
     * 二维码图片的宽度
     */
    private int width;
    /**
     * 二维码图片的高度
     */
    private int height;
    /**
     * 是否添加logo图片
     */
    private boolean isFlag;
    /**
     * 生成图片的格式
     */
    private String format;
    /**
     * 纠错级别
     */
    private char qrcodeErrorCorrect;
    /**
     * 编码模式
     */
    private char qrcodeEncodeModel;
    /**
     * 设置二维码版本
     */
    private int version;

    private int fillRect;
}
