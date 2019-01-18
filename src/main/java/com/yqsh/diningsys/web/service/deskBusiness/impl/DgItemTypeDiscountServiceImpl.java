package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTypeDiscountMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscountQuery;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTypeDiscountService;
@Service
public class DgItemTypeDiscountServiceImpl extends GenericServiceImpl<DgItemTypeDiscount,Integer> implements DgItemTypeDiscountService{
	@Resource
	private DgItemTypeDiscountMapper dgItemTypeDiscountMapper;
	@Override
	public int getCountByItemType(Integer id) {
		return dgItemTypeDiscountMapper.getCountByItemType(id);
	}
	@Override
	public List<DgItemTypeDiscountQuery> getAll() {
		return dgItemTypeDiscountMapper.getAll();
	}
	@Override
	public GenericDao<DgItemTypeDiscount, Integer> getDao() {
		return dgItemTypeDiscountMapper;
	}
	@Override
	public void updateBySrcList(List<DgItemTypeDiscount> record) {
		dgItemTypeDiscountMapper.updateBySrcList(record);
	}
	@Override
	public int getDiscountByDgId(Integer id) {
		return dgItemTypeDiscountMapper.getDiscountByDgId(id);
	}
	@Override
	public int deleteByGateItemId(int id) {
		return dgItemTypeDiscountMapper.deleteByGateItemId(id);
	}
	@Override
	@Transactional
	public void synData(List<DgItemTypeDiscount> listObj) {
		//先删除数据
		dgItemTypeDiscountMapper.delPhy();
		//批量插入数据
		if(null!=listObj&&listObj.size()>0){
			dgItemTypeDiscountMapper.insertBatch(listObj);
		}
	}
}