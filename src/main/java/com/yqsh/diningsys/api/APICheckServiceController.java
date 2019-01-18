package com.yqsh.diningsys.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.model.VariablePrice;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.api.util.DateTimeComputing;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.deskBusiness.DBSBillServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DBSSeetServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.DgWaterCouponService;
import com.yqsh.diningsys.web.service.api.PaySettlementService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;
import com.yqsh.diningsys.web.service.pay.DgPayInterface;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.util.OnlineHttp;
import com.yqsh.diningsys.web.util.TableQueryUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 账单服务接口
 *
 * @author zhshuo create on 2016-12-06 9:59
 */
@RequestMapping("/yqshapi/checkService")
@Controller
@SuppressWarnings("all")
public class APICheckServiceController extends ApiBaseController {

	private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); // 年月日
	
    @Autowired
    private APICheckService apiCheckService;

    @Autowired
    private BusinessPermissionService businessPermissionService;

    @Autowired
    private DgSeatManagerService dgSeatManagerService;

    @Autowired
    private DgCurrentOpenWaterService dgCurrentOpenWaterService;

    @Autowired
    private BillService billService;

    @Autowired
    private DgConsumerSeatService dgConsumerSeatService;

    @Autowired
    private DeskBusinessSettingService deskBusinessSettingService;

    @Autowired
    private PaySettlementService paySettlementService;

    @Autowired
    private DgPayInterface dgPayInterface;

    @Autowired
    private DgPosService dgPosService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysAuthorizationLogService sysAuthorizationLogService;

    @Autowired
    private DgUrlSettingService dgUrlSettingService;

    @Autowired
    private DgWaterCouponService dgWaterCouponService;
    /**
     * 先调用checkSeatManyOpenWater接口进行是否有多个营业流水判断<br>
     * 用户进行拆账之前的操作<br>
     * 判断该客位的营业流水下是否有品项<br>
     * 判断该营业流水是否为埋单操作<br>
     * 成功返回该营业流水下的所有品项
     * @return
     */
    @RequestMapping("/selectOpenWaterInfoBySeat")
    @ResponseBody
    @ApiOperation(value = "根据营业流水返回该营业流水下的所有有效品项", httpMethod = "POST", notes = "根据营业流水返回该营业流水下的所有有效品项")
    public Object selectOpenWaterInfoBySeat(
            @ApiParam(required = true, name = "owNum", value = "待判断的营业流水") @RequestParam(value = "owNum", required = true) String owNum){
        try {
            List<DgOwConsumerDetails> mapList =  apiCheckService.selectOpenWaterWithService(owNum);
            if(mapList.size() == 0){
                return getResult(APIEnumDefine.S997);
            }

            Map openWaterInfoMap = apiCheckService.selectOpenWaterByOwNum(owNum);
            if(openWaterInfoMap.get("ow_state").equals(3)){
                return getResult(APIEnumDefine.S996);
            }
            if(openWaterInfoMap.get("ow_state").equals(8)){
                return getResult(APIEnumDefine.S994);
            }
            return getSuccessResult(mapList);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 使用转账、拆账、拆分品项、挂S账功能前，检测该客位是否存在有多个营业流水
     *
     * @param seatId 客座号码
     * @return owNum为空返回M0202001，客座存在品项返回S001，无品项返回M0202002
     */
    @RequestMapping(value = "/checkSeatManyOpenWater", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "使用转账、拆账以及拆分品项功能前，检测该客位是否存在有多个营业流水", httpMethod = "POST", notes = "检测该客位是否存在有多个营业流水")
    public Object checkSeatManyOpenWater(
            @ApiParam(required = true, name = "seatId", value = "客位ID") @RequestParam(value = "seatId", required = true) String seatId) {
        try {
            List<Map> owMap = apiCheckService.selectOpenWaterBySeatId(seatId);
            if (owMap.size() == 0) {
                return getResult(APIEnumDefine.S998);
            }

            if(owMap.size() > 1){
                return getResult(APIEnumDefine.M0202006,owMap);
            }else{
                return getResult(APIEnumDefine.M0202007,owMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 根据营业流水确认该营业流水下是否存在品项<br>
     * 判断该营业流水的状态是否为埋单，埋单拒绝操作<br>
     * 确认该客座能转账之后，获取当前团队数据<br>
     * @param owNum 营业流水
     * @return 调用成功，返回当前的团队数据
     */
    @RequestMapping(value = "/selectTransferTeam", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取团队数据", httpMethod = "POST", notes = "获取团队数据")
    public Object selectTransferTeam(@ApiParam(required = true, name = "owNum", value = "待判断的营业流水") @RequestParam(value = "owNum") String owNum,
                                     @ApiParam(required = true, name = "pos", value = "pos ID") @RequestParam(value = "pos") Integer pos) {
        try {
            List<Map> mapList = apiCheckService.selectSeatHasItem(owNum);
            if(mapList.size() == 0){
                return getResult(APIEnumDefine.M0202002);
            }

            Map openWaterInfoMap = apiCheckService.selectOpenWaterByOwNum(owNum);
            if(openWaterInfoMap.get("ow_state").equals(3)){
                return getResult(APIEnumDefine.M0202003);
            }
            if(openWaterInfoMap.get("ow_state").equals(8)){
                return getResult(APIEnumDefine.S994);
            }
            return getSuccessResult(billService.selectAllTeamMember(pos));
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    @ResponseBody
    @RequestMapping("/selectOpenWaterByOwNum")
    @ApiOperation(value = "根据营业流水获取营业流水详细信息", httpMethod = "POST", notes = "根据营业流水获取营业流水详细信息")
    public Object selectOpenWaterByOwNum(
            @ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum){
        try {
            Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
            return getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 根据营业流水获取该营业流水下的所有品项信息<br>
     * 包含了向该客位转账的营业流水数据<br>
     */
    @ResponseBody
    @RequestMapping("/selectAllDetails")
    @ApiOperation(value = "根据营业流水获取该营业流水下的所有品项信息,包含了向该客位转账的营业流水数据", httpMethod = "POST", notes = "根据营业流水获取该营业流水下的所有品项信息,包含了向该客位转账的营业流水数据")
    public Object selectAllDetails(
            @ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum){
        try {
            Map map = apiCheckService.selectAllDetails(owNum);
            return getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************转账操作************************************************/

    /**
     * 进行转账操作<br>
     * 判断待转账的营业流水和转账目标的营业流水是否相同<br>
     * 判断待转账的营业流水下是否已经存在转账信息，如果有，不给予转账操作。<br>
     * 如果待转账的营业流水是非团队开单，则将该营业流水的团队号码修改为转账目标的团队号码，<br>
     * 并将该营业流水的状态修改4（代表该营业流水已经转账但未结算或者埋单）<br>
     * 如果待转账的营业流水是团队开单，于非团队开单的营业流水操作步骤一样，修改该团队的团队主客位<br>
     * 转账成功，将 targetOwNum 的seat_amount加上转账的数量
     * @param transferOwNum         待转账的营业流水号
     * @param targetOwNum           转账目标的营业流水号
     * @param authCode           授权码
     * @return
     */
    @RequestMapping("/seatTransfer")
    @ResponseBody
    @ApiOperation(value = "进行转账操作", httpMethod = "POST", notes = "进行转账操作")
    public Object seatTransfer(
            @ApiParam(required = true, name = "userCode", value = "前台登录的用户") @RequestParam(value = "userCode", required = true) String userCode,
            @ApiParam(required = true, name = "transferOwNum", value = "待转账的营业流水号") @RequestParam(value = "transferOwNum", required = true) String transferOwNum,
            @ApiParam(required = true, name = "targetOwNum", value = "转账目标的营业流水号") @RequestParam(value = "targetOwNum", required = true) String targetOwNum,
            @ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
        try {
            DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(transferOwNum);
            if(dgOpenWater == null){
                return getResult(APIEnumDefine.S996);
            }

            if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
                return getResult(APIEnumDefine.S988);
            }

            if(dgOpenWater.getOwState() == 8){
                return getResult(APIEnumDefine.S988);
            }

            if(dgOpenWater.getOwState() == 9){
                return getResult(APIEnumDefine.S993);
            }

            //2017年7月27日13:18:29 取消0元不能转账的限制
            /*List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithService(transferOwNum);
            if(dgOwConsumerDetailss.size() < 1){
                return getResult(APIEnumDefine.M0202014);
            }*/

            if(StringUtils.isEmpty(authCode)){//授权码为空
                SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);
                if(sysPerBusiness == null || sysPerBusiness.getZzQx() == null){
                    return getResult(APIEnumDefine.M0202001);
                }
            }else{
                SysUser sysUser = userService.selectUserByAuthCode(authCode);
                if(sysUser == null){
                    return getResult(APIEnumDefine.S990);
                }
                SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"transfer");
                if(sysRoleMenu == null){
                    return getResult(APIEnumDefine.S989);
                }
                sysAuthorizationLogService.insertAuthLog(
                        new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"转账操作")
                );
            }

            Map map1 = apiCheckService.selectOpenWaterByOwNum(targetOwNum);

            if(map1 == null){
                return getResult(APIEnumDefine.M0202012);
            }

            //判断待转账的营业流水和转账目标的营业流水是否相同
            if (transferOwNum.equalsIgnoreCase(targetOwNum))
                return getResult(APIEnumDefine.M0202004);

            //判断待转账的营业流水下是否已经存在转账信息，如果有，不给予转账操作。
            //2017年7月22日03:22:47  修改待转账的营业流水下如果存在转账信息，给予转账操作
            List<DgOpenWater> transferWater = new ArrayList<>();

            if(dgOpenWater.getTransferTarget() == null){//如果选中的营业流水为转账的主流水
                List<DgOpenWater> maps = apiCheckService.selectOpenWaterByTransferNum(transferOwNum);
                transferWater.addAll(maps);
            }else {//如果选中的营业流水为转账的子流水
                if(dgOpenWater.getTransferTarget() != null){
                    transferOwNum = dgOpenWater.getTransferTarget();
                    List<DgOpenWater> maps = apiCheckService.selectOpenWaterByTransferNum(dgOpenWater.getTransferTarget());
                    transferWater.addAll(maps);
                }
            }
            /*
            if (maps != null && maps.size() > 0) {
                return getResult(APIEnumDefine.M0202005);
            }*/

            Map openWaterInfoMap = apiCheckService.selectOpenWaterByOwNum(transferOwNum);
            Integer teamFlag = (Integer) openWaterInfoMap.get("is_team");
            //teamFlag=0转账的营业流水为非团队/teamFlag=1转账的营业流水为团队
            apiCheckService.updateOpenWaterTransfer(userCode,transferOwNum,targetOwNum,teamFlag,transferWater);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************拆账操作****************************************************/

    /**
     * 拆账操作，客户端提供标准的json字段，服务端判断是否创建多个流水<br>
     * @param splitItemData 其中json字段定义如下，itemFileId：品项ID，itemFileNum：品项数量,serviceId:服务ID
     *
     * <br>数据格式：[{"itemFileId":品项ID,"itemFileNum":品项数量,"serviceId":服务ID}]
     * @return
     */
    @RequestMapping("/splitOpenWater")
    @ResponseBody
    @ApiOperation(value = "拆账操作", httpMethod = "POST", notes = "拆账操作")
    public Object splitOpenWater(
            @ApiParam(required = true, name = "userCode", value = "前台登录的用户") @RequestParam(value = "userCode", required = true) String userCode,
            @ApiParam(required = true, name = "splitItemData", value = "客户端传入的是否创建多个流水的josn数据") @RequestParam(value = "splitItemData", required = true) String splitItemData,
            @ApiParam(required = true, name = "openPos", value = "开单POS") @RequestParam(value = "openPos", required = true) String openPos,
            @ApiParam(required = true, name = "owNum", value = "需要进行拆账的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
            @ApiParam(required = false, name = "token", value = "token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode){
        try {
            DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
            if(dgOpenWater == null){
                return getResult(APIEnumDefine.S998);
            }
            if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
                return getResult(APIEnumDefine.S996);
            }
            if(dgOpenWater.getOwState() == 9){
                return getResult(APIEnumDefine.S993);
            }
            if(dgOpenWater.getOwState() == 8){
                return getResult(APIEnumDefine.S988);
            }

            if(!StringUtils.isEmpty(authCode)){
                SysUser sysUser = userService.selectUserByAuthCode(authCode);
                if(sysUser == null){
                    return getResult(APIEnumDefine.S990);
                }
                SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"closing");
                if(sysRoleMenu == null){
                    return getResult(APIEnumDefine.S989);
                }
                sysAuthorizationLogService.insertAuthLog(
                        new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"拆账操作")
                );
            }else{
                SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);
                if(sysPerBusiness == null || sysPerBusiness.getCzQx() == null){
                    return getResult(APIEnumDefine.M0202008);
                }
            }

            Gson gson = new Gson();
            List<List<VariablePrice>> splitOpenWaterInfos = gson.fromJson(splitItemData,new TypeToken<List<List<VariablePrice>>>(){}.getType());

            if(splitOpenWaterInfos.size() < 1){
                return getResult(APIEnumDefine.M0202013);
            }

            List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithServiceTC(owNum);

            apiCheckService.createOpenWaterWithSplit(userCode,dgOwConsumerDetailss,owNum,openPos,token,splitOpenWaterInfos);
            return getSuccessResult();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************拆账操作 END************************************************/

    /************************************拆分品项****************************************************/

    /**
     * 拆分品项操作，先调用checkSeatManyOpenWater接口检测该客位是否存在多个营业流水<br>
     * 传入具体的营业流水，调用selectOpenWaterInfoBySeat接口，返回该营业流水下面的所有有效品项<br>
     * 用户操作完成之后，调用此接口，进行拆分品项操作<br>
     * @param userCode 前台登录的用户code
     * @param splitItemData 客户端传入的拆分品项的json数据
     *                      数据实例：[{"itemFileId":14,"itemFileAmount":1,"serviceId":39}]
     * @param owNum 需要进行拆分品项操作的营业流水
     * @param token
     * @return
     */
    @RequestMapping("/splitItem")
    @ResponseBody
    @ApiOperation(value = "拆分品项操作", httpMethod = "POST", notes = "拆分品项操作")
    public Object splitItem( @ApiParam(required = true, name = "userCode", value = "登录的用户") @RequestParam(value = "userCode", required = true) String userCode,
                             @ApiParam(required = true, name = "splitItemData", value = "客户端传入的拆分品项的json数据") @RequestParam(value = "splitItemData", required = true) String splitItemData,
                             @ApiParam(required = true, name = "owNum", value = "需要进行拆分品项操作的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
                             @ApiParam(required = false, name = "token", value = "token") @RequestParam(value = "token", required = false) String token){
        try {
            DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
            if(dgOpenWater == null){
                return getResult(APIEnumDefine.S998);
            }
            if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
                return getResult(APIEnumDefine.S996);
            }
            if(dgOpenWater.getOwState() == 9){
                return getResult(APIEnumDefine.S993);
            }
            if(dgOpenWater.getOwState() == 8){
                return getResult(APIEnumDefine.S988);
            }

            Gson gson = new Gson();

            List<Map> splitItemDatas = gson.fromJson(splitItemData,List.class);

            apiCheckService.editServiceItemData(owNum,token,splitItemDatas);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }

    }

    /************************************拆分品项 END************************************************/


    /************************************关账操作************************************************/
    /**
     * 根据座位查询待关账的营业流水<br>
     * @param seatId 待关账的营业流水座位ID
     * @return  zhshuo
     */
    @RequestMapping("/selectCloseBillOpenWater")
    @ResponseBody
    @ApiOperation(value = "根据座位查询待关账的营业流水", httpMethod = "POST", notes = "根据座位查询待关账的营业流水")
    public Object selectCloseBillOpenWater(
            @ApiParam(required = true, name = "seatId", value = "待关账的营业流水座位ID") @RequestParam(value = "seatId", required = true) Integer seatId){
    	List<Map<String,Object>> mapList = apiCheckService.selectCloseBillOpenWater(seatId);
    	if (!mapList.isEmpty()) {
			for (int i = 0; i < mapList.size(); i++) {
				//格式化时间
				Object object = mapList.get(i).get("firstTime");
				if (object != null) {
					String firstTime = mapList.get(i).get("firstTime").toString();
					mapList.get(i).put("firstTime",
							firstTime.substring(0, firstTime.length() - 2));
				}
			}
		}else{
			return getResult(APIEnumDefine.S005);
		}
        return getSuccessResult(mapList);
    }
    
    /**
     * 进行关账操作<br>
     * @param owNum 待关账的营业流水号
     * @return
     */
    @RequestMapping("/checkCloseBillOpenWater")
    @ResponseBody
    @ApiOperation(value = "进行关账操作", httpMethod = "POST", notes = "进行关账操作")
    public Object checkCloseBillOpenWater(
            @ApiParam(required = true, name = "owNum", value = "待关账的营业流水号") @RequestParam(value = "owNum", required = true) String owNum,
            @ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode,
            @ApiParam(required = true, name = "userCode", value = "前台登录的用户") @RequestParam(value = "userCode") String userCode){
        if(!StringUtils.isEmpty(authCode)){
            SysUser sysUser = userService.selectUserByAuthCode(authCode);
            if(sysUser == null){
                return getResult(APIEnumDefine.S990);
            }
            SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"closing");
            if(sysRoleMenu == null){
                return getResult(APIEnumDefine.S989);
            }
            sysAuthorizationLogService.insertAuthLog(
                    new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"关账操作")
            );
        }

        //判断当前营业流水是否存在转账流水
    	List<DgOpenWater> transferNumList = apiCheckService.selectOpenWaterByTransferNum(owNum);
    	//（营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账-1为关账）
    	//("3".equals(owstate) || "4".equals(owstate) || "5".equals(owstate))
    	if(transferNumList.size() > 0){
    		 return getResult(APIEnumDefine.M0207001);
    	}
    	//查询该营业流水下的有效品项，不包括赠送的品项，按照服务单汇总
    	if(apiCheckService.modifyCheckOwNumHasItemWithOutJson(owNum) == null){
   		     return getResult(APIEnumDefine.M0207002);
    	}
    	//判断当前营业流水押金状态
    	List<Map<String,Object>> cashPledMapList0 = apiCheckService.checkCashPledgeOpenWater("0",owNum); //交押金
    	List<Map<String,Object>> cashPledMapList1 = apiCheckService.checkCashPledgeOpenWater("1",owNum); //退押金
    	Double cpMoney0 = 0.0;
    	Double cpMoney1 = 0.0;
    	if (!cashPledMapList0.isEmpty()) {
			for (int i = 0; i < cashPledMapList0.size(); i++) {
				Map<String, Object> cashPledMap = cashPledMapList0.get(i);
				cpMoney0 += Double.parseDouble(cashPledMap.get("cp_money")+"");
			}
		}
    	if (!cashPledMapList1.isEmpty()) {
			for (int i = 0; i < cashPledMapList1.size(); i++) {
				Map<String, Object> cashPledMap = cashPledMapList1.get(i);
				cpMoney1 += Double.parseDouble(cashPledMap.get("cp_money")+"");
			}
		}
    	if(cpMoney0 - cpMoney1 > 0){
    		return getResult(APIEnumDefine.M0207003);
    	}
    	//判断条件通过进行关账处理
    	try {
			//设置流水号为关账状态
			apiCheckService.closeBillForOpenWater(userCode,-1,owNum);
			//设置客位为空闲状态（客位状态。1空闲、2占用、3清扫、4预定、5埋单）
			Map<String,Object> closeBillMap = apiCheckService.checkCloseBillOpenWater(owNum);
			String seatId = closeBillMap.get("seatId")+"";
			List<Map<String,Object>> mapList = apiCheckService.selectCloseBillOpenWater(Integer.parseInt(seatId));
			if(mapList.isEmpty()){
				apiCheckService.closeBillForSeat(1,seatId);
			}
            DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(seatId));

            OnlineHttp.onlineSeatModify(seat.getUuidKey(), 1+"");
      		
        } catch (Exception e) {
			e.printStackTrace();
			return getFailResult();
		}
    	
		return getSuccessResult();
    }
    
    /************************************关账操作 END************************************************/


    /************************************续单操作****************************************************/

    /**
     * 续单之前的操作，查询出该客位之前的所有有效的已经结算的流水
     * 如果没有，拒绝续单操作
     * @param seatId
     * @return
     */
    @RequestMapping("/checkSeatAllCloseWater")
    @ResponseBody
    @ApiOperation(value = "续单之前的操作", httpMethod = "POST", notes = "续单之前的操作")
    public Object checkSeatAllCloseWater(
            @ApiParam(required = true, name = "seatId", value = "待续单的客位的id") @RequestParam(value = "seatId", required = true) String seatId){
        List<Map> maps =  apiCheckService.selectSeatClosedWater(Integer.parseInt(seatId));
        if(maps.size() < 1){
            return getResult(APIEnumDefine.M0202009);
        }
        return getSuccessResult(maps);
    }

    /**
     * 续单操作，传入需要续单的结算流水号码
     * @return
     */
    @RequestMapping("/continuedCheck")
    @ResponseBody
    @ApiOperation(value = "续单操作，传入需要续单的结算流水号码", httpMethod = "POST", notes = "续单之前的操作")
    public Object continuedCheck(
                                 @ApiParam(required = true, name = "clearingWater", value = "需要续单的结算流水号码") @RequestParam(value = "clearingWater", required = true) String clearingWater) {
        try {
           apiCheckService.modifyContinuedCheck(clearingWater);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************续单操作 END************************************************/

    /************************************挂S账操作***************************************************/

    /**
     * 进行挂S账操作之前，先根据客户端传入的客座号，调用checkSeatManyOpenWater接口，判断该客位账单的营业
     * 流水是一个还是多个<br>
     * 得到客户端传入的具体营业流水之后，先判断该营业流水的状态，如果为埋单状态，不给予挂S账操作。<br>
     * @return
     */
    @RequestMapping("/hangingSBill")
    @ResponseBody
    @ApiOperation(value = "挂S账操作", httpMethod = "POST", notes = "挂S账操作")
    public Object hangingSBill(@ApiParam(required = true, name = "owNum", value = "需要挂S账的营业流水") @RequestParam(value = "owNum") String owNum,
                               @ApiParam(required = true, name = "userCode", value = "前台登录用户") @RequestParam(value = "userCode") String userCode,
                               @ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode){
        try {

            if(!StringUtils.isEmpty(authCode)){
                SysUser sysUser = userService.selectUserByAuthCode(authCode);
                if(sysUser == null){
                    return getResult(APIEnumDefine.S990);
                }
                SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"hung_s_accounts");
                if(sysRoleMenu == null){
                    return getResult(APIEnumDefine.S989);
                }
                sysAuthorizationLogService.insertAuthLog(
                        new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"挂S账操作")
                );
            }else{
                SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);

                if(sysPerBusiness == null){
                    return getResult(APIEnumDefine.M0101003);
                }

                Integer pdPx = sysPerBusiness.getPdPx();

                if(pdPx == null || pdPx != 1){
                    return getResult(APIEnumDefine.M0202015);
                }
            }

            Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
            if(map == null){
                return getResult(APIEnumDefine.S998);
            }

            if(map.get("ow_state").equals(3)){
                return getResult(APIEnumDefine.M0202003);
            }
            if(map.get("ow_state").equals(8)){
                return getResult(APIEnumDefine.S994);
            }
            apiCheckService.modifyHangingSBill(userCode,owNum);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************挂S账操作 END************************************************/

    /************************************查看S账操作 END************************************************/
    /**
     * 查询出所有的S帐<br>
     * 如果没有数据，则返回S005
     * @return
     */
    @RequestMapping("/showSBillList")
    @ResponseBody
    @ApiOperation(value = "查看S账操作", httpMethod = "POST", notes = "查看S账操作")
    public Object showSBillList(){
        try {
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllSBill();
            if(dgOpenWaters.size() < 1){
                return getResult(APIEnumDefine.S005);
            }
            return getSuccessResult(dgOpenWaters);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 撤销状态为S帐的营业流水
     * @param owNum
     * @return
     */
    @RequestMapping("/cancelSBill")
    @ResponseBody
    @ApiOperation(value = "撤销S帐", httpMethod = "POST", notes = "撤销S帐")
    public Object cancelSBill(@ApiParam(required = true, name = "owNum", value = "需要撤销S帐的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
                              @ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode,
                              @ApiParam(required = true, name = "userCode", value = "前台登录的用户") @RequestParam(value = "userCode") String userCode){
        try {
            if(!StringUtils.isEmpty(authCode)){
                SysUser sysUser = userService.selectUserByAuthCode(authCode);
                if(sysUser == null){
                    return getResult(APIEnumDefine.S990);
                }
                SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"view_s_accounts:cancel");
                if(sysRoleMenu == null){
                    return getResult(APIEnumDefine.S989);
                }
                sysAuthorizationLogService.insertAuthLog(
                        new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"撤销S账")
                );
            }
            apiCheckService.modifyCancelSBill(userCode,owNum);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /************************************查看S账操作 END************************************************/

    /************************************结班操作***************************************************/
    /**
     * 结班操作
     * @return
     */
    @RequestMapping("/openClass")
    @ApiOperation(value = "结班操作", httpMethod = "POST", notes = "结班操作")
    public String openClass(Model model,
                            @ApiParam(required = true, name = "userCode", value = "前台的用户名") @RequestParam(value = "userCode", required = true) String userCode,
                            @ApiParam(required = true, name = "loginPos", value = "用户登录的POS") @RequestParam(value = "loginPos", required = true) Integer loginPos){
        try {
            SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);
            Map map = apiCheckService.selectOpenClassReport(userCode, loginPos);
            model.addAttribute("userCode",userCode);
            model.addAttribute("loginPos",loginPos);
            model.addAttribute("openClassReport",map);
            //结算方式
            List<DgOwClearingway> frequency =  (List<DgOwClearingway>) apiCheckService.selectOpenClassInfo(userCode, loginPos,5);
            model.addAttribute("frequency",frequency);
            return "api/open_class";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }

    @ResponseBody
    @RequestMapping("/bigTypeReport")
    public Object openClassData(Integer loginPos,String userCode){
        try {
            return apiCheckService.selectOpenClassInfo(userCode, loginPos ,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody()
    @RequestMapping("/smallTypeReport")
    public Object smallTypeReport(Integer loginPos,String userCode){
        try {
            return apiCheckService.selectOpenClassInfo(userCode, loginPos,2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/itemFileReport")
    public Object itemFileReport(Integer loginPos,String userCode){
        try {
            return apiCheckService.selectOpenClassInfo(userCode, loginPos,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/memberGZReport")
    public Object memberGZReport(Integer loginPos,String userCode){
        try {
            return apiCheckService.selectOpenClassInfo(userCode, loginPos,4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/clearingWayReport")
    public Object clearingWayReport(Integer loginPos,String userCode){
        try {
            return apiCheckService.selectOpenClassInfo(userCode, loginPos,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/openClassModify")
    @ApiOperation(value = "结班操作", httpMethod = "POST", notes = "结班操作")
    public Object openClassModify(
                                @ApiParam(required = true, name = "userCode", value = "前台登录的用户编码") @RequestParam(value = "userCode", required = true) String userCode,
                                @ApiParam(required = true, name = "loginPos", value = "前台登录的用户POS") @RequestParam(value = "loginPos", required = true) Integer loginPos
                                ){
        try {
            DgPos dgPos = dgPosService.selectPosByPosId(loginPos);
            if(dgPos.getCanJb() == 0){
                return getResult("该POS不能进行结班！");
            }
            DgUrlSetting setting=dgUrlSettingService.selectByCode("IdleClass");
            if(setting != null && setting.getValue() != null && setting.getValue().equals("1")){
                if(dgConsumerSeatService.selectAllUseSeat()>0){
                    return getResult("还有未结算台位,不能进行结班操作");
                }
            }
            apiCheckService.openClassModify(userCode,loginPos);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getFailResult();
        }
    }

    @RequestMapping("/showOpenClassWaterHistory")
    @ResponseBody
    public Object showOpenClassWaterHistory(int pageSize,int pageNum,String time,String userName,int posId){
        try {
            Map map = apiCheckService.selectOpenClassWaterHistory(pageSize,pageNum,time,userName,posId);
            return getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFailResult();
    }


    /************************************结班操作 END************************************************/


    /************************************买单结算相关功能***************************************************/
    /**
     * 1、先调用yqshapi/checkService/selectOpenWaterInfoBySeat接口，得到需要买单结算的客位下有几个营业流水<br>
     * 2、前台传入需要买单结算的营业流水，如果为结算锁定状态，则返回结算锁定的相关信息<br>
     * 3、如果结算的营业流水无转账流水，且没有其他的任何团队成员，返回的信息不包含营业流水列表，
     *    只有该营业流水的具体信息以及该营业流水的具体品项，没有计算过价格的品项需要在进行一次价格的ll计算。<br>
     * 4、如果有多个营业流水需要结算，则返回营业流水列表。<br>
     */
    @ResponseBody
    @RequestMapping("/paySettlementIndex")
    @ApiOperation(value = "买单结算首页面，根据传入的营业流水，返回不同信息", httpMethod = "POST", notes = "买单结算首页面，根据传入的营业流水，返回不同信息")
    public Object paySettlementIndex(
            @ApiParam(required = true, name = "userCode", value = "前台登录的用户编码") @RequestParam(value = "userCode", required = true) String userCode,
            @ApiParam(required = true, name = "owNum", value = "需要买单结算的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
            @ApiParam(required = true, name = "posId", value = "POS的ID") @RequestParam(value = "posId", required = true) Integer posId
            ){
        try {
            Map<String,Object> resMap = new HashMap(); //返回map

            //营业流水基本信息以及状态判断
            Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
            if(map == null){
                return getResult(APIEnumDefine.M0101001);
            }

            Integer owState = Integer.parseInt(map.get("ow_state").toString());

            //传入的营业流水状态为空
            if(owState == null){
                return getResult(APIEnumDefine.M0201001);
            }

            if(owState == 8){
                return getResult(APIEnumDefine.S988);
            }
            if(owState == 9){
                return getResult(APIEnumDefine.S993);
            }
            //END

            //比例优惠、全单优惠
            Double generalProportions = null,singleProportions = null;

            //如果营业流水存在结算流水ID，得到埋单时可能存入的比例优惠以及全单优惠
            DgReceptionClearingWater clearingWater = null;
            if(map.containsKey("clearing_water_id")){
                clearingWater = paySettlementService.selectClearingWaterById(Integer.parseInt(map.get("clearing_water_id").toString()));
                if(clearingWater.getGeneralProportions() != null){
                    generalProportions = clearingWater.getGeneralProportions();
                }
                if(clearingWater.getSingleProportions() != null){
                    singleProportions = clearingWater.getSingleProportions();
                }
            }

            //所有支付方式
            List<DgSettlementWay> allSettlementWay = apiCheckService.selectAllPayWay(userCode);
            resMap.put("allPayWay",allSettlementWay);

            //营业流水座位
            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            //得到该营业流水所有相关团队成员以及向该营业流水转账的数据集合
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(owNum);

            if(dgOpenWaters == null){
                return getResult(APIEnumDefine.S998);
            }

            //自动锁定营业流水为结算锁定
            apiCheckService.modifyOpenWaterLock(dgOpenWaters,userCode,1,posId);

            //用户所属职务的优惠权限数据
            SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode(userCode);

            //前台营业设置的账单权限
            DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
            //账单权限
            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);
            DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    hydzZeroMonry = 0.0,//会员打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterHydzSubtotal = 0.0,//营业流水会员小计价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0,//营业流水品项打折收费类型应收金额
                    openWaterHydzYs = 0.0;//营业流水会员小计收费类型应收金额

            Boolean isAllAdvancePay = false;
            Integer clearingId = null;
            List<Map> map1 = null;
            Map<String,Object> vipInfo = new HashMap<>();
            String hyLevel = null;
            //循环可以进行买单结算的营业流水
            for(DgOpenWater dgOpenWater:dgOpenWaters) {

                /*if(!StringUtils.isEmpty(dgOpenWater.getTransferTarget())){
                    String joinTeamNotes = dgOpenWater.getJoinTeamNotes();
                    dgOpenWater.setTransferInfo("转账，原客位："+joinTeamNotes.split("，")[2].split("：")[1]);;
                }
*/
        		DgPos pos = new DgPos();
        		pos.setId(dgOpenWater.getOpenPos());
        		pos = dgPosService.getPosByID(pos);
        		
                //获取会员信息，2017年8月15日10:01:43  会员短时间没有启用，下面代码不会执行
                if(dgOpenWater.getCrId() != null ){
                    String memberList = OkHttpUtils.memberGetVipAllCopu(dgOpenWater.getCrId());
                    if(memberList != null){
                        Gson gson = new Gson();
                        Map res = (Map)gson.fromJson(memberList, Map.class);
                        if(res.get("msgCode").toString().equalsIgnoreCase("ok")){
                            String bodys = res.get("body").toString();
                            if(!StringUtils.isEmpty(bodys)){
                                Map  vipCardMap = (Map)res.get("body");
                                if(vipCardMap.containsKey("vipCard") && !vipCardMap.get("vipCard").toString().equals("null")){
                                	String vipCard = vipCardMap.get("vipCard").toString();
                                    Map<String,Object> vipMap = gson.fromJson(vipCard,Map.class);
                                    dgOpenWater.setMemberInfo(vipMap);

                                    if(owNum.equals(dgOpenWater.getOwNum())){
                                        hyLevel = vipMap.get("gradId").toString();
                                        map1 = (List<Map>)gson.fromJson(vipMap.get("listModel").toString(), new TypeToken<List<Map>>(){}.getType());
                                        vipInfo.putAll(vipMap);
                                    }
                                }
                            }
                        }
                    }
                }

                if (dgOpenWater.getClearingWaterId() != null) {
                    isAllAdvancePay = true;
                    clearingId = dgOpenWater.getClearingWaterId();
                } else {
                    isAllAdvancePay = false;
                }

                //循环获取所有营业流水下面的所有有效品项按照服务单分组
                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(dgOpenWater.getOwNum(),0);

                //重要活动价格，品项打折价格，会员打折价格
                Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

                //先计算出所有品项的小计
                for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                    //开单没有优惠的品项的三种折扣价格，计算制作费用
                    if(dgOwConsumerDetails.getIsPriceCal() == 0){//只有当品项为未结算状态才计算
                        apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,hyLevel,Integer.valueOf(pos.getOrganization()),generalProportions,singleProportions);

                        //设置优惠的比例
                        if(generalProportions != null){
                            dgOwConsumerDetails.setDiscount(generalProportions);
                        }

                        if (singleProportions != null) {
                            dgOwConsumerDetails.setDiscount(singleProportions);
                        }

                        //每一个营业流水下所有品项三种价格累加
                        standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
                        standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
                        standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getHydzItemCostsSum());
                    }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
                        //如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
                        dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setPxdzItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());

                                dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setZyhdItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());
                            }
                        }

                        if(maps.size() > 0){
                            if(clearingWater != null){
                                if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount()) ;
                                }else{
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                                }
                            }else{
                                standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                            }
                        }
                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                            }else{
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                            }
                        }else{
                            standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                        }

                        if(hyLevel != null){
                            dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                            dgOwConsumerDetails.setHydzItemCostsSum(dgOwConsumerDetails.getSubtotal());
                            standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getSubtotal());
                        }
                    }
                }

                //品项信息
                dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

                dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
                dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
                dgOpenWater.setHydzItemSubtotal(standPriceHydz);

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }
            }

            //获取优惠券
            List<DgWaterCoupon> dgWaterCoupons = dgWaterCouponService.getCouponCountByWaters(dgOpenWaters);
            if(!dgWaterCoupons.isEmpty()){
                for(DgWaterCoupon dgWaterCoupon:dgWaterCoupons){
                    Map couponInfo = JSON.parseObject(dgWaterCoupon.getCouponInfo(),Map.class);
                    String couponCustId = couponInfo.get("id").toString();//消费id
                    String expiryDate = couponInfo.get("expiryDate").toString(); //到期日期
                    Double preAmount = Double.valueOf(couponInfo.get("preAmount").toString()); //打折金额 或 折扣
                    Integer typeId = Integer.valueOf(couponInfo.get("typeId").toString()); //打折类型
                    Integer dictId = Integer.valueOf(couponInfo.get("dictId").toString()); //打折品项类型
                    Integer couponState = Integer.valueOf(couponInfo.get("couponState").toString()); //卡券状态
                    //后台计算常规优惠比例以及全单比例
                    dgOpenWaters = apiCheckService.wxModifyPercentageDiscount(dgOpenWaters,
                            0.0, preAmount,
                            dictId,typeId,couponInfo
                    );
                    BigDecimal yhMoney=null,pxdzYhMoney=null,zyhdYhMoney = null,hyYhMoney=null;
                    if(couponInfo.containsKey("yhjPxdzYhSutotal")){
                        pxdzYhMoney=new BigDecimal(couponInfo.get("yhjPxdzYhSutotal").toString());
                        zyhdYhMoney=new BigDecimal(couponInfo.get("yhjZyhdYhSubtotal").toString());
                        hyYhMoney=new BigDecimal(couponInfo.get("yhjPxdzYhSutotal").toString());
                        dgWaterCoupon.setHyYhSutotal(hyYhMoney);
                        dgWaterCoupon.setPxdzYhSutotal(pxdzYhMoney);
                        dgWaterCoupon.setZyhdYhSutotal(zyhdYhMoney);
                        dgWaterCouponService.updateCouponService(dgWaterCoupon);
                    }
                }
            }

            for(DgOpenWater dgOpenWater:dgOpenWaters) {
/*              小计中取消包房费，前台自+
                openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterHydzSubtotal =  MathExtend.add(MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()),dgOpenWater.getPrivateRoomCost());
*/

                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                openWaterHydzSubtotal =  MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal());
            }

            DgReceptionClearingWater dgReceptionClearingWater = null;

            if (isAllAdvancePay) {
                Map<String, Object> advancePayInfo = paySettlementService.selectAllAdvancePayInfo(clearingId);
                dgReceptionClearingWater = (DgReceptionClearingWater)advancePayInfo.get("clearingData");
                resMap.putAll(advancePayInfo);
            }

            if(!dgWaterCoupons.isEmpty()){
                for(DgWaterCoupon dgWaterCoupon:dgWaterCoupons){
                    Map couponInfo = JSON.parseObject(dgWaterCoupon.getCouponInfo(),Map.class);
                    String couponCustId = couponInfo.get("id").toString();//消费id
                    String expiryDate = couponInfo.get("expiryDate").toString(); //到期日期
                    Double preAmount = Double.valueOf(couponInfo.get("preAmount").toString()); //打折金额 或 折扣
                    Integer typeId = Integer.valueOf(couponInfo.get("typeId").toString()); //打折类型
                    Integer dictId = Integer.valueOf(couponInfo.get("dictId").toString()); //打折品项类型
                    Integer couponState = Integer.valueOf(couponInfo.get("couponState").toString()); //卡券状态
                    //定额优惠
                    if(typeId.equals(0)){
                        Double dlSjxf = apiCheckService.wxGetItemFileTypeTotal(dgOpenWaters,dictId); //计算出类别消费费用
                        Double ye = null;
                        if(dlSjxf.compareTo(preAmount) < 0){
                            ye = dlSjxf;
                            couponInfo.put("yhMoney",dlSjxf);
                        } else {
                            ye = preAmount;
                            couponInfo.put("yhMoney",preAmount);
                        }
                        openWaterZyhdSubtotal = MathExtend.subtract(openWaterZyhdSubtotal, ye);
                        openWaterPxdzSubtotal = MathExtend.subtract(openWaterPxdzSubtotal, ye);
                        openWaterHydzSubtotal = MathExtend.subtract(openWaterHydzSubtotal, ye);

                        BigDecimal yhMoney=null;
                        if(couponInfo.containsKey("yhMoney")){
                            yhMoney=new BigDecimal(couponInfo.get("yhMoeny").toString());
                            dgWaterCoupon.setYhmoney(yhMoney);
                            dgWaterCoupon.setHyYhSutotal(yhMoney);
                            dgWaterCoupon.setPxdzYhSutotal(yhMoney);
                            dgWaterCoupon.setZyhdYhSutotal(yhMoney);
                            dgWaterCouponService.updateCouponService(dgWaterCoupon);
                        }
                    }
                }
            }

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!dbsBillServDTO.getNoSmallChangeWay().equals("0")){
                if(dbsBillServDTO.getNoSmallChangeWay().equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);
                        hydzZeroMonry = setZeroValue(openWaterHydzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                        openWaterHydzYs = setYsje(openWaterHydzSubtotal);
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdfive(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdfive(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdten(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdten(openWaterHydzSubtotal));
                    }
                }else if(dbsBillServDTO.getNoSmallChangeWay().equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdy(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd5y(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd10y(openWaterHydzSubtotal));
                    }
                }else if(dbsBillServDTO.getNoSmallChangeWay().equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdy(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd5y(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd10y(openWaterHydzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
                openWaterHydzYs = MathExtend.add(openWaterHydzYs,openWaterHydzSubtotal);
            }

            //2018-12-24 更新品项小计
            for(DgOpenWater dow:dgOpenWaters){
                apiCheckService.updateOpenWaterSubtotal(dow);
            }

            //营业流水信息集合
            resMap.put("openWaters",dgOpenWaters);

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 1)){
                //3种价格合计
                resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
                resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
//            resMap.put("openWaterPxdzYs", MathExtend.subtract(returnValue(openWaterPxdzYs),returnValue(pxdzZeroMonry)));
                resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs));
            }

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 2)){
                if(maps.size() > 0){
                    resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                    resMap.put("zyhdZeroMonry",zyhdZeroMonry);
//                resMap.put("openWaterZyhdYs",MathExtend.subtract(openWaterZyhdYs,returnValue(zyhdZeroMonry)));
                    resMap.put("openWaterZyhdYs",openWaterZyhdYs);
                }
            }

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 3)){
                //会员打折价格
                if(hyLevel != null){
                    Double subtract = MathExtend.subtract(openWaterHydzYs, returnValue(hydzZeroMonry));
                    resMap.put("hydzZeroMonry",hydzZeroMonry);
                    resMap.put("openWaterHydzYs",subtract);
                    resMap.put("openWaterHydzSubtotal",openWaterHydzSubtotal);
//                    //优惠券金额计算
//                    List<Map> maps1 = apiCheckService.modifyCouponMoney(map1, subtract, dgOpenWaters);
//                    resMap.put("memberCardList",maps1);
//                    resMap.put("vipInfo",vipInfo);
                }
            }

            //优惠权限相关
            resMap.put("sysPerDiscount",sysPerDiscount);
            resMap.put("otherSetting",dbsBillServDTO);

            //判断客座标签是否自动显示在结算备注里面
            String seatLabel = openWaterSeat.getSeatLabel();
            //自动显示
            if(seatLabel != null && dbsSeetServDTO.getIsAutoInsertDeskLabelToSettlementRemarks().equals("1")){
                resMap.put("autoClearingNotes",seatLabel);
            }
            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 买单结算基本功能
     * @return
     */
    @ResponseBody
    @RequestMapping("/paySettlement")
    @ApiOperation(value = "买单结算基本功能", httpMethod = "POST", notes = "买单结算基本功能")
    public Object paySettlement(@ApiParam(required = true, name = "owNum", value = "需要买单结算的营业流水") @RequestParam(value = "owNum", required = true) String owNum){
        Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
        if(map == null){
            return getResult(APIEnumDefine.M0101001);
        }

        if(map.containsKey("transfer_target")){
            return getResult(APIEnumDefine.M0101001);
        }
        return getSuccessResult();
    }

    /**
     * 验证定额优惠、比例优惠以及全单优惠的授权码
     * @param sqType 1、定额优惠，2、比例优惠，3、全单优惠
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkYhPermisssion")
    public Object checkYhPermisssion(@ApiParam(required = true, name = "sqType", value = "授权类型") @RequestParam(value = "sqType") Integer sqType,
                                     @ApiParam(required = true, name = "sqm", value = "授权码") @RequestParam(value = "sqm") String sqm,
                                     @ApiParam(required = true, name = "value", value = "具体值") @RequestParam(value = "value") String value,
                                     @ApiParam(required = true, name = "userCode", value = "操作人") @RequestParam(value = "userCode") String userCode){
        BigDecimal val = new BigDecimal(value);
        String notes = "";
        SysUser sysUser = userService.selectUserByAuthCode(sqm);
        if(sysUser == null){
            return getResult(APIEnumDefine.S990);
        }
        SysPerDiscount sysPerDiscount = userService.selectSysUserDiscountByZwCode(sysUser.getEmpDuties());
        if(sysPerDiscount == null){
            return getResult(APIEnumDefine.S989);
        }
        if(sqType == 1){//定额优惠
           if(sysPerDiscount.getDeyh() != null && sysPerDiscount.getDeyh() == 1){
               if(sysPerDiscount.getMddeyhOne() == null){
                   return getResult(APIEnumDefine.S981);
               }
               BigDecimal com = new BigDecimal(sysPerDiscount.getMddeyhOne());
               if(val.compareTo(com) == 1){
                   return getResult(APIEnumDefine.S981);
               }
               notes = "授权定额优惠";
           }
        }else if(sqType == 2){//比例优惠
            BigDecimal com = new BigDecimal(sysPerDiscount.getCgyhzdbl());
            if(val.compareTo(com) == -1){
                return getResult(APIEnumDefine.S982);
            }
            notes = "授权常规比例优惠";
        }else if(sqType == 3){//全单优惠
            BigDecimal com = new BigDecimal(sysPerDiscount.getQdblyhzdbl());
            if(val.compareTo(com) == -1){
                return getResult(APIEnumDefine.S983);
            }
            notes = "授权全单比例优惠";
        }
        sysAuthorizationLogService.insertAuthLog(
                new SysAuthorizationLog(sqm,userCode,sysUser.getEmpCode(),notes)
        );
        return getSuccessResult();
    }

    /**
     * 快速结算
     * @param amountsReceivable 该结算类型的应收金额
     * @param openWaterData 所有勾选的参与比例优惠的营业流水数据，数据格式{openWaterPxdzSubtotal:品项打折小计,openWaterPxdzYs:品项打折应收,openWaterZyhdSubtotal:品项重要活动小计,openWaterZyhdYs:品项重要活动应收,otherSetting:[],openWaters:[{营业流水数据},{...},{...}]}
     * @param payType 结算的收费类型，三种金额类型，品项打折（1）、重要活动（2）以及会员支付（3）
     * @param clearingWayData 支付方式数据，数据格式：{["payCode":"支付方式代码","payMoney":"结算金额","conversionMoney":"换算金额","nonZeroMoney":"不找零金额","foreginPay":"外币支付","notes":"备注"]}
     * @return
     */
    @ResponseBody
    @RequestMapping("/fastSettlement")
    @ApiOperation(value = "快速结算", httpMethod = "POST", notes = "快速结算")
    public Object fastSettlement(HttpServletRequest request,HttpServletResponse response,
                                @ApiParam(required = true, name = "userCode", value = "前台用户") @RequestParam(value = "userCode") String userCode,
                                 @ApiParam(required = true, name = "openWaterData", value = "快速结算的营业流水数据") @RequestParam(value = "openWaterData") String openWaterData,
                                 @ApiParam(required = true, name = "amountsReceivable", value = "应收金额") @RequestParam(value = "amountsReceivable") Double amountsReceivable,
                                 @ApiParam(required = true, name = "zeroMoney", value = "抹零金额") @RequestParam(value = "zeroMoney") Double zeroMoney,
                                 @ApiParam(required = true, name = "changeAmount", value = "找零金额") @RequestParam(value = "changeAmount") Double changeAmount,
                                 @ApiParam(required = true, name = "fixedDiscount", value = "定额优惠") @RequestParam(value = "fixedDiscount") Double fixedDiscount,
                                 @ApiParam(required = false, name = "generalProportions", value = "常规比例") @RequestParam(value = "generalProportions",required = false) Double generalProportions,
                                 @ApiParam(required = false, name = "singleProportions", value = "全单比例") @RequestParam(value = "singleProportions",required = false) Double singleProportions,
                                 @ApiParam(required = false, name = "isWechatPay", value = "是否微信支付") @RequestParam(value = "isWechatPay",required = false) Integer isWechatPay,
                                 @ApiParam(required = false, name = "payAuthCode", value = "支付授权码") @RequestParam(value = "payAuthCode",required = false) String  payAuthCode,
                                 @ApiParam(required = false, name = "isAliPay", value = "是否支付宝支付") @RequestParam(value = "isAliPay",required = false) Integer isAliPay,
                                 @ApiParam(required = false, name = "payBody", value = "微信、支付宝支付附加信息") @RequestParam(value = "payBody",required = false) String payBody,
                                 @ApiParam(required = true, name = "clearingWayData", value = "结算方式数据") @RequestParam(value = "clearingWayData") String  clearingWayData,
                                 @ApiParam(required = true, name = "pos", value = "操作的POS") @RequestParam(value = "pos") Integer  pos,
                                 @ApiParam(required = false, name = "invoicingData", value = "发票数据") @RequestParam(value = "invoicingData",required = false) String invoicingData,
                                 @ApiParam(required = false, name = "clearingNotes", value = "结算备注") @RequestParam(value = "clearingNotes",required = false) String  clearingNotes,
                                 @ApiParam(required = false, name = "statementLabel", value = "结账单备注") @RequestParam(value = "statementLabel",required = false) String  statementLabel,
                                @ApiParam(required = true, name = "payType", value = "结算收费类型") @RequestParam(value = "payType") Integer payType,
                                @ApiParam(required = false, name = "jsNum", value = "结算流水号") @RequestParam(value = "jsNum",required = false) String jsNum,
                                @ApiParam(required = false, name = "couponData", value = "微信优惠券信息") @RequestParam(value = "couponData",required = false) String couponData,
                                 @ApiParam(required = false, name = "isNotice", value = "是否提醒前台pos") @RequestParam(value = "isNotice",required = false) String isNotice,
                                 Integer sqmyz,Integer sqmlx){
        try {

            SysUser sysUser = userService.selectUserByUserCode(userCode);

            SysRoleMenu sysRoleMenu = userService.selectMenuByUserZwAndMenuId(sysUser.getEmpDuties(), 413);
            if(sysRoleMenu == null){
                return getResult(APIEnumDefine.S984);
            }

            if(isWechatPay == null)isWechatPay = 0;
            if(isAliPay == null)isAliPay = 0;

            List<Map<String, Object>> clearingWayDataList = new Gson().fromJson(clearingWayData, new TypeToken<List<Map<String, Object>>>() {
            }.getType());

            List<DgOpenWater> dgOpenWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            List<DgOpenWater> closedOpenWater = apiCheckService.selectAllOpenWaterIsClosed(dgOpenWaters);

            if(closedOpenWater.size() > 0){
                return getResult(APIEnumDefine.M0201019);
            }

            if(clearingWayDataList.size() < 1){
                return getResult(APIEnumDefine.M0201010);
            }
            
            Integer isGz = 0;
//            Integer isHyxf = 0;
            for(int i=0;i<clearingWayDataList.size();i++){
                if(clearingWayDataList.get(i).get("payCode").toString().equalsIgnoreCase("HYGZ")){
                    isGz = 1;
                }
                if(clearingWayDataList.get(i).get("payCode").toString().equalsIgnoreCase("HYZF")){
//                	isHyxf = 1;
                    payType = 3;
                    break;
                }
                //新增微信会员支付
                if(clearingWayDataList.get(i).get("payCode").toString().equalsIgnoreCase("WXHY")){
                    payType = 3;
                    break;
                }
                //新增手机会员支付
                if(clearingWayDataList.get(i).get("payCode").toString().equalsIgnoreCase("HYMBZF")){
                    payType = 3;
                    break;
                }
            }

            //2017年9月21日14:41:41  选择会员支付，取消必须绑定会员的条件
            /*if(isHyxf == 1){
            	if(clearingWayDataList.get(0).get("vId") == null){
                    return getResult(APIEnumDefine.M0201022);
                }
            	
            	if(payType != 3){
            		return getResult(APIEnumDefine.M0201023);
            	}
            } */

            //找零金额为负
            if(changeAmount < 0){
                return getResult(APIEnumDefine.M0201013);
            }

            Double paidMoney = 0.0;
            Boolean isWeAli = false,flag = false;


            for(Map way:clearingWayDataList){
//                if(isWechatPay.equals(1)){
//                    if(!way.get("payCode").toString().equals("WECHAT")){
//                        return getResult(APIEnumDefine.M0201017);
//                    }
//                }
//
//                if(isAliPay.equals(1)){
//                    if(!way.get("payCode").toString().equals("ALIPAY")){
//                        return getResult(APIEnumDefine.M0201017);
//                    }
//                }
                if(way.get("payCode").toString().equals("WECHAT")){
                    isWechatPay=1;
                }
                if(way.get("payCode").toString().equals("ALIPAY")){
                    isAliPay=1;
                }
                if(way.containsKey("coupMoney")){
                    Double sub = MathExtend.add(Double.parseDouble(way.get("coupMoney").toString()),Double.parseDouble(way.get("payMoney").toString()));
                    paidMoney = MathExtend.add(paidMoney,sub);
                }else{
                    paidMoney = MathExtend.add(paidMoney,Double.parseDouble(way.get("payMoney").toString()));
                }
            }

            Double checkMoney = MathExtend.subtract(paidMoney,changeAmount);

            if(!checkMoney.equals(amountsReceivable)){
                return getResult(APIEnumDefine.M0201011);
            }

            //会员支付判断，如果是选择会员支付，支付方式只能是会员支付以及会员挂账
            //可能后期允许混合支付 2018/01/09 何帅
//            Boolean checkHyPayWay = true;
//            if(payType == 3){ //会员结账
//                for(Map<String,Object> map:clearingWayDataList){
//                    String payCode = map.get("payCode").toString();
//                    if(!payCode.equals("HYGZ") && !payCode.equals("HYZF")){
//                        checkHyPayWay = false;
//                    }
//                }
//                if(!checkHyPayWay)return getResult(APIEnumDefine.M0201015);
//            }

            List<String> owNums = new ArrayList<>();

            for(int i = 0;i < dgOpenWaters.size();i++){
                owNums.add(dgOpenWaters.get(i).getOwNum());
            }

            if(apiCheckService.modifyCheckOwNumHasDeposit(new Gson().toJson(owNums)) != null){
                return getResult(APIEnumDefine.M0201018);
            }

            /*if(apiCheckService.modifyCheckOwNumHasItem(new Gson().toJson(owNums)) != null){
                return getResult(APIEnumDefine.M0201007);
            }*/

            Map map = paySettlementService.modifyFastSettlement(userCode, dgOpenWaters, clearingWayData, payType,
                        amountsReceivable, pos, zeroMoney, fixedDiscount, clearingNotes, statementLabel,invoicingData,
                    generalProportions,singleProportions,paidMoney,changeAmount,isWechatPay==1?true:false,isAliPay==1?true:false,
                    payAuthCode,payBody,jsNum,isGz,couponData,isNotice,request,response);
            //会员支付、会员挂账的返回
            if(map == null){
                return getResult(APIEnumDefine.M0201016);
            }
            if(map.containsKey("msgCode")){
                if(!map.get("msgCode").equals("ok")){
                    if(map.containsKey("resMsg")){
                        return getResult( map.get("resMsg").toString());
                    }else{
                        return getResult( "会员结算失败！");
                    }
                }
            }

            //微信以及支付宝支付的返回
            if(map.containsKey("result")){
                String result = map.get("result").toString();
                String jsNum1 = map.get("jsNum").toString();
                if(!result.equalsIgnoreCase("P01004") && !result.equalsIgnoreCase("P01005")){
                    //支付失败，对账一次，成功继续，不成功则返回前台状态
                    Map bodyMap = (Map) map.get("body");
                    String orderNo = bodyMap.get("outTradeNo").toString();
                    Map<String, Object> payCheck = dgPayInterface.payCheck(orderNo,request,response);
                    if(!payCheck.get("result").toString().equals("S001")){
                        payCheck.put("jsWater",jsNum1);
                        return payCheck;
                    }
                }else{
                    return map;
                }
            }

            if(map.containsKey("paying")){
                return getResult( "当前流水正在结算，请勿重复操作！");
            }

            return getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 结算时绑定会员，并重新传入的主营业流水下会员价格
     * @param owNum
     * @param crId
     * @return
     */
    @ResponseBody
    @RequestMapping("/bingdingMember")
    @ApiOperation(value = "绑定会员", httpMethod = "POST", notes = "绑定会员")
    public Object bingdingMembers(@ApiParam(required = true, name = "openWaterData", value = "快速结算的营业流水数据") @RequestParam(value = "openWaterData") String openWaterData,
                                    @ApiParam(required = true, name = "owNum", value = "需要绑定会员的营业流水") @RequestParam(value = "owNum") String owNum,
                                 @ApiParam(required = true, name = "posId", value = "pos的ID") @RequestParam(value = "posId") Integer posId,
                                 @ApiParam(required = true, name = "crId", value = "会员ID") @RequestParam(value = "crId") String crId,
                                 @ApiParam(required = true, name = "gradeId", value = "会员卡ID") @RequestParam(value = "gradeId") String gradeId){
        try {

            DgPos dgPos = dgPosService.selectPosByPosId(posId);

            apiCheckService.modifyBingdingMember(owNum,crId);
            Map<String,Object> resMap = new HashMap(); //返回map

            //营业流水基本信息以及状态判断
            Map map = apiCheckService.selectOpenWaterByOwNum(owNum);

            //营业流水座位
            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //前台传入的营业流水相关数据
            List<DgOpenWater> frontDgOpenWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            //得到该营业流水所有相关团队成员以及向该营业流水转账的数据集合
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(owNum);

            //前台营业设置的账单权限
            DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
            //账单权限
            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);

            Double  hydzZeroMonry = 0.0,//会员打折小计抹零金额
                    openWaterHydzSubtotal = 0.0,//营业流水会员小计价格和
                    openWaterHydzYs = 0.0;//营业流水会员小计收费类型应收金额

            List<Map> map1 = null;
            Map vipMap = null;
            //循环可以进行买单结算的营业流水
            for(DgOpenWater dgOpenWater:dgOpenWaters){
                if(dgOpenWater.getOwNum().equals(owNum)){
                    String memberList = OkHttpUtils.memberGetVipAllCopu(dgOpenWater.getCrId());
                    if(StringUtils.isEmpty(memberList)){
                        return getResult(APIEnumDefine.M0201014);
                    }
                    Gson gson = new Gson();
                    Map res = (Map)gson.fromJson(memberList, Map.class).get("body");
                    String vipInfo = res.get("vipCard").toString();
                    vipMap = gson.fromJson(vipInfo,Map.class);
                    String hyLevel = vipMap.get("gradId").toString();
                    map1 = (List<Map>)gson.fromJson(vipMap.get("listModel").toString(), new TypeToken<List<Map>>(){}.getType());
                    dgOpenWater.setMemberInfo(vipMap);
//                    dgOpenWater.setMemberCardList(map1);
                }

                //循环获取所有营业流水下面的所有有效品项按照服务单分组
                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithService(dgOpenWater.getOwNum());

                //会员打折价格
                Double standPriceHydz = 0.0;

                //先计算出所有品项的小计
                for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                    //开单没有优惠的品项的会员折扣价格，计算制作费用
                    if(dgOwConsumerDetails.getIsPriceCal() == 0){
                        apiCheckService.getOpenWaterHyPrice(dgOwConsumerDetails,gradeId,Integer.valueOf(dgPos.getOrganization()));

                        //每一个营业流水下所有品项会员价格累加
                        standPriceHydz = MathExtend.add(standPriceHydz,MathExtend.add(dgOwConsumerDetails.getHydzItemCostsSum(),dgOwConsumerDetails.getProductionCosts()));
                    }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
                        //开单有优惠的品项，会员单价也设置为开单的最终价格，会员小计设置为开单时的小计
                        dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setHydzItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getSubtotal());
                    }
                }

                //品项信息
                dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

                dgOpenWater.setHydzItemSubtotal(standPriceHydz);

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "hy");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

//                openWaterHydzSubtotal =  MathExtend.add(MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterHydzSubtotal =  MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal());
            }

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            String noSmallChangeWay = dbsBillServDTO.getNoSmallChangeWay(),
                    noSmallChangePlace = dbsBillServDTO.getNoSmallChangePlace();
            if(!noSmallChangeWay.equals("0")){
                if(noSmallChangeWay.equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        hydzZeroMonry = setZeroValue(swqdj(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(swqdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        hydzZeroMonry = setZeroValue(hydzZeroMonry);
                        openWaterHydzYs = setYsje(hydzZeroMonry);
                    }else if(noSmallChangePlace.equals("2")){
                        hydzZeroMonry = setZeroValue(swqdfive(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(swqdfive(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        hydzZeroMonry = setZeroValue(swqdten(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(swqdten(openWaterHydzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        hydzZeroMonry = setZeroValue(qwqdj(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(qwqdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        hydzZeroMonry = setZeroValue(qwqdy(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(qwqdy(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        hydzZeroMonry = setZeroValue(qwqd5y(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(qwqd5y(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        hydzZeroMonry = setZeroValue(qwqd10y(openWaterHydzSubtotal));
                        openWaterHydzYs = setYsje(qwqd10y(openWaterHydzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        hydzZeroMonry = setZeroValue(roundingQdj(openWaterHydzSubtotal));

                        openWaterHydzYs = setYsje(roundingQdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        hydzZeroMonry = setZeroValue(roundingQdy(openWaterHydzSubtotal));

                        openWaterHydzYs = setYsje(roundingQdy(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        hydzZeroMonry = setZeroValue(roundingQd5y(openWaterHydzSubtotal));

                        openWaterHydzYs = setYsje(roundingQd5y(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        hydzZeroMonry = setZeroValue(roundingQd10y(openWaterHydzSubtotal));

                        openWaterHydzYs = setYsje(roundingQd10y(openWaterHydzSubtotal));
                    }
                }
            }else{
                openWaterHydzYs = MathExtend.add(openWaterHydzYs,openWaterHydzSubtotal);
            }


            for (DgOpenWater frontDgOpenWater : frontDgOpenWaters) {
                for (DgOpenWater dgOpenWater : dgOpenWaters) {
                    if(frontDgOpenWater.getOwNum().equals(dgOpenWater.getOwNum())){
                        List<DgOwConsumerDetails> itemFileInfos = frontDgOpenWater.getItemFileInfos();
                        List<DgOwConsumerDetails> itemFileInfos1 = dgOpenWater.getItemFileInfos();
                        for (DgOwConsumerDetails itemFileInfo : itemFileInfos) {
                            for (DgOwConsumerDetails dgOwConsumerDetails : itemFileInfos1) {
                                if(itemFileInfo.getItemFileId().equals(dgOwConsumerDetails.getItemFileId()) && itemFileInfo.getOwId().equals(dgOwConsumerDetails.getOwId())){
                                    itemFileInfo.setHydzItemFilePrice(dgOwConsumerDetails.getHydzItemFilePrice());
                                    itemFileInfo.setHydzItemCostsSum(dgOwConsumerDetails.getHydzItemCostsSum());
                                }
                            }
                        }
                        frontDgOpenWater.setMemberInfo(dgOpenWater.getMemberInfo());
                        frontDgOpenWater.setHydzItemSubtotal(dgOpenWater.getHydzItemSubtotal());
                    }
                }
            }

            //营业流水信息集合
            resMap.put("openWaters",frontDgOpenWaters);
            //小计
            resMap.put("openWaterHydzSubtotal",openWaterHydzSubtotal);
            //抹零
            resMap.put("hydzZeroMonry",hydzZeroMonry);
            //应收
            Double subtract = MathExtend.add(openWaterHydzYs, hydzZeroMonry);
            resMap.put("openWaterHydzYs",subtract);

            //优惠券金额计算

            List<Map> maps1 = apiCheckService.modifyCouponMoney(map1, subtract, dgOpenWaters);

            resMap.put("memberCardList",maps1);
            resMap.put("vipInfo",vipMap);

            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     * 结算锁定以及取消结算锁定，埋单以及撤销埋单
     * @param userCode 操作人
     * @param pos 操作的POS ID
     * @param owNums 结算锁定操作的营业流水数据，数据格式["营业流水","营业流水","营业流水","营业流水","营业流水"]
     * @param type 1、结算锁定。2、取消结算锁定。
     * @return
     */
    @ResponseBody
    @RequestMapping("/payLock")
    @ApiOperation(value = "营业结算锁定以及取消锁定", httpMethod = "POST", notes = "营业结算锁定以及取消锁定")
    public Object payLock(
                            @ApiParam(required = true, name = "userCode", value = "操作人") @RequestParam(value = "userCode", required = true) String userCode,
                            @ApiParam(required = true, name = "pos", value = "操作POS") @RequestParam(value = "pos", required = true) Integer pos,
                            @ApiParam(required = true, name = "owNums", value = "营业结算锁定以及取消锁定的营业流水") @RequestParam(value = "owNums", required = true) String owNums,
                            @ApiParam(required = true, name = "type", value = "1、结算锁定。2、取消结算锁定。") @RequestParam(value = "type", required = true) Integer type){
        try {
            apiCheckService.modifyOpenWaterLock(owNums,userCode,type,pos);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    
    @ResponseBody
    @RequestMapping("/authorizationCardInfo")
    @ApiOperation(value = "获取授权卡对应打折信息", httpMethod = "POST", notes = "获取授权卡对应打折信息")
    public Object authorizationCardInfo(@ApiParam(required = true, name = "cardNumber", value = "卡号") @RequestParam(value = "cardNumber") String cardNumber){
    	try{
    		if(StringUtil.isNotEmpty(cardNumber)){
    			Map resmap = new HashMap();
            	SysUser sysUser = userService.selectUserByAuthCode(cardNumber);	
            	if(sysUser!=null){
                	resmap.put("sysUser",sysUser);
                	//优惠权限数据
                    SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode(sysUser.getEmpCode());
                    if(sysPerDiscount !=null){
                    	resmap.put("sysPerDiscount",sysPerDiscount);
                    	return getSuccessResult(resmap);
                    } else{
                    	return getResult(APIEnumDefine.M0700003);
                    }	
            	} else {
            		return getResult(APIEnumDefine.M0700001);
            	}
    		} else {
    			return getResult(APIEnumDefine.M0700002);
    		}
    	} catch(Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }
    /**
     * 后台计算比例优惠以及全单优惠
     * @param openWaterData 所有勾选的参与比例优惠的营业流水数据，数据格式{openWaterPxdzSubtotal:品项打折小计,openWaterPxdzYs:品项打折应收,openWaterZyhdSubtotal:品项重要活动小计,openWaterZyhdYs:品项重要活动应收,otherSetting:[],openWaters:[{营业流水数据},{...},{...}]}
     * @param proportion 优惠的比例
     * @param perDiscount 折扣权限数据
     * @param type 计算的类型，1、比例优惠（将消费明细中的品项优惠至指定比例，考虑允许打折品项和消费区域打折选项的其他设置的权限数据）
     *             2、全单优惠（不考虑允许打折品项和消费区域打折选项的其他设置）
     * @return
     */
    @ResponseBody
    @RequestMapping("/percentageDiscount")
    @ApiOperation(value = "后台计算比例优惠以及全单优惠", httpMethod = "POST", notes = "后台计算比例优惠")
    public Object percentageDiscount(@ApiParam(required = true, name = "openWaterData", value = "营业流水") @RequestParam(value = "openWaterData") String openWaterData,
                                      @ApiParam(required = true, name = "proportion", value = "优惠的比例") @RequestParam(value = "proportion") Double proportion,
                                      @ApiParam(required = true, name = "otherSetting", value = "前台营业设置相关权限数据") @RequestParam(value = "otherSetting") String otherSetting,
                                      @ApiParam(required = true, name = "perDiscount", value = "折扣权限数据") @RequestParam(value = "perDiscount") String perDiscount,
                                      @ApiParam(required = true, name = "type", value = "计算的类型，1、比例优惠。2、全单优惠") @RequestParam(value = "type") Integer type,
                                      @ApiParam(required = true, name = "userCode", value = "前台操作的用户") @RequestParam(value = "userCode") String userCode,
                                     @ApiParam(required = false, name = "isAuth", value = "是否经过授权码验证") @RequestParam(value = "isAuth",required = false) Integer isAuth){
        try {            
        	
            List<DgOpenWater> judgeCloseWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            List<DgOpenWater> closedOpenWater = apiCheckService.selectAllOpenWaterIsClosed(judgeCloseWaters);

            if(closedOpenWater.size() > 0){
                return getResult(APIEnumDefine.M0201019);
            }

            Map<String,Object> resMap = new HashMap();
            //优惠权限数据
            SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode(userCode);

            //isAuth值为0，验证登录用户的优惠权限数据，为1则跳过
            if(isAuth == null){
                //优惠权限数据判断
                if(sysPerDiscount != null){
                    if(type == 1 && proportion < sysPerDiscount.getCgyhzdbl()){
                        return getResult(APIEnumDefine.M0201008);
                    }
                    if(type == 2 && proportion < sysPerDiscount.getQdblyhzdbl()){
                        return getResult(APIEnumDefine.M0201009);
                    }
                }else{
                    return getResult(APIEnumDefine.S995);
                }
            }

            //获取优惠券
            List<DgWaterCoupon> dgWaterCoupons = dgWaterCouponService.getCouponCountByWaters(judgeCloseWaters);
            if(!dgWaterCoupons.isEmpty()){
                return getResult(APIEnumDefine.P02008);
            }


            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(otherSetting, DBSBillServDTO.class);

            //后台计算常规优惠比例以及全单比例
            List<DgOpenWater> dgOpenWaters = apiCheckService.newModifyPercentageDiscount(judgeCloseWaters,dbsBillServDTO,proportion, type);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0;//营业流水品项打折收费类型应收金额



            for(DgOpenWater dgOpenWater:dgOpenWaters){

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

//                openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
//                openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                
            }

            String noSmallChangeWay = dbsBillServDTO.getNoSmallChangeWay(),
                    noSmallChangePlace = dbsBillServDTO.getNoSmallChangePlace();

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!noSmallChangeWay.equals("0")){
                if(noSmallChangeWay.equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
            }

            resMap.put("openWaters",dgOpenWaters);
            //重要活动价格
            if(maps.size() > 0){
                resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                resMap.put("openWaterZyhdYs",openWaterZyhdYs);// - zyhdZeroMonry
            }
            //品项打折价格
            resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
            resMap.put("pxdzZeroMonry",pxdzZeroMonry);
            resMap.put("openWaterPxdzYs",openWaterPxdzYs);// - pxdzZeroMonry

            //返回优惠比例
            resMap.put("generalProportions",type==1?proportion:null);
            resMap.put("singleProportions",type==2?proportion:null);

            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    @ResponseBody
    @RequestMapping("/advancePay")
    @ApiOperation(value = "埋单操作", httpMethod = "POST", notes = "埋单操作")
    public Object advancePay(@ApiParam(required = true, name = "userCode", value = "前台用户") @RequestParam(value = "userCode") String userCode,
                             @ApiParam(required = true, name = "openWaterData", value = "进行埋单的营业流水数据") @RequestParam(value = "openWaterData") String openWaterData,
                             @ApiParam(required = false, name = "fixedDiscount", value = "定额优惠") @RequestParam(value = "fixedDiscount",required = false) Double fixedDiscount,
                             @ApiParam(required = false, name = "clearingId", value = "埋单数据ID") @RequestParam(value = "clearingId",required = false) Integer clearingId,
                             @ApiParam(required = false, name = "clearingWayData", value = "预定的结算方式数据") @RequestParam(value = "clearingWayData",required = false) String  clearingWayData,
                             @ApiParam(required = false, name = "generalProportions", value = "常规比例") @RequestParam(value = "generalProportions",required = false) Integer generalProportions,
                             @ApiParam(required = false, name = "singleProportions", value = "全单比例") @RequestParam(value = "singleProportions",required = false) Integer singleProportions,
                             @ApiParam(required = true, name = "pos", value = "操作的POS") @RequestParam(value = "pos") Integer  pos,
                             @ApiParam(required = false, name = "invoicingData", value = "发票数据") @RequestParam(value = "invoicingData",required = false) String invoicingData,
                             @ApiParam(required = true, name = "payType", value = "结算收费类型") @RequestParam(value = "payType") Integer payType){
        try {
            SysRoleMenu sysRoleMenu = userService.selectMenuByUserZwAndMenuId(userCode, 413);
            if(sysRoleMenu == null){
                return getResult(APIEnumDefine.S984);
            }

            List<DgOpenWater> dgOpenWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            List<DgOpenWater> closedOpenWater = apiCheckService.selectAllOpenWaterIsClosed(dgOpenWaters);

            if(closedOpenWater.size() > 0){
                return getResult(APIEnumDefine.M0201019);
            }

            List<String> owNums = new ArrayList<>();

            for(int i = 0;i < dgOpenWaters.size();i++){
                owNums.add(dgOpenWaters.get(i).getOwNum());
            }

            if(apiCheckService.modifyCheckOwNumHasItem(new Gson().toJson(owNums)) != null){
                return getResult(APIEnumDefine.M0201007);
            }

            Map map = paySettlementService.modifyAdvancePay(userCode, dgOpenWaters, payType,
                     pos, clearingWayData, fixedDiscount,invoicingData,generalProportions,singleProportions,clearingId);
            return getSuccessResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

    /**
     *
     * @param openWater
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancelAdvancePay")
    @ApiOperation(value = "撤销埋单操作", httpMethod = "POST", notes = "撤销埋单操作")
    public Object cancelAdvancePay(@ApiParam(required = true, name = "openWater", value = "撤销埋单的营业流水") @RequestParam(value = "openWater") String openWater,
                                   @ApiParam(required = true, name = "userCode", value = "前台登录用户") @RequestParam(value = "userCode") String userCode){
        try {
            SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);
            if(sysPerBusiness == null || sysPerBusiness.getCxmdQx() == null){
                return getResult(APIEnumDefine.M0202016);
            }

            DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(openWater);

            DgConsumerSeat seat = dgConsumerSeatService.selectByPrimaryKey(dgOpenWater.getSeatId());
            if(seat.getSeatState() != 5){
                return getResult(APIEnumDefine.S991);
            }

            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(openWater);

            if(dgOpenWaters.size() > 0){
                paySettlementService.cancelAdvancePay(dgOpenWaters,dgOpenWater);
                return getSuccessResult();
            }
            return  getResult(APIEnumDefine.S998);

            /*if(seat.getSeatState() == 5){
                seat.setSeatState(2);
                dgConsumerSeatService.updateSeatState(seat);
                paySettlementService.cancelAdvancePay(openWater);
                return getSuccessResult();
            }else{
                return getResult(APIEnumDefine.S991);
            }*/

            /*Map map = apiCheckService.selectOpenWaterByOwNum(openWater);
        	int seatId = Integer.parseInt(map.get("seat_id").toString());
        	DgConsumerSeat seat = dgConsumerSeatService.selectByPrimaryKey(seatId);
            if(map == null){
                return getResult(APIEnumDefine.S991);
            }*/

//        源代码,根据流水判断是否埋单
//            if(map.containsKey("ow_state") && Integer.parseInt(map.get("ow_state").toString())  != 3){
//                return getResult(APIEnumDefine.S991);
//            }
//            paySettlementService.cancelAdvancePay(openWater);
//            return getSuccessResult();
// 根据桌位状态判断埋单
        }  catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }

    }

    @ResponseBody
    @RequestMapping("/printGuestBillingData")
    @ApiOperation(value = "客用账单数据", httpMethod = "POST", notes = "客用账单数据")
    public Object printGuestBillingData(@ApiParam(required = true, name = "owNum", value = "打印客用单的营业流水") @RequestParam(value = "owNum") String owNum,
                                        @ApiParam(required = true, name = "userCode", value = "前台用户") @RequestParam(value = "userCode") String userCode){
        try {
            Map<String,Object> resMap = new HashMap(); //返回map

            //营业流水基本信息以及状态判断
            Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
            if(map == null){
                return getResult(APIEnumDefine.M0101001);
            }

            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(owNum);
            dgOpenWaters = paySettlementService.printGuestBillingData(dgOpenWaters);

            //比例优惠、全单优惠
            Double generalProportions = null,singleProportions = null;

            //如果营业流水存在结算流水ID，得到埋单时可能存入的比例优惠以及全单优惠
            DgReceptionClearingWater clearingWater = null;
            if(map.containsKey("clearing_water_id")){
                clearingWater = paySettlementService.selectClearingWaterById(Integer.parseInt(map.get("clearing_water_id").toString()));
                if(clearingWater.getGeneralProportions() != null){
                    generalProportions = clearingWater.getGeneralProportions();
                }
                if(clearingWater.getSingleProportions() != null){
                    singleProportions = clearingWater.getSingleProportions();
                }
            }

            //营业流水座位
            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            //用户所属职务的优惠权限数据
            SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode(userCode);

            //前台营业设置的账单权限
            DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
            //账单权限
            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);
            DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    hydzZeroMonry = 0.0,//会员打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterHydzSubtotal = 0.0,//营业流水会员小计价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0,//营业流水品项打折收费类型应收金额
                    openWaterHydzYs = 0.0;//营业流水会员小计收费类型应收金额

            Boolean isAllAdvancePay = false;
            Integer clearingId = null;
            List<Map> map1 = null;
            Map<String,Object> vipInfo = new HashMap<>();
            String hyLevel = null;
            //循环可以进行买单结算的营业流水
            for(DgOpenWater dgOpenWater:dgOpenWaters) {
            	
        		DgPos pos = new DgPos();
        		pos.setId(dgOpenWater.getOpenPos());
        		pos = dgPosService.getPosByID(pos);
        		
                //获取会员信息
                if(dgOpenWater.getCrId() != null){
                    String memberList = OkHttpUtils.memberGetVipAllCopu(dgOpenWater.getCrId());
                    if(memberList != null){
                        Gson gson = new Gson();

                        Map res = (Map)gson.fromJson(memberList, Map.class);

                        if(res != null && res.containsKey("msg_code") &&
                                res.get("msg_code").toString().equalsIgnoreCase("ok")){
                            String bodys = res.get("body").toString();
                            Map<String,Object> vipMap = gson.fromJson(res.get("vipCard").toString(),Map.class);
                            dgOpenWater.setMemberInfo(vipMap);

                            if(owNum.equals(dgOpenWater.getOwNum())){
                                hyLevel = vipMap.get("gradId").toString();
                                map1 = (List<Map>)gson.fromJson(vipMap.get("listModel").toString(), new TypeToken<List<Map>>(){}.getType());
                                vipInfo.putAll(vipMap);
                            }
                        }
                    }
                }

                if (dgOpenWater.getClearingWaterId() != null) {
                    isAllAdvancePay = true;
                    clearingId = dgOpenWater.getClearingWaterId();
                } else {
                    isAllAdvancePay = false;
                }

                //循环获取所有营业流水下面的所有有效品项按照服务单分组
                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithService(dgOpenWater.getOwNum());

                //重要活动价格，品项打折价格，会员打折价格
                Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

                //先计算出所有品项的小计
                for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                    //开单没有优惠的品项的三种折扣价格，计算制作费用
                    if(dgOwConsumerDetails.getIsPriceCal() == 0){
                        apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,hyLevel,Integer.valueOf(pos.getOrganization()),generalProportions,singleProportions);

                        //设置优惠的比例
                        if(generalProportions != null){
                            dgOwConsumerDetails.setDiscount(generalProportions);
                        }

                        if (singleProportions != null) {
                            dgOwConsumerDetails.setDiscount(singleProportions);
                        }

                        //每一个营业流水下所有品项三种价格累加
                        standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
                        standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
                        standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getHydzItemCostsSum());
                    }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
                        //如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
                        dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setPxdzItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());

                                dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setZyhdItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());
                            }
                        }

                        if(maps.size() > 0){
                            if(clearingWater != null){
                                if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount()) ;
                                }else{
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                                }
                            }else{
                                standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                            }
                        }
                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                            }else{
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                            }
                        }else{
                            standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                        }

                        if(hyLevel != null){
                            dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                            dgOwConsumerDetails.setHydzItemCostsSum(dgOwConsumerDetails.getSubtotal());
                            standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getSubtotal());
                        }
                    }
                }

                //品项信息
                dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

                dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
                dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
                dgOpenWater.setHydzItemSubtotal(standPriceHydz);

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

//                openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
//                openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());
//                openWaterHydzSubtotal =  MathExtend.add(MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                openWaterHydzSubtotal =  MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal());
            }

            if (isAllAdvancePay) {
                Map<String, Object> advancePayInfo = paySettlementService.selectAllAdvancePayInfo(clearingId);
                resMap.putAll(advancePayInfo);
            }

            String noSmallChangeWay = dbsBillServDTO.getNoSmallChangeWay();
            String noSmallChangePlace = dbsBillServDTO.getNoSmallChangePlace();

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!noSmallChangeWay.equals("0")){
                if(noSmallChangeWay.equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);
                        hydzZeroMonry = setZeroValue(openWaterHydzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                        openWaterHydzYs = setYsje(openWaterHydzSubtotal);
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdfive(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdfive(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdten(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdten(openWaterHydzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdy(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd5y(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd10y(openWaterHydzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdj(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdy(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd5y(openWaterHydzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd10y(openWaterHydzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
                openWaterHydzYs = MathExtend.add(openWaterHydzYs,openWaterHydzSubtotal);
            }

            //营业流水信息集合
            resMap.put("openWaters",dgOpenWaters);

            //3种价格合计
            resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
            resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
            resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs));

            if(maps.size() > 0){
                resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                resMap.put("openWaterZyhdYs",openWaterZyhdYs);
            }

            Double subtract = 0.;

            //会员打折价格
            if(hyLevel != null){
                subtract = MathExtend.subtract(openWaterHydzYs, returnValue(hydzZeroMonry));
                resMap.put("hydzZeroMonry",hydzZeroMonry);
                resMap.put("openWaterHydzYs",subtract);
                resMap.put("openWaterHydzSubtotal",openWaterHydzSubtotal);
                //优惠券金额计算
                List<Map> maps1 = apiCheckService.modifyCouponMoney(map1, subtract, dgOpenWaters);
                resMap.put("memberCardList",maps1);
                resMap.put("vipInfo",vipInfo);
            }

            Double openWaterPxdzYsv = returnValue(openWaterPxdzYs);
            Double openWaterZyhdYsv = returnValue(openWaterZyhdYs);
            Double subtractv = returnValue(subtract);

            List<ValueSort> ps = new LinkedList<ValueSort>();
            ps.add(new ValueSort("pxdz",openWaterPxdzYsv));

            int length = ps.size();
            if(maps.size() > 0){
                ps.add(new ValueSort("zhyd",subtractv));
            }
            if(hyLevel != null){
                ps.add(new ValueSort("hy",subtractv ));
            }

            Collections.sort(ps, new Comparator<ValueSort>() {
                @Override
                public int compare(ValueSort o1, ValueSort o2) {
                    Double cop = MathExtend.subtract(o2.getValue(),o1.getValue());
                    if (cop != 0)
                        return cop.intValue();
                    else
                        return o2.value.compareTo(o1.value);
                }
            });


            String valueKey = ps.get(0).getValueKey();
            if(valueKey.equalsIgnoreCase("pxdz")){
                resMap.put("subtotal",returnValue(pxdzZeroMonry));
                resMap.put("zero",returnValue(pxdzZeroMonry));
                resMap.put("actualYs",returnValue(openWaterPxdzYs));
            }else if(valueKey.equalsIgnoreCase("zhyd")){
                resMap.put("subtotal",openWaterZyhdSubtotal);
                resMap.put("zero",zyhdZeroMonry);
                resMap.put("actualYs",openWaterZyhdYs);
            }else{
                resMap.put("subtotal",openWaterHydzSubtotal);
                resMap.put("zero",hydzZeroMonry);
                resMap.put("actualYs",subtract);
            }
            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    /**
     * 预结单数据
     * @param openWater
     * @return
     */
    @ResponseBody
    @RequestMapping("/yjdData")
    public Object yjzData(String openWater){




       return null;
    }

    @ResponseBody
    @RequestMapping("/kydPrintAjax")
    @ApiOperation(value = "客用账单打印回调", httpMethod = "POST", notes = "客用账单打印回调")
    public Object kydPrintAjax(@ApiParam(required = true, name = "openWaterIds", value = "回调的营业流水ID集合，逗号隔开") @RequestParam(value = "openWaterIds") String openWaterIds){
        try {
            apiCheckService.modifyKydPrintAjax(openWaterIds);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    @ResponseBody
    @RequestMapping("/yjdPrintAjax")
    @ApiOperation(value = "埋单打印（预结单）回调", httpMethod = "POST", notes = "埋单打印（预结单）回调")
    public Object yjdPrintAjax(@ApiParam(required = true, name = "openWaterIds", value = "回调的营业流水ID集合，逗号隔开") @RequestParam(value = "openWaterIds") String openWaterIds){
        try {
            apiCheckService.modifyYjdPrintAjax(openWaterIds);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    @ResponseBody
    @RequestMapping("/jzdPrintData")
    @ApiOperation(value = "结账单打印数据", httpMethod = "POST", notes = "结账单打印数据")
    public Object jzdPrintData(@ApiParam(required = true, name = "clearingWaterId", value = "结账单ID") @RequestParam(value = "clearingWaterId") Integer clearingWaterId,
                               @ApiParam(required = false, name = "isCategory", value = "是否按类别打印") @RequestParam(value = "isCategory" , required = false) Integer isCategory){
        try {
            Map<String,Object> resMap = new HashMap<>();
            //结账单基本数据
            DgReceptionClearingWater dgReceptionClearingWater = apiCheckService.selectClearingWaterById(clearingWaterId);
            //结算方式
            List<DgOwClearingway> dgOwClearingways = apiCheckService.selectClearingWayByCwId(clearingWaterId);
            //结账单下的所有营业流水
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectOpenWaterByCwId(clearingWaterId);
            //根据结算ID查询出优惠信息
            DgOwDiscount dgOwDiscounts = apiCheckService.selectYhxx(clearingWaterId);
            //查看每个营业流水下的所有品项信息
            for(DgOpenWater dgOpenWater:dgOpenWaters){
                if(dgOpenWater.getTransferTarget() != null && !StringUtils.isEmpty(dgOpenWater.getJoinTeamNotes())){
                    String[] joinInfo = dgOpenWater.getJoinTeamNotes().split("，");
                    dgOpenWater.setSeatName(joinInfo[2].split("：")[1]);
                }

                List<DgOwConsumerDetails> dgOwConsumerDetails = apiCheckService.selectClearingItemFileInfos(dgOpenWater.getOwNum(),isCategory);

                List<DgOwConsumerDetails> newConsumerDetails = new ArrayList<>();
                
                for(DgOwConsumerDetails details:dgOwConsumerDetails){
                    if(newConsumerDetails.size() == 0){
                    	details.setDzqTotalMoney(MathExtend.multiply(details.getItemFileNumber(),details.getStandardPrice()));
                        newConsumerDetails.add(details);
                    }else{
                        Boolean flag = false;
                        for(DgOwConsumerDetails details1:newConsumerDetails){
                            if(details1.getItemFileId().equals(details.getItemFileId()) && details1.getItemFinalPrice().equals(details.getItemFinalPrice())){
                                details1.setItemFileNumber(MathExtend.add(details1.getItemFileNumber(),details.getItemFileNumber()));
                                details1.setSubtotal(MathExtend.add(details1.getSubtotal(),MathExtend.multiply(details.getItemFileNumber(),details.getItemFinalPrice())));
                                flag = true;
                            }
                        }
                        if(!flag){
                            newConsumerDetails.add(details);
                        }
                    }
                }
                dgOpenWater.setItemFileInfos(newConsumerDetails);
            }

            resMap.put("dgOwDiscounts",dgOwDiscounts);
            resMap.put("dgReceptionClearingWater",dgReceptionClearingWater);
            resMap.put("dgOwClearingways",dgOwClearingways);
            resMap.put("dgOpenWaters",dgOpenWaters);
            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    @ResponseBody
    @RequestMapping("/jzdPrintData_new")
    @ApiOperation(value = "结账单打印数据", httpMethod = "POST", notes = "结账单打印数据")
    public Object jzdPrintData_new(@ApiParam(required = true, name = "clearingWaterId", value = "结账单ID") @RequestParam(value = "clearingWaterId") Integer clearingWaterId,
                               @ApiParam(required = true, name = "time", value = "时间") @RequestParam(value = "time") String time,
                               @ApiParam(required = false, name = "isCategory", value = "是否按类别打印") @RequestParam(value = "isCategory" , required = false) Integer isCategory){
        try {
            String suffix= "";
            Boolean check = TableQueryUtil.closedBillTimeCheck(time);
            if(!check){
                suffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
                if(StringUtils.isEmpty(suffix)){
                    return getResult("无效时间范围");
                }
            }

            Map<String,Object> resMap = new HashMap<>();
            //结账单基本数据
            DgReceptionClearingWater dgReceptionClearingWater = apiCheckService.selectClearingWaterById_new(clearingWaterId,check,suffix);
            //结算方式
            List<DgOwClearingway> dgOwClearingways = apiCheckService.selectClearingWayByCwId_new(clearingWaterId,check,suffix);
            //结账单下的所有营业流水
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectOpenWaterByCwId_new(clearingWaterId,check,suffix);
            //根据结算ID查询出优惠信息
            DgOwDiscount dgOwDiscounts = apiCheckService.selectYhxx(clearingWaterId);
            //查看每个营业流水下的所有品项信息
            for(DgOpenWater dgOpenWater:dgOpenWaters){
                if(dgOpenWater.getTransferTarget() != null && !StringUtils.isEmpty(dgOpenWater.getJoinTeamNotes())){
                    String[] joinInfo = dgOpenWater.getJoinTeamNotes().split("，");
                    dgOpenWater.setSeatName(joinInfo[2].split("：")[1]);
                }

                List<DgOwConsumerDetails> dgOwConsumerDetails = apiCheckService.selectClearingItemFileInfos_new(dgOpenWater.getOwNum(),check,suffix,isCategory);

                List<DgOwConsumerDetails> newConsumerDetails = new ArrayList<>();

                for(DgOwConsumerDetails details:dgOwConsumerDetails){
                    if(newConsumerDetails.size() == 0){
                        newConsumerDetails.add(details);
                    }else{
                        Boolean flag = false;
                        for(DgOwConsumerDetails details1:newConsumerDetails){
                            if(details1.getItemFileId().equals(details.getItemFileId()) && details1.getItemFinalPrice().equals(details.getItemFinalPrice())){
                                details1.setItemFileNumber(MathExtend.add(details1.getItemFileNumber(),details.getItemFileNumber()));
                                details1.setSubtotal(MathExtend.add(details1.getSubtotal(),MathExtend.multiply(details.getItemFileNumber(),details.getItemFinalPrice())));
                                flag = true;
                            }
                        }
                        if(!flag){
                            newConsumerDetails.add(details);
                        }
                    }
                }

                dgOpenWater.setItemFileInfos(newConsumerDetails);
            }

            resMap.put("dgOwDiscounts",dgOwDiscounts);
            resMap.put("dgReceptionClearingWater",dgReceptionClearingWater);
            resMap.put("dgOwClearingways",dgOwClearingways);
            resMap.put("dgOpenWaters",dgOpenWaters);
            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    private void checkHasSameItem(List<DgOwConsumerDetails> dgOwConsumerDetails,DgOwConsumerDetails details){
        Boolean flag = false;
        for(DgOwConsumerDetails details1:dgOwConsumerDetails){
            if(details1.getItemFileId() == details.getOwId() && details1.getItemFinalPrice() == details.getItemFinalPrice()){
                details1.setItemFileNumber(MathExtend.add(details1.getItemFileNumber(),details.getItemFileNumber()));
                details1.setSubtotal(MathExtend.add(details1.getSubtotal(),MathExtend.multiply(details.getItemFileNumber(),details.getItemFinalPrice())));
                flag = true;
            }
        }
        if(!flag){
            dgOwConsumerDetails.add(details);
        }
    }

    @ResponseBody
    @RequestMapping("/jzdPrintAjax")
    @ApiOperation(value = "结账单打印回调", httpMethod = "POST", notes = "结账单打印回调")
    public Object yjdPrintAjax(@ApiParam(required = true, name = "clearingWaterId", value = "结账单ID") @RequestParam(value = "clearingWaterId") Integer clearingWaterId){
        try {
            apiCheckService.modifyJzdPrintAjax(clearingWaterId);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    @ResponseBody
    @RequestMapping("/onlineYDValidate")
    @ApiOperation(value = "线上预订开台验证", httpMethod = "POST", notes = "线上预订开台验证")
    public Object onlineYDValidate(@ApiParam(required = true, name = "id", value = "客座ID") @RequestParam(value = "id") Integer id,@ApiParam(required = true, name = "validateCode", value = "线上预订验证码") @RequestParam(value = "validateCode") String validateCode){
    	try {
    		DgConsumerSeat seat = dgConsumerSeatService.selectByPrimaryKey(id);
            Map map = apiCheckService.modifyOnlineYDValidate(seat.getUuidKey(),validateCode);


            if(Double.parseDouble(map.get("status").toString())==200){
            	seat.setSeatState(100);
            	dgConsumerSeatService.updateSeatState(seat);
            	return getSuccessResult();

            }
            return getFailResult(map.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }


    /**
     * 使用券相关的支付方式的相关计算使用
     * @param openWaterData 营业流水数据
     * @param wayId 具体的券的支付方式ID
     * @param ticketAmount 券的数量
     * @return
     */
    @RequestMapping("/ticketCal")
    @ResponseBody
    public Object ticketCal(@ApiParam(required = true, name = "openWaterData", value = "营业流水数据") @RequestParam(value = "openWaterData") String openWaterData,
                            @ApiParam(required = true, name = "wayId", value = "具体的券的支付方式ID") @RequestParam(value = "wayId") Integer wayId,
                            @ApiParam(required = true, name = "ticketAmount", value = "券的数量") @RequestParam(value = "ticketAmount") Integer ticketAmount,
                            @ApiParam(required = true, name = "otherSetting", value = "前台营业设置相关权限数据") @RequestParam(value = "otherSetting") String otherSetting){
        try {

            List<DgOpenWater> judgeCloseWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            judgeCloseWaters.sort(new Comparator<DgOpenWater>() {
                @Override
                public int compare(DgOpenWater o1, DgOpenWater o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });

            List<DgOpenWater> closedOpenWater = apiCheckService.selectAllOpenWaterIsClosed(judgeCloseWaters);

            if(closedOpenWater.size() > 0){
                return getResult(APIEnumDefine.M0201019);
            }

            Map<String,Object> resMap = new HashMap();

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(otherSetting, DBSBillServDTO.class);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0;//营业流水品项打折收费类型应收金额


            BigDecimal bzyhdys = BigDecimal.ZERO,
                    bpxdzys = BigDecimal.ZERO;


            for(DgOpenWater dgOpenWater:judgeCloseWaters){

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());

            }

            bzyhdys = new BigDecimal(openWaterZyhdSubtotal);
            bpxdzys = new BigDecimal(openWaterPxdzSubtotal);

            /*以上代码获取到所有营业流水的品项打折应收以及重要活动应收*/

            //根据回传的支付方式ID，获取该支付方式的基本信息
            DgSettlementWay way = apiCheckService.selectSettleWayInfoById(wayId);
            List<Integer> itemIds = JSON.parseArray(way.getItemIds(), Integer.class);

            Integer dklx = way.getDklx(),//抵扣类型
                    qyxdksz = way.getQyxdksz();//具体能抵扣的类型，1：无限制，2：大类，3：小类，4：具体品项
            BigDecimal ticketTotal = BigDecimal.ZERO, //固定金额券的总金额
                    dkje = BigDecimal.ZERO,
                    ticketPercent = new BigDecimal(way.getRollFaceValue()).divide(new BigDecimal(100));//券为折扣百分比
            BigDecimal d = new BigDecimal(way.getRollFaceValue());
            if(dklx == 1){//固定金额类型
                //券如果是固定金额类型，先计算出数量*券面值的总金额
                ticketTotal = new BigDecimal(ticketAmount).multiply(d);

                if(ticketTotal.compareTo(BigDecimal.ZERO) <= 0){
                    return getSuccessResult("总金额<=0");
                }
            }

            if(ticketPercent.compareTo(new BigDecimal("100")) == 0){
                return getSuccessResult("折扣百分比为100");
            }

            Boolean beanFlag = false;

            for(int i = 0;i < judgeCloseWaters.size() && !beanFlag;i++){
                DgOpenWater dgOpenWater = judgeCloseWaters.get(i);
                List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
                for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
                    Integer judgeId = 0;
                    if(qyxdksz == 1){//无特殊限制
                        BigDecimal decimal = BigDecimal.ZERO;
                        if(dklx == 2){
                            decimal = new BigDecimal(dgOwConsumerDetails.getItemFinalPrice()).
                                    multiply(ticketPercent).
                                    multiply(new BigDecimal(dgOwConsumerDetails.getItemFileNumber()));

                            dkje = dkje.add(new BigDecimal(dgOwConsumerDetails.getSubtotal()).subtract(decimal));

                            bzyhdys = bzyhdys.subtract(decimal); //品项打折应收减去抵扣
                            bpxdzys = bpxdzys.subtract(decimal);//重要活动应收减去抵扣
                        }else{
                            decimal = new BigDecimal(dgOwConsumerDetails.getSubtotal());

                            //固定金额券总金额减去符合条件的品项的小计，结果为≥0，则继续，小于0，则说明抵扣金额不足，需要加上抵扣为负数的值
                            ticketTotal = ticketTotal.subtract(decimal);
                            if(ticketTotal.compareTo(BigDecimal.ZERO) < 0){
                                decimal = decimal.add(ticketTotal);
                            }

                            dkje = dkje.add(decimal);

                            bzyhdys = bzyhdys.subtract(decimal); //品项打折应收减去抵扣
                            bpxdzys = bpxdzys.subtract(decimal);//重要活动应收减去抵扣

                            if(ticketTotal.compareTo(BigDecimal.ZERO) <= 0){
                                beanFlag = true;
                                break;
                            }
                        }
                    }else{//有大类、小类或者具体品项限制的券
                        if(qyxdksz == 2) {//大类限制
                            judgeId = dgOwConsumerDetails.getPxdlId();
                        }else if(qyxdksz == 3){
                            judgeId = dgOwConsumerDetails.getPxxlId();
                        }else if(qyxdksz == 4){
                            judgeId = dgOwConsumerDetails.getItemFileId();
                        }
                        if(itemIds.contains(judgeId)){//循环出的品项可以进行券的抵扣
                            BigDecimal decimal = BigDecimal.ZERO;
                            if(dklx == 2){
                                decimal = new BigDecimal(dgOwConsumerDetails.getItemFinalPrice()).
                                        multiply(ticketPercent).
                                        multiply(new BigDecimal(dgOwConsumerDetails.getItemFileNumber()));

                                dkje = dkje.add(new BigDecimal(dgOwConsumerDetails.getSubtotal()).subtract(decimal));

                                bzyhdys = bzyhdys.subtract(decimal); //品项打折应收减去抵扣
                                bpxdzys = bpxdzys.subtract(decimal);//重要活动应收减去抵扣
                            }else{
                                decimal = new BigDecimal(dgOwConsumerDetails.getSubtotal());
                                //固定金额券总金额减去符合条件的品项的小计，结果为≥0，则继续，小于0，则说明抵扣金额不足，需要加上抵扣为负数的值
                                ticketTotal = ticketTotal.subtract(decimal);
                                if(ticketTotal.compareTo(BigDecimal.ZERO) < 0){
                                    decimal = decimal.add(ticketTotal);
                                }

                                dkje = dkje.add(decimal);

                                bzyhdys = bzyhdys.subtract(decimal); //品项打折应收减去抵扣
                                bpxdzys = bpxdzys.subtract(decimal);//重要活动应收减去抵扣

                                if(ticketTotal.compareTo(BigDecimal.ZERO) <= 0){
                                    beanFlag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //抵扣的金额如果存在小数，抵扣金额往下取整
            Integer newDkje = dkje.intValue();
            BigDecimal subtract = dkje.subtract(new BigDecimal(newDkje));
            if(subtract.compareTo(BigDecimal.ZERO) != 0){
                bzyhdys.add(subtract);
                bpxdzys.add(subtract);
            }

            openWaterZyhdSubtotal = bzyhdys.doubleValue();
            openWaterPxdzSubtotal = bpxdzys.doubleValue();

            String noSmallChangeWay = dbsBillServDTO.getNoSmallChangeWay(),
                    noSmallChangePlace = dbsBillServDTO.getNoSmallChangePlace();

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!noSmallChangeWay.equals("0")){
                if(noSmallChangeWay.equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
            }

            resMap.put("openWaters",judgeCloseWaters);
            resMap.put("dkje",newDkje);
            //重要活动价格
            if(maps.size() > 0){
                resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                resMap.put("openWaterZyhdYs",openWaterZyhdYs);// - zyhdZeroMonry
            }
            //品项打折价格
            resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
            resMap.put("pxdzZeroMonry",pxdzZeroMonry);
            resMap.put("openWaterPxdzYs",openWaterPxdzYs);// - pxdzZeroMonry

            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getExceptionResult();
    }

    
    /**
     * 微信会员卡劵折扣
     * @param openWaterData 所有勾选的参与比例优惠的营业流水数据，数据格式{openWaterPxdzSubtotal:品项打折小计,openWaterPxdzYs:品项打折应收,openWaterZyhdSubtotal:品项重要活动小计,openWaterZyhdYs:品项重要活动应收,otherSetting:[],openWaters:[{营业流水数据},{...},{...}]}
     * @param proportion 优惠的比例
     * @param perDiscount 折扣权限数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/wxKjDiscount")
    @ApiOperation(value = "微信会员卡劵折扣", httpMethod = "POST", notes = "微信会员卡劵折扣")
    public Object wxKjDiscount(@ApiParam(required = true, name = "openWaterData", value = "所有勾选的参与比例优惠的营业流水数据") @RequestParam(value = "openWaterData") String openWaterData,
                                      @ApiParam(required = true, name = "couponData", value = "卡券信息") @RequestParam(value = "couponData") String couponData,
                                      @ApiParam(required = true, name = "otherSetting", value = "前台营业设置相关权限数据") @RequestParam(value = "otherSetting") String otherSetting,
                                      @ApiParam(required = true, name = "userCode", value = "前台操作的用户") @RequestParam(value = "userCode") String userCode){
        try {

            List<DgOpenWater> judgeCloseWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
            }.getType());

            List<DgOpenWater> closedOpenWater = apiCheckService.selectAllOpenWaterIsClosed(judgeCloseWaters);

            if(closedOpenWater.size() > 0){
                return getResult(APIEnumDefine.M0201019);
            }

            if(dgWaterCouponService.selectByCouponVal(couponData) != null){
                return getResult(APIEnumDefine.P02005);
            }

            Map<String,Object> resMap = new HashMap();
            
            String couponMsg = OkHttpUtils.getWxCouponInfo(couponData);
            if(couponMsg == null){
            	return getResult(APIEnumDefine.P02001);
            }
            
            Map couponInfo = JSONObject.parseObject(couponMsg, Map.class);
            if((boolean)couponInfo.get("success") == true){
            	if(couponInfo.get("couponInfo") == null || couponInfo.get("couponInfo").equals("null")){
            		return getResult(APIEnumDefine.P02002);
            	}
            } else {
            	return getFailResult(couponInfo.get("msg"));
            }
            
            couponInfo = (Map)couponInfo.get("couponInfo");
            
            String couponCustId = couponInfo.get("id").toString();//消费id
            String expiryDate = couponInfo.get("expiryDate").toString(); //到期日期
            Double preAmount = Double.valueOf(couponInfo.get("preAmount").toString()); //打折金额 或 折扣
            Integer typeId = Integer.valueOf(couponInfo.get("typeId").toString()); //打折类型
            Integer dictId = Integer.valueOf(couponInfo.get("dictId").toString()); //打折品项类型
            Integer couponState = Integer.valueOf(couponInfo.get("couponState").toString()); //卡券状态

            //判断只能使用同种类型的卡券
            List<DgWaterCoupon> dgWaterCoupons = dgWaterCouponService.getCouponCountByWaters(judgeCloseWaters);
            for(DgWaterCoupon dwc:dgWaterCoupons){
                if(!dwc.getCoupontype().equals(typeId)){
                    return getResult(APIEnumDefine.P02006);
                }
            }


            if(typeId.equals(1)){
                //打折券只能使用一张
                if(dgWaterCouponService.getCouponCountByWatersAndCouponType(judgeCloseWaters,1) > 0) {
                    return getResult(APIEnumDefine.P02007);
                }
            }
            
            //判断优惠卷是否已经过期
        	if(!DateTimeComputing.computionBf(formatDate.format(new Date()),expiryDate)){
        		 return getResult(APIEnumDefine.P02003);
        	}
        	
        	if(couponState.equals(1)){
        		return getResult(APIEnumDefine.P02004);
        	}
        	
        	if(couponState.equals(2)){
        		return getResult(APIEnumDefine.P02003);
        	}

            Double dlSjxf = apiCheckService.wxGetItemFileTypeTotal(judgeCloseWaters,dictId); //计算出类别消费费用
            

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(otherSetting, DBSBillServDTO.class);

            //后台计算常规优惠比例以及全单比例
            List<DgOpenWater> dgOpenWaters = apiCheckService.wxModifyPercentageDiscount(judgeCloseWaters,
            		0.0, preAmount,
            		dictId,typeId,couponInfo
            		);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0;//营业流水品项打折收费类型应收金额


            for(DgOpenWater dgOpenWater:dgOpenWaters){

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

//                openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
//                openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());
                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                
            }

            String noSmallChangeWay = dbsBillServDTO.getNoSmallChangeWay(),
                    noSmallChangePlace = dbsBillServDTO.getNoSmallChangePlace();

            //定额优惠
            if(typeId.equals(0)){
            	Double ye = null;
            	if(dlSjxf.compareTo(preAmount) < 0){
            		ye = dlSjxf;
                	couponInfo.put("yhMoney",dlSjxf);	
            	} else {
            		ye = preAmount;
                	couponInfo.put("yhMoney",preAmount);
            	}
            	openWaterZyhdSubtotal = MathExtend.subtract(openWaterZyhdSubtotal, ye);
            	openWaterPxdzSubtotal = MathExtend.subtract(openWaterPxdzSubtotal, ye);
            }
            //返回优惠卷信息
        	resMap.put("couponData",couponInfo);
            
            
            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!noSmallChangeWay.equals("0")){
                if(noSmallChangeWay.equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                    }
                }else if(noSmallChangeWay.equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(noSmallChangePlace.equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                    }else if(noSmallChangePlace.equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
            }

            resMap.put("openWaters",dgOpenWaters);
            //重要活动价格
            if(maps.size() > 0){
                resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                resMap.put("openWaterZyhdYs",openWaterZyhdYs);// - zyhdZeroMonry
            }
            //品项打折价格
            resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
            resMap.put("pxdzZeroMonry",pxdzZeroMonry);
            resMap.put("openWaterPxdzYs",openWaterPxdzYs);// - pxdzZeroMonry

            
//            //返回优惠比例
//            resMap.put("generalProportions",type==1?proportion:null);
//            resMap.put("singleProportions",type==2?proportion:null);
            BigDecimal yhMoney=null,pxdzYhMoney=null,zyhdYhMoney = null,hyYhMoney=null;
            if(couponInfo.containsKey("yhMoney")){
                yhMoney=new BigDecimal(couponInfo.get("yhMoeny").toString());
                pxdzYhMoney=new BigDecimal(couponInfo.get("yhMoeny").toString());
                zyhdYhMoney=new BigDecimal(couponInfo.get("yhMoeny").toString());
                hyYhMoney=new BigDecimal(couponInfo.get("yhMoeny").toString());
            }
            if(couponInfo.containsKey("yhjPxdzYhSutotal")){
                pxdzYhMoney=new BigDecimal(couponInfo.get("yhjPxdzYhSutotal").toString());
                zyhdYhMoney=new BigDecimal(couponInfo.get("yhjZyhdYhSubtotal").toString());
                hyYhMoney=new BigDecimal(couponInfo.get("yhjPxdzYhSutotal").toString());
            }
            //插入优惠券信息
            dgWaterCouponService.insertCoupon(dgOpenWaters.get(0).getId(),couponData,JSON.toJSONString(couponInfo),yhMoney,new Date(),typeId,pxdzYhMoney,zyhdYhMoney,hyYhMoney);
            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }
    
    /**
     * @param mobile 手机号码
     */
    @ResponseBody
    @RequestMapping("/getHyPayVailCode")
    @ApiOperation(value = "获取手机验证码", httpMethod = "POST", notes = "获取手机验证码")
    public Object getHyPayVailCode(@ApiParam(required = true, name = "mobile", value = "手机号码") @RequestParam(value = "mobile") String mobile){
        try {
            apiCheckService.getHyPayVailCode(mobile);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }


    /**
     * 还原特价商品
     * @param openWaterData 所有勾选的参与比例优惠的营业流水数据，数据格式{openWaterPxdzSubtotal:品项打折小计,openWaterPxdzYs:品项打折应收,openWaterZyhdSubtotal:品项重要活动小计,openWaterZyhdYs:品项重要活动应收,otherSetting:[],openWaters:[{营业流水数据},{...},{...}]}
     * @return
     */
    @ResponseBody
    @RequestMapping("/reductionPrice")
    @ApiOperation(value = "还原特价商品", httpMethod = "POST", notes = "还原特价商品")
    public Object reductionPrice(@ApiParam(required = true, name = "openWaterData", value = "所有勾选的参与比例优惠的营业流水数据") @RequestParam(value = "openWaterData") String openWaterData){
        try {
            apiCheckService.reductionPrice(openWaterData);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }
    
    /**
     * 测试code
     * @return
     */
    @ResponseBody
    @RequestMapping("/testMonth")
    public Object testMonth() {
        return TableQueryUtil.tableNameUtilWithMonthRange(null,"2017-07-01");
//        return TableQueryUtil.tableNameUtilWithMonthSingle("2017-08-12");
    }

    /************************************买单结算 END************************************************/

    public class ValueSort{
        private String valueKey;

        private Double value;

        public ValueSort(String valueKey, Double value) {
            this.valueKey = valueKey;
            this.value = value;
        }

        public String getValueKey() {
            return valueKey;
        }

        public void setValueKey(String valueKey) {
            this.valueKey = valueKey;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }
    

}
