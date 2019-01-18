package com.yqsh.diningsys.web.service.pay.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.pay.DgMerchantsMapper;
import com.yqsh.diningsys.web.model.pay.DgMerchants;
import com.yqsh.diningsys.web.service.pay.DgMerchantsService;

/**
 * 商户管理业务实现类
 * @author jianglei
 * 日期 2017年1月10日 下午1:27:30
 *
 */
@Service
public class DgMerchantsServiceImpl
		implements DgMerchantsService{

	@Autowired
	private DgMerchantsMapper merchantsMapper;

	@Override
	public Page<DgMerchants> getPageList(DgMerchants merch) {
		Integer totle = merchantsMapper.countListByPage(merch);
		List<DgMerchants> list = merchantsMapper.getPageList(merch);
		return PageUtil.getPage(totle, merch.getPage(),list,merch.getRows());
	}

	@Override
	public Integer countListByPage(DgMerchants merch) {
		return merchantsMapper.countListByPage(merch);
	}

	@Override
	public int insert(DgMerchants merch) {
		return merchantsMapper.insert(merch);
	}

	@Override
	public int update(DgMerchants merch) {
		return merchantsMapper.update(merch);
	}

	@Override
	public DgMerchants get(String id) {
		return merchantsMapper.get(id);
	}

	@Override
	public int delBatch(List<String> listId) {
		return merchantsMapper.delBatch(listId);
	}

	@Override
	public DgMerchants findOneMerch(String orgId) {
		return merchantsMapper.findOneMerch(orgId);
	}
}
