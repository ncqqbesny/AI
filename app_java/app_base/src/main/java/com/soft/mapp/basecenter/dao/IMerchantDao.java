package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.merchant.MerchantPo;
import com.soft.mapp.basecenter.domain.merchant.MerchantQuery;
import com.soft.mapp.basecenter.domain.merchant.MerchantVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IMerchantDao {
    int addMerchant(MerchantPo merchantPo);
    int updateByExampleSelective(@Param("record") MerchantPo record, @Param("example") MerchantQuery example);
    int deleteByExample(MerchantQuery example);
    int deleteByPrimaryKey(Integer mid);
    List<MerchantPo> findList(MerchantVo merchantVo);
    List<?> getPageMerchant(MerchantQuery merchantQuery);
    List<MerchantPo> findListByWhere(MerchantQuery example);
    //保存
    int insertOrUpdate(MerchantPo merchantPo);

}
