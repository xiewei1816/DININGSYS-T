package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgNonMemberCredit;

public interface DgNonMemberCreditMapper {

	List<DgNonMemberCredit> getListByPage(DgNonMemberCredit dgNonMemberCredit);
	
    Integer countListByPage(DgNonMemberCredit dgNonMemberCredit);
    
    DgNonMemberCredit getDgNonMemberCreditByID(DgNonMemberCredit dgNonMemberCredit);
    
	int newInsert(DgNonMemberCredit dgNonMemberCredit);
	
	int update(DgNonMemberCredit dgNonMemberCredit);
	
	int delete(DgNonMemberCredit dgNonMemberCredit);
	
	List<DgNonMemberCredit> getAllList(DgNonMemberCredit dgNonMemberCredit);
}
