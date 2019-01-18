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
import com.yqsh.diningsys.web.dao.inve.DgDiscPointMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgDepaMaterial;
import com.yqsh.diningsys.web.model.inve.DgDiscPoint;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.service.inve.DgDiscPointService;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 盘点业务实现类
 * @author jianglei
 * 日期 2016年10月26日 上午11:50:22
 *
 */
@Service
public class DgDiscPointServiceImpl implements DgDiscPointService{

	@Autowired
	private DgDiscPointMapper discPointMapper;
	@Autowired
	private DgInventoryService inventoryService;
	@Override
	public Page<DgDiscPoint> getPageList(DgDiscPoint discPoint) {
		Integer totle = discPointMapper.countListByPage(discPoint);
		List<DgDiscPoint> list = discPointMapper.getPageList(discPoint);
		return PageUtil.getPage(totle, discPoint.getPage(),list, discPoint.getRows());
	}
	@Override
	public int update(DgDiscPoint discPoint) {
		return discPointMapper.update(discPoint);
	}

	@Override
	public DgDiscPoint get(String id) {
		return discPointMapper.get(id);
	}

	@Override
	@Transactional
	public Map<String, String> saveDiscPoint(DgDiscPoint discPoint, String jsonArr) {
		Map<String,String> mapMsg=null;
		Map<String,String> mapStr=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		int succ=0;
		int fail=0;
		JSONArray json=JSONArray.fromObject(jsonArr);
		for (Object obj : json) {
			JSONObject jsonStr=JSONObject.fromObject(obj);
			discPoint.setId(UUID.randomUUID().toString());
			discPoint.setItemId(jsonStr.getString("id"));
			discPoint.setNumber(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("number"))));
			discPoint.setItemName(jsonStr.getString("itemName"));
			discPoint.setUnit(jsonStr.getString("unit"));
			discPoint.setOrigPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("origPrice"))));
			discPoint.setPresPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("presPrice"))));
			discPoint.setSumAmount(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("sumAmount"))));
			discPoint.setSinceNumber(jsonStr.getString("sinceNumber"));
			discPoint.setRemark(jsonStr.getString("remark"));
			mapMsg=saveDisc(discPoint);
			if(mapMsg!=null&&"OK".equals(mapMsg.get("success"))){
				++succ;
			}else{
				++fail;
				sb.append(discPoint.getItemName()).append(",");
			}
		}
		mapStr.put("success","OK");
		mapStr.put("msg", "保存成功数量["+succ+"],失败数量["+fail+"]。"+sb.toString());
		return mapStr;
	}
	private synchronized Map<String,String> saveDisc(DgDiscPoint discPoint){
		Map<String,String> mapMsg=new HashMap<String,String>();
		mapMsg.put("msg", "fail");
		// 根据物品编号检测库存是否存在,存在就修改不存在就新增
		if (null != discPoint && !StringUtils.isNotBlank(discPoint.getItemId())) {
			//mapMsg.put("msgInfo", "请选择盘点物品!");
			return mapMsg;
		}
		DgInventory inveParams = new DgInventory();
		inveParams.setItemId(discPoint.getItemId());
		inveParams.setWareID(discPoint.getWareID());
		//获取调出仓库的库存信息
		List<DgInventory> listInve = inventoryService.findListData(inveParams);
		if(null!=listInve&&(listInve.size()>1||listInve.size()<1)){
			//mapMsg.put("msgInfo", "盘点，物品库存数据不正确!");
			return mapMsg;
		}
		if(discPoint.getNumber().doubleValue()<0){
			//mapMsg.put("msgInfo", "请输入正确的盘点数量!");
			return mapMsg;
		}
		//if (null != listInve && listInve.size() == 1) {
			DgInventory dginve = listInve.get(0);
			discPoint.setDiffNum(discPoint.getNumber().subtract(dginve.getNumber()));//差异数量，正数表示多的数量，负数表示少的量
			//计算差异金额
			BigDecimal diffAmount=dginve.getNumber().multiply(discPoint.getPresPrice());
			discPoint.setDiffAmount(discPoint.getSumAmount().subtract(diffAmount));//差异金额，正表示多，负表示少
			dginve.setNumber(discPoint.getNumber());
			dginve.setItemName(discPoint.getItemName());
			inventoryService.update(dginve);
	//	}
		//库存不存在允许盘点,代码可删，暂时保留
		/* else {
			DgInventory inve=new DgInventory();
			inve.setId(UUID.randomUUID().toString());
			inve.setSerialNumber(SerialNumberUtil.createInserialNum("IN"));
			discPoint.setDiffNum(BigDecimal.valueOf(0));//差异数量，正数表示多的数量，负数表示少的量
			discPoint.setDiffAmount(BigDecimal.valueOf(0));//差异金额，正表示多，负表示少
			inve.setItemId(discPoint.getItemId());
			inve.setItemName(discPoint.getItemName());
			inve.setUnit(discPoint.getUnit());
			inve.setNumber(discPoint.getNumber());
			inventoryService.insert(inve);
		}*/
		discPoint.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String procSerNum="";
			//根据条件获取最新一条数据
			DgDiscPoint dpr=new DgDiscPoint();
			dpr.setDateTime(discPoint.getDateTime());
			DgDiscPoint dgproc=discPointMapper.findLastOne(dpr);
			String prefix=FaceImageType.TS.toString();
			if(null!=dgproc&&StringUtils.isNotBlank(dgproc.getSerialNumber())){
				prefix=prefix+DateUtil.fromatStrToStr(discPoint.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS, dgproc.getSerialNumber());
			}else{
				prefix=prefix+DateUtil.fromatStrToStr(discPoint.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS,null); 
			}
			discPoint.setSerialNumber(procSerNum);
			discPointMapper.insert(discPoint);
		mapMsg.put("success", "OK");
		return mapMsg;
	}
}
