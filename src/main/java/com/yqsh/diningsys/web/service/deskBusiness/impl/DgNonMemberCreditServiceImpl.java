package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.DgNonMemberCreditMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgNonMemberCredit;
import com.yqsh.diningsys.web.service.deskBusiness.DgNonMemberCreditService;

@Service
public class DgNonMemberCreditServiceImpl implements DgNonMemberCreditService {
	
	@Resource
    private DgNonMemberCreditMapper dgNonMemberCreditMapper;
	
	@Override
	public Page<DgNonMemberCredit> getPageList(
			DgNonMemberCredit dgNonMemberCredit) {
		Integer totle = dgNonMemberCreditMapper.countListByPage(dgNonMemberCredit);
		List<DgNonMemberCredit> list = dgNonMemberCreditMapper.getListByPage(dgNonMemberCredit);
		return PageUtil.getPage(totle, dgNonMemberCredit.getPage(),list, dgNonMemberCredit.getRows());
	}

	@Override
	public int insertOrUpd(DgNonMemberCredit dgNonMemberCredit) {
		int result = 0;
		if(dgNonMemberCredit.getId() != null && dgNonMemberCredit.getId() > 0){
			result = dgNonMemberCreditMapper.update(dgNonMemberCredit);
		}else{
			dgNonMemberCredit.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = dgNonMemberCreditMapper.newInsert(dgNonMemberCredit);
		}
		return result;
	}

	@Override
	public DgNonMemberCredit getDgNonMemberCreditByID(
			DgNonMemberCredit dgNonMemberCredit) {
		return dgNonMemberCreditMapper.getDgNonMemberCreditByID(dgNonMemberCredit);
	}

	@Override
	public int deleteByIds(DgNonMemberCredit dgNonMemberCredit) {
		return dgNonMemberCreditMapper.delete(dgNonMemberCredit);
	}

	@Override
	public List<DgNonMemberCredit> getAllList(
			DgNonMemberCredit dgNonMemberCredit) {
		return dgNonMemberCreditMapper.getAllList(dgNonMemberCredit);
	}

}
