package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.merchant.MerchantVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

/**
 *  商户接口
 */
public interface IMerchantController extends IRestService {
    ServerResponse<?> addMerchant(MerchantVo merchantVo);
    ServerResponse<?> updateMerchant(MerchantVo merchantVo);
    ServerResponse<?> deleteMerchant(List<Integer> mids);
    ServerResponse<?> findList(MerchantVo merchantVo);
    ServerResponse<?> getPageMerchant(MerchantVo merchantVo, Integer pageSize,
                                      Integer pageNum);
}
