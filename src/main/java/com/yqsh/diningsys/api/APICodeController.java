package com.yqsh.diningsys.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgCashPledge;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgGoodsConsign;
import com.yqsh.diningsys.web.service.api.DgCashPledgeService;
import com.yqsh.diningsys.web.service.api.DgGoodsConsignService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.archive.TbBisService;

/**
 * 字典、登记押金、物品寄存等相关接口
 * 
 * @author xiewei
 */
@RequestMapping("/yqshapi/code")
@Controller
public class APICodeController extends ApiBaseController {

	@Autowired
	private DgPosService posService;
	@Autowired
	private DgConsumerSeatService consumerSeatService;
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;
	@Autowired
	private DgPublicCode0Service dgPublicCode0Service;
	@Autowired
	private TbBisService tbBisService;
	@Autowired
	private DgCashPledgeService dgCashPledgeService;
	@Autowired
	private DgGoodsConsignService dgGoodsConsignService;

	/**
	 * 获取全部公共代码<br>
	 * 成功返回页面所需要的公共代码<br>
	 * @return 公共代码数据
	 */
	@RequestMapping("/getAllCode")
	@ResponseBody
	@ApiOperation(value = "获取所有公共代码数据", httpMethod = "GET", notes = "成功返回页面所需要的公共代码")
	public Object getAllCode() {
		List<Map<String, List<DgPublicCode0>>> dpc = dgPublicCode0Service
				.getAllDpcToPage();
		if (dpc.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(dpc);
	}

	/**
	 * 根据父级id查询子级公共代码<br>
	 * 成功返回页面所需要的子级公共代码
	 * @param pId 父级id 必须
	 * @return 返回对应子级公共代码
	 */
	@RequestMapping("/getChildCode")
	@ResponseBody
	@ApiOperation(value = "获取子级公共代码数据", httpMethod = "POST", notes = "传入参数获取子级公共代码信息<br>pId-字典名称: <br>"+
	 "2-学历,3-血型,4-民族,5-行业,6-年薪,7-联系方式,8-亲属关系,9-开户银行,10-企业所有制,11-注册资本,12-年营业额,13-客户开发方式,14-效果,15-地理区域,16-职务,17-籍贯,18-会计科目,19-凭证类字别"+
	    "20-科目编码,21-结算方式,22-部门编码,23-个人编码,24-客户编码,25-供应商编码,26-项目编码,27-兴趣爱好,28-宴会类型,29-信用卡类型,30-支票类型,31-基本单位,32-特约商户,33-品牌,34-加盟商等级,35-加盟店等级"+
		"36-物品寄存处理方式,37-顾客年龄划分,38-聚餐形式,39-单品评级,40-项目调整方案,41-角标,42-打印分组,189-时段,214-使用类型,218-管理模式,224-客位类型,239-客座号,251-币种,253-数据同步"+
		"<br>（新添字典依次加入）")
	public Object getChildCode(
			@ApiParam(required = true, name = "pId", value = "父级id") @RequestParam(value = "pId", required = true) String pId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cParent", pId);
		List<DgPublicCode0> dpc = dgPublicCode0Service.selectSmallDpc(params);
		if (dpc.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(dpc);
	}

	/**
	 * 获取所有营业市别数据<br>
	 * 成功返回页面所需要的营业市别
	 * @return 营业市别数据
	 */
	@RequestMapping("/getAllBis")
	@ResponseBody
	@ApiOperation(value = "获取所有营业市别数据", httpMethod = "GET", notes = "成功返回页面所需要的营业市别")
	public Object getAllBis() {
		List<TbBis> bis = tbBisService.getAllList(null);
		if (bis == null) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(bis);
	}
	
	/****************************客位服务-押金*********************************/
	/**
	 * 登记押金-退押金<br>
	 * 成功返回 success
	 * @param owNum 开单编号
	 * @param cpType 押金类型-登记押金;退押金
	 * @param cpCurrency 币种
	 * @param cpMoney 金额
	 * @param refInfo 参考信息
	 * @param remark 备注
	 * @return
	 */
	@RequestMapping("/regMoney")
	@ResponseBody
	@ApiOperation(value = "登记押金-退押金", httpMethod = "POST", notes = "登记押金-退押金")
	public Object regMoney(
			@ApiParam(required = true, name = "owNum", value = "开单编号") @RequestParam(value = "owNum", required = true) String owNum,
			@ApiParam(required = true, name = "cpType", value = "押金类型 (0-登记押金;1-退押金)") @RequestParam(value = "cpType", required = true) String cpType,
			@ApiParam(required = true, name = "cpCurrency", value = "币种") @RequestParam(value = "cpCurrency", required = true) String cpCurrency,
			@ApiParam(required = true, name = "cpMoney", value = "金额") @RequestParam(value = "cpMoney", required = true) Double cpMoney,
			@ApiParam(required = false, name = "refInfo", value = "参考信息") @RequestParam(value = "refInfo", required = false) String refInfo,
			@ApiParam(required = false, name = "remark", value = "备注") @RequestParam(value = "remark", required = false) String remark) {
		
		List<DgCashPledge> dcp0 = dgCashPledgeService.selectCashPledge(owNum,"0"); //缴押金集合
		List<DgCashPledge> dcp1 = dgCashPledgeService.selectCashPledge(owNum,"1"); //退押金集合
		/*
		 * 登记押金 0
		 */
		if(cpType.equals("0")){
			if(!dcp0.isEmpty()){
				double money = dcp0.get(0).getCpMoney();//修改
				int count = dgCashPledgeService.updateCashPledge(owNum, "0",cpCurrency,cpMoney + money, refInfo, remark);
				if (count <= 0) {
					return getFailResult();
				}
			}else{//新增
				int count = dgCashPledgeService.regMoney(owNum, "0", cpCurrency, cpMoney, refInfo, remark);
				if (count <= 0) {
					return getFailResult();
				}
			}
		}
		
		/*
		 * 退押金 1
		 */
		if(cpType.equals("1")){
			if(!dcp1.isEmpty()){
				if (!dcp0.isEmpty()) {//修改
					double money0 = dcp0.get(0).getCpMoney();
					double money1 = dcp1.get(0).getCpMoney();
					if (money1 + cpMoney <= money0) {
						int count = dgCashPledgeService.updateCashPledge(owNum,"1",cpCurrency,money1 + cpMoney, refInfo, remark);
						if (count <= 0) {
							return getFailResult();
						}
					} else {
						return getResult(APIEnumDefine.M0101005);
					}
				}else{
					return getResult(APIEnumDefine.M0101006);
				}
			}else{
				if (!dcp0.isEmpty()) {//新增
					double money = dcp0.get(0).getCpMoney();
					if (cpMoney <= money) {
						int count = dgCashPledgeService.regMoney(owNum, "1",cpCurrency, cpMoney, refInfo, remark);
						if (count <= 0) {
							return getFailResult();
						}
					} else {
						return getResult(APIEnumDefine.M0101005);
					}
				}else{
					return getResult(APIEnumDefine.M0101006);
				}
			}
		}

		return getSuccessResult();
	}
	
	/**
	 * 根据开单编号获取所有押金记录<br>
	 * 成功返回页面所需要的押金记录
	 * @param owNum 开单编号 必须
	 * @return 返回对应子级公共代码
	 */
	@RequestMapping("/selectCashPledge")
	@ResponseBody
	@ApiOperation(value = "根据开单编号获取所有押金记录", httpMethod = "POST", notes = "根据开单编号获取所有押金记录")
	public Object selectCashPledge(
			@ApiParam(required = true, name = "owNum", value = "开单编号") @RequestParam(value = "owNum", required = true) String owNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DgCashPledge>  dcp = dgCashPledgeService.selectCashPledge(owNum,null);
		double totalMoney = 0.0;
		if (!dcp.isEmpty()) {
			//获取剩余押金
			double payMoney = 0.0; //总押金
			double backMoney = 0.0; //已退总押金
			for (int i = 0; i < dcp.size(); i++) {
				String cpType = dcp.get(i).getCpType(); //登记押金;退押金
				if(cpType.equals("0")){
					payMoney += dcp.get(i).getCpMoney();
				}
				if(cpType.equals("1")){
					backMoney += dcp.get(i).getCpMoney();
				}
			}
			totalMoney = payMoney - backMoney;
		}
		map.put("totalMoney", totalMoney);
		map.put("dcp",dcp);
		return getSuccessResult(map);
	}
	
	/**
	 * 根据开单编号修改打印次数<br>
	 * 成功返回1
	 * @param owNum 开单编号 必须
	 * @return
	 */
	@RequestMapping("/updetePrintNumber")
	@ResponseBody
	@ApiOperation(value = "修改打印次数", httpMethod = "POST", notes = "修改打印次数")
	public Object updetePrintNumber(
			@ApiParam(required = true, name = "owNum", value = "开单编号") @RequestParam(value = "owNum", required = true) String owNum) {
			int count = dgCashPledgeService.updetePrintNumber(owNum);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/****************************吧台管理-物品寄存*********************************/
	
	/**
	 * 物品寄存页面访问接口
	 * @param model
	 * @return
	 */
	@RequestMapping("/goGoodsConsign")
	@ApiOperation(value = "物品寄存页面访问", httpMethod = "POST", notes = "物品寄存页面访问")
	public Object goGoodsConsign(Model model){
		//寄存POS
		model.addAttribute("posList",posService.getAllPosList());
		//客位
		model.addAttribute("seatList",consumerSeatService.getAllList(null));
		//消费区域
		model.addAttribute("areaList",consumptionAreaService.getAllList(null));
		ModelAndView modelAndView = new ModelAndView("api/goGoodsConsign");
        return modelAndView;
	}
	
	/**
	 * 物品寄存编辑页面访问接口
	 * @param model
	 * @param rowId 物品信息ID
	 * @return
	 */
	@RequestMapping("/editGoodsConsign")
	public Object editGoodsConsign(
			@ApiParam(required = false, name = "rowId", value = "物品信息ID") @RequestParam(value = "rowId", required = false) Integer rowId,
			@ApiParam(required = false, name = "clFlag", value = "处理状态") @RequestParam(value = "clFlag", required = false) String clFlag,
			Model model){
		//处理状态
		model.addAttribute("clFlag",clFlag);
		//寄存POS
		model.addAttribute("posList",posService.getAllPosList());
		//客位
		model.addAttribute("seatList",consumerSeatService.getAllList(null));
		//消费区域
		model.addAttribute("areaList",consumptionAreaService.getAllList(null));
		//物品种类
		model.addAttribute("dgctList",dgGoodsConsignService.selectGoodsType());
		if(rowId != null){
			List<DgGoodsConsign>  dgcList = dgGoodsConsignService.selectGoodsConsign(rowId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
			if(dgcList.size() > 0){
				DgGoodsConsign dgc = dgcList.get(0);
				model.addAttribute("dgc",dgc);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("api/goodsConsign-edit");
        return modelAndView;
	}
	
	/**
	 * 寄存物品添加<br>
	 * 成功返回 success
	 * @param clientName 客户名称
	 * @param clientPhone 客户电话
	 * @param clientSeat 客位
	 * @param goodsType 物品寄存种类
	 * @param goodsCode 物品编号
	 * @param goodsName 物品名称
	 * @param goodsNumber 数量
	 * @param goodsSpecification 规格
	 * @param goodsColor 颜色
	 * @param goodsExpirationDate 保质截止日期
	 * @param goodsExplain 说明
	 * @param gcFlag 寄存状态
	 * @param gcPos 寄存操作POS
	 * @param gcOperator 寄存操作员
	 * @param gcStartTime 寄存时间
	 * @param gcEndTime 寄存截止时间
	 * @param gcAddress 寄存位置
	 * @param clWay 处理方式
	 * @param clPos 处理操作POS
	 * @param clOperator 处理操作员
	 * @param clExplain 处理说明
	 * @return
	 */
	@RequestMapping("/addGoodsConsign")
	@ResponseBody
	@ApiOperation(value = "寄存物品添加", httpMethod = "POST", notes = "寄存物品添加")
	public Object addGoodsConsign(
			@ApiParam(required = false, name = "id", value = "物品ID") @RequestParam(value = "id", required = false) Integer id,
			//客户信息
			@ApiParam(required = true, name = "clientName", value = "客户名称") @RequestParam(value = "clientName", required = true) String clientName,
			@ApiParam(required = true, name = "clientPhone", value = "客户电话") @RequestParam(value = "clientPhone", required = true) String clientPhone,
			@ApiParam(required = false, name = "clientSeat", value = "客位") @RequestParam(value = "clientSeat", required = false) String clientSeat,
			//物品信息
			@ApiParam(required = false, name = "goodsType", value = "物品寄存种类") @RequestParam(value = "goodsType", required = false) String goodsType,
			@ApiParam(required = false, name = "goodsCode", value = "物品编号") @RequestParam(value = "goodsCode", required = false) String goodsCode,
			@ApiParam(required = true, name = "goodsName", value = "物品名称") @RequestParam(value = "goodsName", required = true) String goodsName,
			@ApiParam(required = true, name = "goodsNumber", value = "数量") @RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
			@ApiParam(required = false, name = "goodsSpecification", value = "规格") @RequestParam(value = "goodsSpecification", required = false) String goodsSpecification,
			@ApiParam(required = false, name = "goodsColor", value = "颜色") @RequestParam(value = "goodsColor", required = false) String goodsColor,
			@ApiParam(required = false, name = "goodsExpirationDate", value = "保质截止日期") @RequestParam(value = "goodsExpirationDate", required = false) String goodsExpirationDate,
			@ApiParam(required = false, name = "goodsExplain", value = "说明") @RequestParam(value = "goodsExplain", required = false) String goodsExplain,
			//寄存信息
			@ApiParam(required = false, name = "gcFlag", value = "寄存状态") @RequestParam(value = "gcFlag", required = false) String gcFlag,
			@ApiParam(required = false, name = "gcPos", value = "寄存操作POS") @RequestParam(value = "gcPos", required = false) String gcPos,
			@ApiParam(required = false, name = "gcOperator", value = "寄存操作员") @RequestParam(value = "gcOperator", required = false) String gcOperator,
			@ApiParam(required = false, name = "gcStartTime", value = "寄存时间") @RequestParam(value = "gcStartTime", required = false) String gcStartTime,
			@ApiParam(required = false, name = "gcEndTime", value = "寄存截止时间") @RequestParam(value = "gcEndTime", required = false) String gcEndTime,
			@ApiParam(required = false, name = "gcAddress", value = "寄存位置") @RequestParam(value = "gcAddress", required = false) String gcAddress, 
			//处理方式
			@ApiParam(required = false, name = "clWay", value = "处理方式") @RequestParam(value = "clWay", required = false) String clWay,
			@ApiParam(required = false, name = "clPos", value = "处理操作POS") @RequestParam(value = "clPos", required = false) String clPos,
			@ApiParam(required = false, name = "clOperator", value = "处理操作员") @RequestParam(value = "clOperator", required = false) String clOperator,
			@ApiParam(required = false, name = "clExplain", value = "处理说明") @RequestParam(value = "clExplain", required = false) String clExplain,
			Model model){
			int count = 0;
			if(id == null){ //添加
				 count = dgGoodsConsignService.insertGoodsConsign(clientName,clientPhone,clientSeat,goodsType,goodsCode,
						goodsName,goodsNumber,goodsSpecification,goodsColor,goodsExpirationDate,goodsExplain,gcFlag,gcPos,
						gcOperator,gcStartTime,gcEndTime,gcAddress,clWay,clPos,clOperator,clExplain);
			}else{ //修改
				count = dgGoodsConsignService.updateGoodsConsign(id, clientName, clientPhone, clientSeat, goodsType, goodsCode, 
						goodsName, goodsNumber, goodsSpecification, goodsColor, goodsExpirationDate, goodsExplain, gcFlag, 
						gcPos, gcOperator, gcStartTime, gcEndTime, gcAddress, clWay, clPos, clOperator, clExplain);
			}
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 寄存物品修改<br>
	 * 成功返回 success
	 * @param id
	 * @param clientName 客户名称
	 * @param clientPhone 客户电话
	 * @param clientSeat 客位
	 * @param goodsType 物品寄存种类
	 * @param goodsCode 物品编号
	 * @param goodsName 物品名称
	 * @param goodsNumber 数量
	 * @param goodsSpecification 规格
	 * @param goodsColor 颜色
	 * @param goodsExpirationDate 保质截止日期
	 * @param goodsExplain 说明
	 * @param gcFlag 寄存状态
	 * @param gcPos 寄存操作POS
	 * @param gcOperator 寄存操作员
	 * @param gcStartTime 寄存时间
	 * @param gcEndTime 寄存截止时间
	 * @param gcAddress 寄存位置
	 * @param clWay 处理方式
	 * @param clPos 处理操作POS
	 * @param clOperator 处理操作员
	 * @param clExplain 处理说明
	 * @return
	 */
	@RequestMapping("/updGoodsConsign")
	@ResponseBody
	@ApiOperation(value = "寄存物品修改", httpMethod = "POST", notes = "寄存物品修改")
	public Object updGoodsConsign(
			@ApiParam(required = true, name = "id", value = "id") @RequestParam(value = "id", required = true) Integer id,
			//客户信息
			@ApiParam(required = true, name = "clientName", value = "客户名称") @RequestParam(value = "clientName", required = true) String clientName,
			@ApiParam(required = true, name = "clientPhone", value = "客户电话") @RequestParam(value = "clientPhone", required = true) String clientPhone,
			@ApiParam(required = false, name = "clientSeat", value = "客位") @RequestParam(value = "clientSeat", required = false) String clientSeat,
			//物品信息
			@ApiParam(required = true, name = "goodsType", value = "物品寄存种类") @RequestParam(value = "goodsType", required = true) String goodsType,
			@ApiParam(required = false, name = "goodsCode", value = "物品编号") @RequestParam(value = "goodsCode", required = false) String goodsCode,
			@ApiParam(required = true, name = "goodsName", value = "物品名称") @RequestParam(value = "goodsName", required = true) String goodsName,
			@ApiParam(required = true, name = "goodsNumber", value = "数量") @RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
			@ApiParam(required = false, name = "goodsSpecification", value = "规格") @RequestParam(value = "goodsSpecification", required = false) String goodsSpecification,
			@ApiParam(required = false, name = "goodsColor", value = "颜色") @RequestParam(value = "goodsColor", required = false) String goodsColor,
			@ApiParam(required = false, name = "goodsExpirationDate", value = "保质截止日期") @RequestParam(value = "goodsExpirationDate", required = false) String goodsExpirationDate,
			@ApiParam(required = false, name = "goodsExplain", value = "说明") @RequestParam(value = "goodsExplain", required = false) String goodsExplain,
			//寄存信息
			@ApiParam(required = false, name = "gcFlag", value = "寄存状态") @RequestParam(value = "gcFlag", required = false) String gcFlag,
			@ApiParam(required = false, name = "gcPos", value = "寄存操作POS") @RequestParam(value = "gcPos", required = false) String gcPos,
			@ApiParam(required = false, name = "gcOperator", value = "寄存操作员") @RequestParam(value = "gcOperator", required = false) String gcOperator,
			@ApiParam(required = false, name = "gcStartTime", value = "寄存时间") @RequestParam(value = "gcStartTime", required = false) String gcStartTime,
			@ApiParam(required = false, name = "gcEndTime", value = "寄存截止时间") @RequestParam(value = "gcEndTime", required = false) String gcEndTime,
			@ApiParam(required = false, name = "gcAddress", value = "寄存位置") @RequestParam(value = "gcAddress", required = false) String gcAddress, 
			//处理方式
			@ApiParam(required = false, name = "clWay", value = "处理方式") @RequestParam(value = "clWay", required = false) String clWay,
			@ApiParam(required = false, name = "clPos", value = "处理操作POS") @RequestParam(value = "clPos", required = false) String clPos,
			@ApiParam(required = false, name = "clOperator", value = "处理操作员") @RequestParam(value = "clOperator", required = false) String clOperator,
			@ApiParam(required = false, name = "clExplain", value = "处理说明") @RequestParam(value = "clExplain", required = false) String clExplain){
			int count = dgGoodsConsignService.updateGoodsConsign(id, clientName,clientPhone,clientSeat,goodsType,goodsCode,
					goodsName,goodsNumber,goodsSpecification,goodsColor,goodsExpirationDate,goodsExplain,gcFlag,gcPos,
					gcOperator,gcStartTime,gcEndTime,gcAddress,clWay,clPos,clOperator,clExplain);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 寄存物品取走信息添加<br>
	 * 成功返回 success
	 * @param id
	 * @param qzTime 取走时间
	 * @param qzPos 取走操作POS
	 * @param qzOperator 取走操作员
	 * @return
	 */
	@RequestMapping("/addGoodsConsignByQz")
	@ResponseBody
	@ApiOperation(value = "寄存物品取走信息添加", httpMethod = "POST", notes = "寄存物品取走信息添加")
	public Object addGoodsConsignByQz(
			@ApiParam(required = true, name = "editIds", value = "editIds") @RequestParam(value = "editIds", required = true) String editIds,
			//取走信息
			@ApiParam(required = false, name = "gcFlag", value = "寄存状态") @RequestParam(value = "gcFlag", required = false) String gcFlag,
			@ApiParam(required = false, name = "qzTime", value = "取走时间") @RequestParam(value = "qzTime", required = false) String qzTime,
			@ApiParam(required = false, name = "qzPos", value = "取走操作POS") @RequestParam(value = "qzPos", required = false) String qzPos,
			@ApiParam(required = false, name = "qzOperator", value = "取走操作员") @RequestParam(value = "qzOperator", required = false) String qzOperator){
			try {
				for (String ids : editIds.split(",")) {
					Integer id = Integer.parseInt(ids);
					dgGoodsConsignService.addGoodsConsignByQz(id, gcFlag,
							qzTime, qzPos, qzOperator);
				}
			} catch (Exception e) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 根据条件查询寄存物品记录<br>
	 * 成功返回页面所需要的寄存物品记录
	 * @param id 寄存物品信息id
	 * @param jcStartTime 寄存开始日期
	 * @param jcEndTime 寄存结束日期
	 * @param goodsName 物品名称
	 * @param goodsColor 物品颜色
	 * @param gcPos 寄存POS
	 * @param qzStartTime 取走开始日期
	 * @param qzEndTime 取走结束日期
	 * @param clientName 客户
	 * @param clientSeat 客位
	 * @param expArea 消费区域
	 * @param aboveDays 超出截止日期X天
	 * @param goodsExpirationDate 超出保存期
	 * @param gcEndTime 超出寄存截止日期
	 * @return
	 */
	@RequestMapping("/selectGoodsConsign")
	@ResponseBody
	@ApiOperation(value = "根据条件查询寄存物品记录", httpMethod = "POST", notes = "根据条件查询寄存物品记录")
	public Object selectGoodsConsign(
			@ApiParam(required = false, name = "id", value = "寄存物品信息id") @RequestParam(value = "id", required = false) Integer id,
			@ApiParam(required = false, name = "isDel", value = "删除状态(0-未删除 1-已删除)") @RequestParam(value = "isDel", required = false) String isDel,
			@ApiParam(required = false, name = "jcStartTime", value = "寄存开始日期") @RequestParam(value = "jcStartTime", required = false) String jcStartTime,
			@ApiParam(required = false, name = "jcEndTime", value = "寄存结束日期") @RequestParam(value = "jcEndTime", required = false) String jcEndTime,
			@ApiParam(required = false, name = "goodsName", value = "物品名称") @RequestParam(value = "goodsName", required = false) String goodsName,
			@ApiParam(required = false, name = "goodsColor", value = "物品颜色") @RequestParam(value = "goodsColor", required = false) String goodsColor,
			@ApiParam(required = false, name = "gcPos", value = "寄存操作POS") @RequestParam(value = "gcPos", required = false) String gcPos,
			@ApiParam(required = false, name = "qzStartTime", value = "取走开始日期") @RequestParam(value = "qzStartTime", required = false) String qzStartTime,
			@ApiParam(required = false, name = "qzEndTime", value = "取走结束日期") @RequestParam(value = "qzEndTime", required = false) String qzEndTime,
			@ApiParam(required = false, name = "clientName", value = "客户名称") @RequestParam(value = "clientName", required = false) String clientName,
			@ApiParam(required = false, name = "clientSeat", value = "客位") @RequestParam(value = "clientSeat", required = false) String clientSeat,
			@ApiParam(required = false, name = "expArea", value = "消费区域") @RequestParam(value = "expArea", required = false) String expArea,
			@ApiParam(required = false, name = "aboveDays", value = "超出截止日期X天(大于0)") @RequestParam(value = "aboveDays", required = false) Integer aboveDays,
			@ApiParam(required = false, name = "goodsExpirationDate", value = "超出保存期(任意值)") @RequestParam(value = "goodsExpirationDate", required = false) String goodsExpirationDate,
			@ApiParam(required = false, name = "gcEndTime", value = "超出寄存截止日期(任意值)") @RequestParam(value = "gcEndTime", required = false) String gcEndTime,
			@ApiParam(required = false, name = "gcFlag", value = "寄存状态") @RequestParam(value = "gcFlag", required = false) String gcFlag) {
		List<DgGoodsConsign>  dcp = dgGoodsConsignService.selectGoodsConsign(id,isDel,jcStartTime,jcEndTime,goodsName,goodsColor,gcPos,qzStartTime,qzEndTime,clientName,clientSeat,expArea,aboveDays,goodsExpirationDate,gcEndTime,gcFlag);
		if (dcp.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return dcp;
	}
	
	/**
	 * 寄存物品删除<br>
	 * 成功返回 success
	 * @param editIds 待删除寄存物品信息id以逗号“,”隔开
	 * @return
	 */
	@RequestMapping("/delGoodsConsign")
	@ResponseBody
	@ApiOperation(value = "寄存物品删除", httpMethod = "POST", notes = "寄存物品删除")
	public Object delGoodsConsign(
			@ApiParam(required = true, name = "editIds", value = "待删除寄存物品信息id以逗号“,”隔开") @RequestParam(value = "editIds", required = true) String editIds){
			int count = dgGoodsConsignService.deleteGoodsConsign(editIds);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 寄存物品信息回收<br>
	 * 成功返回 success
	 * @param editIds 待删除寄存物品信息id以逗号“,”隔开
	 * @return
	 */
	@RequestMapping("/delGoodsConsignTrash")
	@ResponseBody
	@ApiOperation(value = "寄存物品信息回收", httpMethod = "POST", notes = "寄存物品信息回收")
	public Object delGoodsConsignTrash(
			@ApiParam(required = true, name = "editIds", value = "待回收寄存物品信息id以逗号“,”隔开") @RequestParam(value = "editIds", required = true) String editIds){
			int count = dgGoodsConsignService.deleteGoodsConsignTrash(editIds);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 寄存物品信息还原<br>
	 * 成功返回 success
	 * @param editIds 待删除寄存物品信息id以逗号“,”隔开
	 * @return
	 */
	@RequestMapping("/replyGoodsConsign")
	@ResponseBody
	@ApiOperation(value = "寄存物品信息还原", httpMethod = "POST", notes = "寄存物品信息还原")
	public Object replyGoodsConsign(
			@ApiParam(required = true, name = "editIds", value = "待还原寄存物品信息id以逗号“,”隔开") @RequestParam(value = "editIds", required = true) String editIds){
			int count = dgGoodsConsignService.replyGoodsConsign(editIds);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}

	/**
	 * 物品寄存种类添加<br>
	 * 成功返回 success
	 * @param gtName 种类名称
	 * @param isRemind 结算时是否提醒  0-不提醒，1-提醒
	 * @return
	 */
	@RequestMapping("/addGoodsType")
	@ResponseBody
	@ApiOperation(value = "物品寄存种类添加", httpMethod = "POST", notes = "物品寄存种类添加")
	public Object addGoodsType(
			@ApiParam(required = true, name = "gtName", value = "种类名称") @RequestParam(value = "gtName", required = true) String gtName,
			@ApiParam(required = false, name = "isRemind", value = "结算时是否提醒  0-不提醒，1-提醒 ") @RequestParam(value = "isRemind", required = false) String isRemind){
			int count = dgGoodsConsignService.insertGoodsType(gtName,isRemind);
			if (count < 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 物品寄存种类删除<br>
	 * 成功返回 success
	 * @param id 物品寄存种类信息id
	 * @return
	 */
	@RequestMapping("/delGoodsType")
	@ResponseBody
	@ApiOperation(value = "物品寄存种类删除", httpMethod = "POST", notes = "物品寄存种类删除")
	public Object delGoodsType(
			@ApiParam(required = true, name = "id", value = "物品寄存种类信息id ") @RequestParam(value = "id", required = true) Integer id){
			int count = dgGoodsConsignService.deleteGoodsType(id);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	/**
	 * 物品寄存种类修改<br>
	 * 成功返回 success
	 * @param id 物品寄存种类信息id
	 * @return
	 */
	@RequestMapping("/updGoodsType")
	@ResponseBody
	@ApiOperation(value = "物品寄存种类修改", httpMethod = "POST", notes = "物品寄存种类修改")
	public Object updGoodsType(
			@ApiParam(required = true, name = "id", value = "物品寄存种类信息id ") @RequestParam(value = "id", required = true) Integer id,
			@ApiParam(required = false, name = "gtName", value = "种类名称") @RequestParam(value = "gtName", required = false) String gtName,
			@ApiParam(required = false, name = "isRemind", value = "结算时是否提醒  0-不提醒，1-提醒 ") @RequestParam(value = "isRemind", required = false) String isRemind){
			int count = dgGoodsConsignService.updateGoodsType(id, gtName, isRemind);
			if (count <= 0) {
				return getFailResult();
			}
		return getSuccessResult();
	}
	
	/**
	 * 获取所有物品寄存种类数据<br>
	 * 成功返回所有物品寄存种类信息
	 * @return
	 */
	@RequestMapping("/getAllGoodsType")
	@ResponseBody
	@ApiOperation(value = "获取所有物品寄存种类数据", httpMethod = "GET", notes = "获取所有物品寄存种类数据")
	public Object selectGoodsType(){
		List<Map<String, Object>> map = dgGoodsConsignService.selectGoodsType();
		if (map.isEmpty()) {
			return getResult(APIEnumDefine.S005);
		}
		return getSuccessResult(map);
	}
}
