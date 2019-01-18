package com.yqsh.diningsys.web.service.archive.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.archive.DgItemFileZccfMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFileZccf;
import com.yqsh.diningsys.web.service.archive.DgItemFileZccfService;
@Service
public class DgItemFileZccfServiceImpl extends GenericServiceImpl<DgItemFileZccf,Integer> implements DgItemFileZccfService{

	@Resource
	private DgItemFileZccfMapper dgItemFileZccfMapper;
	@Override
	public List<DgItemFileZccf> getZccfByItemId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemFileZccfMapper.getZccfByItemId(id);
	}

	@Override
	public GenericDao<DgItemFileZccf, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgItemFileZccfMapper;
	}

	@Override
	public List<DgItemFileZccf> selectHaveItem(String s) {
	    List ids = new ArrayList();
        Collections.addAll(ids,s.split(","));
		return dgItemFileZccfMapper.selectHaveItem(ids);
	}

	@Override
	public int insertList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try{
			String id = request.getParameter("id"); //主表id
			String obj = request.getParameter("zccfObj");
			dgItemFileZccfMapper.deleteByItemId(Integer.parseInt(id));//先删除所有父表数据
			JSONArray array = JSONArray.fromObject(obj);
			if(array.size() > 0)
			{
				for(int i=0;i<array.size();i++)
				{
					JSONObject o  = (JSONObject)array.get(i);
					DgItemFileZccf c = new DgItemFileZccf();
					String inveItemId  = o.getString("inveItemId");
					Integer itemId = Integer.parseInt(id);
					String counter = o.getString("count");
					c.setInveItemId(inveItemId);
					c.setItemId(itemId);
					c.setCounter(Double.parseDouble(counter));
					dgItemFileZccfMapper.insertSelective(c);
				}
			}	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteByItemId(Integer id) {
		// TODO Auto-generated method stub
		return dgItemFileZccfMapper.deleteByItemId(id);
	}

}