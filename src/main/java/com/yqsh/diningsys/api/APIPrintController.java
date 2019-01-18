package com.yqsh.diningsys.api;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.enums.SysVariableDefine;
import com.yqsh.diningsys.web.model.DgCommonsVariable;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.model.online.DgTakeoutByonline;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.base.DgCommonsVariableService;
import com.yqsh.diningsys.web.service.online.DgTakeoutByonlineService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@RequestMapping("/yqshapi/print")
@Controller
public class APIPrintController extends ApiBaseController{
	@Autowired
	private DgPrintDataService dgPrintDataService;

	@Autowired
	private DgCommonsVariableService dgCommonsVariableService;

	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;
	
    @Autowired
	private DgConsumerSeatService consumerSeatService;
    
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;
	
	@Autowired
	private DgPosService posService;
	
    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private BusinessPermissionService businessPermissionService;
	
    
    @Autowired
    private APIModifyService apiModifyService;
    
    @Autowired
    private DgTakeoutByonlineService dgTakeoutByonlineService;
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private APICheckService apiCheckService;
	/**
	 * 测试<br>
	 * 根据服务号,获取打印信息<br>
	 *
	 * @param owId
	 *           服务号
	 * @return 根据服务号,获取打印信息
	 */
	@RequestMapping("/printInfo")
	@ResponseBody
	@ApiOperation(value = "根据服务号,获取打印信息", httpMethod = "POST", notes = "根据服务号,获取打印信息")
	public Object printInfo(
			HttpServletRequest request,
			@ApiParam(required = true, name = "owId", value = "服务号") @RequestParam(value = "owId", required = true) String owId) {
		dgPrintDataService.insertAddBillPrint(Integer.valueOf(owId), 500.00);
		return getSuccessResult();
	}
	
	
	/**
	 * 测试包房费<br>
	 * 根据服务号,获取打印信息<br>
	 *
	 * @param owId
	 *           服务号
	 * @return 根据服务号,获取打印信息
	 */
	@RequestMapping("/testBff")
	@ResponseBody
	@ApiOperation(value = "测试包房费", httpMethod = "POST", notes = "测试包房费")
	public Object testBff(HttpServletRequest request,
			@ApiParam(required = true, name = "faId", value = "包房方案") @RequestParam(value = "faId", required = true) String faId,
			@ApiParam(required = true, name = "startTime", value = "开始时间") @RequestParam(value = "startTime", required = true) String startTime,
			@ApiParam(required = true, name = "endTime", value = "开始时间") @RequestParam(value = "endTime", required = true) String endTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double money = 0.0;
		Map org = new HashMap();
		try {
			 money = apiModifyService.calculationBff(Integer.valueOf(faId),formatter.parse(startTime), formatter.parse(endTime));
			 org.put("money",money);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getSuccessResult(org);
	}
	
	
	/**
	 * 测试<br>
	 * 催菜单打印数据<br>
	 *
	 * @param owId
	 *           服务号
	 * @return 催菜单打印数据
	 */
	@RequestMapping("/printReminder")
	@ResponseBody
	@ApiOperation(value = "催菜单打印数据", httpMethod = "POST", notes = "催菜单打印数据")
	public Object printReminder(
			HttpServletRequest request,
			@ApiParam(required = true, name = "orgs", value = "催单参数") @RequestParam(value = "orgs", required = true) String orgs) {
		List<Map> list = new ArrayList<Map>();
		JSONArray pAr = JSONArray.fromObject(orgs);
		for (int i = 0; i < pAr.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject obj = pAr.getJSONObject(i);
			map.put("itemFileId", obj.getInt("itemFileId"));
			map.put("reminderItemNumber", obj.getInt("reminderItemNumber"));
			map.put("serviceId", obj.getInt("serviceId"));
			list.add(map);
		}
		dgPrintDataService.insertReminderBill(list);
		return getSuccessResult();
	}
	
	
	/**
	 * 测试<br>
	 * 单品转台打印数据<br>
	 *
	 *           服务号
	 * @return 单品转台打印数据
	 */
	@RequestMapping("/printModifyDishesTurntable")
	@ResponseBody
	@ApiOperation(value = "单品转台打印数据", httpMethod = "POST", notes = "单品转台打印数据")
	public Object printModifyDishesTurntable(
			HttpServletRequest request,
			@ApiParam(required = true, name = "oWaterId", value = "需要转台的流水id") @RequestParam(value = "oWaterId", required = true) String oWaterId,
			@ApiParam(required = true, name = "tWaterId", value = "转入的流水id") @RequestParam(value = "tWaterId", required = true) String tWaterId,
			@ApiParam(required = true, name = "orgs", value = "催单参数") @RequestParam(value = "orgs", required = true) String orgs) {
		List<Map> list = new ArrayList<Map>();
		JSONArray pAr = JSONArray.fromObject(orgs);
		for (int i = 0; i < pAr.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject obj = pAr.getJSONObject(i);
			map.put("itemFileNum",obj.get("itemFileNum"));
			map.put("itemFileZs",obj.get("itemFileZs"));
			map.put("itemFileId",obj.get("itemFileId"));
			map.put("serviceId",obj.get("serviceId"));
			list.add(map);
		}
		dgPrintDataService.insertModifyDishesTurntable(Integer.valueOf(oWaterId),
				Integer.valueOf(tWaterId),list);
		return getSuccessResult();
	}
	
	
	/**
	 * 测试<br>
	 * 更换客位,获取打印信息<br>
	 *
	 * @return 更换客位,获取打印信息
	 */
	@RequestMapping("/printChangeTable")
	@ResponseBody
	@ApiOperation(value = "更换客位,获取打印信息", httpMethod = "POST", notes = "更换客位,获取打印信息")
	public Object printChangeTable(
			@ApiParam(required = true, name = "waterid", value = "服务员ID") @RequestParam(value = "waterid", required = false) String waterid,
			@ApiParam(required = true, name = "ow_num", value = "营业单号") @RequestParam(value = "ow_num", required = false) String ow_num,
			@ApiParam(required = true, name = "tableid", value = "目标客桌ID") @RequestParam(value = "tableid", required = false) String tableid,
			@ApiParam(required = true, name = "isGgFa", value = "是否更改包房收费方案") @RequestParam(value = "isGgFa", required = false) String isGgFa,
			@ApiParam(required = true, name = "isJsBffPx", value = "是否将之前的包房费生存一个品项") @RequestParam(value = "isJsBffPx", required = false) String isJsBffPx) {
		
//		dgPrintDataService.insertChangeTable(Integer.valueOf(waterid),ow_num,Integer.valueOf(tableid),
//				Integer.valueOf(isGgFa),Integer.valueOf(isJsBffPx));
		return getSuccessResult();
	}
	
	/**
	 * 测试<br>
	 * 更换客位,获取打印信息<br>
	 *
	 * @return 更换客位,获取打印信息
	 */
	@RequestMapping("/printJoinTeam")
	@ResponseBody
	@ApiOperation(value = "更换客位,获取打印信息", httpMethod = "POST", notes = "更换客位,获取打印信息")
	public Object printJoinTeam(
			@ApiParam(required = true, name = "operCode", value = "服务员code") @RequestParam(value = "operCode", required = false) String operCode,
			@ApiParam(required = true, name = "openNum", value = "营业单号") @RequestParam(value = "openNum", required = false) String openNum,
			@ApiParam(required = true, name = "teamNumber", value = "目标客桌ID") @RequestParam(value = "teamNumber", required = false) String teamNumber) {
		dgPrintDataService.insertJoinTeam(operCode,openNum,teamNumber);
		return getSuccessResult();
	}
	
	/**
	 * 测试<br>
	 * 更改库存<br>
	 *
	 * @return 更改库存
	 */
	@RequestMapping("/updateInve")
	@ResponseBody
	@ApiOperation(value = "更改库存", httpMethod = "POST", notes = "更改库存")
	public Object updateInve() {
		Map orgs = new HashedMap();
		orgs.put("itemFileId",32);
		orgs.put("ItemFileCount",3);
		billService.updateInveDate(orgs);
		return getSuccessResult();
	}
	
	
//	/**
//	 * 测试<br>
//	 * 更换客位,获取打印信息<br>
//	 *
//	 * @return 更换客位,获取打印信息
//	 */
//	@RequestMapping("/printWm")
//	@ResponseBody
//	@ApiOperation(value = "测试打印外卖数据", httpMethod = "POST", notes = "测试打印外卖数据")
//	public Object printWm() {
//		List<DgTakeoutByonline> dates = dgTakeoutByonlineService.selectTop5();
//		dgPrintDataService.insertWmBill(dates);
//		return getSuccessResult();
//	}
	
	/**
	 * 测试<br>
	 * 起菜,获取打印信息<br>
	 * [{"qcNumber":1.0,"qcFs":2,"owId":4,"itemFileId":12}]
	 * @return 起菜,获取打印信息
	 */
	@RequestMapping("/printQc")
	@ResponseBody
	@ApiOperation(value = "起菜,获取打印信息", httpMethod = "POST", notes = "起菜,获取打印信息")
	public Object printQc(
			@ApiParam(required = true, name = "operCode", value = "服务员code") @RequestParam(value = "operCode", required = false) String operCode,
			@ApiParam(required = true, name = "openNum", value = "营业单号") @RequestParam(value = "openNum", required = false) String openNum,
			@ApiParam(required = true, name = "orgs", value = "json数据") @RequestParam(value = "orgs", required = false) String orgs) {
		List<Map> list = new ArrayList<Map>();
		JSONArray pAr = JSONArray.fromObject(orgs);
		for (int i = 0; i < pAr.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject obj = pAr.getJSONObject(i);
			map.put("qcNumber", obj.getDouble("qcNumber"));
			map.put("itemFileId", obj.getInt("itemFileId"));
			map.put("owId", obj.getInt("owId"));
			map.put("qcZhsj", new Date());
			map.put("qcFs", 2);
			list.add(map);
		}
		dgPrintDataService.insertQcBill(operCode,openNum,list);
		return getSuccessResult();
	}
	
	 @RequestMapping("/print")
	  @ResponseBody
	  @ApiOperation(value = "根据服务号,获取打印信息", httpMethod = "POST", notes = "根据服务号,获取打印信息")
	  public Object printInfo(HttpServletRequest request) throws Exception {
		 String length = request.getHeader("content-length");
		 
	        if (length == null || "0".equals(length))
	            return null;
		 byte[] buffer = new byte[Integer.parseInt(length)];
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	 
	        try {
	            ServletInputStream stream = request.getInputStream();
	            int len = stream.read(buffer);
	            bos.write(buffer, 0, len);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        String bodyDate = new String(bos.toByteArray(), "UTF-8");
	        System.out.println(bodyDate);
	        return null;
	  }

	@RequestMapping("/closedBillIndex")
	public String closedBillIndex(Model model,
								  @ApiParam(required = true, name = "userCode", value = "前台登录userCode") @RequestParam(value = "userCode") String userCode){
//		SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);

		/*if(sysPerBusiness == null || sysPerBusiness.getFwjsQx() == null){
			model.addAttribute("returnSettlement",false);
		}else{
			model.addAttribute("returnSettlement",true);
		}

		if(sysPerBusiness == null || sysPerBusiness.getFkxzQx() == null){
			model.addAttribute("fkxz",false);
		}else{
			model.addAttribute("fkxz",true);
		}
		if(sysPerBusiness == null || sysPerBusiness.getBkfpQx() == null){
			model.addAttribute("bkfp",false);
		}else{
			model.addAttribute("bkfp",true);
		}*/

		model.addAttribute("userCode",userCode);

        //客位
        model.addAttribute("seatList",consumerSeatService.getAllList(null));
        //消费区域
        model.addAttribute("areaList",consumptionAreaService.getAllList(null));
        //POS
		model.addAttribute("posList",posService.getAllPosList());
        //市别
		model.addAttribute("bisList",tbBisService.getAllList(null));
		
		return "deskBusiness/closedBills/closed_bills_index";
	}

	@RequestMapping("/openBillindex")
	public String openBillindex(Model model){
		/*DgCommonsVariable dgCommonsVariable = dgCommonsVariableService.selectByCode(new DgCommonsVariable(SysVariableDefine.CURRENT_OPEN_WATER_FLUSH, null));
		model.addAttribute("flushTime",dgCommonsVariable.getCvValue());*/

		//客位
       /* model.addAttribute("seatList",consumerSeatService.getAllList(null));*/
        //消费区域
        model.addAttribute("areaList",consumptionAreaService.getAllList(null));

//		List<DgConsumptionArea> dgConsumptionAreas = dgConsumptionAreaService.selectAllArea();
//		model.addAttribute("dgConsumptionAreas",dgConsumptionAreas);
		return "deskBusiness/openBills/open_bills_index";
	}
	
	/**
	 * 获取接班信息<br>
	 * 结班,获取打印信息<br>
	 *
	 * @return 结班,获取打印信息
	 */
	@RequestMapping("/printOpenClass")
	@ResponseBody
	@ApiOperation(value = "结班,获取打印信息", httpMethod = "POST", notes = "结班,获取打印信息")
	public Object printOpenClass(
			 @ApiParam(required = true, name = "userCode", value = "前台的用户名") @RequestParam(value = "userCode", required = true) String userCode,
             @ApiParam(required = true, name = "loginPos", value = "用户登录的POS") @RequestParam(value = "loginPos", required = true) Integer loginPos) {
		Map<String, Object> resultData = new HashMap<String, Object>();
        try {
			Map map = apiCheckService.selectOpenClassReport(userCode, loginPos);
			resultData.put("userCode",userCode);
			resultData.put("loginPos",loginPos);
			resultData.put("openClassReport",map);
			//结算方式
			List<DgOwClearingway> frequency =  (List<DgOwClearingway>) apiCheckService.selectOpenClassInfo(userCode, loginPos,5);
			resultData.put("frequency",frequency);
			return getSuccessResult(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 2018年6月22日 结班单历史打印
	 * 获取接班信息<br>
	 * 结班,获取打印信息<br>
	 *
	 * @return 结班,获取打印信息
	 */
	@RequestMapping("/printOpenClassH")
	@ResponseBody
	@ApiOperation(value = "结班信息历史打印", httpMethod = "POST", notes = "结班信息历史打印")
	public Object printOpenClassH(
			@ApiParam(required = true, name = "id", value = "结班数据ID") @RequestParam(value = "id", required = true) Integer id) {
		Map<String, Object> resultData = new HashMap();
		try {
			Map map = apiCheckService.selectOpenClassReportWithId(id);
			Map loginInfo = (Map)map.get("loginInfo");
			resultData.put("openClassReport",map);
			//结算方式
			List<DgOwClearingway> frequency =  (List<DgOwClearingway>) apiCheckService.selectOpenClassInfo(loginInfo,5);
			resultData.put("frequency",frequency);
			return getSuccessResult(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	@RequestMapping("/getAllClearingWaterByDate")
	@ResponseBody
	@ApiOperation(value = "预结单重打数据查询", httpMethod = "POST", notes = "预结单重打数据查询")
	public Object getAllClearingWater(String queryTime){
		try {
			List<DgReceptionClearingWater> clearingWaters = apiCheckService.selectClearingWaterByTime(queryTime);
			return getSuccessResult(clearingWaters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getFailResult();
	}


	/**
	 * 测试菜品价格<br>
	 * 更改库存<br>
	 *
	 * @return 更改库存
	 */
	@RequestMapping("/getDishPrice")
	@ResponseBody
	@ApiOperation(value = "测试菜品价格", httpMethod = "POST", notes = "测试菜品价格")
	public Object getDishPrice(Integer itemId) {
		Map<String, Object> price = billService.getDishPrice(itemId,
		1,57, 1, -1, false,
		false);
		return getSuccessResult(price);
	}
}
