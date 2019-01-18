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
import com.yqsh.diningsys.web.dao.inve.DgTransfersMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgDepaMaterial;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.model.inve.DgTransfers;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;
import com.yqsh.diningsys.web.service.inve.DgTransfersService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 调拨业务接口实现类
 * @author jianglei
 * 日期 2016年10月25日 下午2:19:43
 *
 */
@Service
public class DgTransfersServiceImpl implements DgTransfersService{
	
	@Autowired
	private DgTransfersMapper transfersMapper;
	@Autowired
	private DgInventoryService inventoryService;

	@Override
	public Page<DgTransfers> getPageList(DgTransfers tran) {
		Integer totle = transfersMapper.countListByPage(tran);
		List<DgTransfers> list = transfersMapper.getPageList(tran);
		return PageUtil.getPage(totle, tran.getPage(),list, tran.getRows());
	}
	@Override
	public int update(DgTransfers tran) {
		return transfersMapper.update(tran);
	}
	@Override
	public DgTransfers get(String id) {
		return transfersMapper.get(id);
	}
	@Override
	@Transactional
	public Map<String, String> saveTran(DgTransfers tran, String jsonArr) {
		Map<String,String> mapMsg=null;
		Map<String,String> mapStr=new HashMap<String,String>();
		StringBuffer sb=new StringBuffer();
		int succ=0;
		int fail=0;
		JSONArray json=JSONArray.fromObject(jsonArr);
		for (Object obj : json) {
			JSONObject jsonStr=JSONObject.fromObject(obj);
			tran.setId(UUID.randomUUID().toString());
			tran.setItemId(jsonStr.getString("id"));
			tran.setNumber(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("number"))));
			tran.setItemName(jsonStr.getString("itemName"));
			tran.setUnit(jsonStr.getString("unit"));
			tran.setOrigPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("origPrice"))));
			tran.setPresPrice(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("presPrice"))));
			tran.setSumAmount(BigDecimal.valueOf(Double.valueOf(jsonStr.getString("sumAmount"))));
			tran.setSinceNumber(jsonStr.getString("sinceNumber"));
			tran.setRemark(jsonStr.getString("remark"));
			mapMsg=saveTran(tran);
			if(mapMsg!=null&&"OK".equals(mapMsg.get("success"))){
				++succ;
			}else{
				++fail;
				sb.append(tran.getItemName()).append(",");
			}
		}
		mapStr.put("success","OK");
		mapStr.put("msg", "操作成功数量["+succ+"],失败数量["+fail+"]。"+sb.toString());
		return mapStr;
	}
	private synchronized Map<String,String> saveTran(DgTransfers tran){
		Map<String,String> mapMsg=new HashMap<String,String>();
		mapMsg.put("msg", "fail");
		// 根据物品编号检测库存是否存在,存在就修改不存在就新增
		if (null != tran && !StringUtils.isNotBlank(tran.getItemId())) {
			//mapMsg.put("msgInfo", "请选择调拨物品!");
			return mapMsg;
		}
		DgInventory inve = new DgInventory();
		inve.setItemId(tran.getItemId());
		inve.setWareID(tran.getOutWareId());
		//获取调出仓库的库存信息
		List<DgInventory> listInveOut = inventoryService.findListData(inve);
		inve.setWareID(tran.getInWareId());
		List<DgInventory> listInveIn = inventoryService.findListData(inve);
		if(null!=listInveOut&&listInveOut.size()!=1){
			//mapMsg.put("msgInfo", "调出仓库，物品库存数据不正确!");
			return mapMsg;
		}
		if(null!=listInveIn&&listInveIn.size()>1){
			//mapMsg.put("msgInfo", "调入仓库，物品库存数据不正确!");
			return mapMsg;
		}
		if(tran.getNumber().compareTo(listInveOut.get(0).getNumber())>0){
			//mapMsg.put("msgInfo", "调出仓库物品数量不足!");
			return mapMsg;
		}
		DgInventory dginveOut = listInveOut.get(0);
		dginveOut.setNumber(dginveOut.getNumber().subtract(tran.getNumber()));
		dginveOut.setItemName(tran.getItemName());
		inventoryService.update(dginveOut);
		if (null != listInveIn && listInveIn.size() == 1) {
			DgInventory dginveIn = listInveIn.get(0);
			dginveIn.setNumber(dginveIn.getNumber().add(tran.getNumber()));
			dginveIn.setItemName(tran.getItemName());
			tran.setInveId(dginveIn.getId());
			inventoryService.update(dginveIn);
		} else {
			inve.setId(UUID.randomUUID().toString());
			inve.setWareID(tran.getInWareId());
			inve.setItemId(tran.getItemId());
			inve.setItemName(tran.getItemName());
			inve.setUnit(tran.getUnit());
			inve.setNumber(tran.getNumber());
			tran.setInveId(inve.getId());
			inventoryService.insert(inve);
		}
		tran.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String procSerNum="";
			//根据条件获取最新一条数据
			DgTransfers dpr=new DgTransfers();
			dpr.setDateTime(tran.getDateTime());
			DgTransfers dgproc=transfersMapper.findLastOne(dpr);
			String prefix=FaceImageType.MO.toString();
        if(null!=dgproc&&StringUtils.isNotBlank(dgproc.getSerialNumber())){
				prefix=prefix+DateUtil.fromatStrToStr(tran.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS, dgproc.getSerialNumber());
			}else{
				prefix=prefix+DateUtil.fromatStrToStr(tran.getDateTime(), 
						DateUtil.YYYY_MM_DD, DateUtil.YYYYMMDD);
				procSerNum=SerialNumberUtil.createNo(prefix, DgDepaMaterial.SERIA_DIGITS,null); 
			}
			tran.setSerialNumber(procSerNum);
			transfersMapper.insert(tran);
		mapMsg.put("success", "OK");
		return mapMsg;
	}
}
