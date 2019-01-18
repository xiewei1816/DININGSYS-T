package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTypeDiscountMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemDiscountService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTypeDiscountService;
@Service
public class DgItemDiscountServiceImpl implements DgItemDiscountService{

	@Resource
	private DgItemDiscountMapper dgItemDiscountMapper;
	@Override
	public int getCountByItem(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.getCountByItem(id);
	}

	@Override
	public List<DgItemFile> getAll() {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.getAll();
	}

	@Override
	public List<DgItemFile> selectAllItemFile() {
		return dgItemDiscountMapper.selectAllItemFile();
	}

	@Override
	public void updateBySrcList(List<DgItemFile> record) {
		dgItemDiscountMapper.updateBySrcList(record);
	}

	@Override
	public List<DgItemFileType> selectItemSmallByParentId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.selectItemSmallByParentId(id);
	}

	@Override
	public List<DgItemFile> selectSmallItemFile(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.selectSmallItemFile(id);
	}

	@Override
	public List<DgItemFile> selectBigItemFile(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.selectBigItemFile(id);
	}

	@Override
	public List<DgItemFileType> selectAllItemFileBigType() {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.selectAllItemFileBigType();
	}

	@Override
	public List<DgItemFile> getSmallByID(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.getSmallByID(id);
	}

	@Override
	public List<DgItemFile> getBigByID(Integer id) {
		// TODO Auto-generated method stub
		return dgItemDiscountMapper.getBigByID(id);
	}


}