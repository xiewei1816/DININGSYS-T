package com.yqsh.diningsys.web.service.api.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.model.ModifyItemFileNum;
import com.yqsh.diningsys.api.model.VariablePrice;
import com.yqsh.diningsys.api.util.DoubleCompare;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.APIReMoMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.PaySettlementMapper;
import com.yqsh.diningsys.web.dao.api.SysBusinessLogMapper;
import com.yqsh.diningsys.web.dao.archive.*;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.ServiceClassMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwConsumerDetailsMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwDetailsOtherMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwDetailsOther;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.api.APIReMoService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.sevlet.CacheInit;
import com.yqsh.diningsys.web.util.OnlineHttp;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 前台-更换修改接口serviceImpl
 *
 * @author zhshuo create on 2016-12-15 13:08
 */
@Service
public class APIReMoServiceImpl implements APIReMoService {
	private static SimpleDateFormat formatDate = new SimpleDateFormat(
			"yyyy-MM-dd"); // 年月日
	@Resource
	private APIReMoMapper apiReMoMapper;

	@Resource
	private DgOpenWaterMapper dgOpenWaterMapper;

	@Resource
	private APICheckServiceMapper apiCheckServiceMapper;

	@Resource
	private BillMapper billMapper;

	@Resource
	private DgItemFileMapper dgItemFileMapper;

	@Resource
	private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;

	@Resource
	private DgOwDetailsOtherMapper dgOwDetailsOtherMapper;

	@Resource
	private DgItemFilePackageMapper dgItemFilePackageMapper;

	@Resource
	private DgItemFilePackageBxMapper dgItemFilePackageBxMapper;

	@Resource
	private DgItemSaleLimitMapper dgItemSaleLimitMapper;

	@Resource
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;

	@Resource
	private DgPrintDataService dgPrintDataService;

	@Resource
	private DeskBusinessSettingService deskBusinessSettingService;

	@Resource
	private DgPosMapper dgPosMapper;

	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private TbOrgMapper tbOrgMapper;

	@Resource
	private ServiceClassMapper serviceClassMapper;

	@Resource
	private TableInfoService tableInfoService;

	@Autowired
	private BusinessPermissionService businessPermissionService;

	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;

	@Resource
	private PaySettlementMapper paySettlementMapper;

