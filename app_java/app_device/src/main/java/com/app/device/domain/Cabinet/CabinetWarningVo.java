package com.app.device.domain.Cabinet;

import com.app.device.domain.Cabinet.CabinetConfigDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "货柜报警信息集合")
public class CabinetWarningVo extends CabinetConfigDTO.CabinetWarningDTO {

}
