package com.hdpt.device.services;


import com.hdpt.device.domain.Device.ExtendDescDTO;

import java.util.List;

public interface IExtendDescService {

    List<ExtendDescDTO>  findByTypeCode(String typeCode, Integer mid);

}
