package com.yqsh.diningsys.web.service.archive.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.archive.DgItemFileColorMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFileColor;
import com.yqsh.diningsys.web.service.archive.DgItemFileColorService;
@Service
public class DgItemFileColorServiceImpl implements DgItemFileColorService{

    @Resource
    private DgItemFileColorMapper dgItemFileColorMapper;
    
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DgItemFileColor record) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.insert(record);
	}

	@Override
	public int insertSelective(DgItemFileColor record) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.insertSelective(record);
	}

	@Override
	public DgItemFileColor selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DgItemFileColor record) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DgItemFileColor record) {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<DgItemFileColor> selectAllItemFileColor() {
		// TODO Auto-generated method stub
		return dgItemFileColorMapper.selectAllItemFileColor();
	}

}
