package com.yqsh.diningsys.web.service.businessMan.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.businessMan.DgAreaLimitItemService;
import com.yqsh.diningsys.web.service.businessMan.DgAreaManagerService;
import com.yqsh.diningsys.web.service.businessMan.DgConsumSeatManagerService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeSService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatItemService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemForWeekService;
/**
 * 
 * 
 * @author Heshuai
 * 事物回滚
 */
@Service
@Transactional(rollbackFor=Exception.class) 
public class DgConsumSeatManagerServiceImpl implements DgConsumSeatManagerService{
	@Autowired
    private DgConsumptionAreaService consumptionAreaService;
	
	@Autowired
    private DgConsumerSeatService consumerSeatService;
	
	@Autowired
	private DgAreaLimitItemService dgAreaLimitItemService;
	
	@Autowired
	private DgAreaManagerService dgAreaManagerService;
	
	@Autowired
	private DgSeatManagerService dgSeatManagerService;
	
	@Autowired
	private DgSeatItemService dgSeatItemService;

	@Autowired
	private DgItemForWeekService  dgItemForWeekService;
	
	@Autowired
	private DgPublicCode0Service dgPublicCode0Service; //数据字典 selectSmallDpc查询字段
	
	
	@Autowired
	private DgSeatChargingSchemeService dgSeatChargingSchemeService;
	
	@Autowired
	private DgSeatChargingSchemeSService dgSeatChargingSSchemeService;

	@Override
	public int saveConsumerSeatNext(String content, String seatId) {
		try{
			dgSeatItemService.deleteBySeatId(Integer.parseInt(seatId));
			JSONArray array = JSONArray.fromObject(content);
			if(array.size()>0)
			{
				for(int i =0;i<array.size();i++)
				{
					JSONObject object = array.getJSONObject(i);
					String itemId  = (String) object.get("itemId");
					String itemCode  = (String) object.get("itemCode");
					String count  = (String) object.get("count");
					String useOpenCounter  = (String) object.get("useOpenCounter");
					DgSeatItem item = new DgSeatItem();
					item.setSeatId(Integer.parseInt(seatId));
					item.setItemId(Integer.parseInt(itemId));
					item.setItemCode(itemCode);
					item.setCount(Double.parseDouble(count));
					item.setUseOpenCounter(Integer.parseInt(useOpenCounter));
					dgSeatItemService.insert(item);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(); 
		}
		return 1;
	}

	@Override
	public int saveConsumerAreaManNext(String content, String areaId) {
		try{
			dgAreaLimitItemService.deleteByAreaId(Integer.parseInt(areaId));
			JSONArray array = JSONArray.fromObject(content);
			if(array.size()>0)
			{
				for(int i =0;i<array.size();i++)
				{
					JSONObject object = array.getJSONObject(i);
					String itemId  = (String) object.get("itemId");
					String itemCode  = (String) object.get("itemCode");
					DgAreaLimitItem item = new DgAreaLimitItem();
					item.setAreaId(Integer.parseInt(areaId));
					item.setItemId(Integer.parseInt(itemId));
					item.setItemCode(itemCode);
					dgAreaLimitItemService.insert(item);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(); 
		}
		return 0;
	}

	@Override
	public int saveBffProNext(String childContent, String id, String type) {
		try{
			dgSeatChargingSSchemeService.deleteAllByPid(Integer.parseInt(id)); //删除子表信息
			if(type.equals("1") || type.equals("4"))
			{
				JSONArray obj = JSONArray.fromObject(childContent);
				if(obj.size()>0)
				{
					for(int i=0;i<obj.size();i++)
					{
						JSONObject o = obj.getJSONObject(i);
						DgSeatChargingSchemeS s = new DgSeatChargingSchemeS();
						if(StringUtils.isEmpty(o.getString("discount")))
						{
							s.setDiscount(null);
						}
						else
						{
							s.setDiscount(o.getInt("discount"));	
						}
						
						if(StringUtils.isEmpty(o.getString("money")))
						{
							s.setMoney(null);
						}
						else
						{
							s.setMoney(o.getDouble("money"));	
						}
						s.setpId(Integer.parseInt(id));
						s.setSd(o.getInt("sd"));
						dgSeatChargingSSchemeService.insertSelective(s);
					}
				}		
			}
			else
			{
				JSONArray obj = JSONArray.fromObject(childContent);
				if(obj.size()>0)
				{
					for(int i=0;i<obj.size();i++)
					{
						JSONObject o = obj.getJSONObject(i);
						DgSeatChargingSchemeS s = new DgSeatChargingSchemeS();
						
						if(StringUtils.isEmpty(o.getString("money")))
						{
							s.setMoney(null);
						}
						else
						{
							s.setMoney(o.getDouble("money"));	
						}
						
						if(StringUtils.isEmpty(o.getString("tLong")))
						{
							s.settLong(null);
						}
						else
						{
							s.settLong(o.getInt("tLong"));	
						}
						s.setpId(Integer.parseInt(id));
						dgSeatChargingSSchemeService.insertSelective(s);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(); 
		}
		return 0;
	}
}