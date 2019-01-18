package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yqsh.diningsys.core.util.DateUtil;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.DgNonMemberMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgNonMember;
import com.yqsh.diningsys.web.service.deskBusiness.DgNonMemberService;
import org.springframework.util.StringUtils;

@Service
public class DgNonMemberServiceImpl implements DgNonMemberService {
	
	@Resource
    private DgNonMemberMapper dgNonMemberMapper;

	@Override
	public Page<DgNonMember> getPageList(DgNonMember dgNonMember) {
		if(!StringUtils.isEmpty(dgNonMember.getCrEndTime())){
			Date dateByFormat = DateUtil.getDateByFormat(dgNonMember.getCrEndTime(), "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateByFormat);
			calendar.add(Calendar.DATE,1);
			String nextDay = DateUtil.dateToStr(calendar.getTime(),"yyyy-MM-dd");
			dgNonMember.setCrEndTime(nextDay);
		}
		Integer totle = dgNonMemberMapper.countListByPage(dgNonMember);
		List<DgNonMember> list = dgNonMemberMapper.getListByPage(dgNonMember);
		return PageUtil.getPage(totle, dgNonMember.getPage(),list, dgNonMember.getRows());
	}

	@Override
	public int insertOrUpd(DgNonMember dgNonMember) {
		int result = 0;
		if(dgNonMember.getId() != null && dgNonMember.getId() > 0){
			result = dgNonMemberMapper.update(dgNonMember);
		}else{
			dgNonMember.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = dgNonMemberMapper.newInsert(dgNonMember);
		}
		return result;
	}

	@Override
	public DgNonMember getDgNonMemberByID(DgNonMember dgNonMember) {
		return dgNonMemberMapper.getDgNonMemberByID(dgNonMember);
	}

	@Override
	public int deleteByIds(DgNonMember dgNonMember) {
		return dgNonMemberMapper.delete(dgNonMember);
	}

	@Override
	public List<DgNonMember> getAllList(DgNonMember dgNonMember) {
		return dgNonMemberMapper.getAllList(dgNonMember);
	}

}
