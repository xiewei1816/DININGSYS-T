package com.yqsh.diningsys.web.service.inve.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.yqsh.diningsys.web.dao.inve.DgInveItemsMapper;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.dao.inve.DgDepaMaterialMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgDepaMaterial;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.service.inve.DgDepaMaterialService;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 部门用料业务实现类
 * @author jianglei
 * 日期 2016年10月25日 上午10:05:33
 *
 */
@Service
public class DgDepaMaterialServiceImpl implements DgDepaMaterialService{
	@Autowired
	private DgDepaMaterialMapper depaMaterialMapper;
	@Autowired
	private DgInventoryService inventoryService;

	@Autowired
	private DgInveItemsMapper dgInveItemsMapper;

	@Override
	public Page<DgDepaMaterial> getPageList(DgDepaMaterial depaMate) {
		Integer totle = depaMaterialMapper.countListByPage(depaMate);
		List<DgDepaMaterial> list = depaMaterialMapper.getPageList(depaMate);
		return PageUtil.getPage(totle, depaMate.getPage(),list, depaMate.getRows());
	}
	@Override
	public int update(DgDepaMaterial depaMate) {
		return depaMaterialMapper.update(depaMate);
	}
	@Override
	public DgDepaMaterial get(String id) {
		return depaMaterialMapper.get(id);
	}
	@Override
	@Transactional
	public Map<String, String> depaMeteInsert(DgDepaMaterial depaMate, String jsonArr) {
		Map<String,String> mapMsg=null;
		Map<String,String> mapStr=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		int succ=0;
		int fail=0;
		JSONArray json=JSONArray.fromObject(jsonArr);
		for (Object obj : json) {
			JSONObject jsonStr=JSONObject.fromObject(obj);
			depaMate.setId(UUID.randomUUID().toString());
			depaMate.setItemId(jsonStr.getString("id"));
			depaMate.setNumber(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("number"))));
			depaMate.setItemName(jsonStr.getString("itemName"));
			depaMate.setUnit(jsonStr.getString("unit"));
			depaMate.setOrigPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("origPrice"))));
			depaMate.setPresPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("presPrice"))));
			depaMate.setSumAmount(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("sumAmount"))));
			depaMate.setSinceNumber(jsonStr.getString("sinceNumber"));
			depaMate.setRemark(jsonStr.getString("remark"));
			mapMsg=saveDepaMete(depaMate);
			if(mapMsg!=null&&"OK".equals(mapMsg.get("success"))){
				++succ;
			}else{
				++fail;
				sb.append(depaMate.getItemName()).append(",");
			}
		}
		mapStr.put("success","OK");
		mapStr.put("msg", "保存成功数量["+succ+"],失败数量["+fail+"]。"+sb.toString());
		return mapStr;
	}
	private synchronized Map<String,String> saveDepaMete(DgDepaMaterial depaMate){
		//根据物品编号检测库存是否存在,存在就修改不存在就新增
		Map<String,String> mapStr=new HashMap<String,String>();
		mapStr.put("success", "fail");
		if(null!=depaMate&&!StringUtils.isNotBlank(depaMate.getItemId())){
			return mapStr;
		}
		DgInventory inve=new DgInventory();
		inve.setItemId(depaMate.getItemId());
		inve.setWareID(depaMate.getWareID());
		List<DgInventory> listInve=inventoryService.findListData(inve);
		if(null!=listInve&&listInve.size()!=1){
			return mapStr;
		}
		//只有当物品设置部门领料减少库存，才执行以下代码  2017年11月7日17:09:14
		DgInventory dginve=listInve.get(0);
		DgInveItems dgInveItems = dgInveItemsMapper.selectInveItemById(dginve.getItemId());
		if(dgInveItems.getInCode().equals("1")){
			if(DgDepaMaterial.MATETYPE_LED.equals(depaMate.getMateType())){
				if(depaMate.getNumber().compareTo(dginve.getNumber())>0){
					return mapStr;
				}
				dginve.setNumber(dginve.getNumber().subtract(depaMate.getNumber()));
			}else if(DgDepaMaterial.MATETYPE_REFUND.equals(depaMate.getMateType())){//采购入库
				dginve.setNumber(dginve.getNumber().add(depaMate.getNumber()));
			}
			dginve.setItemName(depaMate.getItemName());
			depaMate.setInveId(dginve.getId());
			inventoryService.update(dginve);
		}
		//END
		depaMate.setCreateDate(DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String procSerNum="";
		String prefix="";
			//根据条件获取最新一条数据
			DgDepaMaterial dpr=new DgDepaMaterial();
			dpr.setMateType(depaMate.getMateType());
			dpr.setDateTime(depaMate.getDateTime());
			DgDepaMaterial dgproc=depaMaterialMapper.findLastOne(dpr);
			if(DgDepaMaterial.MATETYPE_LED.equals(depaMate.getMateType())){
				prefix=FaceImageType.OU.toString();
			}else if(DgDepaMaterial.MATETYPE_REFUND.equals(depaMate.getMateType())){
				prefix=FaceImageType.RE.toString();
			}
			
			if(null!=dgproc&&StringUtils.isNotBlank(dgproc.getSerialNumber())){
				prefix=prefix+DateUtil.fromatStrToStr(depaMate.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS, dgproc.getSerialNumber());
			}else{
				prefix=prefix+DateUtil.fromatStrToStr(depaMate.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS,null); 
			}
			depaMate.setSerialNumber(procSerNum);
			depaMaterialMapper.insert(depaMate);
		mapStr.put("success", "OK");
		return mapStr;
	}
}
