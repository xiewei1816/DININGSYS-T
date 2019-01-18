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
import com.yqsh.diningsys.web.dao.deskBusiness.DgSpeciallyBusinessMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpeciallyBusiness;
import com.yqsh.diningsys.web.service.deskBusiness.DgSpeciallyBusinessService;
import org.springframework.util.StringUtils;

@Service
public class DgSpeciallyBusinessServiceImpl implements DgSpeciallyBusinessService {

	@Resource
    private DgSpeciallyBusinessMapper dgSpeciallyBusinessMapper;
	
	@Override
	public Page<DgSpeciallyBusiness> getPageList(
			DgSpeciallyBusiness dgSpeciallyBusiness) {
		if(!StringUtils.isEmpty(dgSpeciallyBusiness.getCrEndTime())){
			Date dateByFormat = DateUtil.getDateByFormat(dgSpeciallyBusiness.getCrEndTime(), "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateByFormat);
			calendar.add(Calendar.DATE,1);
			String nextDay = DateUtil.dateToStr(calendar.getTime(),"yyyy-MM-dd");
			dgSpeciallyBusiness.setCrEndTime(nextDay);
		}
		Integer totle = dgSpeciallyBusinessMapper.countListByPage(dgSpeciallyBusiness);
		List<DgSpeciallyBusiness> list = dgSpeciallyBusinessMapper.getListByPage(dgSpeciallyBusiness);
		return PageUtil.getPage(totle, dgSpeciallyBusiness.getPage(),list, dgSpeciallyBusiness.getRows());
	}

	@Override
	public int insertOrUpd(DgSpeciallyBusiness dgSpeciallyBusiness) {
		int result = 0;
		if(dgSpeciallyBusiness.getId() != null && dgSpeciallyBusiness.getId() > 0){
			result = dgSpeciallyBusinessMapper.update(dgSpeciallyBusiness);
		}else{
			dgSpeciallyBusiness.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = dgSpeciallyBusinessMapper.newInsert(dgSpeciallyBusiness);
		}
		return result;
	}

	@Override
	public DgSpeciallyBusiness getDgSpeciallyBusinessByID(
			DgSpeciallyBusiness dgSpeciallyBusiness) {
		return dgSpeciallyBusinessMapper.getDgSpeciallyBusinessByID(dgSpeciallyBusiness);
	}

	@Override
	public int deleteByIds(DgSpeciallyBusiness dgSpeciallyBusiness) {
		return dgSpeciallyBusinessMapper.delete(dgSpeciallyBusiness);
	}

	@Override
	public List<DgSpeciallyBusiness> getAllList(
			DgSpeciallyBusiness dgSpeciallyBusiness) {
		return dgSpeciallyBusinessMapper.getAllList(dgSpeciallyBusiness);
	}

}
