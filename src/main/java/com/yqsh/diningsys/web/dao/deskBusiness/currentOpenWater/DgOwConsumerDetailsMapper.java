package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import org.springframework.stereotype.Repository;

@Repository
public interface DgOwConsumerDetailsMapper {
    int insert(DgOwConsumerDetails record);

    int insertSelective(DgOwConsumerDetails record);
    
    DgOwConsumerDetails selectByOwId(Map src);
    
    int updateConsumerDetailsNumber(DgOwConsumerDetails record);

    DgOwConsumerDetails selectDetailByServiceIdAndItemFileId(Map param);

    int insertBatch(List<DgOwConsumerDetails> record);
}