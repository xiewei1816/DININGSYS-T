package com.yqsh.diningsys.api;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wordnik.swagger.annotations.Api;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.report.BackReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.service.api.QueryPrintService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询打印接口
 * 
 * @author xiewei
 */
@RequestMapping("/yqshapi/queryPrint")
@Controller
public class APIQueryPrintController extends ApiBaseController {

	@Autowired
	private DgConsumerSeatService consumerSeatService;
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;
	@Autowired
	private QueryPrintService queryPrintService;

	@Autowired
	private APICheckService apiCheckService;

	@Autowired
	private BackReportService backReportService;
	
    /************************************当前客单************************************************/
	/**
	 * 查询当前客单信息<br>
	 * 成功返回页面所需要的查询当前客单信息
	 * @param seatId 座位ID
	 * @return 客单信息
	 */
	@RequestMapping("/selectGuestList")
	@ApiOperation(value = "查询当前客单信息", httpMethod = "POST", notes = "查询当前客单信息")
	public Object selectGuestList(
			@ApiParam(required = true, name = "seatId", value = "座位ID") @RequestParam(value = "seatId", required = true) Integer seatId,
			Model model) {
		Map<String,Object> guestMap = queryPrintService.selectSeatAreaList(seatId);
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		//根据座位ID查询营业流水
		List<Map<String,Object>> openWaterList = queryPrintService.selectDocListOpenWater(seatId);
		if(openWaterList.size() > 0){
			for (int i = 0; i < openWaterList.size(); i++) {
				//根据营业流水号查询当前客单信息
				String owNum = openWaterList.get(i).get("owNum")+"";
				Map<String, Object> map = queryPrintService.selectGuestList(owNum);
				if (map != null && map.size() > 0) {
					//根据团队编号获取团队成员
					String teamMembers = map.get("teamMembers").toString();
					Map<String, Object> teamMembersMap = queryPrintService.selectTeamSeatMembersList(teamMembers);
					if(teamMembersMap.size() > 0){
						String teamMember = teamMembersMap.get("teamMember").toString();
						//去重
						String[] array = teamMember.split(",");
				        Set<String> set = new HashSet<>();  
				        for(int j=0;j<array.length;j++){  
				            set.add(array[j]);  
				        }  
				        String[] arrayResult = set.toArray(new String[set.size()]);
						map.put("teamMember", Arrays.toString(arrayResult));
					}
					//添加客单信息下品项信息
					String id = map.get("id").toString();
					List<Map<String, Object>> itemFileDatas = queryPrintService.selectGuestItemFileList(id);
					map.put("itemFileDatas", itemFileDatas);
					//合计
					BigDecimal total = new BigDecimal(0.0); 
					BigDecimal subtotal = new BigDecimal(0.0);
					for (int j = 0; j < itemFileDatas.size(); j++) {
						Map<String, Object> m = itemFileDatas.get(j);
						subtotal = new BigDecimal(m.get("subtotal")+"");
						total = total.add(subtotal);
					}
					map.put("total", total.doubleValue());
				}
				resultList.add(map);
			}
			guestMap.put("resultList", resultList);
			model.addAttribute("data",guestMap);
		}
		ModelAndView modelAndView = new ModelAndView("api/selectGuestListPage");
        return modelAndView;
	}
	
