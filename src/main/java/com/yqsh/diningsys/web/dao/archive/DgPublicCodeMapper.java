package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DgPublicCodeMapper extends GenericDao<DgPublicCode,Integer>{
    int deleteByPrimaryKey(String cgpcid);

    int insert(DgPublicCode record);

    int insertSelective(DgPublicCode record);

    DgPublicCode selectByPrimaryKey(String cgpcid);

    int updateByPrimaryKeySelective(DgPublicCode record);

    int updateByPrimaryKey(DgPublicCode record);

    List<DgPublicCode> selectAllData(String cgpcid);

    int deleteDataWithLogic(String cgpcid);

    List<DgPublicCode> getListByPage(DgPublicCode record);

    Integer countListByPage(DgPublicCode record);
    /**
     * 根据相关条件获取公共代码
     * @author jianglei
     * 日期 2016年10月19日 下午1:08:10
     * @param publicCode
     * @return
     */
    List<DgPublicCode> findListData(DgPublicCode publicCode);

}