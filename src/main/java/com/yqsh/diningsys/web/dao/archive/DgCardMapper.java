package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DgCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DgCard record);

    int insertSelective(DgCard record);

    DgCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DgCard record);

    int updateByPrimaryKey(DgCard record);

    List<DgCard> getCardsByConsumerSeatId(DgCard record);

    Integer countListByPage(DgCard record);
    
    Integer countBySeatIdAndCardNum(DgCard record);
}