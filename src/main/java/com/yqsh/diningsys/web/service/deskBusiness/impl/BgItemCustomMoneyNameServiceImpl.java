package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.List;

import javax.annotation.Resource;







import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.BgItemCustomMoneyNameMapper;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.service.deskBusiness.BgItemCustomMoneyNameService;
@Service
public class BgItemCustomMoneyNameServiceImpl extends GenericServiceImpl<BgItemCustomMoneyName,Integer> implements BgItemCustomMoneyNameService{
	@Resource
	private BgItemCustomMoneyNameMapper dgItemCustomMoneyNameMapper;
	@Override
	public GenericDao<BgItemCustomMoneyName, Integer> getDao() {
		return dgItemCustomMoneyNameMapper;
	}
	@Override
	public List<BgItemCustomMoneyName> getAll() {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyNameMapper.getAll();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyNameMapper.getCount();
	}
	@Override
	public int getCountByItemCode(Integer itemId) {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyNameMapper.getCountByItemCode(itemId);
	}
	@Override
	public int updateByItemCode(BgItemCustomMoneyName record) {
		// TODO Auto-generated method stub
		return dgItemCustomMoneyNameMapper.updateByItemCode(record);
	}
}