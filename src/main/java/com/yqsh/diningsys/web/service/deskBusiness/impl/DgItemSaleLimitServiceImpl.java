package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemSaleLimitSService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemSaleLimitService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemSaleLimitServiceImpl extends GenericServiceImpl<DgItemSaleLimit,Integer> implements DgItemSaleLimitService{
	@Resource
	private DgItemSaleLimitMapper DgItemSaleLimitMapper;
	@Autowired
	private DgItemSaleLimitSService dgItemSaleLimitSService;
	@Autowired
	private DgItemSaleLimitSMapper dgItemSaleLimitsMapper;
	
	
	@Override
	public GenericDao<DgItemSaleLimit, Integer> getDao() {
		return DgItemSaleLimitMapper;
	}
	@Override
	public DgItemSaleLimit selectByDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(date);
		return DgItemSaleLimitMapper.selectByDate(strDate);
	}
	@Override
	public int getCountByData(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(date);
		return DgItemSaleLimitMapper.getCountByData(strDate);
	}
	@Override
	public int insertBackId(DgItemSaleLimit record) {
		return DgItemSaleLimitMapper.insertBackId(record);
	}
	@Override
	@Transactional
	public int saveItemSaleLimit(String data) {
		int relust=0;
		if(StringUtils.isNotBlank(data)){
			JSONObject json = JSONObject.fromObject(data);
			int todayCount =getCountByData(new Date());
			int limitId;
			if (todayCount == 0){ // 要是没有就创建个
				DgItemSaleLimit saleLimit = new DgItemSaleLimit();
				saleLimit.setToday(new Date());
				saleLimit.setUuidKey(UUID.randomUUID().toString());
				saleLimit.setType(Integer.parseInt((String) json.get("type"))); // 获取返回的type类型
				DgItemSaleLimitMapper.insertBackId(saleLimit); // 插入
				limitId = saleLimit.getId();
			}else{  // 存在就更新类型
				DgItemSaleLimit s = selectByDate(new Date()); // 根据事件获取主表id
				s.setType(Integer.parseInt((String) json.get("type")));
				DgItemSaleLimitMapper.updateByPrimaryKeySelective(s);
				
				limitId = s.getId();
			}
			JSONArray jArray = json.getJSONArray("array");
			if (jArray.size() > 0) {
				for (int i = 0; i < jArray.size(); i++) {
					JSONObject job = jArray.getJSONObject(i);
					DgItemSaleLimitS item = new DgItemSaleLimitS();
					item.setLimitId(limitId);
					item.setItemId(Integer.parseInt((String) job.get("itemId")));
					item.setItemCode((String) job.get("itemCode"));
					if (!StringUtils.isEmpty((String) job.get("saleCount"))) {
						item.setSaleCount(Double.valueOf((String) job.get("saleCount")));
						item.setReservationCount(0.0); // 预约数量先设置为0,还没有那个功能
						item.setFrontSaleCount(Double.valueOf((String) job.get("saleCount"))); // 前台限量设置为存在数量
					}else{
						item.setSaleCount(null);
						item.setReservationCount(0.0); // 预约数量先设置为0,还没有那个功能
						item.setFrontSaleCount(null); // 前台限量也设置为null
					}
					dgItemSaleLimitSService.deleteParams(item.getItemId(),item.getLimitId());//删除子表数据
					dgItemSaleLimitSService.insert(item);
				}
				relust=1;
			}
		}
		return relust;
	}
	@Override
	@Transactional
	public void synItemSaleLimit(List<DgItemSaleLimit> listItemSaleLimit, List<DgItemSaleLimitS> listItemSaleLimits) {
		DgItemSaleLimitMapper.delPhy();
		dgItemSaleLimitsMapper.delPhy();
		if(null!=listItemSaleLimit&&listItemSaleLimit.size()>0){
			DgItemSaleLimitMapper.insertBatch(listItemSaleLimit);
		}
		if(null!=listItemSaleLimits&&listItemSaleLimits.size()>0){
			dgItemSaleLimitsMapper.insertBatch(listItemSaleLimits);
		}
	}
}