    /************************************单据信息************************************************/
    /**
     * 根据座位查询核对信息营业流水信息 <br>
     * @param seatId 座位ID
     * @return
     */
    @RequestMapping("/selectDocListOpenWater")
    @ResponseBody
    @ApiOperation(value = "根据座位查询核对信息营业流水信息 ", httpMethod = "POST", notes = "根据座位查询核对信息营业流水信息 ")
    public Object selectDocListOpenWater(
            @ApiParam(required = true, name = "seatId", value = "座位ID") @RequestParam(value = "seatId", required = true) Integer seatId){
    	List<Map<String,Object>> mapList = queryPrintService.selectDocListOpenWater(seatId);
    	if (mapList.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
        return getSuccessResult(mapList);
    }
	
	/**
	 * 核对单据信息<br>
	 * 成功返回页面所需要的单据信息
	 * @param owNum 营业流水号
	 * @return 单据信息
	 */
	@RequestMapping("/selectCheckDocList")
	@ApiOperation(value = "核对单据信息", httpMethod = "POST", notes = "核对单据信息")
	public Object selectCheckDocList(
			@ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum,
			Model model) {
		//根据营业流水查询区域及客位
		Map<String,Object> resultMap = queryPrintService.selectCheckDocSeatList(owNum);
		//根据营业流水查询核对单据信息
		List<Map<String, Object>> checkDocList = queryPrintService.selectCheckDocList(owNum);
		if (checkDocList != null && checkDocList.size() > 0) {
			Map<String,Object> map = null;
			for (int i = 0; i < checkDocList.size(); i++) {
				map = checkDocList.get(i);
				String sfId = map.get("id").toString();
				List<Map<String, Object>> itemMapList = queryPrintService.selectCheckItemFileList(sfId);
				//如果该营业流水进行了加单操作
				if(itemMapList != null){
					//添加客单信息下品项信息
					map.put("itemFileDatas", itemMapList);
					double slTotal = 0.0; //数量总计
					double zsTotal = 0.0; //只数总计
					double zzTotal = 0.0; //制作费用总计
					double total = 0.0;//合计
					for (int j = 0; j < itemMapList.size(); j++) {
						Map<String, Object> m = itemMapList.get(j);
						double itemFileNumber = Double.parseDouble(m.get("item_file_number") == null ? "0" : m.get("item_file_number")+"");
						double itemFileZs = Double.parseDouble(m.get("item_file_zs") == null ? "0" : m.get("item_file_zs")+"");
						double productionCosts = Double.parseDouble(m.get("production_costs") == null ? "0" : m.get("production_costs")+"");
						double subtotal = Double.parseDouble(m.get("subtotal")  == null ? "0" : m.get("subtotal")+"");
						slTotal += itemFileNumber;
						zsTotal += itemFileZs;
						zzTotal += productionCosts;
						total += subtotal;
					}
					map.put("slTotal", slTotal);
					map.put("zsTotal", zsTotal);
					map.put("zzTotal", zzTotal);
					map.put("total", total);
				}
			}
			resultMap.put("checkDocList", checkDocList);
			model.addAttribute("data",resultMap);
		}
		ModelAndView modelAndView = new ModelAndView("api/selectCheckDocListPage");
        return modelAndView;
	}
	
    /************************************团队信息************************************************/

	/**
	 * 返回团队信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goTeamList")
	@ApiOperation(value = "返回团队信息页面", httpMethod = "POST", notes = "返回团队信息页面")
	public Object goTeamList(Model model) {
		ModelAndView modelAndView = new ModelAndView("api/goTeamList");
        return modelAndView;
	}
	
	/**
	 * 获取团队信息树形数据<br>
	 * 成功返回页面所需要的团队信息树形数据<br>
	 * @return 团队信息树形数据
	 */
	@RequestMapping("/getTeamBySeatNameList")
	@ResponseBody
	@ApiOperation(value = "查询团队信息树形数据", httpMethod = "GET", notes = "成功返回页面所需要的团队信息树形数据")
	public Object selectTeamBySeatNameList() {
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		List<Map<String, Object>> teamMembersList = queryPrintService.selectTeamMembersList();
		map = new HashMap<String, Object>();
		map.put("id", 0);
		map.put("pId", "");
		map.put("name", "团队信息");
		lm.add(map);
		if (teamMembersList != null && teamMembersList.size() > 0) {
			for (int i = 0; i < teamMembersList.size(); i++) {
				map = new HashMap<String, Object>();
				String teamMembers = teamMembersList.get(i).get("teamMembers")+"";
				map.put("id", teamMembers);
				map.put("pId", 0);
				map.put("name", teamMembers);
				lm.add(map);
				List<Map<String, Object>> teamSeatList = queryPrintService.selectTeamBySeatNameList(teamMembers);
				if (teamSeatList != null && teamSeatList.size() > 0) {
					for (int j = 0; j < teamSeatList.size(); j++) {
						map = new HashMap<String, Object>();
						Map<String, Object> teamSeatMap = teamSeatList.get(j);
						String id = teamSeatMap.get("id")+"";
						String seatName = teamSeatMap.get("seatName")+"";
						String owNum = teamSeatMap.get("owNum")+"";
						map.put("id", id);
						map.put("pId", teamMembers);
						map.put("name", seatName);
						map.put("owNum", owNum);
						lm.add(map);
					}
				}
			}
		}
		return lm;
	}
	
	/**
	 * 根据客位查询团队信息<br>
	 * 成功返回页面所需要的团队信息
	 * @param seatId 客位id
	 * @return 团队信息
	 */
	@RequestMapping("/selectTeamList")
	@ResponseBody
	@ApiOperation(value = "根据客位查询团队信息", httpMethod = "POST", notes = "根据客位查询团队信息")
	public Object selectTeamList(
			@ApiParam(required = false, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = false) String seatId,
			@ApiParam(required = false, name = "teamMembers", value = "团队编号") @RequestParam(value = "teamMembers", required = false) String teamMembers,
			@ApiParam(required = false, name = "OwNum", value = "团队编号") @RequestParam(value = "owNum", required = false) String owNum) {
		return queryPrintService.selectTeamList(seatId,teamMembers,owNum);
	}
	
	/**
	 * 根据营业流水号退出团队<br>
	 * 成功返回 success
	 * @param owNum 营业流水号
	 * @return 
	 */
	@RequestMapping("/updWaterJoinTeamNotes")
	@ResponseBody
	@ApiOperation(value = "根据营业流水号退出团队", httpMethod = "POST", notes = "根据营业流水号退出团队")
	public Object updWaterJoinTeamNotes(
			@ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum) {

		DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
		if(dgOpenWater.getOwState() == 9){
			return getResult(APIEnumDefine.S993);
		}else if(dgOpenWater.getOwState() == 8){
			return getResult(APIEnumDefine.S994);
		}else if(dgOpenWater.getOwState() == -1){
			return getResult(APIEnumDefine.S979);
		}else if(dgOpenWater.getOwState() == 4 || dgOpenWater.getOwState() == 5){
			return getResult(APIEnumDefine.S978);
		}

		//2017年7月27日11:25:06 by zhshuo,查询要退出团队的营业流水是否有营业流水转账到该流水，如果有转账的流水，则不允许退出团队
		List<DgOpenWater> dgOpenWaters = apiCheckService.selectTransferOpenWaterByOwNum(owNum);

		if(dgOpenWaters != null && dgOpenWaters.size() > 0){
			return getResult(APIEnumDefine.S977);
		}

		int count = queryPrintService.updWaterJoinTeamNotes(owNum);
		if (count <= 0) {
			return getResult(APIEnumDefine.S980);
		}
		return getSuccessResult();
	}
	
	/**
	 * 根据营业流水号撤销转账<br>
	 * 成功返回 success
	 * @param owNum 营业流水号
	 * @return 
	 */
	@RequestMapping("/updWaterRepealTeamNotes")
	@ResponseBody
	@ApiOperation(value = "根据营业流水号撤销转账", httpMethod = "POST", notes = "根据营业流水号撤销转账")
	public Object updWaterRepealTeamNotes(
			@ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum) {
		//根据撤销转账营业流水号更改客位状态为占用
		Integer flag = queryPrintService.updRepealWater(owNum);
		if (flag == 3) {
			return getFailResult("准备还原的客座正在清扫，请稍后操作！");
		}
		if (flag == 4) {
			return getFailResult("准备还原的客座已经被预订，请稍后操作！");
		}
		return getSuccessResult();
	}
	
	/************************************转账流水查询************************************************/
	/**
	 * 返回转账流水查询页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goTransferOpenWaterList")
	@ApiOperation(value = "返回转账流水查询页面", httpMethod = "POST", notes = "返回转账流水查询页面")
	public Object goTransferOpenWaterList(Model model) {
		//客位
		model.addAttribute("seatList",consumerSeatService.getAllList(null));
		//消费区域
		model.addAttribute("areaList",consumptionAreaService.getAllList(null));
		ModelAndView modelAndView = new ModelAndView("api/goTransferOpenWaterList");
        return modelAndView;
	}
	
	
	/**
	 * 根据转账备注查询转入、转出转账流水信息<br>
	 * 成功返回页面查询转入、转出转账流水信息
	 * @param joinTeamNotes 转账备注
	 * @param expArea 消费区域
	 * @param clientSeat 客位
	 * @param owNum 营业流水
	 * @return 转账流水信息
	 */
	@RequestMapping("/selectIntoOrOutOpenWaterList")
	@ResponseBody
	@ApiOperation(value = "根据转账备注查询转入、转出转账流水信息", httpMethod = "POST", notes = "根据转账备注查询转入、转出转账流水信息")
	public Object selectIntoOrOutOpenWaterList(
			
			@ApiParam(required = true, name = "joinTeamNotes", value = "转账备注") @RequestParam(value = "joinTeamNotes", required = true) String joinTeamNotes,
			@ApiParam(required = false, name = "expArea", value = "消费区域") @RequestParam(value = "expArea", required = false) String expArea,
			@ApiParam(required = false, name = "clientSeat", value = "客位") @RequestParam(value = "clientSeat", required = false) String clientSeat,
			@ApiParam(required = false, name = "owNum", value = "营业流水") @RequestParam(value = "owNum", required = false) String owNum) {
		List<Map<String, Object>> list = queryPrintService.selectIntoOrOutOpenWaterList(joinTeamNotes,expArea,clientSeat,owNum);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				//根据团队编号获取团队成员
				String teamMembers = map.get("teamMembers").toString();
				Map<String, Object> teamMembersMap = queryPrintService.selectTeamSeatMembersList(teamMembers);
				if(teamMembersMap.size() > 0){
					String teamMember = teamMembersMap.get("teamMember").toString();
					//去重
					String[] array = teamMember.split(",");
			        Set<String> set = new HashSet<>();  
			        for(int j=0;j<array.length;j++){  
			            set.add(array[j]);  
			        }  
			        String[] arrayResult = set.toArray(new String[set.size()]);
					map.put("teamMembers", Arrays.toString(arrayResult));
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据转账流水查询品项信息<br>
	 * 成功返回页面转账流水品项信息
	 * @param owNum 营业流水
	 * @return 转账流水信息
	 */
	@RequestMapping("/selectIntoOrOutItemFileList")
	@ResponseBody
	@ApiOperation(value = "根据转账流水查询品项信息", httpMethod = "POST", notes = "根据转账流水查询品项信息")
	public Object selectIntoOrOutItemFileList(
			Model model,
			@ApiParam(required = true, name = "owNum", value = "营业流水") @RequestParam(value = "owNum", required = true) String owNum) {
		return queryPrintService.selectIntoOrOutItemFileList(owNum);
	}
	
	/**
	 * 返回已结账单页面
	 * @param model
	 * @param model
	 * @return
	 */
	@RequestMapping("/goClearingWaterList")
	@ApiOperation(value = "返回已结账单页面", httpMethod = "POST", notes = "返回已结账单页面")
	public Object goClearingWaterList(
			Model model,
			@ApiParam(required = true, name = "cwNum", value = "结算流水") @RequestParam(value = "cwNum", required = true) String cwNum) {
		Map<String, Object> clearingBaseMap = queryPrintService.selectClearingBaseList(cwNum);
		model.addAttribute("datas",clearingBaseMap);
		ModelAndView modelAndView = new ModelAndView("api/goClearingWaterList");
        return modelAndView;
	}
	
	
	
    /************************************已结账单************************************************/
	/**
	 * 查询已结账单信息+翻台账单查询<br>
	 * 成功返回页面所需要的已结账单信息
	 * @param seatId 客位id
	 * @param openBis 市别
	 * @param consArea 消费区域
	 * @param firstTime 营业日期
	 * @return 
	 */
	@RequestMapping("/selectClearingList")
	@ResponseBody
	@ApiOperation(value = "查询已结账单信息+翻台账单查询", httpMethod = "POST", notes = "查询已结账单信息+翻台账单查询")
	public Object selectClearingList(
			@ApiParam(required = false, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = false) String seatId,
			@ApiParam(required = false, name = "openBis", value = "市别") @RequestParam(value = "openBis", required = false) String openBis,
			@ApiParam(required = false, name = "consArea", value = "消费区域") @RequestParam(value = "consArea", required = false) String consArea,
			@ApiParam(required = false, name = "firstTime", value = "营业日期") @RequestParam(value = "firstTime", required = false) String firstTime) {
		List<Map<String, Object>> clearingList = queryPrintService.selectClearingList(seatId,openBis,consArea,firstTime);
		if (clearingList == null) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(clearingList);
	}
	
	/**
	 * 根据结算流水查询结算流水基本信息<br>
	 * 成功返回页面所需要的结算流水基本信息
	 * @param cwNum 结算流水号
	 * @return 
	 */
	@RequestMapping("/selectClearingBaseList")
	@ResponseBody
	@ApiOperation(value = "根据结算流水查询结算流水基本信息", httpMethod = "POST", notes = "根据结算流水查询结算流水基本信息")
	public Object selectClearingBaseList(
			@ApiParam(required = false, name = "cwNum", value = "结算流水号") @RequestParam(value = "cwNum", required = false) String cwNum) {
		Map<String, Object> clearingBaseMap = queryPrintService.selectClearingBaseList(cwNum);
		if (clearingBaseMap == null) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(clearingBaseMap);
	}
	
	/**
	 * 根据结算流水查询营业流水基本信息<br>
	 * 成功返回页面所需要的营业流水基本信息
	 * @param cwNum 结算流水号
	 * @return 
	 */
	@RequestMapping("/selectOpenWaterBaseList")
	@ResponseBody
	@ApiOperation(value = "根据结算流水查询营业流水基本信息", httpMethod = "POST", notes = "根据结算流水查询营业流水基本信息")
	public Object selectOpenWaterBaseList(
			@ApiParam(required = false, name = "cwNum", value = "结算流水号") @RequestParam(value = "cwNum", required = false) String cwNum) {
		List<Map<String, Object>> openWaterBaseList = queryPrintService.selectOpenWaterBaseList(cwNum);
		return openWaterBaseList;
	}
	
	/**
	 * 根据营业流水ID查询结算方式 、消费明细、优惠信息、发票信息<br>
	 * 成功返回页面所需要的营业流水基本信息
	 * @param flag 查询标识 1,2,3,4 - 分别标识返回4项tab数据
	 * @param id 营业流水ID
	 * @return 
	 */
	@RequestMapping("/selectTabsBaseList")
	@ResponseBody
	@ApiOperation(value = "根据营业流水ID查询结算方式 、消费明、优惠信息、发票信息", httpMethod = "POST", notes = "根据营业流水ID查询结算方式 、消费明、优惠信息、发票信息")
	public Object selectTabsBaseList(
			@ApiParam(required = false, name = "flag", value = "查询标识 1,2,3,4 - 分别标识返回4项tab数据") @RequestParam(value = "flag", required = false) Integer flag,
			@ApiParam(required = false, name = "id", value = "营业流水ID") @RequestParam(value = "id", required = false) String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		switch (flag) {
		case 1:
			//结算方式
			list = queryPrintService.selectClearingWayBaseList(id);
			break;
		case 2:
			//消费明细
			list = queryPrintService.selectGuestItemFileList(id);
			break;
		case 3:
			//优惠信息
			break;
		case 4:
			//发票信息
			list = queryPrintService.selectReceiptFileList(id);
			break;
		default:
			break;
		}
		return list;
	}

	/**
	 *	获取没有结算的品项，返回VIEW页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toNoBalanceView")
	public ModelAndView toNoBalanceView(
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api/noBalance");
		List<Map<String, Object>> datas = queryPrintService.getNoBalenceDataList();
		request.setAttribute("datas",datas);
		return model;
	}

	/**
	 * 核对单据
	 * @param openWater 需要核对单据的营业流水
	 * @param operaUser 操作的用户
	 * @return
	 */
	@RequestMapping("/judgeOpenWater")
	public String judgeOpenWater(String openWater,String operaUser){
		return "api/hdjd/judgeOpenWater_index";
	}

	@ResponseBody
	@RequestMapping("/getINGOpenWater")
	public List<DgOpenWater> getIngOpenWater(){
		try {
			return apiCheckService.selectINGOpenWaters();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/selectClosedWater")
	public List<Map> selectClosedWater(String beginTime,String endTime,Integer sortType){
		try {
			return apiCheckService.selectClosedWater(beginTime,endTime,sortType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/serviceData/{openWater}")
	public String dataSearchDetailsIndex(@PathVariable String openWater,String startTime,String endTime, Model model){
		try {
			List<Integer> inList = backReportService.selectServiceDataByOwnum(openWater,startTime,endTime);
			model.addAttribute("inList",inList);
			return "api/hdjd/itemFileTypeDetail_index";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "500";
	}

	@ResponseBody
	@RequestMapping("/dataSearch_details_next/{serviceId}")
	public Map dataSearch_details_next(@PathVariable Integer serviceId,String startTime,String endTime){
		try {

			Map map = backReportService.dataSearchDetailsNext_new(serviceId,startTime,endTime);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
