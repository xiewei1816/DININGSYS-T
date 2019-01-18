package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.businessMan.DgItemShowRankMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.service.businessMan.DgItemShowRankService;

/**
 * 消费品项显示设置实现层
 * @author xiewei
 *
 */
@Service
public class DgItemShowRankServiceImpl implements DgItemShowRankService{

	 @Resource
	 private DgItemShowRankMapper dgItemShowRankMapper;
	 
	@Override
	public int insertOrUpdDgItemShowRank(DgItemShowRank dgItemShowRank) {
		int result = 0;
		if(dgItemShowRank.getId() != null && dgItemShowRank.getId() > 0){
			dgItemShowRank.setUpdateTime(new Date());
			result = dgItemShowRankMapper.update(dgItemShowRank);
		}else{
			dgItemShowRank.setCreateTime(new Date());
			result = dgItemShowRankMapper.newInsert(dgItemShowRank);
		}
		return result;
	}

	@Override
	public DgItemShowRank getDgItemShowRankById(DgItemShowRank dgItemShowRank) {
		return dgItemShowRankMapper.getDgItemShowRankById(dgItemShowRank);
	}

	@Override
	public int deleteByIds(DgItemShowRank dgItemShowRank) {
		return dgItemShowRankMapper.delete(dgItemShowRank);
	}

	@Override
	public List<DgItemFile> selectDgItemFileList(Map params) {
		 return dgItemShowRankMapper.selectDgItemFileList(params);
	}

	@Override
	public DgItemShowRank getDgItemShowRankByPxId(DgItemShowRank dgItemShowRank) {
		return dgItemShowRankMapper.getDgItemShowRankByPxId(dgItemShowRank);
	}

	@Override
	public List<DgItemFile> selectDgItemBySearch(Map<String, Object> params) {
		 return dgItemShowRankMapper.selectDgItemBySearch(params);
	}

	@Override
	public int dgItemShowSetRank(Map<String, Object> params) {
		return dgItemShowRankMapper.dgItemShowSetRank(params);
	}

	@Override
	public DgItemShowRank getDgItemShowRankByMoveUp(DgItemShowRank dgItemShowRank) {
		return dgItemShowRankMapper.getDgItemShowRankByMoveUp(dgItemShowRank);
	}

	@Override
	public DgItemShowRank getDgItemShowRankByMoveDown(DgItemShowRank dgItemShowRank) {
		return dgItemShowRankMapper.getDgItemShowRankByMoveDown(dgItemShowRank);
	}

	@Override
	public List<DgItemFileType> selectDgItemFileSmallList(
			Map<String, Object> params) {
		return dgItemShowRankMapper.selectDgItemFileSmallList(params);
	}

	@Override
	public List<DgItemFile> selectDgItemFileNoShowRankList(
			Map<String, Object> params) {
		return dgItemShowRankMapper.selectDgItemFileNoShowRankList(params);
	}

	@Override
	public List<DgItemFile> selectDgSmallItemFileNoShowRankList(
			Map<String, Object> params) {
		return dgItemShowRankMapper.selectDgSmallItemFileNoShowRankList(params);
	}


}


