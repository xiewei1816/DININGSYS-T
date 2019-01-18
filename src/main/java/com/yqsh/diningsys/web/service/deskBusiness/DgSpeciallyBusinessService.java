package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgSpeciallyBusiness;


public interface DgSpeciallyBusinessService {

	com.yqsh.diningsys.core.util.Page<DgSpeciallyBusiness> getPageList(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	int insertOrUpd(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	DgSpeciallyBusiness getDgSpeciallyBusinessByID(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	int deleteByIds(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	List<DgSpeciallyBusiness> getAllList(DgSpeciallyBusiness dgSpeciallyBusiness);
}
