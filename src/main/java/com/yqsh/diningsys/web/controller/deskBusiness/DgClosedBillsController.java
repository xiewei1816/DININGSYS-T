package com.yqsh.diningsys.web.controller.deskBusiness;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgClosedOpenWaterService;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;

import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已结账单 controller
 *
 * @author zhshuo create on 2016-11-22 下午3:31
 */
@RequestMapping("/deskBusiness/closedBills")
@Controller
public class DgClosedBillsController extends BaseController{

    @Autowired
    private DgClosedOpenWaterService dgClosedOpenWaterService;
    @Autowired
    private DgCurrentOpenWaterService dgCurrentOpenWaterService;
    @Autowired
	private DgConsumerSeatService consumerSeatService;
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;
	@Autowired
	private DgPosService posService;
    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private UserService userService;

    @Autowired
    private BusinessPermissionService businessPermissionService;

    @RequestMapping("/index")
    public String index(Model model){

        String empCode = getCurrentUser().getEmpCode();

        model.addAttribute("userCode",empCode);

        model.addAttribute("returnSettlement",false);
        
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

    @RequestMapping("/selectClosedOpenWaterByDate")
    @ResponseBody
    public List<Map> selectClosedOpenWater(String date,String clientSeat,String bis,String expArea,String pos,String choiceCode,String code){
        return dgClosedOpenWaterService.selectClosedOpenWater(date,clientSeat,bis,expArea,pos,choiceCode,code);
    }

    @RequestMapping("/selectClosedClearingWaterInfo/{cwId}")
    @ResponseBody
    public Map selectClosedClearingWaterInfo(@PathVariable Integer cwId,String time){
        try {
        	Map resMap = new HashMap();
            //获取结算流水的基本信息
            resMap.put("cwInfo",dgClosedOpenWaterService.selectCwInfoById(cwId,time));
            //获取该结算流水下面的所有营业流水
            resMap.put("owInfo",dgClosedOpenWaterService.selectOwInfoByCwId(cwId,time));
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getOpenWaterConDeInfo/{owId}/{cwId}")
    @ResponseBody
    public Map selectOpenWaterInfo(@PathVariable Integer owId,@PathVariable Integer cwId,String time){
        Map resMap = new HashMap();
        //根据结算流水获取的结算方式、优惠以及发票等信息
       resMap.putAll(dgCurrentOpenWaterService.getOpenWaterOtherInfo_new(cwId,time));

        List<DgOwConsumerDetails> openWaterConDeInfo = dgCurrentOpenWaterService.getOpenWaterConDeInfo_new(owId,time);

        resMap.put("openWaterConDeInfo",openWaterConDeInfo);
        return resMap;
    }

    //补开发票相关
    @RequestMapping("/toRepairInvoice/{cwId}")
    public String toRepairInvoice(@PathVariable Integer cwId,String time,Model model){
        model.addAttribute("allReceipt",dgClosedOpenWaterService.selectAllReceipt());

        model.addAttribute("cwInfo",dgClosedOpenWaterService.selectCwInfoById(cwId, time));

        return "deskBusiness/closedBills/repair_invoice";
    }

    @RequestMapping("/getReceiptInfoByCwId/{cwId}")
    @ResponseBody
    public List<DgOwReceipt> getReceiptInfoByCwId(@PathVariable Integer cwId,String time){
        List<DgOwReceipt> receiptInfoByCwId = dgCurrentOpenWaterService.getReceiptInfoByCwId_new(cwId,time);
        Integer addRowCount = 1;
        for(DgOwReceipt dgOwReceipt:receiptInfoByCwId){
            if(dgOwReceipt.getReceiptDenomination() == null || dgOwReceipt.getReceiptDenomination() == 0){
                dgOwReceipt.setInvoiceRowId("addRow"+addRowCount);
                addRowCount+=1;
            }else{
                dgOwReceipt.setInvoiceRowId(dgOwReceipt.getReceiptDenomination()+"");
            }
        }
        return receiptInfoByCwId;
    }

    @ResponseBody
    @RequestMapping("/checkBkfpPermission")
    public Object checkBkfpPermission(String authCode){
        if(!StringUtils.isEmpty(authCode)){
            SysUser sysUser = userService.selectUserByAuthCode(authCode);
            if(sysUser == null){
                return returnFail("无效的授权码");
            }
            SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"closeBill_repairInvoice");
            if(sysRoleMenu == null){
                return returnFail("该授权码无权限");
            }

            SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(sysUser.getEmpCode());
            if(sysPerBusiness == null || sysPerBusiness.getBkfpQx() == 0){
                return returnFail("该授权码无权限");
            }

            return returnSuccess();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/checkFkxzPermission")
    public Object checkFkxzPermission(String authCode){
        if(!StringUtils.isEmpty(authCode)){
            SysUser sysUser = userService.selectUserByAuthCode(authCode);
            if(sysUser == null){
                return returnFail("无效的授权码");
            }
            SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"closeBill_paymentCorrection");
            if(sysRoleMenu == null){
                return returnFail("该授权码无权限");
            }

            SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(sysUser.getEmpCode());
            if(sysPerBusiness == null || sysPerBusiness.getFkxzQx() == 0){
                return returnFail("该授权码无权限");
            }

            return returnSuccess();
        }
        return null;
    }

    @RequestMapping("/repairInvoice")
    @ResponseBody
    public ResultInfo repairInvoice(String tableJsonData,Integer cwId,String time){
        try {
            dgClosedOpenWaterService.editCwInvoice(tableJsonData,cwId,time);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }


    //todo  暂时关闭后台的付款修正功能 2017年8月6日15:43:48 by zhshuo
    //付款修正相关
    @RequestMapping("/toPaymentCorrect/{cwId}")
    public String toPaymentCorrect(@PathVariable Integer cwId,String time,Model model){
        model.addAttribute("cwInfo",dgClosedOpenWaterService.selectCwInfoById(cwId, time));
        //获取常用结算方式
        model.addAttribute("commonWay",dgClosedOpenWaterService.selectAllCommonWay());
        dgClosedOpenWaterService.selectAllOtherWay();
        return "deskBusiness/closedBills/payment_correction";
    }

    @RequestMapping("/selectClearingWayByCwId/{cwId}")
    @ResponseBody
    public List<DgOwClearingway> selectClearingWayByCwId(@PathVariable Integer cwId){
        List<DgOwClearingway> clearingWayByCwId = dgCurrentOpenWaterService.getClearingWayByCwId(cwId);
        for(DgOwClearingway dgOwClearingway:clearingWayByCwId){
            dgOwClearingway.setNrowId("O"+dgOwClearingway.getCwCode());
        }
        return clearingWayByCwId;
    }

    @RequestMapping("/checkCwClearingWay/{cwId}")
    public String checkCwClearingWay(@PathVariable Integer cwId,String seCode,String seName,Double ysje,String time, Model model){
        DgReceptionClearingWater dgReceptionClearingWater = dgClosedOpenWaterService.selectCwInfoById(cwId, time);
        Double needMoney = dgReceptionClearingWater.getAmountsReceivable() - ysje;
        model.addAttribute("needMoney",needMoney);
        model.addAttribute("ysje",ysje);
        model.addAttribute("seCode",seCode);
        model.addAttribute("seName",seName);
        model.addAttribute("cwInfo",dgReceptionClearingWater);
        return "deskBusiness/closedBills/payment_correction_add";
    }

    @RequestMapping("/updateClearingWay")
    @ResponseBody
    public ResultInfo updateClearingWay(String tableJsonData,Integer cwId,Double ss,Double zl){
        try {
            dgClosedOpenWaterService.updateClearingWay(tableJsonData,cwId,ss,zl);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @ResponseBody
    @RequestMapping("/returnSettlement")
    public ResultInfo returnSettlement(String deskUserCode,Integer clearingWaterId,String authCode,String time){
        try{
            SysUser authUser = null;
            if(!StringUtils.isEmpty(authCode)){
                authUser = userService.selectUserByAuthCode(authCode);
                if(authUser == null){
                   return returnFail("无效的授权码");
                }
                SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(authUser,"closeBill_returnSettlement");
                if(sysRoleMenu == null){
                    return returnFail("该授权码无权限");
                }

                SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(authUser.getEmpCode());
                if(sysPerBusiness == null || sysPerBusiness.getFwjsQx() == 0){
                    return returnFail("该授权码无权限");
                }
            }

            DgReceptionClearingWater dgReceptionClearingWater = dgClosedOpenWaterService.selectCwInfoById(clearingWaterId, time);

            int daysBetween = TableQueryUtil.daysBetween(dgReceptionClearingWater.getClearingTime(), new SimpleDateFormat(SystemConstants.DATE_FORMATE_DATE).format(new Date()));

            if(daysBetween > TableQueryUtil.getDefaultReturnLimit()){
                return returnFail("该流水已经超过返位结算日期，无法返位结算");
            }

            //首先判断，需要返位结算的客座的状态以及内部留房
            String errMsg = "";
            List<DgOpenWater> dgOpenWaters = dgClosedOpenWaterService.selectOpenWaterbyCwId(clearingWaterId);
            List<DgConsumerSeat> dgConsumerSeats = dgClosedOpenWaterService.selectSeatInfoBySeatIds(dgOpenWaters);
            if(dgConsumerSeats.size() > 0){
                for(DgConsumerSeat dgConsumerSeat:dgConsumerSeats){
                    if(dgConsumerSeat.getSeatState() == 3){
                        errMsg += "客座："+dgConsumerSeat.getName()+"出去清扫状态，";
                    }else if(dgConsumerSeat.getInsetRetentionRoom() ==  1){
                        errMsg += "客座："+dgConsumerSeat.getName()+"属于内部留房，";
                    }
                }
                errMsg += "无法完成返位结算操作";
                return returnFail(errMsg);
            }
            String res = dgClosedOpenWaterService.modifySettlement(authUser,deskUserCode,authCode,dgOpenWaters,dgReceptionClearingWater);
            if(res.equals("success")){
                return returnSuccess();	
            } else {
                return returnFail(res);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnFail();
    }

    @ResponseBody
    @RequestMapping("/checkPermission")
    public Map checkPermission(String userCode,String fcode){
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isEmpty(userCode) || StringUtils.isEmpty(fcode)){
            map.put("state",0);
            return map;
        }

        SysUser sysUser = userService.selectUserByUserCode(userCode);

        SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);

        if(fcode.equals("fwjs")){//返位结算
            if(sysPerBusiness == null || sysPerBusiness.getFwjsQx() != 1){
                map.put("state",1); //用户无权限
                return map;
            }
        }else if(fcode.equals("bkfp")){//补开发票
            if(sysPerBusiness == null || sysPerBusiness.getBkfpQx() != 1){
                map.put("state",1); //用户无权限
                return map;
            }
        }else if(fcode.equals("fkxz")){//付款修正
            if(sysPerBusiness == null || sysPerBusiness.getFkxzQx() != 1){
                map.put("state",1); //用户无权限
                return map;
            }
        }
        map.put("state",2); //用户有权限
        return map;
    }

}
