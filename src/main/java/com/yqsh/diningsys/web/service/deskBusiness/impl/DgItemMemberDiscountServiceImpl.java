package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemMemberDiscountMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemMemberDiscountSMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemMemberDiscountService;
@Service
@SuppressWarnings({ "rawtypes", "unchecked" ,"unused"})
public class DgItemMemberDiscountServiceImpl extends GenericServiceImpl<DgItemMemberDiscount,Integer> implements DgItemMemberDiscountService{
	@Resource
	private DgItemMemberDiscountMapper dgItemMemberDiscountMapper;
	@Resource
	private DgItemMemberDiscountSMapper dgItemMemberDiscountSMapper;
	
	
	@Override
	public int insertBackId(DgItemMemberDiscount record) {
		return dgItemMemberDiscountMapper.insertBackId(record);
	}

	@Override
	public Page<DgItemMemberDiscount> getAllMemberDishcount(
			DgItemMemberDiscount dgItemMemberDiscount) {
		Integer totle = dgItemMemberDiscountMapper.countAllData(dgItemMemberDiscount);
	    List<DgItemMemberDiscount> list = dgItemMemberDiscountMapper.getAllData(dgItemMemberDiscount);
	    return PageUtil.getPage(totle, dgItemMemberDiscount.getPage(),list, dgItemMemberDiscount.getRows());
	}

	@Override
	public void deleteIds(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemMemberDiscountMapper.deleteIds(ids);
	}

	@Override
	public GenericDao<DgItemMemberDiscount, Integer> getDao() {
		return dgItemMemberDiscountMapper;
	}

	@Override
	public void publish(Integer id) {
		DgItemMemberDiscount dis = dgItemMemberDiscountMapper.selectByPrimaryKey(id);
		dgItemMemberDiscountMapper.updateLevelUnPulish(dis.getLevelId());
	    dgItemMemberDiscountMapper.update(id);
	}

	@Override
	public List<DgItemMemberDiscount> seleAll() {
		return dgItemMemberDiscountMapper.seleAll();
	}

	@Override
	public void trash(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemMemberDiscountMapper.trash(ids);
	}

	@Override
	public void restore(String s) {
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    dgItemMemberDiscountMapper.restore(ids);
	}

	@Override
	public List<Map<String, Object>> reminder() {
		return dgItemMemberDiscountMapper.reminder();
	}

	@Override
	public DgItemMemberDiscount selectByItem(DgItemMemberDiscount src) {
		return dgItemMemberDiscountMapper.selectByItem(src);
	}

	@Override
	public int insertP(HttpServletRequest request,
			HttpServletResponse response, DgItemMemberDiscount dim) {
		Map<String, Object> ret = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(dim.getId())) // 更新
		{
			if (StringUtils.isEmpty(dim.getEnable())) {
				dim.setEnable(0);
			}
			dgItemMemberDiscountMapper.updateByPrimaryKeySelective(dim);
			List ids = new ArrayList();
		    ids.add(dim.getId());
			dgItemMemberDiscountSMapper.deleteIds(ids); // 删除子表信息
		} else // 插入
		{
			if (StringUtils.isEmpty(dim.getEnable())) {
				dim.setEnable(0);
			}
			dim.setRecycleBin(0); // 初始化不进入回收站
			dim.setUpdateTime(new Date()); // 设置更新时间为当前时间
			dim.setPublish(0); // 设置不发布状态
			dgItemMemberDiscountMapper.insertBackId(dim);
		}
		return dim.getId();
	}

	@Override
	public void insertChild(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("id");// 方案id
		String childContent = request.getParameter("childContent");
		JSONArray json = JSONArray.fromObject(childContent);
		if (json.size() > 0) {
			List<DgItemMemberDiscountS> list = new ArrayList<DgItemMemberDiscountS>();
			for (int i = 0; i < json.size(); i++) {
				DgItemMemberDiscountS item = new DgItemMemberDiscountS();
				JSONObject job = json.getJSONObject(i);
				item.setpId(Integer.parseInt(id)); // 主表id
				item.setItemId(Integer.parseInt((String) job.get("itemId")));
				item.setPrice(Double.valueOf((String) job.get("price")));
				item.setEnable(Integer.valueOf((String) job.get("enable")));
				list.add(item);
			}
			dgItemMemberDiscountSMapper.insertChilds(list);
		}
	}

	@Override
	public int seleNameCode(DgItemMemberDiscount src) {
		return dgItemMemberDiscountMapper.seleNameCode(src);
	}

	@Override
	@Transactional
	public void synItemMemberDiscount(List<DgItemMemberDiscount> listMemberDisc,List<DgItemMemberDiscountS> listMemberDiscs) {
		dgItemMemberDiscountMapper.delPhy();
		dgItemMemberDiscountSMapper.delPhy();
		if(null!=listMemberDisc&&listMemberDisc.size()>0){
			dgItemMemberDiscountMapper.insertBatch(listMemberDisc);
		}
		if(null!=listMemberDiscs&&listMemberDiscs.size()>0){
			dgItemMemberDiscountSMapper.insertBatch(listMemberDiscs);
		}
	}

}