	@Resource
	private SysBusinessLogMapper sysBusinessLogMapper;
	
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Override
	public void modifyItemFilePrice(String userCode,String itemFilePrice, SysUser sysUser,
			Integer variablePriceType,String owNum) {
		Gson gson = new Gson();
		List<VariablePrice> list = gson.fromJson(itemFilePrice,
				new TypeToken<List<VariablePrice>>() {
				}.getType());

		List<DgItemFileType> dgItemFileTypes = null;
		List<DgItemFile> dgItemFiles = null;

		if (variablePriceType == 1) {// 只能变价指定小类
			dgItemFileTypes = businessPermissionService
					.selectItemFileTypeByZwCodeAndType(sysUser.getEmpDuties(),
							3);
		} else if (variablePriceType == 2) {// 只能变价指定品项
			dgItemFiles = businessPermissionService
					.selectItemFileByZwCodeAndType(sysUser.getEmpDuties(), 3);
		}

		for (VariablePrice itemData : list) {
			Map param = new HashedMap();

			Double variablePrice = itemData.getItemFilePrice();
			Double variableSubtotal = null;
			param.put("itemFileId", itemData.getItemFileId());
			param.put("serviceId", itemData.getServiceId());
			//加单数据
			DgOwConsumerDetails dgOwConsumerDetails = apiCheckServiceMapper
					.selectDataByServiceIdAndOwId(param);
			
			param.put("itemFilePrice", itemData.getItemFilePrice());
//			param.put("itemFileNum", itemData.getItemFileNum());
			param.put("initalPrice", itemData.getInitalPrice());
//			variableSubtotal = itemData.getItemFileNum() * variablePrice;
			variableSubtotal = MathExtend.multiply(variablePrice, dgOwConsumerDetails.getItemFileNumber());
			

			if (checkIsContinue(dgOwConsumerDetails, dgItemFileTypes,
					dgItemFiles, variablePriceType)) {
				
				//退单修改数量
				List<DgOwConsumerDetails> backDocds = apiReMoMapper.selectItemFileBackOws(param);
				for(DgOwConsumerDetails bd:backDocds){
					Map bmap = new HashMap();
					bmap.put("serviceId",bd.getOwId());
					bmap.put("itemFileId",bd.getItemFileId());
//					bmap.put("itemFileNum",bd.getItemFileNumber());
					bmap.put("itemFilePrice",variablePrice);
					bmap.put("initalPrice",bd.getItemFinalPrice());
					bmap.put("subtotal",MathExtend.multiply(bd.getItemFileNumber(), variablePrice));
					
					variableSubtotal += -bd.getSubtotal() + MathExtend.multiply(bd.getItemFileNumber(), variablePrice);
					apiReMoMapper.modifyItemFilePrice(param);
					
				}
				
				DgOpenWater dgOpenWater = apiCheckServiceMapper
						.selectOpenWaterByItemAndServiceId(param);

				param.put("subtotal", variableSubtotal);
				apiReMoMapper.modifyItemFilePrice(param);

				DgOpenWater dgOpenWater1 = new DgOpenWater();
				dgOpenWater1.setId(dgOpenWater.getId());
				dgOpenWater1.setSubtotal(dgOpenWater.getSubtotal()
						- dgOwConsumerDetails.getSubtotal() + variableSubtotal);
				dgOpenWater1.setItemCostsSum(dgOpenWater.getItemCostsSum()
						- dgOwConsumerDetails.getSubtotal() + variableSubtotal);

				apiReMoMapper.updateOpenWaterSubtotal(dgOpenWater1);
			}
		}
		
		//插入日志
		DgOpenWater ow = dgOpenWaterMapper.selectByOpenWaterByNum(owNum);
		SysUser user= sysUserMapper.chekUserCode(userCode);
		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(ow.getSeatId()); //客位信息
		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 5, ow.getOwNum(), 
				 seat.getName(), "操作客位:"+seat.getName()+",进行修改品项价格的操作",new SimpleDateFormat("yyyy_MM").format(new Date())));
	}

	Boolean checkIsContinue(DgOwConsumerDetails itemFile,
			List<DgItemFileType> dgItemFileTypes, List<DgItemFile> dgItemFiles,
			Integer type) {
		ArrayList<Integer> integers = new ArrayList<>();
		integers.add(itemFile.getItemFileId());
		DgItemFile dgItemFiles1 = dgItemFileMapper
				.selectItemFileInIds(integers).get(0);
		if (type == 1) {// 只能变价指定小类
			if(dgItemFileTypes != null && dgItemFileTypes.size()>0){
				for (DgItemFileType dgItemFileTypes1 : dgItemFileTypes) {
					if (dgItemFileTypes1.getId() == dgItemFiles1.getPpxlId()) {
						return true;
					}
				}
			}
		} else {// 只能变价指定品项
			if(dgItemFiles != null){
				for (DgItemFile dgItemFile1 : dgItemFiles) {
					if (dgItemFile1.getId() == dgItemFiles1.getId()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void modifyReplaceWaiter(String owNum, Integer newWaiterId,
			Integer newMaketingStaff, Integer replaceService) {
		Map param = new HashedMap();

		param.put("owNum", owNum);
		param.put("newWaiterId", newWaiterId);
		param.put("newMaketingStaff", newMaketingStaff);
		param.put("replaceService", replaceService);
		apiReMoMapper.ReplaceWaiter(param);

		DgOpenWater dgOpenWater = dgOpenWaterMapper
				.selectByOpenWaterByNum(owNum);

		param.put("oldWaiterId", dgOpenWater.getWaiter());

		if (replaceService == 1 && newWaiterId != null) {
			apiReMoMapper.ReplaceWaiterService(param);
		}
	}

	@Override
	public Integer modifyResetSeatState(Integer id) {
        Integer integer = apiReMoMapper.resetSeatState(id);
        DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(id);

		OnlineHttp.onlineSeatModify(seat.getUuidKey(), 1+"");

		return integer;
	}

	@Override
	public Integer modifySeatIdentify(Integer id, String seatLable,
			Integer isVip, Integer isInternal) {
		Map param = new HashMap();

		param.put("seatId", id);
		param.put("seatLable", seatLable == null ? "" : seatLable);
		param.put("isVip", isVip);
		param.put("isInternal", isInternal);

		return apiReMoMapper.modifySeatIdentify(param);
	}

	@Override
	public List<DgOwConsumerDetails> selectAllItemFile() {
		return null;
	}

	@Override
	public void modifyItemFileNumber(String userCode,String owNum, String modifyData) {
		Gson gson = new Gson();
		List<ModifyItemFileNum> list = gson.fromJson(modifyData,
				new TypeToken<List<ModifyItemFileNum>>() {
				}.getType());

		for (ModifyItemFileNum itemData : list) {

			Integer dataItemFileId = itemData.getItemFileId(), dataItemServiceId = itemData
					.getServiceId();

			Map<String, Object> otherParam = new HashedMap();
			otherParam.put("serviceId", dataItemServiceId);
			otherParam.put("itemFileId", dataItemFileId);

			DgOpenWater dgOpenWater = apiCheckServiceMapper
					.selectOpenWaterByItemAndServiceId(otherParam);

			DgOwConsumerDetails currentItemFileDetail = apiCheckServiceMapper
					.selectDataByServiceIdAndOwId(otherParam); // 得到当前拆账下具体品项的详细信息
			Double itemFileNum = itemData.getItemFileNum(), newSubTotal = itemFileNum
					* currentItemFileDetail.getItemFinalPrice();

			List<DgOwDetailsOther> dgOwDetailsOthers = apiCheckServiceMapper
					.selectDetailOtherInfo(otherParam);
			for (DgOwDetailsOther dgOwDetailsOther : dgOwDetailsOthers) {
				if (dgOwDetailsOther.getZzffSf() == 1) {
					if (dgOwDetailsOther.getZzffSfType() == 0) {
						newSubTotal += dgOwDetailsOther.getOcosts();
					} else {
						newSubTotal += dgOwDetailsOther.getOcosts()
								* itemFileNum;
					}
				}
			}

			Map<String, Object> map = new HashMap();

			// 修改品项的数量
			map.put("itemFileId", dataItemFileId);
			map.put("serviceId", dataItemServiceId);
			map.put("itemFileNum", itemData.getItemFileNum());
			map.put("initalPrice", itemData.getProductionCosts());
			map.put("subtotal", newSubTotal);
			apiReMoMapper.modifyItemFileNumber(map);

			// 更新营业流水的小计
			DgOpenWater dgOpenWater1 = new DgOpenWater();
			dgOpenWater1.setId(dgOpenWater.getId());
			Double subtotal = dgOpenWater.getSubtotal() + newSubTotal
					- currentItemFileDetail.getSubtotal();
			dgOpenWater1.setSubtotal(subtotal);
			dgOpenWater1.setItemCostsSum(dgOpenWater.getItemCostsSum()
					+ newSubTotal - currentItemFileDetail.getSubtotal());
			apiReMoMapper.updateOpenWaterSubtotal(dgOpenWater1);
		}
		
		//插入日志
		DgOpenWater ow = dgOpenWaterMapper.selectByOpenWaterByNum(owNum);
		SysUser user= sysUserMapper.chekUserCode(userCode);
		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(ow.getSeatId()); //客位信息
		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 4, ow.getOwNum(), 
				 seat.getName(), "操作客位:"+seat.getName()+",进行修改品项数量的操作",new SimpleDateFormat("yyyy_MM").format(new Date())));
	}

	@Override
	public void modifyDishesFree(String userCode,String dishFreeData, Integer pos,
			SysUser sysUser, String openWater, Integer modifyType,
			Integer freeType,String reason) {
			Gson gson = new Gson();
		List<Map<String,String>> list = gson.fromJson(dishFreeData,
				new TypeToken<List<Map<String,String>>>() {
				}.getType());

		List<DgItemFileType> dgItemFileTypes = null;
		List<DgItemFile> dgItemFiles = null;

		if(freeType == 1){// 只能变价指定小类
			dgItemFileTypes = businessPermissionService.selectItemFileTypeByZwCodeAndType(sysUser.getEmpDuties(), 1);
		}else if(freeType == 2){// 只能变价指定品项
			dgItemFiles = businessPermissionService.selectItemFileByZwCodeAndType(sysUser.getEmpDuties(), 1);
		}

		DgPos dgPos = new DgPos();
		dgPos.setId(pos);
		dgPos = dgPosMapper.getPosByID(dgPos);
		TbOrg org = new TbOrg();
		org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
		org = tbOrgMapper.getOrgByID(org);

		// 获取前台营业设置，判断加单时的默认点单元defaultWaiter:servcer/当前服务员,waiter/当前点单元
		DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService
				.getDeskBusinessSetting();
		String seatServ = deskBusinessSetting.getSeatServ();
		DBSSeetServDTO dbsSeetServDTO = gson.fromJson(seatServ,
				DBSSeetServDTO.class);
		String defaultWaiter = dbsSeetServDTO.getDefaultWaiter();

		DgOpenWater dgOpenWater1 = apiCheckServiceMapper
				.selectOpenWaterByowNum(openWater);

		Integer waiterId = null;
		if (defaultWaiter.equals("server")) {// 后台设置的为当前服务员

			Integer seatId = dgOpenWater1.getSeatId();

			Map<String, Object> serviceClassParam = new HashMap<>();
			serviceClassParam.put("bisId", tableInfoService.getMealInt());
			serviceClassParam.put("seatId", seatId);

			ServiceClass serviceClass = serviceClassMapper
					.selectDataByBisIdAndSeatId(serviceClassParam);
			if (serviceClass != null) {
				waiterId = serviceClass.getWaiterId();
			} else {
				waiterId = sysUser.getId();
			}
		} else if (defaultWaiter.equals("waiter")) {// 后台设置的为当前点单员
			waiterId = sysUser.getId();
		}

		List<String> fwList = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(org.getOrgCode(), dgPos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		Map serviceMap = new HashMap();
		serviceMap.put("owId", dgOpenWater1.getId());
		serviceMap.put("waiterId", waiterId);
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("serviceType", modifyType == 1 ? 3 : 2); // 赠送品项服务流水类型为3，撤销赠送服务流水类型为2
		serviceMap.put("serviceNum", fwList.get(0)); // 新增服务流水号
		serviceMap.put("zdbz",reason);
		billMapper.insertOwServiceWater(serviceMap); // 插入服务流水

		Integer freeServiceId = Integer.parseInt(serviceMap.get("id")
				.toString()); // 赠送品项的服务单ID

        Integer newServiceId = MapUtils.getInteger(serviceMap, "id");


        for (Map<String,String> itemData : list) {
			Map<String, Object> map = new HashMap();

			map.put("itemFileId", itemData.get("itemFileId"));
			map.put("serviceId", itemData.get("serviceId"));
			map.put("itemFileNumber", itemData.get("itemFileNumber"));

			// 查询出每一个赠送/取消赠送 的品项的具体信息
			DgOwConsumerDetails dgOwConsumerDetails1 = apiCheckServiceMapper
					.selectDataByServiceIdAndOwId(map);

			if (checkIsContinue(dgOwConsumerDetails1, dgItemFileTypes,
					dgItemFiles, freeType)) {
				DgOpenWater dgOpenWater = apiCheckServiceMapper
						.selectOpenWaterByItemAndServiceId(map);

				if (modifyType == 1) {
					//如果赠送的数量和原点单的数量相等，则直接修改为赠单
					if(dgOwConsumerDetails1.getItemFileNumber().compareTo(Double.parseDouble(itemData.get("itemFileNumber").toString())) == 0){
						DgOwConsumerDetails dgOwConsumerDetails = new DgOwConsumerDetails();
						BeanUtils.copyProperties(dgOwConsumerDetails1,
								dgOwConsumerDetails);
						dgOwConsumerDetails.setNewServiceId(freeServiceId);

						dgOwConsumerDetails
								.setZsItemFinalPrice(dgOwConsumerDetails1
										.getItemFinalPrice());
						dgOwConsumerDetails
								.setZsProductionCosts(dgOwConsumerDetails1
										.getProductionCosts());
						dgOwConsumerDetails.setZsSubtotal(dgOwConsumerDetails1
								.getSubtotal());

						dgOwConsumerDetails.setItemFinalPrice(0.0);
						dgOwConsumerDetails.setProductionCosts(0.0);
						dgOwConsumerDetails.setSubtotal(0.0);
						dgOwConsumerDetails.setNotes("3");

						// 赠送品项 修改价格信息
						apiCheckServiceMapper.setDetailFree(dgOwConsumerDetails);

						map.put("subtotal", MathExtend.subtract(dgOpenWater.getSubtotal(),dgOwConsumerDetails1.getSubtotal()));

						map.put("itemCostSum",MathExtend.subtract(dgOpenWater.getItemCostsSum(),dgOwConsumerDetails1.getSubtotal()));

						map.put("serviceId", newServiceId);

						apiReMoMapper.modifyOpenWaterSubtotal(map);
					}else{//如果赠单的数量小于原点单数量，则修改原点单的数量和金额，并新增一条新的服务流水以及品项详细
						//品项赠送可以选择品项赠送的数量
						Double itemFileNumber = dgOwConsumerDetails1.getItemFileNumber();
						Double newItemFileNumber = MathExtend.subtract(itemFileNumber, Double.parseDouble(map.get("itemFileNumber").toString()));
						dgOwConsumerDetails1.setItemFileNumber(newItemFileNumber);
						dgOwConsumerDetails1.setSubtotal(MathExtend.multiply(newItemFileNumber,dgOwConsumerDetails1.getItemFinalPrice()));
						//修改原品项详细
						apiCheckServiceMapper.updateDetailInfo(dgOwConsumerDetails1);

						map.put("subtotal", MathExtend.subtract(dgOpenWater.getSubtotal(),dgOwConsumerDetails1.getSubtotal()));
						map.put("itemCostSum",MathExtend.subtract(dgOpenWater.getItemCostsSum(),dgOwConsumerDetails1.getSubtotal()));
						map.put("serviceId", newServiceId);

						apiReMoMapper.modifyOpenWaterSubtotal(map);

						DgOwConsumerDetails dgOwConsumerDetails = new DgOwConsumerDetails();
						BeanUtils.copyProperties(dgOwConsumerDetails1,
								dgOwConsumerDetails);
						dgOwConsumerDetails.setItemFileNumber(Double.parseDouble(map.get("itemFileNumber").toString()));
						dgOwConsumerDetails.setNewServiceId(freeServiceId);
						dgOwConsumerDetails
								.setZsItemFinalPrice(dgOwConsumerDetails1
										.getItemFinalPrice());
						dgOwConsumerDetails
								.setZsProductionCosts(MathExtend.multiply(dgOwConsumerDetails.getItemFileNumber(),MathExtend.divide(dgOwConsumerDetails1
										.getProductionCosts(),dgOwConsumerDetails1.getItemFileNumber())));
						dgOwConsumerDetails.setZsSubtotal(MathExtend.multiply(dgOwConsumerDetails.getItemFileNumber(),dgOwConsumerDetails.getZsItemFinalPrice()));

						dgOwConsumerDetails.setItemFinalPrice(0.0);
						dgOwConsumerDetails.setProductionCosts(0.0);
						dgOwConsumerDetails.setSubtotal(0.0);
						dgOwConsumerDetails.setNotes("3");
						//插入赠送的品项详细
						apiCheckServiceMapper.insertFreeDetailInfo(dgOwConsumerDetails);


					}
				} else if (modifyType == 2) {
					DgOwConsumerDetails dgOwConsumerDetails = new DgOwConsumerDetails();
					BeanUtils.copyProperties(dgOwConsumerDetails1,
							dgOwConsumerDetails);
					dgOwConsumerDetails.setNewServiceId(freeServiceId);

					dgOwConsumerDetails.setZsItemFinalPrice(0.0);
					dgOwConsumerDetails.setZsProductionCosts(0.0);
					dgOwConsumerDetails.setZsSubtotal(0.0);

					dgOwConsumerDetails.setItemFinalPrice(dgOwConsumerDetails1
							.getZsItemFinalPrice());
					dgOwConsumerDetails.setProductionCosts(dgOwConsumerDetails1
							.getZsProductionCosts());
					dgOwConsumerDetails.setSubtotal(dgOwConsumerDetails1
							.getZsSubtotal());
					dgOwConsumerDetails.setNotes("2");

					// 赠送品项 修改价格信息
					apiCheckServiceMapper.cancelDetailFree(dgOwConsumerDetails);

                    map.put("subtotal", MathExtend.add(dgOpenWater.getSubtotal(),dgOwConsumerDetails1.getZsSubtotal()));

                    map.put("itemCostSum",MathExtend.add(dgOpenWater.getItemCostsSum(),dgOwConsumerDetails1.getZsSubtotal()));

                    map.put("serviceId", newServiceId);
                    apiReMoMapper.modifyOpenWaterSubtotal(map);
				}
			}
		}
        if(modifyType == 1) {
            SysUser user= sysUserMapper.chekUserCode(userCode);
    		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(dgOpenWater1.getSeatId());
    		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 6, dgOpenWater1.getOwNum(), 
    				seat.getName(), "操作客位:"+seat.getName()+",做了品项赠送操作",new SimpleDateFormat("yyyy_MM").format(new Date())));		
        } else if(modifyType == 2) {
            SysUser user= sysUserMapper.chekUserCode(userCode);
    		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(dgOpenWater1.getSeatId());
    		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 7, dgOpenWater1.getOwNum(), 
    				seat.getName(), "操作客位:"+seat.getName()+",做了撤销品项赠送操作",new SimpleDateFormat("yyyy_MM").format(new Date())));	
        }
	}

	@Override
	public Map modifyDishesTurntable(String userCode,String targetOpenWater,
			String operaOpenWater, List<Map> maps) {

		DgOpenWater tOpenWater = dgOpenWaterMapper
				.selectByOpenWaterByNum(targetOpenWater);
		DgOpenWater oOpenWater = dgOpenWaterMapper
				.selectByOpenWaterByNum(operaOpenWater);
		Map ret = new HashMap();
		// 验证数据是否正确
		Map checkBack = checkBackBill(operaOpenWater, maps);
		if (checkBack.containsKey("error")) {
			ret.put("error", APIEnumDefine.M0101012);
			return ret;
		}

		List<Map> insertMaps = preModifyDishesTurntable(maps);
		// 计算退去,增加的总金额
		double openwaterSubtotal = 0;
		for (Map cmaps : insertMaps) {
			Gson gson = new Gson();
			DgPos pos = new DgPos();
			pos.setId(tOpenWater.getOpenPos());
			pos = dgPosMapper.getPosByID(pos);
			TbOrg tbOrg = new TbOrg();
			tbOrg.setId(Integer.valueOf(pos.getOrganization()));
			tbOrg = tbOrgMapper.getOrgByID(tbOrg);
			List<String> serviceNums = gson.fromJson(deskBusinessSettingService
					.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 2,
							SerialRulEnum.FW), new TypeToken<List<String>>() {
			}.getType());

			List<Map> syNumber = (List<Map>) checkBack.get("syNumber");

			Map<String, Object> map = new HashMap();
			Integer owId = tOpenWater.getId();
			Integer waiterId = tOpenWater.getWaiter();
			map.put("owId", owId);
			map.put("waiterId", waiterId);
			map.put("serviceTime", new Date());
			map.put("serviceType", cmaps.get("serviceType"));
			map.put("serviceNum", serviceNums.get(0));
			// 创建加单的服务流水
			billMapper.insertOwServiceWater(map);
			Integer serviceId = Integer.parseInt(map.get("id").toString());// 加单的服务单ID

			Integer backOwId = oOpenWater.getId();
			Integer backWaiterId = oOpenWater.getWaiter();
			Map<String, Object> omap = new HashMap();
			omap.put("owId", backOwId);
			omap.put("waiterId", waiterId);
			omap.put("serviceTime", new Date());
			omap.put("serviceType", 4);
			omap.put("serviceNum", serviceNums.get(1));
			billMapper.insertOwServiceWater(omap);
			Integer backServiceId = Integer.parseInt(omap.get("id").toString());// 退单的服务单ID

			List<Map> child = (List<Map>)cmaps.get("child");
			for (Map map1 : child) {
				// 减单
				Map<String, Object> src = new HashMap<String, Object>();
				src.put("owId", map1.get("serviceId"));
				src.put("itemId", map1.get("itemFileId"));

				Map<String, Object> param = new HashMap();
				param.put("itemFileId",
						Integer.parseInt(map1.get("itemFileId").toString()));
				param.put("serviceId",
						Integer.parseInt(map1.get("serviceId").toString()));

				DgOwConsumerDetails dgOwConsumerDetails1 = dgOwConsumerDetailsMapper
						.selectDetailByServiceIdAndItemFileId(param);
				// 先退单
				DgItemFile itemFile = dgItemFileMapper
						.selectByPrimaryKey(dgOwConsumerDetails1
								.getItemFileId());
				DgOwConsumerDetails cd = new DgOwConsumerDetails();
				cd.setItemFileId(dgOwConsumerDetails1.getItemFileId());
				cd.setItemFileNumber(-Double.valueOf(""
						+ map1.get("itemFileNum")));
				cd.setNotes("4");
				cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile
						.getIsTc());
				cd.setOwId(backServiceId);
				cd.setBackOwId(Integer
						.valueOf(map1.get("serviceId").toString()));
				// 不计算制作费用(退单一律不减)
				double zzffTotal = 0;// 制作费用和
				List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
						.selectByOwId(src);
				for (DgOwDetailsOther other : rOther) {
					if (other.getZzffSf() != null && other.getZzffSf() == 1) {
						// 按品项
						if (other.getZzffSfType() == 0) {
							for (Map sy : syNumber) {
								if (((int) sy.get("item_file_id") == dgOwConsumerDetails1
										.getItemFileId())
										&& ((int) sy.get("ow_id") == (int) map1
												.get("serviceId"))) {
									if (DoubleCompare
											.compare(
													(double) sy
															.get("item_file_number"),
													Double.valueOf(""
															+ map1.get("itemFileNum")))) {
										zzffTotal = MathExtend.add(zzffTotal,
												other.getOcosts());
									} else {
										zzffTotal = 0.0;
									}
									break;
								}
							}
						} else if (other.getZzffSfType() == 1) {
							zzffTotal = MathExtend
									.add(zzffTotal, MathExtend.multiply(
											other.getOcosts(),
											Double.valueOf(""
													+ map1.get("itemFileNum"))));
						}
					}
				}
				// 插入套餐子项
				if (cd.getIsTc() == 1) {
					insertTc(itemFile.getId(), cd.getItemFileNumber(),
							cd.getOwId(), "4");
				}

				cd.setProductionCosts(-zzffTotal);
				cd.setItemFinalPrice(dgOwConsumerDetails1.getItemFinalPrice());
				double moc = MathExtend.multiply(cd.getItemFinalPrice(),
						cd.getItemFileNumber());
				double subtotal = MathExtend.add(-zzffTotal, moc);
				cd.setSubtotal(subtotal);
				dgOwConsumerDetailsMapper.insertSelective(cd);
				openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);

				// 加单
				DgOwConsumerDetails jd = new DgOwConsumerDetails();
				jd.setItemFileId(dgOwConsumerDetails1.getItemFileId());
				jd.setItemFileNumber(Double.valueOf(""
						+ map1.get("itemFileNum")));
				jd.setServingType(dgOwConsumerDetails1.getServingType());
				jd.setProductionCosts(zzffTotal);
				jd.setServingTypeGlobal(dgOwConsumerDetails1
						.getServingTypeGlobal());
				jd.setExpectationsServingTime(DateUtil.getDateByFormat(
						dgOwConsumerDetails1.getExpectationsServingTime(),
						"yyyy-MM-dd HH:mm:ss"));
				jd.setNotes(""+cmaps.get("serviceType"));
				jd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile
						.getIsTc());
				jd.setOwId(serviceId);
				// 先录入额外项(方便计算服务流水小计)
				for (DgOwDetailsOther o : rOther) {
					o.setSfId(serviceId);
					dgOwDetailsOtherMapper.insertSelective(o);
				}
				// 插入套餐子项
				if (cd.getIsTc() == 1) {
					insertTc(itemFile.getId(), jd.getItemFileNumber(),
							jd.getOwId(), ""+cmaps.get("serviceType"));
				}

				jd.setItemFinalPrice(dgOwConsumerDetails1.getItemFinalPrice());
				jd.setSubtotal(MathExtend.add(zzffTotal, -moc));
				jd.setDiscount(1.0);
				dgOwConsumerDetailsMapper.insertSelective(jd);
			}
		}

		// 原始单据退单,最后更新营业流水号
		oOpenWater.setItemCostsSum(MathExtend.add(oOpenWater.getItemCostsSum(),
				openwaterSubtotal));
		oOpenWater.setSubtotal(MathExtend.add(oOpenWater.getSubtotal(),
				openwaterSubtotal));
		billMapper.updateOpenWaterPrimaryKey(oOpenWater);

		// 目标单据,最后更新营业流水号
		tOpenWater.setItemCostsSum(MathExtend.add(tOpenWater.getItemCostsSum(),
				-openwaterSubtotal));
		tOpenWater.setSubtotal(MathExtend.add(tOpenWater.getSubtotal(),
				-openwaterSubtotal));
		billMapper.updateOpenWaterPrimaryKey(tOpenWater);

		// 单品转台
		dgPrintDataService.insertModifyDishesTurntable(oOpenWater.getId(),
				tOpenWater.getId(), maps);
		
		
		//插入日志
		SysUser user= sysUserMapper.chekUserCode(userCode);
		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(oOpenWater.getSeatId());
		DgConsumerSeat tseat = dgConsumerSeatMapper.getConsumerSeatById(tOpenWater.getSeatId());
		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 8, oOpenWater.getOwNum(), 
				seat.getName(), "操作客位:"+seat.getName()+",做了单品转台操作,转入客位:"+tseat.getName(),new SimpleDateFormat("yyyy_MM").format(new Date())));	
		return ret;
	}

	private List<List<Map>> convertListMap(List<Map> orgs) {
		List<List<Map>> ret = new ArrayList<List<Map>>();
		Map<Integer, Integer> mapCount = new HashMap<Integer, Integer>();
		for (Map o : orgs) {
			int itemId = (int) o.get("itemFileId");
			if (mapCount.containsKey(itemId)) {
				mapCount.put(itemId, mapCount.get(itemId).intValue() + 1);
			} else {
				mapCount.put(itemId, 1);
			}
		}
		int max = 0;
		for (Map.Entry<Integer, Integer> entry : mapCount.entrySet()) {
			if (max < entry.getValue()) {
				max = entry.getValue();
			}
		}
		for (int i = 0; i < max; i++) {
			List<Map> items = new ArrayList<Map>();
			Iterator<Map> re = orgs.iterator();
			while (re.hasNext()) {
				Map rr = re.next();
				boolean hava = false;
				for (Map item : items) {
					if (((int) item.get("itemFileId") == (int) rr.get("itemFileId"))&& !rr.containsKey("insertMap")) {
						hava = true;
						break;
					}
				}
				if (!hava) {
					rr.put("insertMap",1);
					items.add(rr);
				}
			}
			ret.add(items);
		}
		return ret;
	}

	
	private List<Map> preModifyDishesTurntable(List<Map> orgs) 
	{
		List<Map> ret = new ArrayList<Map>();
		for(Map org:orgs) {
			if(ret.isEmpty())
			{
				Map map = new HashMap();
				List<Map> child = new ArrayList<Map>();
				map.put("serviceId",org.get("serviceId"));
				map.put("serviceType",billMapper.selectServerTypeByServerId((int)org.get("serviceId")));
				child.add(org);
				map.put("child",child);
				ret.add(map);
			}
			else
			{
				boolean havaServer = false;
				for(Map r:ret)
				{
					if((int)r.get("serviceId") == (int)org.get("serviceId"))
					{
						List<Map> child = (List<Map>) r.get("child");
						child.add(org);
						havaServer = true;
						break;
					}
				}
				if(!havaServer)
				{
					Map map = new HashMap();
					List<Map> child = new ArrayList<Map>();
					map.put("serviceId",org.get("serviceId"));
					map.put("serviceType",billMapper.selectServerTypeByServerId((int)org.get("serviceId")));
					child.add(org);
					map.put("child",child);
					ret.add(map);
				}
			}
		}
		return ret;
	}
	@Override
	public void modifyCancelPayState(String owNum) {
		Map<String, Object> map = new HashMap();
		map.put("owNum", owNum);
		map.put("state", 1);
		apiReMoMapper.modifyOpenWaterState(map);

        DgOpenWater dgOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);

		map.clear();
		map.put("state",2);
		map.put("id",dgOpenWater.getSeatId());
        paySettlementMapper.modifySeatState(map);
        DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(dgOpenWater.getSeatId());

        OnlineHttp.onlineSeatModify(seat.getUuidKey(), 2+"");

    }

	@Override
	public List<DgOpenWater> selectAllLockedData() {
		return apiReMoMapper.selectAllLockedData();
	}

	@Override
	public void modifyManualLocking(DgOpenWater dgOpenWater, String userCode,
			Integer state, Integer pos) {
        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectOpenwaterByTeamNum(dgOpenWater.getTeamMembers());
        for(DgOpenWater dgOpenWater1:dgOpenWaters){
            Map<String, Object> map = new HashMap();
            map.put("owNum", dgOpenWater1.getOwNum());
            map.put("state", state);
            map.put("userCode", userCode);
            map.put("pos", pos);
            apiReMoMapper.modifyOpenWaterState(map);
            apiCheckServiceMapper.insertLockLog(map);
        }
	}

	@Override
	public void modifyUnlock(DgOpenWater dgOpenWater, Integer type) {
        List<DgOpenWater> teamOpenWaters = apiCheckServiceMapper.selectOpenwaterByTeamNum(dgOpenWater.getTeamMembers());
        for(DgOpenWater dgOpenWater1:teamOpenWaters){
            Map<String, Object> map = new HashMap();
            map.put("owNum", dgOpenWater1.getOwNum());
            map.put("state", 1);
            if(type == 8){//解除手工锁定
                apiReMoMapper.modifyUnlock(map);
            }else if(type == 9){//解除结算锁定
                if(dgOpenWater1.getClearingWaterId() != null){//该营业流水被埋单
                    map.put("state",3);
                    if(dgOpenWater1.getTransferTarget() != null){//该营业流水是转账的营业流水
                        map.put("state",5);
                    }
                }
                apiReMoMapper.modifyUnlock(map);
            }
        }
	}

	@Override
	public void updateOWCustomerInfo(Map openWaterInfo, String customerData) {
		Gson gson = new Gson();
		List<Map> maps = gson.fromJson(customerData, List.class);

		Map<String, Object> map = new HashMap();
		map.put("owId", Integer.parseInt(openWaterInfo.get("id").toString()));
		map.put("list", maps);
		apiReMoMapper.updateOWCustomerInfo(map);
	}

    /**
	 * 验证单个退单数据正确性
	 *
	 * @param openNumber
	 * @param org
	 * @return
	 */
	private Map<String, Object> checkBackBill(String openNumber, List<Map> org) {
		// TODO Auto-generated method stub
		Map<String, Object> back = new HashMap<String, Object>();
		List<Map<String, Object>> ret = billMapper
				.selectItemByWater(openNumber);
		Iterator<Map<String, Object>> re = ret.iterator();
		while (re.hasNext()) {
			Map sorg = new HashMap();
			Map<String, Object> r = re.next();
			sorg.put("owId", r.get("ow_id"));
			sorg.put("itemFileId", r.get("item_file_id"));
			Double surplusItemNumber = billMapper
					.selectConsumerDetailByOwId(sorg);
			if (surplusItemNumber != null && surplusItemNumber > 0) {
				r.put("item_file_number", surplusItemNumber);
			} else if (surplusItemNumber != null && surplusItemNumber == 0) {
				re.remove();
			}
		}

		for (Map<String, Object> o : org) {
			int owId = (new Double("" + o.get("serviceId"))).intValue();
			double itemFileNumber = Double.valueOf("" + o.get("itemFileNum"));
			int itemFileId = (int) o.get("itemFileId");
			for (Map<String, Object> m : ret) {
				int mOwId = (int) m.get("ow_id");
				double mItemFileNumber = Double.valueOf(""
						+ m.get("item_file_number"));
				int mItemFileId = (int) m.get("item_file_id");
				if (owId == mOwId && itemFileId == mItemFileId) {
					if (itemFileNumber > mItemFileNumber) {
						back.put("error", APIEnumDefine.M0101012);
					}
					break;
				}
			}
			if (back.containsKey("error")) {
				break;
			}
		}
		back.put("syNumber", ret);
		return back;
	}

	/*
	 * 
	 * itemId套餐id number套餐数量 serviceId 服务号id
	 */
	private void insertTc(int itemId, double number, int serviceId, String notes) {
		DgItemFilePackage pk = dgItemFilePackageMapper
				.selectByItemFileId(itemId);
		if (pk != null) {
			List<Map> bx = dgItemFilePackageBxMapper.selectByPackageId(pk
					.getId());
			{
				for (Map b : bx) {
					if (b.get("item_file_id") == null
							|| b.get("item_amount") == null) {
						continue;
					}
					DgItemFile tItemFile = dgItemFileMapper
							.selectByPrimaryKey((int) b.get("item_file_id")); // 品项是否存在
					DgOwConsumerDetails detail = new DgOwConsumerDetails();
					detail.setItemFileId(tItemFile.getId());
					if (notes.equals("4") || notes.equals("5")) { // 退单
						detail.setItemFileNumber(-number
								* (int) b.get("item_amount"));
					} else { // 开单自增品项/加单/赠单
						detail.setItemFileNumber(number
								* (int) b.get("item_amount"));
					}
					detail.setNotes(notes);
					detail.setIsTc(-1);
					detail.setParentId(itemId);
					detail.setOwId(serviceId);
					detail.setItemFinalPrice(0.0); // 套餐内价格都设置为0
					detail.setSubtotal(0.0);
					DgItemSaleLimit li = dgItemSaleLimitMapper
							.selectByDate(formatDate.format(new Date()));
					if (li != null) {
						DgItemSaleLimitS lis = new DgItemSaleLimitS();
						if (notes.equals("4") || notes.equals("5")) { // 退单
							lis.setUseCount(-number
									* (int) b.get("item_amount"));
						} else { // 开单自增品项/加单/赠单
							lis.setUseCount(number * (int) b.get("item_amount"));
						}
						lis.setLimitId(li.getId());
						lis.setItemId(tItemFile.getId());
						dgItemSaleLimitSMapper.updateCount(lis);
					}
					// 插入明细表
					dgOwConsumerDetailsMapper.insertSelective(detail);

				}
			}
		}
	}
}