package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysPerBusinessMapper extends GenericDao<SysPerBusiness,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysPerBusiness record);

    int insertSelective(SysPerBusiness record);

    SysPerBusiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPerBusiness record);

    int updateByPrimaryKey(SysPerBusiness record);

    SysPerBusiness selectGeneralPerByZwCode(Map param);

    Map selectNextDutiesCode();

    void addNewDuties(Map param);

    SysPerDiscount selectDiscountPerByZwCode(Map<String, Object> map);
}