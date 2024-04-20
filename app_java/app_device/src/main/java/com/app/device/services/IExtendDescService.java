package com.app.device.services;


import com.app.device.domain.Device.ExtendDescDTO;

import java.util.List;

public interface IExtendDescService {

    List<ExtendDescDTO>  findByTypeCode(String typeCode, Integer mid);

}
