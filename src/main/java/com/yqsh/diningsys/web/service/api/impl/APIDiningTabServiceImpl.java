package com.yqsh.diningsys.web.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.PinYinUtil;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillColorMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillMapper;
import com.yqsh.diningsys.web.dao.archive.DgCardMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.api.DgPreOrderbillColor;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIDiningTabService;
import com.yqsh.diningsys.web.service.api.BillService;

@Service
public class APIDiningTabServiceImpl implements APIDiningTabService{
	@Autowired
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Autowired
	private DgCardMapper dgCardMapper;
	@Autowired
	private DgItemFileMapper dgItemFileMapper;
	@Autowired
	private BillService  billService;
	@Autowired
	private APICheckServiceMapper apiCheckServiceMapper;
	@Autowired
	private APICheckService apiCheckService;
	@Autowired
	private DgPreOrderbillMapper dgPreOrderbillMapper;
	@Override
	public Map queryTable(String org) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Gson gson = new Gson();
		List<Map> body = new ArrayList<Map>();
		String content = org.substring(org.indexOf("{"), org.lastIndexOf("}")+1);
		Map tableno = gson.fromJson((content), new TypeToken<Map>() {}.getType());
		if(StringUtils.isEmpty(tableno.get("tableno").toString())){
			DgConsumerSeat seat = new DgConsumerSeat();
			seat.setDelFlag(0);
			List<DgConsumerSeat> seats = dgConsumerSeatMapper.getAllList(seat);
			for(DgConsumerSeat s:seats){
				DgCard dgCard = new DgCard();
				dgCard.setConsumerid(""+s.getId());
				List<DgCard> cards = dgCardMapper.getCardsByConsumerSeatId(dgCard);
				Map sitem = new HashMap();
				sitem.put("tableno",s.getNumber());
				sitem.put("tablename",s.getName());
				sitem.put("tablestate",""+s.getSeatState());
				int min = cards.size() > 3 ? 3 : cards.size();
				for(int i=1;i<=min;i++){
					sitem.put("card"+i,cards.get(i-1).getCardnum().replaceFirst("^0*", ""));
				}
				if(min<3){
					for(;min<3;min++){
						sitem.put("card"+(min+1),"");
					}
				}
				body.add(sitem);
			}
		} else {
			DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatByNumber(tableno.get("tableno").toString());
			if(seat != null) {
				DgCard dgCard = new DgCard();
				dgCard.setConsumerid(""+seat.getId());
				List<DgCard> cards = dgCardMapper.getCardsByConsumerSeatId(dgCard);
				Map sitem = new HashMap();
				sitem.put("tableno",seat.getNumber());
				sitem.put("tablename",seat.getName());
				sitem.put("tablestate",""+seat.getSeatState());
				int min = cards.size() > 3 ? 3 : cards.size();
				for(int i=1;i<=min;i++){
					sitem.put("card"+i,cards.get(i-1).getCardnum().replaceFirst("^0*", ""));
				}	
				body.add(sitem);
			}
		}
		
