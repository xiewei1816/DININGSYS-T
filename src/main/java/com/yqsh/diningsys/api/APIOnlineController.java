package com.yqsh.diningsys.api;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.util.OnlineHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.service.online.DgTakeoutByonlineService;

import java.util.List;
import java.util.Map;

/**
 * Created by yqsh-hs on 2017/7/8.
 */
@Controller
@RequestMapping("/yqshapi/online")
@SuppressWarnings("all")
public class APIOnlineController extends ApiBaseController {
	public static final Object lock = new Object();
	@Autowired
	private TableInfoService tableInfoService;
	@Autowired
	private DgTakeoutByonlineService dgTakeoutByonlineService;
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	@Autowired
	private BillService billService;
	/**
	 * 获取外卖信息
	 *
	 */
	@RequestMapping(value = "/getWmInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取外卖信息", httpMethod = "POST", notes = "获取外卖信息")
	public Object getWmInfo() {
		try {
			return getSuccessResult(dgTakeoutByonlineService.selectTop20());
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/**
	 * 更新外卖状态为接收状态(id组合,d)
	 *
	 */
	@RequestMapping(value = "/updateWmStatus", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "更新外卖状态为接收状态(id组合,d)", httpMethod = "POST", notes = "更新外卖状态为接收状态(id组合,d)")
	public Object updateWmStatus(@ApiParam(required = true, name = "ids", value = "更新外卖状态为接收状态(id组合,d)") @RequestParam(value = "ids", required = true) String ids) {
		try {
			dgTakeoutByonlineService.updateIds(ids);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}


	/**
	 * 获取客位状态
	 */
	@RequestMapping(value = "/getSeatState", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取客位状态", httpMethod = "POST", notes = "获取客位状态")
	public Object getSeatState(@ApiParam(required = true, name = "seatUuidKey", value = "客位uuid") @RequestParam(value = "seatUuidKey", required = true) String seatUuidKey) {
		try {
			DgConsumerSeat seat = dgConsumerSeatService.selectSeatIdByUuidKey(seatUuidKey);
			return getSuccessResult(seat);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 开台
	 */
	@RequestMapping(value = "/openTable", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "开台", httpMethod = "POST", notes = "开台")
	public Object openTable(@ApiParam(required = true, name = "seatUuidKey", value = "客位uuid") @RequestParam(value = "seatUuidKey", required = true) String seatUuidKey,
							@ApiParam(required = true, name = "eatNumber", value = "开单人数") @RequestParam(value = "eatNumber", required = true) String eatNumber) {
		try {
			synchronized(lock){
				DgConsumerSeat seat = dgConsumerSeatService.selectSeatIdByUuidKey(seatUuidKey);
				Map<String, Object> map = tableInfoService.openBill(eatNumber,
						"yxe_water", seat.getId().toString(), "yxe_pos", null,null,false);
				if (map.containsKey("success")) {
					DgConsumerSeat useSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.valueOf(seat.getId()));
					OnlineHttp.onlineSeatModify(useSeat.getUuidKey(), "2");
					return getSuccessResult(map.get("water"));
				} else {
					if (map.containsKey("error") && (map.get("error") instanceof String)) {
						return getResult(APIEnumDefine.S1000, (String) map.get("error"));
					} else if (map.containsKey("error") && (map.get("error") instanceof APIEnumDefine)) {
						return getResult((APIEnumDefine) map.get("error"));
					} else {
						return getSuccessResult();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 点菜
	 */

	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "点菜", httpMethod = "POST", notes = "点菜")
	public Object addOrder(@ApiParam(required = true, name = "seatUuidKey", value = "客位uuid") @RequestParam(value = "seatUuidKey", required = true) String seatUuidKey,
							@ApiParam(required = true, name = "dishItems", value = "菜品数据") @RequestParam(value = "dishItems", required = true) String dishItems) {
		try {
			DgConsumerSeat seat = dgConsumerSeatService.selectSeatIdByUuidKey(seatUuidKey);
			if(seat == null){
				return getResult(APIEnumDefine.S1000, "客位不存在");
			}
			if(seat.getSeatState() != 2){
				return getResult(APIEnumDefine.S1000, "客位不是占用状态");
			}

			List<Map> dishLists = JSON.parseArray(dishItems,Map.class);
			Map map = billService.insertCxptOrderBill(dishLists,seat);
			if (map.containsKey("result") && (boolean)map.get("result")) {
				return getSuccessResult();
			} else {
				if (map.containsKey("error") && (map.get("error") instanceof String)) {
					return getResult(APIEnumDefine.S1000, (String) map.get("error"));
				} else if (map.containsKey("error") && (map.get("error") instanceof APIEnumDefine)) {
					return getResult((APIEnumDefine) map.get("error"));
				} else {
					return getSuccessResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
}
