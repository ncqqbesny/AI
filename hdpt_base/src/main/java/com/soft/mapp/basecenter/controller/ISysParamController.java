package com.soft.mapp.basecenter.controller;


import com.soft.mapp.basecenter.utils.ServerResponse;

public  interface ISysParamController  extends IRestService {
    ServerResponse<?> getSysParamByType(String paramType);
    ServerResponse<?> getSysParamByTypeAndCode(String paramType,String paramCode);
}
