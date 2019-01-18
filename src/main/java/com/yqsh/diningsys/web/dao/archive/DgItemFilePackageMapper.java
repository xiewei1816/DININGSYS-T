package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackage;

@Repository
public interface DgItemFilePackageMapper extends GenericDao<DgItemFilePackage,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemFilePackage record);

    int insertSelective(DgItemFilePackage record);

    DgItemFilePackage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFilePackage record);

    int updateByPrimaryKey(DgItemFilePackage record);

    /**
     * 获取套餐表当前最大ID
     * @return
     */
    Integer getNextPrimaryKey();
    
    DgItemFilePackage selectByItemFileId(Integer fileItemId);
    /**
   	 * 物理删除所有数据，此方法慎用
   	 * @author jianglei
   	 * 日期 2017年1月18日 上午9:14:16
   	 */
    void delPhy();
   	/**
   	 * 批量插入
   	 * @author jianglei
   	 * 日期 2017年1月18日 上午11:07:10
   	 * @param listObj
   	 */
    void insertBatch(@Param("listObj") List<DgItemFilePackage> listObj);
}