package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.DgSpeciallyBusiness;
import org.springframework.stereotype.Repository;

@Repository
public interface DgSpeciallyBusinessMapper {
	
	List<DgSpeciallyBusiness> getListByPage(DgSpeciallyBusiness dgSpeciallyBusiness);
	
    Integer countListByPage(DgSpeciallyBusiness dgSpeciallyBusiness);
    
    DgSpeciallyBusiness getDgSpeciallyBusinessByID(DgSpeciallyBusiness dgSpeciallyBusiness);
    
	int newInsert(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	int update(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	int delete(DgSpeciallyBusiness dgSpeciallyBusiness);
	
	List<DgSpeciallyBusiness> getAllList(DgSpeciallyBusiness dgSpeciallyBusiness);
}
