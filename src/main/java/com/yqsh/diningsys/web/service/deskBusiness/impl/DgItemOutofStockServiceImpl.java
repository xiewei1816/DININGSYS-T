package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemOutofStockMapper;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemOutofStockService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("dgItemOutofStockServiceImpl")
public class DgItemOutofStockServiceImpl extends GenericServiceImpl<DgItemOutofStock,Integer> implements DgItemOutofStockService{
	@Resource
	private DgItemOutofStockMapper dgItemOutofStockMapper;
	@Autowired
	private TbBisService tbBisService;
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	
	@Override
	public GenericDao<DgItemOutofStock, Integer> getDao() {
		return dgItemOutofStockMapper;
	}
	@Override
	public List<DgItemOutofStock> getAllData(DgItemOutofStock record) {
		return dgItemOutofStockMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgItemOutofStockMapper.getAllItemId();
	}
	@Override
	public List<DgItemOutofStock> selectItemByAdd(List<Integer> ids) {
		return dgItemOutofStockMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll(int type) {
		return dgItemOutofStockMapper.deleteAll(type);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemOutofStockMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemOutofStockMapper.deleteNotIn(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgItemOutofStockMapper.deleteByItemId(id);
	}
	@Override
	public int deleteByType(DgItemOutofStock src) {
		return dgItemOutofStockMapper.deleteByType(src);
	}
	@Override
	public int insertChilds(List<DgItemOutofStock> record) {
		return dgItemOutofStockMapper.insertChilds(record);
	}
	@Override
	@Transactional
	public int saveItemOutofStock(String data) {
		int relust=0;
		if(StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
			if (json.size() > 0) {
				List<DgItemOutofStock> list = new ArrayList<DgItemOutofStock>();
				for (int i = 0; i < json.size(); i++) {
					JSONObject job = json.getJSONObject(i);
					DgItemOutofStock item = new DgItemOutofStock();
					item.setmType(Integer.parseInt(job.getString("mType")));
					item.setItemId(Integer.parseInt((String) job.get("itemId")));
					//根据类别与品项id删除数据
					dgItemOutofStockMapper.deleteByType(item);
					item.setItemCode((String) job.get("itemCode"));
					item.setDate(new Date());
					item.setUuidKey(UUID.randomUUID().toString());
					if(item.getmType() == 2){//按市别 得插入市别
						List<TbBis> tbBiss = tbBisService.selectAllBis();
						List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
						for (int j = 0; j < tbBiss.size(); j++) {
							if (j != tbBiss.size() - 1) {
								Map<String, Object> m = new HashMap<String, Object>();
								m.put("startTime", tbBiss.get(j).getBisTime());
								m.put("endTime", tbBiss.get(j + 1).getBisTime());
								m.put("nowTime", format.format(new Date()));
								m.put("id", tbBiss.get(j).getId());
								timeD.add(m);
							} else {
								Map<String, Object> m = new HashMap<String, Object>();
								m.put("startTime", tbBiss.get(j).getBisTime());
								m.put("endTime", tbBiss.get(0).getBisTime());
								m.put("nowTime", format.format(new Date()));
								m.put("id", tbBiss.get(0).getId());
								timeD.add(m);
							}
						}
						int TbisID = 0; // 获取市别id
						for (int j = 0; j < timeD.size(); j++) {
							int count = tbBisService.calculateSJD(timeD.get(j));
							if (count > 0) {
								TbisID = (int) timeD.get(j).get("id");
								break;
							}
						}
						if (TbisID == 0 && timeD.size() >= 1) // 没有就是最后个时段
						{
							TbisID = (int) timeD.get(timeD.size() - 1).get("id");
						}
						item.setmBis(TbisID);//没有市别信息
					}
					list.add(item);
				}
				dgItemOutofStockMapper.insertChilds(list);
				relust=1;
			}
		}
		return relust;
	}
	@Override
	@Transactional
	public int synItemOutOfStock(List<DgItemOutofStock> listItemOutOfStock) {
		int relust=0;
		dgItemOutofStockMapper.delPhy();
		if(null!=listItemOutOfStock&&listItemOutOfStock.size()>0){
			dgItemOutofStockMapper.insertBatch(listItemOutOfStock);
		}
		relust=1;
		return relust;
	}

}