		if(body.size() > 0) {
			ret.put("result","0000");
			ret.put("msg","成功");
			ret.put("count",""+body.size());
			ret.put("body",body);
		} else {
			ret.put("result","0004");
			ret.put("msg","台位(桌位)不存在");
			ret.put("count","0");
		}		
		return ret;
	}

	@Override
	public Map queryItemFile(String org) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Gson gson = new Gson();
		List<Map> body = new ArrayList<Map>();
		String content = org.substring(org.indexOf("{"), org.lastIndexOf("}")+1);
		Map dishno = gson.fromJson((content), new TypeToken<Map>() {}.getType());
		List<DgItemFile> sfile = dgItemFileMapper.getItemByTableOrg(dishno.get("dishno").toString());
		for(DgItemFile s:sfile){
			Map sitem = new HashMap();
			sitem.put("dishno",s.getNum());
			sitem.put("dishname",s.getName());
			sitem.put("dishunit",s.getUnit()==null?"":s.getUnit());
			sitem.put("dishprice",s.getStandardPrice()==null?"0.00":s.getStandardPrice()+"");
			sitem.put("barcode",s.getSptm()==null?"":s.getSptm());
			sitem.put("simplecode",PinYinUtil.getFirstSpellUpperCase(s.getName()));
			sitem.put("fclassid",s.getbNum()==null?"":s.getbNum());
			sitem.put("fclassname",s.getbName()==null?"":s.getbName());
			sitem.put("cclassid",s.getsNum()==null?"":s.getsNum());
			sitem.put("cclassname",s.getcName()==null?"":s.getcName());
			body.add(sitem);
		}
		
		if(body.size() > 0) {
			ret.put("result","0000");
			ret.put("msg","成功");
			ret.put("count",""+body.size());
			ret.put("body",body);
		} else {
			ret.put("result","0005");
			ret.put("msg","商品(菜品)不存在");
			ret.put("count","0");
		}		
		return ret;
	}

	@Override
	public Map insertOrderItemFile(String org,int type) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Gson gson = new Gson();
		String content = org.substring(org.indexOf("{"), org.lastIndexOf("}")+1);
		Map orderContent = gson.fromJson((content), new TypeToken<Map>() {}.getType());
		String code = orderContent.get("code").toString(); //挂单序号(每日从 000001 开始)
		String posid  = orderContent.get("posid").toString(); //表示由哪台智慧餐台发起,是餐台的唯一编号
		String tableno = orderContent.get("tableno").toString(); //台位号
		String count = orderContent.get("count").toString();//body记录条数
		String preNum = null;
		if(type == 2){
			 preNum = orderContent.get("preNum").toString(); //台位号
		}
		List<Map> body = (List<Map>)orderContent.get("body");
		List<Map<String,Object>> dishItems = new ArrayList<Map<String,Object>>();
		for(Map b:body) {
			if(dishItems.isEmpty()){
				DgItemFile item = dgItemFileMapper.getDgItemFileByNumber(b.get("dishno").toString());
				if(item== null){
					setMapError(ret,"0005","失败(商品(菜品)不存在)",code,posid,tableno,preNum,null,null);
					return ret;
				}
				Map dish = new HashMap();
				dish.put("dishno",b.get("dishno"));
				dish.put("itemFileId",item.getId());
				dish.put("itemFileNumber",Double.valueOf(b.get("dishcount").toString()));
				dish.put("extra", null);
				dishItems.add(dish);
			} else {
				boolean hava = false;
				for(Map d:dishItems){
					String bdishno = b.get("dishno").toString();
					String ddishno = d.get("dishno").toString();
					if(bdishno.equals(ddishno)){
						Double itemFileNumber = (Double) d.get("itemFileNumber");
						itemFileNumber = MathExtend.add(itemFileNumber,Double.valueOf(b.get("dishcount").toString()));
						d.put("itemFileNumber",itemFileNumber);
						hava = true;
						break;
					}
				}
				if(!hava) {
					DgItemFile item = dgItemFileMapper.getDgItemFileByNumber(b.get("dishno").toString());
					Map dish = new HashMap();
					dish.put("dishno",b.get("dishno"));
					dish.put("itemFileId",item.getId());
					dish.put("itemFileNumber",Double.valueOf(b.get("dishcount").toString()));
					dish.put("extra", null);
					dishItems.add(dish);
				}
			}
		}
		
		if(dishItems.size() == 0){
			setMapError(ret,"0012","没有菜品!",code,posid,"",preNum,null,null);
			return ret;
		}
		
		DgConsumerSeat seatInfo = dgConsumerSeatMapper.getConsumerSeatByNumber(tableno);
		if(seatInfo == null) {
			setMapError(ret,"0004","失败(台位(桌位)不存在)",code,posid,"",preNum,null,null);
			return ret;
		}
		Map seatmap = new HashMap();
		seatmap.put("seatId", seatInfo.getId());
		//查询出最后条流水并且流水状态为(1,3,8,9)
		Map water =  apiCheckServiceMapper.selectOpenWaterBySeatIdLastOne(seatmap);
		if(water == null || water.isEmpty()) {
			setMapError(ret,"0013","失败(台位未开台)",code,posid,tableno,preNum,null,null);
		} else {
			Integer owState = (Integer) water.get("ow_state");
			if (owState == 3) {
				setMapError(ret,"0009","失败(台位已埋单)",code,posid,tableno,preNum,null,null);
			} else if(owState == 8) {
				setMapError(ret,"0010","失败(台位已手工锁定)",code,posid,tableno,preNum,null,null);
			} else if(owState == 9) {
				setMapError(ret,"0011","失败(台位已结算锁定)",code,posid,tableno,preNum,null,null);
			} else{
				Map addret = null;
				if(type == 1){
					addret = billService.addBill(null, dishItems, water.get("ow_num").toString(), "3", "餐台点菜",false);		
				} else {
					addret = billService.addBill(null, dishItems, water.get("ow_num").toString(), "2", "餐台预点菜加单",false);		
				}
				if (addret.containsKey("error")) {
					if (addret.get("error") instanceof String) {
		                setMapError(ret,"0012",(String) addret.get("error"),code,posid,tableno,preNum,null,null);
		            }  else {
		            	setMapError(ret,"9999","未知错误",code,posid,tableno,preNum,null,null);
		            }
				} else {
					if(type == 2){
						//更新颜色盘子数量
						for(Map<String,Object> d:dishItems) {
							DgPreOrderbill dgPreOrderbill= new DgPreOrderbill();
							dgPreOrderbill.setItemFileId((int)d.get("itemFileId"));
							dgPreOrderbill.setWaterId((int)water.get("id"));
							List<DgPreOrderbill> dgPreOrderbills = dgPreOrderbillMapper.selectByWaterIdAndItemFileId(dgPreOrderbill);
							int dinCount = (int)(double)d.get("itemFileNumber");
							for(DgPreOrderbill dgPre : dgPreOrderbills){
								if(dinCount == 0){
									break;
								}
								if(dgPre.getItemFileCount().intValue() > dinCount) {
									//update 大于
									dgPre.setItemFileCount(dgPre.getItemFileCount() - dinCount);
									dgPreOrderbillMapper.updateItemCount(dgPre);
								} else {
									//delete 等于
									dgPreOrderbillMapper.deleteByPrimaryKey(dgPre.getId());
									dinCount = dinCount - dgPre.getItemFileCount();
								}
							}
						}	
					}
					setMapSuccess(ret,code,posid,tableno,body,preNum,null);
				}
			}
		}
		return ret;
	}
	
	private void setMapError(Map src,String errorCode,String msg,String orderCode,String posid,String tableno
			,String preNum,List<DgPreOrderbill> dgPreOrderbills,List<Map> errorMap){
		src.put("result",errorCode);
		src.put("msg",msg);
		src.put("posid",posid);
		src.put("tableno",tableno);
		src.put("count","0");
		if(!StringUtils.isEmpty(preNum)){
			src.put("preNum",preNum);
		}
		if(!StringUtils.isEmpty(orderCode)){
			src.put("code",orderCode);
		}
		if(dgPreOrderbills != null){
			src.put("preItems",dgPreOrderbills);
		}
		if(errorMap != null){
			src.put("errorItems",errorMap);
		}
	}
	
	private void setMapSuccess(Map src,String orderCode,String posid,String tableno,
			List<Map> body,String preNum,List<DgPreOrderbill> dgPreOrderbills){
		src.put("result","0000");
		src.put("msg","成功");
		src.put("posid",posid);
		src.put("tableno",tableno);
		src.put("count",body.size()+"");
		List<Map> list = new ArrayList<Map>();
		for(Map b:body){
			Map l = new HashMap();
			l.put("keyid",b.get("keyid"));
			list.add(l);
		}
		src.put("body",list);
		if(!StringUtils.isEmpty(preNum)){
			src.put("preNum",preNum);
		}
		if(!StringUtils.isEmpty(orderCode)){
			src.put("code",orderCode);
		}
		if(dgPreOrderbills != null){
			src.put("preItems",dgPreOrderbills);
		}
	}

	@Override
	public Map insertPreOrderItemFileBefor(String org) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Gson gson = new Gson();
		String content = org.substring(org.indexOf("{"), org.lastIndexOf("}")+1);
		Map orderContent = gson.fromJson((content), new TypeToken<Map>() {}.getType());
		String posid  = orderContent.get("posid").toString(); //表示由哪台智慧餐台发起,是餐台的唯一编号
		String tableno = orderContent.get("tableno").toString(); //台位号
		String count = orderContent.get("count").toString();//body记录条数
		String preNum = orderContent.get("preNum").toString();//预定单号
		List<Map> body = (List<Map>)orderContent.get("body");
		List<DgPreOrderbill> dishItems = new ArrayList<DgPreOrderbill>();
		for(Map b:body) {
			if(dishItems.isEmpty()){
				DgItemFile item = dgItemFileMapper.getDgItemFileByNumber(b.get("dishno").toString());
				if(item== null){
					setMapError(ret,"0005","失败(商品(菜品)不存在)",null,posid,tableno,preNum,null,null);
					return ret;
				}
				DgPreOrderbill dish = new DgPreOrderbill();
				dish.setItemNum(b.get("dishno").toString());
				dish.setItemName(item.getName());
				dish.setItemFileId(item.getId());
				dish.setItemFileCount(Integer.valueOf(b.get("dishcount").toString()));
				dishItems.add(dish);
			} else {
				boolean hava = false;
				for(DgPreOrderbill d:dishItems){
					String bdishno = b.get("dishno").toString();
					String ddishno = d.getItemNum();
					if(bdishno.equals(ddishno)){
						int itemFileNumber = d.getItemFileCount();
						itemFileNumber =(int) MathExtend.add(itemFileNumber,Integer.valueOf(b.get("dishcount").toString()));
						d.setItemFileCount(itemFileNumber);
						hava = true;
						break;
					}
				}
				if(!hava) {
					DgItemFile item = dgItemFileMapper.getDgItemFileByNumber(b.get("dishno").toString());
					DgPreOrderbill dish = new DgPreOrderbill();
					dish.setItemNum(b.get("dishno").toString());
					dish.setItemName(item.getName());
					dish.setItemFileId(item.getId());
					dish.setItemFileCount(Integer.valueOf(b.get("dishcount").toString()));
					dishItems.add(dish);
				}
			}
		}
