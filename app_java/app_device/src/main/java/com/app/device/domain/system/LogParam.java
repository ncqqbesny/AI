package com.app.device.domain.system;

import lombok.Data;

@Data
public class LogParam {
    @CustomTag(desc = "年")
    private String year;
    @CustomTag(desc = "类型")
    private String type;
    @CustomTag(desc = "编号")
    private String ids;

}
