package com.yqsh.diningsys.web.service.inve.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.dao.inve.DgProcurementMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.model.inve.DgProcurement;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;
import com.yqsh.diningsys.web.service.inve.DgProcurementService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 采购管理业务实现类
 * @author jianglei
 * 日期 2016年10月21日 下午2:55:57
 *
 */
@Service
public class DgProcurementServiceImpl implements DgProcurementService{
	@Autowired
	private DgProcurementMapper procurementMapper;
	@Autowired
	private DgInventoryService inventoryService;

	@Override
	public Page<DgProcurement> getPageList(DgProcurement proc) {
		Integer totle = procurementMapper.countListByPage(proc);
		List<DgProcurement> list = procurementMapper.getPageList(proc);
		return PageUtil.getPage(totle, proc.getPage(),list, proc.getRows());
	}
	@Override
	public int update(DgProcurement proc) {
		return procurementMapper.update(proc);
	}
	@Override
	public DgProcurement get(String id) {
		return procurementMapper.get(id);
	}
	@Override
	@Transactional
	public Map<String, String> insertProc(DgProcurement proc, String jsonArr) {
		Map<String,String> mapMsg=null;
		Map<String,String> mapStr=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		int succ=0;
		int fail=0;
		JSONArray json=JSONArray.fromObject(jsonArr);
		for (Object obj : json) {
			JSONObject jsonStr=JSONObject.fromObject(obj);
			proc.setId(UUID.randomUUID().toString());
			proc.setItemId(jsonStr.getString("id"));
			proc.setNumber(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("number"))));
			proc.setItemName(jsonStr.getString("itemName"));
			proc.setUnit(jsonStr.getString("unit"));
			proc.setOrigPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("origPrice"))));
			proc.setPresPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("presPrice"))));
			proc.setSumAmount(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("sumAmount"))));
			proc.setSinceNumber(jsonStr.getString("sinceNumber"));
			proc.setRemark(jsonStr.getString("remark"));
			mapMsg=saveProc(proc);
			if(mapMsg!=null&&"OK".equals(mapMsg.get("success"))){
				++succ;
			}else{
				++fail;
				sb.append(proc.getItemName()).append(",");
			}
		}
		mapStr.put("success","OK");
		mapStr.put("msg", "保存成功数量["+succ+"],失败数量["+fail+"]。"+sb.toString());
		return mapStr;
	}

	@Override
	public void insertFastStorage(DgProcurement proc) {
		proc.setId(UUID.randomUUID().toString());
		saveProc(proc);
	}

	private synchronized Map<String,String> saveProc(DgProcurement proc){
		//根据物品编号检测库存是否存在,存在就修改不存在就新增
		Map<String,String> mapStr=new HashMap<String,String>();
		mapStr.put("success", "fail");
		if(null!=proc&&!StringUtils.isNotBlank(proc.getItemId())){
			return mapStr;
		}
		
		DgInventory inve=new DgInventory();
		inve.setItemId(proc.getItemId());
		inve.setWareID(proc.getWareID());
		List<DgInventory> listInve=inventoryService.findListData(inve);
		if(null!=listInve&&listInve.size()>1){
			return mapStr;
		}
		if(null!=listInve&&listInve.size()==1){
			DgInventory dginve=listInve.get(0);
			if(DgProcurement.PROCTYPE_OUT.equals(proc.getProcType())){
				if(proc.getNumber().compareTo(dginve.getNumber())>0){//比较大小
					return mapStr;
				}
				dginve.setNumber(dginve.getNumber().subtract(proc.getNumber()));//相减赋值
			}else if(DgProcurement.PROCTYPE_INTO.equals(proc.getProcType())){//采购入库
				dginve.setNumber(dginve.getNumber().add(proc.getNumber()));//相加赋值
			}
			dginve.setItemName(proc.getItemName());
			proc.setInveId(dginve.getId());
			inventoryService.update(dginve);
		}else{
			if(!DgProcurement.PROCTYPE_INTO.equals(proc.getProcType())){
				return mapStr;
			}
			inve.setId(UUID.randomUUID().toString());
			inve.setWareID(proc.getWareID());
			inve.setItemId(proc.getItemId());
			inve.setItemName(proc.getItemName());
			inve.setUnit(proc.getUnit());
			inve.setNumber(proc.getNumber());
			proc.setInveId(inve.getId());
			inventoryService.insert(inve);
		}
		proc.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String procSerNum="";
			//根据条件获取最新一条数据
			DgProcurement dpr=new DgProcurement();
			dpr.setProcType(proc.getProcType());
			dpr.setDateTime(proc.getDateTime());
			DgProcurement dgproc=procurementMapper.findLastOne(dpr);
			String prefix="";
			if(DgProcurement.PROCTYPE_INTO.equals(proc.getProcType())){
				prefix=FaceImageType.IN.toString();
			}else if(DgProcurement.PROCTYPE_OUT.equals(proc.getProcType())){
				prefix=FaceImageType.BA.toString();
			}
			if(null!=dgproc&&StringUtils.isNotBlank(dgproc.getSerialNumber())){
				prefix=prefix+DateUtil.fromatStrToStr(proc.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgProcurement.SERIA_DIGITS, dgproc.getSerialNumber());
			}else{
				prefix=prefix+DateUtil.fromatStrToStr(proc.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgProcurement.SERIA_DIGITS,null); 
			}
			proc.setSerialNumber(procSerNum);
			procurementMapper.insert(proc);
		mapStr.put("success", "OK");
		return mapStr;
	}
}
