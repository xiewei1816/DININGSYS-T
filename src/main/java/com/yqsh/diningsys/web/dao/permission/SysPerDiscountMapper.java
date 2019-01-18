package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SysPerDiscountMapper extends GenericDao<SysPerDiscount,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysPerDiscount record);

    int insertSelective(SysPerDiscount record);

    SysPerDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPerDiscount record);

    int updateByPrimaryKey(SysPerDiscount record);

    SysPerDiscount selectDiscountPerByZwCode(Map param);
}