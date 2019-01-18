package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgNonMember;
import org.springframework.stereotype.Repository;

@Repository
public interface DgNonMemberMapper {
	
	List<DgNonMember> getListByPage(DgNonMember dgNonMember);
	
    Integer countListByPage(DgNonMember dgNonMember);
    
    DgNonMember getDgNonMemberByID(DgNonMember dgNonMember);
    
	int newInsert(DgNonMember dgNonMember);
	
	int update(DgNonMember dgNonMember);
	
	int delete(DgNonMember dgNonMember);
	
	List<DgNonMember> getAllList(DgNonMember dgNonMember);
}
