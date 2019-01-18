package com.yqsh.diningsys.web.service.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.api.SysBusinessLogMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.service.api.APILogService;

@Service
@SuppressWarnings("all")
public class APILogServiceImpl implements APILogService{

	@Resource
	private SysBusinessLogMapper  sysBusinessLogMapper;
	@Override
	public Page<SysBusinessLog> getListByPage(SysBusinessLog page) {
		// TODO Auto-generated method stub
		Integer totle = sysBusinessLogMapper.getCount(page);
		List<SysBusinessLog> list = sysBusinessLogMapper.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(), list, page.getRows());
	}

}
