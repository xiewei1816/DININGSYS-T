package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageKx;

@Repository
public interface DgItemFilePackageKxMapper extends GenericDao<DgItemFilePackageKx,Integer>{
    int deleteByPrimaryKey(Integer id);

    int deleteByPackageId(Integer id);

    int insert(DgItemFilePackageKx record);

    int insertSelective(DgItemFilePackageKx record);

    DgItemFilePackageKx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFilePackageKx record);

    int updateByPrimaryKey(DgItemFilePackageKx record);
    
    List<DgItemFilePackageKx> selectByPackageId(Integer fileItemId);
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
    void insertBatch(@Param("listObj") List<DgItemFilePackageKx> listObj);
}