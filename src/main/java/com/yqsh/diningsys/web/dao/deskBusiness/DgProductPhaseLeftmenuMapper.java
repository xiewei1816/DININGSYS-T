package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
@Repository
public interface DgProductPhaseLeftmenuMapper extends GenericDao<DgProductPhaseLeftmenu,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgProductPhaseLeftmenu record);

    int insertSelective(DgProductPhaseLeftmenu record);

    DgProductPhaseLeftmenu selectByPrimaryKey(Integer id);
    

    int updateByPrimaryKeySelective(DgProductPhaseLeftmenu record);

    int updateByPrimaryKey(DgProductPhaseLeftmenu record);
    
    /**
     * 根据父标签获取子代标签
     */
    List<DgProductPhaseLeftmenu> selectByParentPrimaryKey(Integer parentId);
}