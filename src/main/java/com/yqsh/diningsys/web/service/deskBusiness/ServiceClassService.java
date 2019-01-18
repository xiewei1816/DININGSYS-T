package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;


public interface ServiceClassService {

	com.yqsh.diningsys.core.util.Page<ServiceClass> getPageList(ServiceClass serviceClass);
	
	int insertOrUpd(ServiceClass serviceClass);
	
	ServiceClass getServiceClassByID(ServiceClass serviceClass);
	
	int deleteByIds(ServiceClass serviceClass);
	
	List<ServiceClass> getAllList(ServiceClass serviceClass);
	
	void insertBatch(ServiceClass serviceClass);

	ServiceClass selectDataByBisIdAndSeatId(Integer nowBisId, Integer seatId);
}
