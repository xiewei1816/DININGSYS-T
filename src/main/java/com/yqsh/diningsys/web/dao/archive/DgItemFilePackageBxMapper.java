package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageBx;

@Repository
public interface DgItemFilePackageBxMapper extends GenericDao<DgItemFilePackageBx,Integer>{
    int deleteByPakcageId(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insert(DgItemFilePackageBx record);

    int insertSelective(DgItemFilePackageBx record);

    DgItemFilePackageBx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFilePackageBx record);

    int updateByPrimaryKey(DgItemFilePackageBx record);
    
    List<Map> selectByPackageId(Integer id);
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
    void insertBatch(@Param("listObj") List<DgItemFilePackageBx> listObj);
}