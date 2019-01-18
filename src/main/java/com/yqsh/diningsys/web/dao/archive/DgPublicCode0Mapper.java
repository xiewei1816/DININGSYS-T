package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;

@Repository
public interface DgPublicCode0Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgPublicCode0 record);

    int insertSelective(DgPublicCode0 record);

    DgPublicCode0 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPublicCode0 record);

    int updateByPrimaryKey(DgPublicCode0 record);

    DgPublicCode0 selectByDpcName(String DpcName);

    DgPublicCode0 authentication(DgPublicCode0 dpc);

    List<DgPublicCode0> selectByConAndPage(Page<DgPublicCode0> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<DgPublicCode0> getListByPage(DgPublicCode0 dpc);
    
    Integer countListByPage(DgPublicCode0 dpc);
    
    DgPublicCode0 getDpcById(DgPublicCode0 dpc);
    
	DgPublicCode0 getDpcParentNameById(DgPublicCode0 dpc);
    
	int newInsert(DgPublicCode0 dpc);
	
	int update(DgPublicCode0 dpc);
	
	int deleteTrash(DgPublicCode0 dpc);
	
	int replyDpc(DgPublicCode0 dpc);
	
	int delete(DgPublicCode0 dpc);
	
	List<DgPublicCode0> selectAllDpc(Map<String,Object> params);

	List<DgPublicCode0> getAllList(DgPublicCode0 dpc);

	DgPublicCode0 getLastcCode(DgPublicCode0 dpc);

	List<DgPublicCode0> selectSmallDpc(Map<String, Object> params);
	
	List<DgPublicCode0> selectSmallByParentName(Map<String, Object> params);
	/**
	 * 根据条件修改速记码
	 * @author jianglei
	 * 日期 2016年12月26日 下午6:54:53
	 * @param pubCode
	 * @return
	 */
	int updatePubCode(DgPublicCode0 pubCode);
	/**
	 * 根据条件检测数据是否正在同步
	 * @author jianglei
	 * 日期 2016年12月26日 下午6:55:42
	 * @param pubCode
	 * @return
	 */
	int isExists(DgPublicCode0 pubCode);

    List<DgPublicCode0> selectPublicCodeByKey(@Param("key") String key);
}