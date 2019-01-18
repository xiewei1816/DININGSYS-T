package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceClassMapper {

	List<ServiceClass> getListByPage(ServiceClass serviceClass);

    Integer countListByPage(ServiceClass serviceClass);

    ServiceClass getServiceClassByID(ServiceClass serviceClass);

	int newInsert(ServiceClass serviceClass);

	int update(ServiceClass serviceClass);

	int delete(ServiceClass serviceClass);

	List<ServiceClass> getAllList(ServiceClass serviceClass);

    ServiceClass selectDataByBisIdAndSeatId(Map<String, Object> map);
}
