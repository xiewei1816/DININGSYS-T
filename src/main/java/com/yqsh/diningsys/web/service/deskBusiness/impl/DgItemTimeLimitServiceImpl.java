package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTimeLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTimeLimitPMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTimeLimitPicMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitP;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTimeLimitService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class DgItemTimeLimitServiceImpl extends GenericServiceImpl<DgItemTimeLimit,Integer> implements DgItemTimeLimitService{
	@Resource
	private DgItemTimeLimitMapper dgItemTimeLimitMapper;
	@Resource
	private DgItemTimeLimitPMapper dgItemTimeLimitPMapper;
	@Resource
	private DgItemTimeLimitPicMapper dgItemTimeLimitPicMapper;
	@Override
	public GenericDao<DgItemTimeLimit, Integer> getDao() {
		return dgItemTimeLimitMapper;
	}
	@Override
	public List<DgItemTimeLimit> getAllData(DgItemTimeLimit record) {
		return dgItemTimeLimitMapper.getAllData(record);
	}
	@Override
	public List<Integer> getAllItemId() {
		return dgItemTimeLimitMapper.getAllItemId();
	}
	@Override
	public List<DgItemFile> selectItemByAdd(List<Integer> ids) {
		return dgItemTimeLimitMapper.selectItemByAdd(ids);
	}
	@Override
	public int deleteAll() {
		dgItemTimeLimitPMapper.deleteAll();
		dgItemTimeLimitPicMapper.deleteAll();
		return dgItemTimeLimitMapper.deleteAll();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemTimeLimitMapper.deleteIds(ids);
	}
	@Override
	public void deleteNotIn(List<Integer> ids) {
		dgItemTimeLimitMapper.deleteNotIn(ids);
	}
	@Override
	public int deleteByItemId(int id) {
		return dgItemTimeLimitMapper.deleteByItemId(id);
	}
	@Override
	public DgItemTimeLimit selectByItemId(Integer id) {
		return dgItemTimeLimitMapper.selectByItemId(id);
	}
	@Override
	public int insertAll(DgItemTimeLimitP p,List<DgItemTimeLimit> record,String zPics,String hPics) {
		dgItemTimeLimitPMapper.insertBackId(p);
		for(DgItemTimeLimit l: record)
		{
			l.setpId(p.getId());
		}
		dgItemTimeLimitPicMapper.deleteAll();
		if(!StringUtils.isEmpty(zPics))
		{
			String [] picArr = zPics.split("/");
			List<DgItemTimeLimitPic> itemPics = new ArrayList<DgItemTimeLimitPic>();
			for(int i = 0;i<picArr.length;i++)
			{
				DgItemTimeLimitPic pic = new DgItemTimeLimitPic();
				pic.setPicNames(picArr[i]);
				pic.setpId(p.getId());
				pic.setType(2);
				itemPics.add(pic);
			}
			dgItemTimeLimitPicMapper.insertChilds(itemPics);
		}
		
		if(!StringUtils.isEmpty(hPics))
		{
			String [] picArr = hPics.split("/");
			List<DgItemTimeLimitPic> itemPics = new ArrayList<DgItemTimeLimitPic>();
			for(int i = 0;i<picArr.length;i++)
			{
				DgItemTimeLimitPic pic = new DgItemTimeLimitPic();
				pic.setPicNames(picArr[i]);
				pic.setpId(p.getId());
				pic.setType(1);
				itemPics.add(pic);
			}
			dgItemTimeLimitPicMapper.insertChilds(itemPics);
		}
		return dgItemTimeLimitMapper.insertChilds(record);
	}
	@Override
	public DgItemTimeLimit getOne() {
		return dgItemTimeLimitMapper.getOne();
	}
	@Override
	public int picHCount() {
		return dgItemTimeLimitPicMapper.selectHCount();
	}
	@Override
	public int picZCount() {
		return dgItemTimeLimitPicMapper.selectZCount();
	}
	@Override
	public List<DgItemTimeLimitPic> selectAllHPic() {
		return dgItemTimeLimitPicMapper.selectAllHPic();
	}
	@Override
	public List<DgItemTimeLimitPic> selectAllZPic() {
		return dgItemTimeLimitPicMapper.selectAllZPic();
	}
	@Override
	@Transactional
	public Map<String,Object> saveItemTimeLimit(String data, String zPics, String hPics) {
		Map<String,Object> ret=new HashMap<String,Object>();
		if(org.apache.commons.lang.StringUtils.isNotBlank(data)){
			JSONArray json = JSONArray.fromObject(data);
			if (json.size() > 0) {
				List<DgItemTimeLimit> list = new ArrayList<DgItemTimeLimit>();
				DgItemTimeLimitP p = new DgItemTimeLimitP();
				for (int i = 0; i < json.size(); i++) {
					JSONObject job = json.getJSONObject(i);
					DgItemTimeLimit item = new DgItemTimeLimit();
					item.setId(Integer.parseInt((String) job.get("itemId")));
					item.setItemId(Integer.parseInt((String) job.get("itemId")));
					item.setStartTime((String) job.get("startTime"));
					item.setEndTime((String) job.get("endTime"));
					if (!StringUtils.isEmpty(job.get("proPrice"))) {
						item.setPrice(Double.valueOf((String) job.get("proPrice")));
					} else {
						item.setPrice(null);
					}
					if (!StringUtils.isEmpty(job.get("maxCount"))) {
						item.setSaleLimit(Double.valueOf((String) job
								.get("maxCount")));
						item.setSurplusLimit(Double.valueOf((String) job
								.get("maxCount")));
					} else {
						item.setSaleLimit(0.0);
						item.setSurplusLimit(0.0);
					}
					list.add(item);

					if (i == 0) {
						p.setEndTime((String) job.get("endTime"));
						p.setStartTime((String) job.get("startTime"));
					}
					dgItemTimeLimitMapper.deleteByItemId(item.getItemId());
				}
				insertAll(p, list, zPics, hPics);
			} else {
				insertAllPic(zPics, hPics);
			}

			List<DgItemTimeLimitPic> zPicLimitPics =selectAllZPic();
			String zPicNames = "";
			if (zPicLimitPics.size() > 0) {
				for (int i = 0; i < zPicLimitPics.size(); i++) {
					zPicNames += zPicLimitPics.get(i).getPicNames() + "/";
				}
				zPicNames = zPicNames.substring(0, zPicNames.length() - 1);
			}
			List<DgItemTimeLimitPic> hPicLimitPics =selectAllHPic();
			String hPicNames = "";
			if (hPicLimitPics.size() > 0) {
				for (int i = 0; i < hPicLimitPics.size(); i++) {
					hPicNames += hPicLimitPics.get(i).getPicNames() + "/";
				}
				hPicNames = hPicNames.substring(0, hPicNames.length() - 1);
			}
			ret.put("success", true);
			ret.put("zPics", zPicNames);
			ret.put("hPics", hPicNames);
		}
		return ret;
	}
	@Override
	public int insertAllPic(String zPics, String hPics) {
		// TODO Auto-generated method stub
		dgItemTimeLimitPicMapper.deleteAll();
		if(!StringUtils.isEmpty(zPics)){
			String [] picArr = zPics.split("/");
			List<DgItemTimeLimitPic> itemPics = new ArrayList<DgItemTimeLimitPic>();
			for(int i = 0;i<picArr.length;i++)
			{
				DgItemTimeLimitPic pic = new DgItemTimeLimitPic();
				pic.setPicNames(picArr[i]);
				pic.setpId(-1);
				pic.setType(2);
				itemPics.add(pic);
			}
			dgItemTimeLimitPicMapper.insertChilds(itemPics);
		}
		
		if(!StringUtils.isEmpty(hPics)){
			String [] picArr = hPics.split("/");
			List<DgItemTimeLimitPic> itemPics = new ArrayList<DgItemTimeLimitPic>();
			for(int i = 0;i<picArr.length;i++)
			{
				DgItemTimeLimitPic pic = new DgItemTimeLimitPic();
				pic.setPicNames(picArr[i]);
				pic.setpId(-1);
				pic.setType(1);
				itemPics.add(pic);
			}
			dgItemTimeLimitPicMapper.insertChilds(itemPics);
		}
		return 0;
	}

}
