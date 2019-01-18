package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgNonMember;


public interface DgNonMemberService {
	
	com.yqsh.diningsys.core.util.Page<DgNonMember> getPageList(DgNonMember dgNonMember);
	
	int insertOrUpd(DgNonMember dgNonMember);
	
	DgNonMember getDgNonMemberByID(DgNonMember dgNonMember);
	
	int deleteByIds(DgNonMember dgNonMember);
	
	List<DgNonMember> getAllList(DgNonMember dgNonMember);
}
