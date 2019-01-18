package com.yqsh.diningsys.api;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIMainService;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.api.impl.APICheckServiceImpl;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;
import com.yqsh.diningsys.web.service.online.DgTakeoutByonlineService;

/**
 *
 * 主界面相关接口
 * 
 * @author yqsh-zc Created by yqsh-zc on 2016/12/1.
 */
@RequestMapping("/yqshapi/main")
@Controller
public class APIMainController extends ApiBaseController {
	@Autowired
	private DgPosService posService;
	@Autowired
	private DgConsumerSeatService consumerSeatService;
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;
	@Autowired
	private DgCurrentOpenWaterService dgCurrentOpenWaterService;
	@Autowired
	private APIMainService aPIMainService;
	@Autowired
	private TableInfoService tableInfoService;
	@Autowired
	private DgTakeoutByonlineService dgTakeoutByonlineService;
	/**
	 * 获取桌位信息<br>
	 * 用户获取桌位信息，可以传入POS的ID号用于过滤非该POS区域下的桌位<br>
	 * POS为空则获取全部桌位<br>
	 * 该接口主要为主界面返回数据(包含部分营业单信息),后期如果只获取桌位信息，可以不调用此接口以提高效率
	 * is_team说明：0非团队，1团队，为团队，展示出团队号码以及主客位等信息
	 * 
	 * @param posID
	 *            POS的ID 非必须
	 * @param areaId
	 *            区域ID 非必须
	 * @param state
	 *            筛选状态 非必须
	 * @return 返回桌位信息
	 */
	@RequestMapping("/getTables")
	@ResponseBody
	@ApiOperation(value = "获取桌位信息", httpMethod = "POST", notes = "用户获取桌位信息，可以传入POS的ID号用于过滤非该POS区域下的桌位")
	public Object getTables(
			@ApiParam(required = true, name = "userCode", value = "前台用户登录的code") @RequestParam(value = "userCode") String userCode,
			@ApiParam(required = true, name = "posID", value = "POSID") @RequestParam(value = "posID") String posID,
			@ApiParam(required = false, name = "areaId", value = "areaId") @RequestParam(value = "areaId", required = false) Integer areaId,
			@ApiParam(required = false, name = "state", value = "state") @RequestParam(value = "state", required = false) Integer state) {
		String consumerSArea = null;
		try {
			Map res = new HashMap();
			DgPos pos = new DgPos();
			if (!StringUtil.isBlank(posID)) {
				pos.setId(Integer.parseInt(posID));
				pos = posService.getPosByID(pos);
				consumerSArea = pos.getConsumerAreas();
			}
			List<Map<String, Object>> seats = consumerSeatService
					.getConsumerSeatByArea(consumerSArea, areaId, state);

			Map seatCount = dgCurrentOpenWaterService
					.selectCurrentSeatCountByPosArea(consumerSArea);

			Map map = aPIMainService.selectUserLastLoginPos(userCode, pos.getId());

			if(map != null && map.containsKey("login_time")){
				String	loginTime = map.get("login_time").toString();
				//当前班次、当前市别统计
				Date loginData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(loginTime);
				DgPos p = posService.selectPosByPosId(pos.getId());
				String consumerAreas = p.getConsumerAreas();
				
				List<String> ids = new ArrayList<String>();
	   			for(String str : consumerAreas.split(",")){
	   				ids.add(str);
	   			}
				Map currentBC = aPIMainService.selectCountCurrentFrequency(pos.getId(), loginData,ids);
				res.put("currentBC", currentBC);
				
				//当前市别统计
				Map currentSB = aPIMainService.selectCountBis(pos.getId(), loginData,ids);
				res.put("currentSB", currentSB);
				
			}

			res.put("seatCount", seatCount);
			res.put("seats", seats);
//			res.put("online", CacheUtil.getWmCache());	
//			CacheUtil.setWmCache("0");
			res.put("callInfo",tableInfoService.getCallServiceTop5ByPos(pos));
			if(pos.getCanJb() != null && pos.getCanJb().equals(1)) {
				res.put("yxeZfInfo",tableInfoService.selectTopYxeZf5(pos));	
				res.put("ZccfInfo",tableInfoService.selectTopZccf5());
				res.put("reserve",tableInfoService.selectReserveCount());
			}
			return getSuccessResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取桌位信息<br>
	 * 用户获取桌位信息，可以传入POS的ID号用于过滤非该POS的区域<br>
	 * POS为空则获取全部桌位区域<br>
	 * 
	 * @param posID
	 *            POS的ID 非必须
	 * @return 返回桌位区域信息
	 */
	@RequestMapping("/getTableAreas")
	@ResponseBody
	@ApiOperation(value = "获取桌位区域信息", httpMethod = "POST", notes = "用户获取桌位区域信息，可以传入POS的ID号用于过滤非该POS的区域")
	public Object getTableAreas(
			HttpServletRequest request,
			@ApiParam(required = false, name = "posID", value = "posID") @RequestParam(value = "posID", required = false) String posID) {
		String ids = null;
		try {
			if (!StringUtil.isBlank(posID)) {
				DgPos pos = new DgPos();
				pos.setId(Integer.parseInt(posID));
				pos = posService.getPosByID(pos);
				ids = pos.getConsumerAreas();
			}
			List<Map<String, Object>> list = consumptionAreaService
					.getAreaByIds(ids);
			return getSuccessResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取全部POS信息<br>
	 * 成功返回页面所需要的POS信息<br>
	 * 
	 * @return POS信息数据
	 */
	@RequestMapping("/getAllPos")
	@ResponseBody
	@ApiOperation(value = "获取所有POS信息数据", httpMethod = "GET", notes = "成功返回页面所需要的POS信息")
	public Object getAllos() {
		List<Map<String, Object>> pos = posService.getAllPosList();
		if (pos.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(pos);
	}

	/**
	 * 获取全部客位信息<br>
	 * 成功返回页面所需要的客位信息<br>
	 * 
	 * @return 客位信息数据
	 */
	@RequestMapping("/getAllConsumerSeat")
	@ResponseBody
	@ApiOperation(value = "获取所有桌位信息数据", httpMethod = "GET", notes = "成功返回页面所需要的客位信息")
	public Object getAllConsumerSeat() {
		List<DgConsumerSeat> consumerSeat = consumerSeatService
				.getAllList(null);
		if (consumerSeat.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(consumerSeat);
	}

	/**
	 * 获取全部消费区域信息<br>
	 * 成功返回页面所需要的消费区域信息<br>
	 * 
	 * @return 消费区域信息数据
	 */
	@RequestMapping("/getAllConsumptionArea")
	@ResponseBody
	@ApiOperation(value = "获取所有消费区域信息数据", httpMethod = "GET", notes = "成功返回页面所需要的消费区域信息")
	public Object getAllConsumptionArea() {
		List<DgConsumptionArea> consumptionArea = consumptionAreaService
				.getAllList(null);
		if (consumptionArea.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(consumptionArea);
	}

	@RequestMapping("/getOpenWaterDetailsOrderByItem")
	public ModelAndView getOpenWaterDetails(
			HttpServletRequest request,int seatId) {
		try {
			Map seatInfo = aPIMainService.selectSeatInfoBySeatId(seatId);
			List<Map> 	itemVal = aPIMainService.getOpenWaterDetailsOrderByItem(seatId);
			request.setAttribute("seatInfo", seatInfo);
			request.setAttribute("itemVal", itemVal);
			request.setAttribute("seatId", seatId);

			ModelAndView model = new ModelAndView("api/seatWaterDetailByItem");
	    	return model;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("api/seatWaterDetailByItem");
		}
	}
	
	@RequestMapping("/getOpenWaterDetailsAjax")
	@ResponseBody
	public Object getOpenWaterDetailsAjax(
			HttpServletRequest request,int seatId) {
		try {
			Map  ret = new HashMap();
			List<Map> 	subVal = aPIMainService.getOpenWaterDetailSubByService(seatId);
			request.setAttribute("subVal", subVal);
			List<Map> 	bigVal = aPIMainService.getOpenWaterDetailsAccordingBig(seatId);
			request.setAttribute("bigVal", bigVal);
			List<Map> 	serviceVal = aPIMainService.getOpenWaterDetailsOrderByService(seatId);
			request.setAttribute("serviceVal", serviceVal);
			ret.put("subVal",subVal);
			ret.put("bigVal",bigVal);
			ret.put("serviceVal",serviceVal);
	    	return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @RequestMapping("/getOpenWaterDetailsForPad")
    @ResponseBody
    @ApiOperation(value = "获取流水下品相明细", httpMethod = "POST", notes = "获取流水下品相明细")
    public Object getOpenWaterDetailsForPad(@ApiParam(required = true, name = "owNum", value = "owNum") @RequestParam(value = "owNum")String owNum,
                                            @ApiParam(required = true, name = "type", value = "type") @RequestParam(value = "type")Integer type) {
        try {
            if(type == 1){
                List<Map> itemVal = aPIMainService.getOpenWaterDetailsOrderByItem(owNum);
                return 	getSuccessResult(itemVal);
            } else if(type == 2){
                List<Map<String,Object>> 	subVal = aPIMainService.getOpenWaterDetailSubByService(owNum);
                return 	getSuccessResult(subVal);
            } else if(type == 3){
                List<Map> 	bigVal = aPIMainService.getOpenWaterDetailsAccordingBig(owNum);
                return getSuccessResult(bigVal);
            } else if(type == 4){
                List<Map> 	serviceVal = aPIMainService.getOpenWaterDetailsOrderByService(owNum);
                return getSuccessResult(serviceVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFailResult();
    }

}