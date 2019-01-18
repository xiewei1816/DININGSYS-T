package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;

@Repository
public interface DgItemShowRankMapper extends GenericDao<DgItemShowRank,Integer> {

	int update(DgItemShowRank dgItemShowRank);

	int newInsert(DgItemShowRank dgItemShowRank);

	DgItemShowRank getDgItemShowRankById(DgItemShowRank dgItemShowRank);

	int delete(DgItemShowRank dgItemShowRank);

	List<DgItemFile> selectDgItemFileList(Map param);
	
	DgItemShowRank getDgItemShowRankByPxId(DgItemShowRank dgItemShowRank);

	List<DgItemFile> selectDgItemBySearch(Map<String, Object> params);

	int dgItemShowSetRank(Map<String, Object> params);

	DgItemShowRank getDgItemShowRankByMoveUp(DgItemShowRank dgItemShowRank);

	DgItemShowRank getDgItemShowRankByMoveDown(DgItemShowRank dgItemShowRank);

	List<DgItemFileType> selectDgItemFileSmallList(Map<String, Object> params);

	List<DgItemFile> selectDgItemFileNoShowRankList(Map<String, Object> params);

	List<DgItemFile> selectDgSmallItemFileNoShowRankList(Map<String, Object> params);
    
}