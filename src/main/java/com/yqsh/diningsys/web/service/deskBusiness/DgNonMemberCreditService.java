package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgNonMemberCredit;


public interface DgNonMemberCreditService {
	
	com.yqsh.diningsys.core.util.Page<DgNonMemberCredit> getPageList(DgNonMemberCredit dgNonMemberCredit);
	
	int insertOrUpd(DgNonMemberCredit dgNonMemberCredit);
	
	DgNonMemberCredit getDgNonMemberCreditByID(DgNonMemberCredit dgNonMemberCredit);
	
	int deleteByIds(DgNonMemberCredit dgNonMemberCredit);
	
	List<DgNonMemberCredit> getAllList(DgNonMemberCredit dgNonMemberCredit);
}
