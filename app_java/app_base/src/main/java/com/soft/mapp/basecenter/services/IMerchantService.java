package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.merchant.MerchantVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface IMerchantService {
    int addMerchant(MerchantVo merchantVo);
    int updateMerchant(MerchantVo merchantVo);
    int deleteMerchant(List<Integer> mids);
    List<MerchantVo> findList(MerchantVo merchantVo);
    CommonPage<MerchantVo> getPageMerchant(MerchantVo merchantVo, Integer pageSize,
                                           Integer pageNum);
    boolean IsExistMerchanName (String name);
}
