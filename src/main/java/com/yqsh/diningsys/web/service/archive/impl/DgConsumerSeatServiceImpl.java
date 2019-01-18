package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.yqsh.diningsys.api.util.DateTimeComputing;
import com.yqsh.diningsys.core.util.*;
import com.yqsh.diningsys.web.dao.api.DgReserveManagerMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveSeatidsMapper;
import com.yqsh.diningsys.web.dao.archive.DgCardMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DeskBusinessSettingMapper;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.DBSSeetServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.service.api.APICheckService;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatManagerMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;

@Service("dgConsumerSeatServiceImpl")
public class DgConsumerSeatServiceImpl implements DgConsumerSeatService{
	
	@Resource
    private DgConsumerSeatMapper consumerSeatMapper;
	@Resource
	private DgCardMapper cardMapper;

	@Resource
	private DeskBusinessSettingMapper deskBusinessSettingMapper;
	@Resource
	private DgSeatManagerMapper dgSeatManagerMapper;

	@Resource
	private DgPosMapper dgPosMapper;

	@Autowired
	private APICheckService apiCheckService;
	
	@Autowired
	private DgReserveManagerMapper dgReserveManagerMapper;

	@Override
	public Page<DgConsumerSeat> getPageList(DgConsumerSeat dgConsumerSeat) {
		if(!StringUtils.isEmpty(dgConsumerSeat.getCrEndTime())){
            Date dateByFormat = DateUtil.getDateByFormat(dgConsumerSeat.getCrEndTime(), "yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateByFormat);
			calendar.add(Calendar.DATE,1);
            String nextDay = DateUtil.dateToStr(calendar.getTime(),"yyyy-MM-dd");
            dgConsumerSeat.setCrEndTime(nextDay);
        }
		
		Integer totle = consumerSeatMapper.countListByPage(dgConsumerSeat);
		List<DgConsumerSeat> list = consumerSeatMapper.getListByPage(dgConsumerSeat);
		return PageUtil.getPage(totle, dgConsumerSeat.getPage(),list, dgConsumerSeat.getRows());
	}

	@Override
	public Page<DgCard> getCardsByConsumerSeatId(DgCard record) {
		Integer totle = cardMapper.countListByPage(record);
		List<DgCard> list = cardMapper.getCardsByConsumerSeatId(record);
		return PageUtil.getPage(totle, record.getPage(),list, record.getRows());
	}

	@Override
	public void deleteCardByid(DgCard record) {
		cardMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public void saveCard(DgCard record) {
		cardMapper.insert(record);
	}

	@Override
	public List<DgConsumerSeat> getAllSeatWithServiceClass(Integer bisId) {
		return consumerSeatMapper.getAllSeatWithServiceClass(bisId);
	}

    @Override
    public List<DgConsumerSeat> selectDataByAreaId(Integer id) {
        return consumerSeatMapper.selectDataByAreaId(id);
    }

    @Override
    public List<DgConsumerSeat> selectAllClearingSeat() {
		return consumerSeatMapper.selectAllClearingSeat();
    }

	@Override
	public DgReceptionClearingWater selectEndTime(Integer id) {
		return consumerSeatMapper.selectEndTime(id);
	}

	@Override
	public void modifySeatSeat(Integer id) {
		consumerSeatMapper.modifySeatSeat(id);
	}

	@Override
	public Integer updateSeatPDState(Map<String, Object> param) {
		return consumerSeatMapper.updateSeatPDState(param);
	}

	@Override
	public int insertOrUpd(DgConsumerSeat dgConsumerSeat) {
		int result = 0;
		if(dgConsumerSeat.getId() != null && dgConsumerSeat.getId() > 0){
			result = consumerSeatMapper.update(dgConsumerSeat);
		}else{
			dgConsumerSeat.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = consumerSeatMapper.newInsert(dgConsumerSeat);
//			dgSeatManagerMapper.insertSelective(new DgSeatManager(1, 1, 0, 1, dgConsumerSeat.getId()));
		}
		return result;
	}

	@Override
	public DgConsumerSeat getDgConsumerSeatByID(DgConsumerSeat dgConsumerSeat) {
		return consumerSeatMapper.getDgConsumerSeatByID(dgConsumerSeat);
	}

	@Override
	public DgConsumerSeat selectByPrimaryKey(Integer id) {
		return consumerSeatMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(DgConsumerSeat dgConsumerSeat) {
		dgSeatManagerMapper.deleteBySeatIds(dgConsumerSeat);
		return consumerSeatMapper.delete(dgConsumerSeat);
	}

	@Override
	public List<DgConsumerSeat> getAllList(DgConsumerSeat dgConsumerSeat) {
		return consumerSeatMapper.getAllList(dgConsumerSeat);
	}

    @Override
    public DgConsumerSeat getConsumerSeatByNumber(String number) {
        return consumerSeatMapper.getConsumerSeatByNumber(number);
    }

    private String checkIsTransferWater(List<DgOpenWater> dgOpenWaters,String judgeWater){
		String transferInfo = "";
		for(DgOpenWater openWater:dgOpenWaters){
			if(openWater.getTransferTarget().equals(judgeWater)){
				String joinTeamNotes = openWater.getJoinTeamNotes();
				transferInfo += joinTeamNotes.split("，")[1].split("：")[1] + "转,";
			}
		}
		return StringUtils.isEmpty(transferInfo)?null:transferInfo.substring(0,transferInfo.length()-1);
	}

	@Override
	public List<Map<String, Object>> getConsumerSeatByArea(String areas,Integer areaId,Integer state) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("areas", CommonUtils.stringToList(areas));
		map.put("areaId",areaId);
		map.put("state",state);

		/*//桌子的基本信息
		List<DgConsumerSeat> dgConsumerSeats = consumerSeatMapper.selectAllUsingSeat(map);

		//查询出当前正常营业中的所有被转账的流水*/
		//List<DgOpenWater> dgOpenWaters = consumerSeatMapper.selectCurrentTransferOpenWater(map);

		List<Map<String, Object>> consumerSeatByArea = consumerSeatMapper.getConsumerSeatByArea(map);

		List<Map<String, Object>> returnSeatArea = new ArrayList<>();


		/*for(Map info:consumerSeatByArea){
			Integer seatId2 = MapUtils.getInteger(info, "seatId2");
			String owNum = MapUtils.getString(info, "ow_num");
			Integer isTeam = MapUtils.getInteger(info, "is_team");
			if(StringUtils.isEmpty(MapUtils.getString(info,"transfer_target"))){
				String transferInfo = checkIsTransferWater(dgOpenWaters, owNum);
				if(!StringUtils.isEmpty(transferInfo)){//如果该营业流水是转账的主流水，
					for(DgConsumerSeat seat:dgConsumerSeats){
						if(seat.getId().equals(seatId2)){
							List<String> ot = seat.getTransferInfo();
							ot.add(transferInfo);
							seat.setTransferInfo(ot); //转账的信息设置完毕
						}
					}
				}else{//如果不是转账的主流水，那么判断是否为团队
					if(isTeam.equals(1)){//为团队
						for(DgConsumerSeat seat:dgConsumerSeats){
							if(seat.getId().equals(seatId2)){
								List<String> ot = seat.getTeamInfo();
								ot.add("团");
								seat.setTransferInfo(ot); //转账的信息设置完毕
							}
						}
					}
				}
			}



		}*/

		//所有的客位设置
//        List<DgSeatManager> dgSeatManagers = dgSeatManagerMapper.selectAllDetailBySeatId();

        //时间差计算
		//TODO 手动每次计算每条营业流水的subtotal，留待后改   去掉小计
		Double tempSubtotal = 0.0;
        for(Map seatInfo:consumerSeatByArea){
        	if(seatInfo.get("is_team") != null){
//				if(Integer.parseInt(seatInfo.get("is_team").toString()) == 1 && seatInfo.get("transfer_target") == null){//为团队流水
				if(Integer.parseInt(seatInfo.get("is_team").toString()) == 1){//为团队流水
					seatInfo.put("teamInfo","团："+seatInfo.get("teamMainSeatName"));
				}
			}
        	
			if((int)seatInfo.get("seatState") == 4){
				Map org = new HashMap();
				org.put("seatId",seatInfo.get("seatId2"));
				org.put("time",new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS).format(new Date()));
				seatInfo.put("ydInfo",dgReserveManagerMapper.selectBySeatId(org));
			}
			
			

			/*if(seatInfo.get("transferTeamInfo") != null){
				String transferTeamInfo = seatInfo.get("transferTeamInfo").toString();
				String[] split = transferTeamInfo.split(",");
				String transferInfo = "";
				for(String s:split){
					transferInfo += s.split("，")[2].split("：")[1] + "转，";
				}
				seatInfo.put("transferInfo",transferInfo.substring(0,transferInfo.length()-1));
			}*/

            if(seatInfo.containsKey("lastOpenTime") && seatInfo.get("lastOpenTime") != null){
                seatInfo.put("timeCal",DateTimeComputing.compution(seatInfo.get("lastOpenTime")));
            }

            if(seatInfo.containsKey("yd_id") && seatInfo.get("yd_id") != null){
            	seatInfo.put("ydInfo",dgReserveManagerMapper.selectByPrimaryKey((int)seatInfo.get("yd_id")));
            }
//TODO 手动每次计算每条营业流水的subtotal，留待后改   去掉小计
//			Double tempSubtotal = 0.0;
//			if(seatInfo.containsKey("ow_num")){
//				if(!StringUtils.isEmpty(seatInfo.get("ow_num").toString())){
//					//循环获取所有营业流水下面的所有有效品项按照服务单分组
//					List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(seatInfo.get("ow_num").toString());
//					//循环增加每条营业流水下的所有品项的价格
//					for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
//						tempSubtotal += MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts());
//					}
//				}
//			}
			seatInfo.put("subtotal",tempSubtotal);

			//2017年3月2日16:30:41 一个笨办法去重，无关消费金额
			if(checkHasSame(consumerSeatByArea,seatInfo.get("seatId2").toString())){//存在多条流水
				if(!checkHasSameInReturnData(returnSeatArea,seatInfo.get("seatId2").toString())){
					returnSeatArea.add(seatInfo);
				}else{
                    //如果返回的营业流水里已经有了相同的客座，将该营业流水的subtotal加入
                    modifyResDataSubtotal(returnSeatArea,seatInfo);
                }
			}else{//一条流水直接返回
				returnSeatArea.add(seatInfo);
			}
			
			
			
        }
        return returnSeatArea;
	}

	Boolean checkHasSame(List<Map<String,Object>> consumerSeatByArea,String seatId){
		Integer i = 0;
		for(Map seatInfo2:consumerSeatByArea){
			if(seatInfo2.get("seatId2").toString().equals(seatId)){
				i++;
			}
		}
		return i > 1;
	}

	Boolean checkHasSameInReturnData(List<Map<String,Object>> returnData,String seatId){
		for(Map seatInfo2:returnData){
			if(seatInfo2.get("seatId2").toString().equals(seatId)){
				return true;
			}
		}
		return false;
	}

	void modifyResDataSubtotal(List<Map<String,Object>> returnData,Map judgeData){
        Integer seatId2 = Integer.parseInt(judgeData.get("seatId2").toString());
        String subtotal1 = judgeData.get("subtotal").toString();
        for(Map map:returnData){
            Integer seatId21 = Integer.parseInt(map.get("seatId2").toString());
            if(seatId2.equals(seatId21)){
                String subtotal = map.get("subtotal").toString();
                String add = MathExtend.add(subtotal, subtotal1);
                map.put("subtotal",add);
			}
        }
    }


	@Override
	public int updateState(DgConsumerSeat seat) {
		// TODO Auto-generated method stub
		return consumerSeatMapper.updateState(seat);
	}

	@Override
	public List<DgConsumerSeat> getAllFreeSeat(Integer pos) {
		DeskBusinessSetting deskBusinessSetting = deskBusinessSettingMapper.getDeskBusinessSetting();
		DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);
        String isRetainRoomReserve = dbsSeetServDTO.getIsRetainRoomReserve();

        Map<String,Object> map = new HashMap<>();

        Boolean isAdmit = false;
        //允许内部留房开台、预定
        if(isRetainRoomReserve.equals("1")){
            isAdmit = true;
        }

		DgPos dgPos = dgPosMapper.selectPosByPosId(pos);

		map.put("isAdmit",isAdmit);
        map.put("list", StringUtil.arrayToList(dgPos.getConsumerAreas()));

        return consumerSeatMapper.getAllFreeSeat(map);
	}

