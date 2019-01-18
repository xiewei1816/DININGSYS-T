package com.yqsh.diningsys.web.service.sysSettings.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;

@Service
public class DgUrlSettingServiceImpl extends
		GenericServiceImpl<DgUrlSetting, Integer> implements
		DgUrlSettingService {
	@Resource
	private DgUrlSettingMapper dgUrlSettingMapper;

	@Override
	public Page<DgUrlSetting> getListByPage(DgUrlSetting page) {
		// TODO Auto-generated method stub
		Integer totle = dgUrlSettingMapper.countListByPage(page);
		List<DgUrlSetting> list = dgUrlSettingMapper
				.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(), list, page.getRows());
	}

	@Override
	public GenericDao<DgUrlSetting, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgUrlSettingMapper;
	}

	@Override
	public int insertOrUpdate(DgUrlSetting page) {
		// TODO Auto-generated method stub
		if (page.getId() == null) {
			int count = dgUrlSettingMapper.selectCountByCodeOrName(page);
			if (count > 0) {
				return -1;
			}
			return dgUrlSettingMapper.insert(page);
		} 
		return dgUrlSettingMapper.updateByPrimaryKey(page);
	}

	@Override
	public DgUrlSetting selectByCode(String code) {
		// TODO Auto-generated method stub
		return dgUrlSettingMapper.selectByCode(code);
	}

	@Override
	public List<DgUrlSetting> selectAllData() {
		return dgUrlSettingMapper.selectAllData();
	}
}
