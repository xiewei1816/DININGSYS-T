package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;



import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
@Repository
public interface DgGateItemAllowCustomMapper extends GenericDao<DgGateItemAllowCustom,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgGateItemAllowCustom record);

    int insertSelective(DgGateItemAllowCustom record);

    DgGateItemAllowCustom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgGateItemAllowCustom record);

    int updateByPrimaryKey(DgGateItemAllowCustom record);
    
    
    // 查询是否有小类id,没有就创建
    int selectByGateItemId(int id);
    //获取所有数据
    List<DgGateItemAllowCustom> getAll();
    //删除没有的小类
    int deleteByGateItemId(int id);
    int deleteByGateId(int id);
}