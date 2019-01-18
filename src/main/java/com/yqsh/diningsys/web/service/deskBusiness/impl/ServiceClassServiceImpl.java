package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.ServiceClassMapper;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import com.yqsh.diningsys.web.service.deskBusiness.ServiceClassService;

@Service
public class ServiceClassServiceImpl implements ServiceClassService {
	
	@Resource
    private ServiceClassMapper serviceClassMapper;
	
	@Override
	public Page<ServiceClass> getPageList(ServiceClass serviceClass) {
		Integer totle = serviceClassMapper.countListByPage(serviceClass);
		List<ServiceClass> list = serviceClassMapper.getListByPage(serviceClass);
		return PageUtil.getPage(totle, serviceClass.getPage(),list, serviceClass.getRows());
	}

	@Override
	public int insertOrUpd(ServiceClass serviceClass) {
		int result = 0;
		if(serviceClass.getId() != null && serviceClass.getId() > 0){
			result = serviceClassMapper.update(serviceClass);
		}else{
			serviceClass.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = serviceClassMapper.newInsert(serviceClass);
		}
		return result;
	}

	@Override
	public ServiceClass getServiceClassByID(ServiceClass serviceClass) {
		return serviceClassMapper.getServiceClassByID(serviceClass);
	}

	@Override
	public int deleteByIds(ServiceClass serviceClass) {
		return serviceClassMapper.delete(serviceClass);
	}

	@Override
	public List<ServiceClass> getAllList(ServiceClass serviceClass) {
		return serviceClassMapper.getAllList(serviceClass);
	}

	@Override
	public void insertBatch(ServiceClass serviceClass) {
		if(serviceClass.getSeatIds() != null && !serviceClass.getSeatIds().equals("")){
			String[] seatIds = serviceClass.getSeatIds().split(",");
			String[] waiterIds = serviceClass.getWaiterIds().split(",");
			for(int i = 0;i < seatIds.length;i++){
				ServiceClass scs = new ServiceClass();
				scs.setSeatId(Integer.parseInt(seatIds[i]));
				scs.setEatTimeId(serviceClass.getEatTimeId());
				scs.setCreateUserid(serviceClass.getCreateUserid());
				List<ServiceClass> checkList = serviceClassMapper.getAllList(scs);
				scs.setWaiterId(Integer.parseInt(waiterIds[i]));
				if(checkList == null || checkList.size() == 0){
					serviceClassMapper.newInsert(scs);
				}else{
					for(ServiceClass sc : checkList){
						scs.setId(sc.getId());
						serviceClassMapper.update(scs);
					}
				}
			}
		}
	}

    @Override
    public ServiceClass selectDataByBisIdAndSeatId(Integer nowBisId, Integer seatId) {
		Map<String,Object> map = new HashMap<>();
		map.put("bisId",nowBisId);
		map.put("seatId",seatId);
        return serviceClassMapper.selectDataByBisIdAndSeatId(map);
    }

}