	@Override
	public DgConsumerSeat getConsumerSeatById(Integer id) {
		// TODO Auto-generated method stub
		return consumerSeatMapper.getConsumerSeatById(id);
	}

	@Override
	public int deleteByIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    DgConsumerSeat seat = new DgConsumerSeat();
	    seat.setIds(ids);
		return consumerSeatMapper.deleteByIds(seat);
	}

	@Override
	public int restore(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
	    Collections.addAll(ids,s.split(","));
	    DgConsumerSeat seat = new DgConsumerSeat();
	    seat.setIds(ids);
		return consumerSeatMapper.restore(seat);
	}

	@Override
	public int updateSeatState(DgConsumerSeat seat) {
		// TODO Auto-generated method stub
		return consumerSeatMapper.updateSeatState(seat);
	}

	@Override
	public List<Map> selectAllSeat() {
		// TODO Auto-generated method stub
		return consumerSeatMapper.selectAllSeat();
	}

	@Override
	public Integer updateSeatByUuidKey(Map seatInfos) {
		// TODO Auto-generated method stub
		return consumerSeatMapper.updateSeatByUuidKey(seatInfos);
	}

	@Override
	public DgConsumerSeat selectSeatIdByUuidKey(String uuidKey) {
		// TODO Auto-generated method stub
		return consumerSeatMapper.selectSeatIdByUuidKey(uuidKey);
	}

	@Override
	public List<DgConsumerSeat> getAllSeat(Integer pos) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		DgPos dgPos = dgPosMapper.selectPosByPosId(pos);
	    map.put("list", StringUtil.arrayToList(dgPos.getConsumerAreas()));

	    return consumerSeatMapper.getAllSeatByPos(map);
	}

	@Override
	public List<DgConsumerSeat> selectByIds(List<DgOpenWater> dows) {
		return consumerSeatMapper.selectByIds(dows);
	}

	@Override
	public int selectAllUseSeat() {
		return consumerSeatMapper.selectAllUseSeat();
	}
}
