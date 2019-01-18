package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysPerOverviewMapper extends GenericDao<SysPerOverview,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysPerOverview record);

    int insertSelective(SysPerOverview record);

    SysPerOverview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPerOverview record);

    int updateByPrimaryKey(SysPerOverview record);

    List<DgPublicCode0> selectAllPostData();

    SysPerOverview selecOverViewByZwCode(Map param);

    /**
     * 根据ID修改状态
     * @param record
     */
    void updateStateById(SysPerOverview record);

    /**
     * 根据ID，修改赠送权限
     * @param record
     */
    void updateFreeDataById(SysPerOverview record);

    /**
     * 根据ID，修改退单权限相关
     * @param record
     */
    void updateChargeBackDataById(SysPerOverview record);

    /**
     * 根据ID，修改编辑权限相关
     * @param record
     */
    void updateVarialePriceDataById(SysPerOverview record);

}