//		if(dishItems.size() == 0){
//			setMapError(ret,"0012","没有菜品!",null,posid,"",preNum,null);
//			return ret;
//		}
		
		DgOpenWater water =  dgPreOrderbillMapper.selectWaterInfoByPreNum(preNum);
		if(water == null) {
			setMapError(ret,"0004","该预订单下没有未上品项!",null,posid,"",preNum,null,null);
		} else {
			List<DgPreOrderbill> dgPreOrderbills = dgPreOrderbillMapper.selectByWaterId(water.getId());
			Integer owState = water.getOwState();
			if (owState == 3) {
				setMapError(ret,"0009","失败(台位已埋单)",null,posid,water.getTableno(),preNum,dgPreOrderbills,null);
			} else if(owState == 8) {
				setMapError(ret,"0010","失败(台位已手工锁定)",null,posid,water.getTableno(),preNum,dgPreOrderbills,null);
			} else if(owState == 9) {
				setMapError(ret,"0011","失败(台位已结算锁定)",null,posid,water.getTableno(),preNum,dgPreOrderbills,null);
			} else{
				List<Map> errorList = new ArrayList<Map>();
				boolean addret = checkPreSuccess(dishItems,preNum,water.getId(),errorList,dgPreOrderbills);
				if (!addret) {
		            setMapError(ret,"0013","请看错误列表",null,posid,water.getTableno(),preNum,dgPreOrderbills,errorList);
				} else {
					setMapSuccess(ret,null,posid,water.getTableno(),body,preNum,dgPreOrderbills);
				}
			}
		}
		return ret;
	}
	
	private boolean checkPreSuccess(List<DgPreOrderbill> dishItems,String preNum,Integer waterId,List<Map> errorList,List<DgPreOrderbill> dgPreOrderbills){
		for(DgPreOrderbill d:dishItems){
			d.setFirstNum(preNum);
			d.setWaterId(waterId);
			boolean havaItem = false;
			for(DgPreOrderbill drPre : dgPreOrderbills) {
				if(drPre.getItemFileId().intValue() == d.getItemFileId().intValue()){
					if(drPre.getItemFileCount().intValue() < d.getItemFileCount().intValue()){
						Map errorItem = new HashMap();
						errorItem.put("itemNum",d.getItemNum());
						errorItem.put("itemName",d.getItemName());
						errorItem.put("itemFileCount",d.getItemFileCount());
						errorItem.put("errorMsg","数量大于预点数量");
						errorList.add(errorItem);
						drPre.setCompareIde(2);
					} else if(drPre.getItemFileCount().intValue() > d.getItemFileCount().intValue()) {
						drPre.setCompareIde(0);
					} else {
						drPre.setCompareIde(1);
					}
					drPre.setThisCount(d.getItemFileCount());
					havaItem = true;
					break;
				}
			}
			if(!havaItem){
				Map errorItem = new HashMap();
				errorItem.put("itemNum",d.getItemNum());
				errorItem.put("itemName",d.getItemName());
				errorItem.put("itemFileCount",d.getItemFileCount());
				errorItem.put("errorMsg","菜品不在预点单中");
				errorList.add(errorItem);
			}
		}
		return errorList.size() > 0 ? false : true;
	}
	
}
