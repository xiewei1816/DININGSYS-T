package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;
@Repository
public interface DgItemTimeLimitPicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemTimeLimitPic record);

    int insertSelective(DgItemTimeLimitPic record);

    DgItemTimeLimitPic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemTimeLimitPic record);

    int updateByPrimaryKey(DgItemTimeLimitPic record);
    
    
    int selectHCount();
    
    int selectZCount();
    
    int deleteAll();
    
    List<DgItemTimeLimitPic> selectAllHPic();
    
    List<DgItemTimeLimitPic> selectAllZPic();
    
    int insertChilds(List<DgItemTimeLimitPic> pics);
}