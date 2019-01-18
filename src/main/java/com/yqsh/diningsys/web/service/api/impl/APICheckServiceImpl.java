package com.yqsh.diningsys.web.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.model.VariablePrice;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.SysBusinessLogMapper;
import com.yqsh.diningsys.web.dao.archive.*;
import com.yqsh.diningsys.web.dao.deskBusiness.*;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwConsumerDetailsMapper;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.util.OnlineHttp;
import com.yqsh.diningsys.web.util.TableQueryUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-12-06 10:08
 */
@Service
@SuppressWarnings("all")
public class APICheckServiceImpl implements APICheckService {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private static SimpleDateFormat formatDate = new SimpleDateFormat(
            "yyyy-MM-dd"); // 年月日

    private static SimpleDateFormat formatTime = new SimpleDateFormat(
            "HH:mm:ss"); // 年月日

    @Resource
    private APICheckServiceMapper apiCheckServiceMapper;

    @Resource
    private BillMapper billMapper;

    @Resource
    private DgPosMapper dgPosMapper;

    @Resource
    private TbOrgMapper tbOrgMapper;

    @Resource
    private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;

    @Autowired
    private DeskBusinessSettingService deskBusinessSettingService;

    @Resource
    private DgConsumerSeatMapper dgConsumerSeatMapper;

    @Resource
    private TbBisMapper tbBisMapper;

    @Resource
    private DgImportantAcitivityDiscountSMapper dgImportantAcitivityDiscountSMapper;

    @Resource
    private DgWeekDiscountMapper dgWeekDiscountMapper;

    @Resource
    private DgItemDiscountProgrammeMapper dgItemDiscountProgrammeMapper;

    @Resource
    private DgItemDiscountProgrammeSMapper dgItemDiscountProgrammeSMapper;

    @Resource
    private DgItemMemberDiscountMapper dgItemMemberDiscountMapper;

    @Resource
    private DgItemMemberDiscountSMapper dgItemMemberDiscountSMapper;

    @Resource
    private DgSeatManagerService dgSeatManagerService;

    @Autowired
    private APIModifyService apiModifyService;

    @Resource
    private DeskBusinessSettingMapper deskBusinessSettingMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private BusinessPermissionService businessPermissionService;

    @Autowired
    private DgItemFileMapper dgItemFileMapper;

    @Resource
    private DgConsumerSeatMapper dgConsumerSeatService;
    
    @Resource
    private DgSettlementWayMapper dgSettlementWayMapper;

    @Resource
    private SysBusinessLogMapper sysBusinessLogMapper;

    @Resource
    private DgUrlSettingMapper dgUrlSettingMapper;
    @Override
    public List<Map> selectSeatHasItem(String owNum) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("owNum",owNum);

        List<Map> maps = apiCheckServiceMapper.selectSeatHasItem(param);

        return maps;
    }

    @Override
    public List<DgOwConsumerDetails> selectOpenWaterWithService(String owNum) {

        Map<String, String> param = new HashMap<>();
        param.put("owNum",owNum);
        List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectAllItemDataAddItem(param); //服务类型为开单自增以及加单的品项数据
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据


        //TODO 2017年7月31日18:03:30 by zhshuo
        /*List<DgOwConsumerDetails> allItemInfos = apiCheckServiceMapper.selectAllItemDataByOwNum(param); //一次性查询出全部加单以及退单的品项数据，新增单品转台
        List<DgOwConsumerDetails> backList = new ArrayList<>();
        for(Iterator<DgOwConsumerDetails> iterator = allItemInfos.iterator();iterator.hasNext();){
            if(iterator.next().getItemFileNumber() < 0){
                backList.add(iterator.next());
                iterator.remove();
            }
        }


        //将开单以及加单的数据与退单的数据整合

        for(DgOwConsumerDetails dgOwConsumerDetails:allItemInfos){
            Map<String,Object> res = setItemFileNum(backList,dgOwConsumerDetails);
            backList = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:allItemInfos){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }*/

        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }
        return resList;
    }

    @Override
    public List<DgOwConsumerDetails> selectClearingItemFileInfos(String owNum,Integer isCategory) {
        Map<String, String> param = new HashMap<>();
        param.put("owNum",owNum);
        /*List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectClearingItemFileInfosWithService(param); //服务类型为开单自增以及加单的品项数据
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectClearingItemFileInfosTD(param); //服务类型为退单的品项数据*/

       //一次性查询出所有加单以及退单的品项数据
        List<DgOwConsumerDetails> allItemFile = apiCheckServiceMapper.selectClearingAllItemFileInfos(param);
        //用来存放筛选的退单数据
        List<DgOwConsumerDetails> backList = new ArrayList<>();
        for(Iterator<DgOwConsumerDetails> iterator = allItemFile.iterator();iterator.hasNext();){
            DgOwConsumerDetails next = iterator.next();
            if(next.getItemFileNumber() < 0){
                backList.add(next);
                iterator.remove();
            }
        }

        for(DgOwConsumerDetails dgOwConsumerDetails:allItemFile){
            Map<String,Object> res = setItemFileNum(backList,dgOwConsumerDetails);
            backList = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:allItemFile){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }

        //将开单以及加单的数据与退单的数据整合g
        /*for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }*/


        for(DgOwConsumerDetails dgOwConsumerDetails:resList){
            if(dgOwConsumerDetails.getNotes().equals("1")){
                dgOwConsumerDetails.setNotes("自增品项");
            }else if(dgOwConsumerDetails.getNotes().equals("2")){
                dgOwConsumerDetails.setNotes("");
            }else if(dgOwConsumerDetails.getNotes().equals("3")){
                dgOwConsumerDetails.setNotes("赠单");
            }else if(dgOwConsumerDetails.getNotes().equals("4")){
                dgOwConsumerDetails.setNotes("退单");
            }else if(dgOwConsumerDetails.getNotes().equals("5")){
                dgOwConsumerDetails.setNotes("减少人数减单");
            }else if(dgOwConsumerDetails.getNotes().equals("6")){
                dgOwConsumerDetails.setNotes("增加人数自增");
            }else if(dgOwConsumerDetails.getNotes().equals("7")){
                dgOwConsumerDetails.setNotes("包房费品项");
            }
        }

        if(null != isCategory && isCategory == 1){
            DgUrlSetting CP_GATES_SETTING = dgUrlSettingMapper.selectByCode("CP_GATES");
            DgUrlSetting GD_GATES_SETTING = dgUrlSettingMapper.selectByCode("GD_GATES");
            DgUrlSetting XC_GATES_SETTING = dgUrlSettingMapper.selectByCode("XC_GATES");
            DgUrlSetting JS_GATES_SETTING = dgUrlSettingMapper.selectByCode("JS_GATES");
            DgUrlSetting QT_GATES_SETTING = dgUrlSettingMapper.selectByCode("QT_GATES");
            String CP_GATES = CP_GATES_SETTING.getValue();
            String GD_GATES = GD_GATES_SETTING.getValue();
            String XC_GATES = XC_GATES_SETTING.getValue();
            String JS_GATES = JS_GATES_SETTING.getValue();
            String QT_GATES = QT_GATES_SETTING.getValue();
            List<String> cpGates = new ArrayList<>();
            List<String> gdGates = new ArrayList<>();
            List<String> xcGates = new ArrayList<>();
            List<String> jsGates = new ArrayList<>();
            List<String> qtGates = new ArrayList<>();
            if(StringUtil.isNotEmpty(CP_GATES)){
                cpGates.addAll(Arrays.asList(CP_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(GD_GATES)){
                gdGates.addAll(Arrays.asList(GD_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(XC_GATES)){
                xcGates.addAll(Arrays.asList(XC_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(JS_GATES)){
                jsGates.addAll(Arrays.asList(JS_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(QT_GATES)){
                qtGates.addAll(Arrays.asList(QT_GATES.split(",")));
            }
            for(DgOwConsumerDetails dgOwConsumerDetails:resList){
                boolean find = false;
                for(String cp:cpGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(cp))){
                        dgOwConsumerDetails.setPxdlId(CP_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("菜品");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String gd:gdGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(gd))){
                        dgOwConsumerDetails.setPxdlId(GD_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("锅底");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String xc:xcGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(xc))){
                        dgOwConsumerDetails.setPxdlId(XC_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("小吃烧烤");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String js:jsGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(js))){
                        dgOwConsumerDetails.setPxdlId(JS_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("酒水饮料");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }

                for(String qt:qtGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(qt))){
                        dgOwConsumerDetails.setPxdlId(QT_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("其它");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
            }
            resList.sort(new Comparator<DgOwConsumerDetails>() {
                @Override
                public int compare(DgOwConsumerDetails o1, DgOwConsumerDetails o2) {
                    return o2.getPxdlId()-o1.getPxdlId();
                }
            });
        }

        return resList;
    }

    @Override
    public List<DgOpenWater> selectOpenWaterObjBySeatIdAndTeamNum(Map<String,Object> param) {
        return apiCheckServiceMapper.selectOpenWaterObjBySeatIdAndTeamNum(param);
    }

    @Override
    public List<DgOwConsumerDetails> selectBackBillDetailInfoByAddBillInfo(List<DgOwConsumerDetails> itemFileInfos) {
        return apiCheckServiceMapper.selectBackBillDetailInfoByAddBillInfo(itemFileInfos);
    }

    @Override
    public List<DgReceptionClearingWater> selectClearingWaterByTime(String queryTime) {
        List<DgReceptionClearingWater> maps = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("queryTime",queryTime);

        Boolean flag = TableQueryUtil.closedBillTimeCheck(queryTime);
        map.put("flag",flag);

        if(!flag){
            String suffix = TableQueryUtil.tableNameUtilWithMonthSingle(queryTime);
            map.put("suffix",suffix);
            if(StringUtils.isEmpty(suffix)){
                return maps;
            }

        }
        maps = apiCheckServiceMapper.selectClearingWaterByTime(map);
        return maps;
    }

    @Override
    public List<DgOpenWater> selectINGOpenWaters() {
        return apiCheckServiceMapper.selectINGOpenWaters();
    }

    @Override
    public DgOwDiscount selectYhxx(int clearingWaterId) {
        return apiCheckServiceMapper.selectYhxx(clearingWaterId);
    }

    @Override
    public List<DgOpenWater> selectTransferOpenWaterByOwNum(String owNum) {
        return apiCheckServiceMapper.selectTransferOpenWaterByOwNum(owNum);
    }

    @Override
    public List<Map> selectClosedWater(String beginTime, String endTime, Integer sortType) {
        List<Map> maps = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("sortType",sortType);

        List<String> tableSuffixList = TableQueryUtil.tableNameUtilWithMonthRange(beginTime,endTime);
        map.put("tableSuffixList",tableSuffixList);

        if(tableSuffixList == null || tableSuffixList.size() < 1){
            return maps;
        }
        maps = apiCheckServiceMapper.selectClosedWater(map);
        return maps;
    }

    @Override
    public List<DgOwConsumerDetails> selectOpenWaterClearing(String owNum) {
        return apiCheckServiceMapper.selectOpenWaterClearing(owNum);
    }

    @Override
    public DgReceptionClearingWater selectClearingWaterById_new(Integer clearingWaterId,Boolean check, String suffix) {
        Map<String,Object> map = new HashMap<>();
        map.put("clearingWaterId",clearingWaterId);
        map.put("flag",check);
        map.put("suffix",suffix);
        return apiCheckServiceMapper.selectClearingWaterById_new(map);
    }

    @Override
    public List<DgOwClearingway> selectClearingWayByCwId_new(Integer clearingWaterId,Boolean check, String suffix) {
        Map<String,Object> map = new HashMap<>();
        map.put("clearingWaterId",clearingWaterId);
        map.put("flag",check);
        map.put("suffix",suffix);
        return apiCheckServiceMapper.selectClearingWayByCwId_new(map);
    }

    @Override
    public List<DgOpenWater> selectOpenWaterByCwId_new(Integer clearingWaterId,Boolean check, String suffix) {
        Map<String,Object> map = new HashMap<>();
        map.put("clearingWaterId",clearingWaterId);
        map.put("flag",check);
        map.put("suffix",suffix);
        return apiCheckServiceMapper.selectOpenWaterByCwId_new(map);
    }

    @Override
    public List<DgOwConsumerDetails> selectClearingItemFileInfos_new(String owNum, Boolean check,String suffix,Integer isCategory) {
        Map<String, Object> param = new HashMap<>();
        param.put("owNum",owNum);
        param.put("flag",check);
        param.put("suffix",suffix);

        /*List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectClearingItemFileInfosWithService(param); //服务类型为开单自增以及加单的品项数据
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectClearingItemFileInfosTD(param); //服务类型为退单的品项数据*/

        //一次性查询出所有加单以及退单的品项数据
        List<DgOwConsumerDetails> allItemFile = apiCheckServiceMapper.selectClearingAllItemFileInfos_new(param);
        //用来存放筛选的退单数据
        List<DgOwConsumerDetails> backList = new ArrayList<>();
        for(Iterator<DgOwConsumerDetails> iterator = allItemFile.iterator();iterator.hasNext();){
            DgOwConsumerDetails next = iterator.next();
            if(next.getItemFileNumber() < 0){
                backList.add(next);
                iterator.remove();
            }
        }

        for(DgOwConsumerDetails dgOwConsumerDetails:allItemFile){
            Map<String,Object> res = setItemFileNum(backList,dgOwConsumerDetails);
            backList = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:allItemFile){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }

        //将开单以及加单的数据与退单的数据整合g
        /*for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }*/

        for(DgOwConsumerDetails dgOwConsumerDetails:resList){
            if(dgOwConsumerDetails.getNotes().equals("1")){
                dgOwConsumerDetails.setNotes("自增品项");
            }else if(dgOwConsumerDetails.getNotes().equals("2")){
                dgOwConsumerDetails.setNotes("");
            }else if(dgOwConsumerDetails.getNotes().equals("3")){
                dgOwConsumerDetails.setNotes("赠单");
            }else if(dgOwConsumerDetails.getNotes().equals("4")){
                dgOwConsumerDetails.setNotes("退单");
            }else if(dgOwConsumerDetails.getNotes().equals("5")){
                dgOwConsumerDetails.setNotes("减少人数减单");
            }else if(dgOwConsumerDetails.getNotes().equals("6")){
                dgOwConsumerDetails.setNotes("增加人数自增");
            }else if(dgOwConsumerDetails.getNotes().equals("7")){
                dgOwConsumerDetails.setNotes("包房费品项");
            }
        }

        if(null != isCategory && isCategory == 1){
            DgUrlSetting CP_GATES_SETTING = dgUrlSettingMapper.selectByCode("CP_GATES");
            DgUrlSetting GD_GATES_SETTING = dgUrlSettingMapper.selectByCode("GD_GATES");
            DgUrlSetting XC_GATES_SETTING = dgUrlSettingMapper.selectByCode("XC_GATES");
            DgUrlSetting JS_GATES_SETTING = dgUrlSettingMapper.selectByCode("JS_GATES");
            DgUrlSetting QT_GATES_SETTING = dgUrlSettingMapper.selectByCode("QT_GATES");
            String CP_GATES = CP_GATES_SETTING.getValue();
            String GD_GATES = GD_GATES_SETTING.getValue();
            String XC_GATES = XC_GATES_SETTING.getValue();
            String JS_GATES = JS_GATES_SETTING.getValue();
            String QT_GATES = QT_GATES_SETTING.getValue();
            List<String> cpGates = new ArrayList<>();
            List<String> gdGates = new ArrayList<>();
            List<String> xcGates = new ArrayList<>();
            List<String> jsGates = new ArrayList<>();
            List<String> qtGates = new ArrayList<>();
            if(StringUtil.isNotEmpty(CP_GATES)){
                cpGates.addAll(Arrays.asList(CP_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(GD_GATES)){
                gdGates.addAll(Arrays.asList(GD_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(XC_GATES)){
                xcGates.addAll(Arrays.asList(XC_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(JS_GATES)){
                jsGates.addAll(Arrays.asList(JS_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(QT_GATES)){
                xcGates.addAll(Arrays.asList(QT_GATES.split(",")));
            }
            for(DgOwConsumerDetails dgOwConsumerDetails:resList){
                boolean find = false;
                for(String cp:cpGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(cp))){
                        dgOwConsumerDetails.setPxdlId(CP_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("菜品");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String gd:gdGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(gd))){
                        dgOwConsumerDetails.setPxdlId(GD_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("锅底");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String xc:xcGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(xc))){
                        dgOwConsumerDetails.setPxdlId(XC_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("小吃烧烤");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String js:jsGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(js))){
                        dgOwConsumerDetails.setPxdlId(JS_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("酒水饮料");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }

                for(String qt:qtGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(qt))){
                        dgOwConsumerDetails.setPxdlId(QT_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("其它");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
            }
            resList.sort(new Comparator<DgOwConsumerDetails>() {
                @Override
                public int compare(DgOwConsumerDetails o1, DgOwConsumerDetails o2) {
                    return o2.getPxdlId()-o1.getPxdlId();
                }
            });
        }

        return resList;
    }

    @Override
    public DgSettlementWay selectSettleWayInfoById(Integer wayId) {
        return apiCheckServiceMapper.selectSettleWayInfoById(wayId);
    }

    @Override
    public List<DgOwConsumerDetails> selectOpenWaterClearingWithService(String owNum,Integer isCategory) {

        Map<String, String> param = new HashMap<>();
        param.put("owNum",owNum);
        List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectOpenWaterClearingWithService(param); //服务类型为开单自增以及加单的品项数据
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据

        //将开单以及加单的数据与退单的数据整合g

        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }

        for(DgOwConsumerDetails dgOwConsumerDetails:resList){
            if(dgOwConsumerDetails.getNotes().equals("1")){
                dgOwConsumerDetails.setNotes("自增品项");
            }else if(dgOwConsumerDetails.getNotes().equals("2")){
                dgOwConsumerDetails.setNotes("");
            }else if(dgOwConsumerDetails.getNotes().equals("3")){
                dgOwConsumerDetails.setNotes("赠单");
            }else if(dgOwConsumerDetails.getNotes().equals("4")){
                dgOwConsumerDetails.setNotes("退单");
            }else if(dgOwConsumerDetails.getNotes().equals("5")){
                dgOwConsumerDetails.setNotes("减少人数减单");
            }else if(dgOwConsumerDetails.getNotes().equals("6")){
                dgOwConsumerDetails.setNotes("增加人数自增");
            }else if(dgOwConsumerDetails.getNotes().equals("7")){
                dgOwConsumerDetails.setNotes("包房费品项");
            }
        }

        if(null != isCategory && isCategory == 1){
        	DgUrlSetting CP_GATES_SETTING = dgUrlSettingMapper.selectByCode("CP_GATES");
            DgUrlSetting GD_GATES_SETTING = dgUrlSettingMapper.selectByCode("GD_GATES");
            DgUrlSetting XC_GATES_SETTING = dgUrlSettingMapper.selectByCode("XC_GATES");
            DgUrlSetting JS_GATES_SETTING = dgUrlSettingMapper.selectByCode("JS_GATES");
            DgUrlSetting QT_GATES_SETTING = dgUrlSettingMapper.selectByCode("QT_GATES");
            String CP_GATES = CP_GATES_SETTING.getValue();
            String GD_GATES = GD_GATES_SETTING.getValue();
            String XC_GATES = XC_GATES_SETTING.getValue();
            String JS_GATES = JS_GATES_SETTING.getValue();
            String QT_GATES = QT_GATES_SETTING.getValue();
            List<String> cpGates = new ArrayList<>();
            List<String> gdGates = new ArrayList<>();
            List<String> xcGates = new ArrayList<>();
            List<String> jsGates = new ArrayList<>();
            List<String> qtGates = new ArrayList<>();
            if(StringUtil.isNotEmpty(CP_GATES)){
            	cpGates.addAll(Arrays.asList(CP_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(GD_GATES)){
                gdGates.addAll(Arrays.asList(GD_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(XC_GATES)){
            	xcGates.addAll(Arrays.asList(XC_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(JS_GATES)){
                jsGates.addAll(Arrays.asList(JS_GATES.split(",")));
            }
            if(StringUtil.isNotEmpty(QT_GATES)){
                qtGates.addAll(Arrays.asList(QT_GATES.split(",")));
            }
            for(DgOwConsumerDetails dgOwConsumerDetails:resList){
                boolean find = false;
                for(String cp:cpGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(cp))){
                        dgOwConsumerDetails.setPxdlId(CP_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("菜品");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String gd:gdGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(gd))){
                        dgOwConsumerDetails.setPxdlId(GD_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("锅底");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
                for(String xc:xcGates){
                	if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(xc))){
                		dgOwConsumerDetails.setPxdlId(XC_GATES_SETTING.getId());
                		dgOwConsumerDetails.setPxdlName("小吃烧烤");
                		find = true;
                		break;
                	}
                }
                if(find){
                	continue;
                }
                for(String js:jsGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(js))){
                        dgOwConsumerDetails.setPxdlId(JS_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("酒水饮料");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }

                for(String qt:qtGates){
                    if(dgOwConsumerDetails.getPxdlId().equals(Integer.valueOf(qt))){
                        dgOwConsumerDetails.setPxdlId(QT_GATES_SETTING.getId());
                        dgOwConsumerDetails.setPxdlName("其它");
                        find = true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
            }
            resList.sort(new Comparator<DgOwConsumerDetails>() {
                @Override
                public int compare(DgOwConsumerDetails o1, DgOwConsumerDetails o2) {
                    return o2.getPxdlId()-o1.getPxdlId();
                }
            });
        }

        return resList;
    }

    @Override
    public List<DgOwConsumerDetails> selectOpenWaterFreeWithService(String owNum) {
        Map<String, String> param = new HashMap<>();
        param.put("owNum",owNum);

        List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectFreeItemDataAddItem(param);
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param);

        //将开单以及加单的数据与退单的数据整合g

        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }
        return resList;
    }

    @Override
    public List<DgOwConsumerDetails> selectOpenWaterWithServiceTC(String owNum) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("owNum",owNum);

        List<DgOwConsumerDetails> itemDetail1 = apiCheckServiceMapper.selectAllItemDataAddItemTC(param); //服务类型为开单自增以及加单的品项数据
        List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItemTC(param); //服务类型为退单的品项数据

        //将开单以及加单的数据与退单的数据整合g

        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
            itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
            dgOwConsumerDetails = (DgOwConsumerDetails)res.get("obj");
        }

        List<DgOwConsumerDetails> resList = new ArrayList<>();
        for(DgOwConsumerDetails dgOwConsumerDetails:itemDetail1){
            if(dgOwConsumerDetails.getItemFileNumber() > 0){
                resList.add(dgOwConsumerDetails);
            }
        }
        return resList;
    }

    @Override
    public List<Map> selectOpenWaterBySeatId(String seatId) {
        Map param = new HashedMap();
        param.put("seatId",seatId);
        List<Map> maps = apiCheckServiceMapper.selectOpenWaterBySeatId(param);
        for(Map map:maps){
            Integer ow_state = MapUtils.getInteger(map, "ow_state");
            String joinTeamNotes = MapUtils.getString(map, "join_team_notes");
            //1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、
            //5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账
            if(ow_state == 1){
                map.put("stateName","正常");
            }else if(ow_state == 2){
                map.put("stateName","结算");
            }else if(ow_state == 3){
                map.put("stateName","埋单");
            }else if(ow_state == 4){
                if(!StringUtils.isEmpty(joinTeamNotes)){
                    map.put("stateName","正常(转账），"+"原客座为："+joinTeamNotes.split("，")[2].split("：")[1]);
                }else{
                    map.put("stateName","正常(转账）");
                }
            }else if(ow_state == 5){
                if(!StringUtils.isEmpty(joinTeamNotes)){
                    map.put("stateName","埋单(转账），"+"原客座为："+joinTeamNotes.split("，")[2].split("：")[1]);
                }else{
                    map.put("stateName","埋单(转账）");
                }
            }else if(ow_state == 6){
                map.put("stateName","结算(转账)");
            }else if(ow_state == 7){
                map.put("stateName","S账");
            }else if(ow_state == 8){
                map.put("stateName","手工锁定");
            }else if(ow_state == 9){
                map.put("stateName","结算锁定");
            }else if(ow_state == -1){
                map.put("stateName","关账");
            }
        }
        return maps;
    }

    @Override
    public Map selectOpenWaterByOwNum(String owNum) {
        Map param = new HashedMap();
        param.put("owNum",owNum);
        return apiCheckServiceMapper.selectOpenWaterByOwNUm(param);
    }

    @Override
    public DgOpenWater selectOpenWaterObjByOwNum(String owNum) {
        return apiCheckServiceMapper.selectOpenWaterObjByOwNum(owNum);
    }

    @Override
    public List<DgOpenWater> selectOpenWaterByTransferNum(String owNum) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("transferNum",owNum);
        return apiCheckServiceMapper.selectOpenWaterByTransferNum(param);
    }

    @Override
    public List<Map> selectTeamMembersByTeamCode(String teamCode,String owNum) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("teamCode",teamCode);
        param.put("owNum",owNum);
        return apiCheckServiceMapper.selectTeamMembersByTeamCode(param);
    }

    @Override
    public void updateOpenWaterTransfer(String userCode,String transferOwNum,String targetOwNum,Integer opFlag,List<DgOpenWater> transferWaters) {
        Map param = new HashedMap();
        param.put("transferOwNum",transferOwNum);
        param.put("targetOwNum",targetOwNum);
        param.put("opFlag",opFlag);
        
//        Integer integer = apiCheckServiceMapper.openWaterTransfer(param);

        DgOpenWater tartgetOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(targetOwNum);
        DgOpenWater transferOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(transferOwNum);

        //待转账的营业流水客座信息
        DgConsumerSeat dgConsumerSeat = dgConsumerSeatMapper.selectByPrimaryKey(transferOpenWater.getSeatId());

        
        //插入日志
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(transferOpenWater.getSeatId());
  		DgConsumerSeat nowseat = dgConsumerSeatMapper.getConsumerSeatById(tartgetOpenWater.getSeatId());
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 9, transferOpenWater.getOwNum(), 
  				seat.getName(), "操作客位:"+seat.getName()+",做了转账操作,转入客位:"+nowseat.getName(),new SimpleDateFormat("yyyy_MM").format(new Date())));
      		
      		
        if(opFlag == 0){//待转账的营业流水非团队
            Map<String,Object> map = new HashMap<>();
            map.put("targetTeamMembers",tartgetOpenWater.getTeamMembers());
            map.put("targetOwNum",targetOwNum);
            map.put("targetTeamMembersMainSeat",tartgetOpenWater.getTeamMainSeat());
            map.put("transferOwNum",transferOwNum);
            map.put("seatId",tartgetOpenWater.getSeatId());
            map.put("joinTeamNotes","转账，原客座序号为："+dgConsumerSeat.getId() + "，客座名称为："+dgConsumerSeat.getName());
            apiCheckServiceMapper.updateOpenWaterTransfer(map);
//            apiCheckServiceMapper.updateOpenWaterTransfer(param);
        }else if(opFlag == 1){//待转账的营业流水为团队
            //该营业流水下存在转账的信息，则转账所有营业流水为新客座
            if(transferWaters != null && transferWaters.size() > 0){
                DgOpenWater dgOpenWater_temp = apiCheckServiceMapper.selectOtherFirstTeamOwNum(transferOpenWater);
                if(dgOpenWater_temp != null && dgOpenWater_temp.getOwNum() != null){
                    DgOpenWater dgOpenWater1 = apiCheckServiceMapper.selectOpenWaterByowNum(dgOpenWater_temp.getOwNum());
                    Map<String,Object> map = new HashMap<>();
                    map.put("seatId",dgOpenWater1.getSeatId());
                    map.put("teamMember",transferOpenWater.getTeamMembers());
                    apiCheckServiceMapper.updateTeamMainSeatByTeamMembers(map);
                }
                Map<String,Object> map = new HashMap<>();
                map.put("targetTeamMembers",tartgetOpenWater.getTeamMembers());
                map.put("targetOwNum",targetOwNum);
                map.put("targetTeamMembersMainSeat",tartgetOpenWater.getTeamMainSeat());
                map.put("transferOwNum",transferOwNum);
                map.put("seatId",tartgetOpenWater.getSeatId());
                map.put("joinTeamNotes","转账，原客座序号为："+dgConsumerSeat.getId() + "，客座名称为："+dgConsumerSeat.getName());
                for(DgOpenWater dgOpenWater:transferWaters){
                    map.put("openWater",dgOpenWater.getOwNum());
                    apiCheckServiceMapper.updateOpenWaterSeat(map);
                }
                apiCheckServiceMapper.updateOpenWaterTransfer(map);
            }else{
                DgOpenWater dgOpenWater = apiCheckServiceMapper.selectOtherFirstTeamOwNum(transferOpenWater);
                if(dgOpenWater != null && dgOpenWater.getOwNum() != null){
                    DgOpenWater dgOpenWater1 = apiCheckServiceMapper.selectOpenWaterByowNum(dgOpenWater.getOwNum());
                    Map<String,Object> map = new HashMap<>();
                    map.put("seatId",dgOpenWater1.getSeatId());
                    map.put("teamMember",transferOpenWater.getTeamMembers());
                    apiCheckServiceMapper.updateTeamMainSeatByTeamMembers(map);
                }
                Map<String,Object> map = new HashMap<>();
                map.put("targetTeamMembers",tartgetOpenWater.getTeamMembers());
                map.put("targetOwNum",targetOwNum);
                map.put("targetTeamMembersMainSeat",tartgetOpenWater.getTeamMainSeat());
                map.put("transferOwNum",transferOwNum);
                map.put("seatId",tartgetOpenWater.getSeatId());
                map.put("joinTeamNotes","转账，原客座序号为："+dgConsumerSeat.getId() + "，客座名称为："+dgConsumerSeat.getName());
                apiCheckServiceMapper.updateOpenWaterTransfer(map);
//            apiCheckServiceMapper.updateOpenWaterTransfer(param);
            }
        }

        DgOpenWater dgOpenWater = apiCheckServiceMapper.selectTransferOtherOwNum(transferOpenWater.getSeatId());

        if(dgOpenWater == null){//修改客位状态为空闲
            apiCheckServiceMapper.updateSeatIdle(transferOpenWater.getSeatId());
        }

        int newSeatAmount = tartgetOpenWater.getSeatAmount() + transferWaters.size() + 1;

        Map<String,Object> seatAmountParam = new HashMap<>();
        seatAmountParam.put("newSeatAmount",newSeatAmount);
        seatAmountParam.put("targetOwNum",targetOwNum);

        apiCheckServiceMapper.updateOpenWatetSeatAmount(seatAmountParam);//修改目标营业流水下的客座输

        if(tartgetOpenWater.getIsTeam() == 0){//如果转账目标的营业流水非团队，先修改该营业流水为团队
            apiCheckServiceMapper.updateOpenWatetTeamState(targetOwNum);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("targetTeamMembers",tartgetOpenWater.getTeamMembers());
        map.put("targetTeamMembersMainSeat",tartgetOpenWater.getTeamMainSeat());
        map.put("transferOwNum",transferOpenWater.getOwNum());
        apiCheckServiceMapper.updateOpenWaterTransferTeamMember(map);

//        DgConsumerSeat dgConsumerSeat = apiCheckServiceMapper.checkSeatState(transferOwNum);
//        if(dgConsumerSeat.getSeatState() == 1){
//            DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(dgConsumerSeat.getId());
//
//            OnlineHttp.onlineSeatModify(seat.getUuidKey(), 1+"");
//        }
    }

    @Override
    public void createOpenWaterWithSplit(String userCode,List<DgOwConsumerDetails> dgOwConsumerDetailss,String owNum,String openPos,String token,
                                         List<List<VariablePrice>> splitOpenWaterInfos) {

        //todo 缺少服务流水code
        Map<Integer,List<DgOwConsumerDetails>> backDetails = new HashedMap();//存放拆账时前台有拆账行为的品项信息

        SysUser sysUser = sysUserMapper.selectByUsername(userCode);

        /*先对拆分的品项进行数据增加*/
        Gson gson = new Gson();

        Map param = new HashedMap();
        param.put("owNum",owNum);

        Map openWaterInfo = apiCheckServiceMapper.selectOpenWaterByOwNUm(param);
        Integer seatId = Integer.parseInt(openWaterInfo.get("seat_id").toString());
        Integer waiterId = Integer.parseInt(openWaterInfo.get("waiter").toString());

        DgPos pos = new DgPos();
        pos.setId(Integer.valueOf(openPos));
        pos = dgPosMapper.getPosByID(pos);
        TbOrg org = new TbOrg();
        org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
        org = tbOrgMapper.getOrgByID(org);

        
		Map owFlowRul = deskBusinessSettingService.getFlowRul(org.getOrgCode(),pos.getNumber(),SerialRulEnum.YY);
		
//        List<String> ornums = gson.fromJson(deskBusinessSettingService
//                        .createFlowNumber(org.getOrgCode(), pos.getNumber(), splitOpenWaterInfos.size(),
//                                SerialRulEnum.YY),
//                new TypeToken<List<String>>() {
//                }.getType());

        //用来存放每个新创建的服务流水，是否被使用
        Map<Integer,Integer> map = new HashMap<>();

        for(int i = 0;i < splitOpenWaterInfos.size();i++){
            List<VariablePrice> data = splitOpenWaterInfos.get(i);

            //计算营业流水小计
            Double shamSubToTal = 0.0;
            for(VariablePrice shamData:data){
                Map<String,Integer> openWaterDetailParam = new HashedMap();
                openWaterDetailParam.put("serviceId",shamData.getServiceId());
                openWaterDetailParam.put("itemFileId",shamData.getItemFileId());
                DgOwConsumerDetails openWaterDetailSub = apiCheckServiceMapper.selectDataByServiceIdAndOwId(openWaterDetailParam); //得到当前拆账下具体品项的详细信息

                shamSubToTal += shamData.getItemFileNum() * openWaterDetailSub.getItemFinalPrice();

                List<DgOwDetailsOther> dgOwDetailsOthers = apiCheckServiceMapper.selectDetailOtherInfo(openWaterDetailParam);

                //制作费用
                if(dgOwDetailsOthers.size() > 0){
                    for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                        if(dgOwDetailsOther.getZzffSf() == 1){
                            if(dgOwDetailsOther.getZzffSfType() == 0){
                                shamSubToTal += dgOwDetailsOther.getOcosts();
                            }else{
                                shamSubToTal += dgOwDetailsOther.getOcosts() * shamData.getItemFileNum();
                            }
                        }
                    }
                }
            }

            DgOpenWater dgOpenWater = new DgOpenWater();

            dgOpenWater.setFirstTime(new Date());
//            dgOpenWater.setOwNum(ornums.get(i));
            dgOpenWater.setIsBeginWithOne(owFlowRul.get("isBeginWithOne").toString());
    		dgOpenWater.setHeadStr(owFlowRul.get("headStr").toString());
            dgOpenWater.setSeatId(seatId);
            dgOpenWater.setPeopleCount(0);
            dgOpenWater.setOpenPos(Integer.parseInt(openPos));
            dgOpenWater.setWaiter(waiterId);
            dgOpenWater.setIsTeam(0);
            dgOpenWater.setTeamMainSeat(seatId);
            dgOpenWater.setSplitOpenWater(1);
            dgOpenWater.setSplitOpenWaterNum(owNum);
            dgOpenWater.setSplitTime(new Date());
            dgOpenWater.setOpenBis(getMealInt());
            dgOpenWater.setServiceCharge(0.0);
            dgOpenWater.setPrivateRoomCost(0.0);
            dgOpenWater.setMinimumConsumption(0.0);
            dgOpenWater.setSeatAmount(1);
            dgOpenWater.setIsTeam(1);
            dgOpenWater.setTeamMembers(openWaterInfo.get("team_members").toString());
            dgOpenWater.setIsIncreasingItem(0);
            dgOpenWater.setOpenOperator(userCode);
            if(openWaterInfo.containsKey("discount_costs")){
                dgOpenWater.setDiscountCosts(Double.parseDouble(openWaterInfo.get("discount_costs").toString()));
            }
            dgOpenWater.setSubtotal(shamSubToTal);
            dgOpenWater.setItemCostsSum(shamSubToTal);

            billMapper.insertOpenWater(dgOpenWater);

            //每一次循环最外层，都代表一个新的营业流水，都插入一条新的服务流水
            Map<String,Object> addServiceMapper = new HashMap<>();
            addServiceMapper.put("owId",dgOpenWater.getId());
            addServiceMapper.put("waiterId",openWaterInfo.get("waiter"));
            addServiceMapper.put("serviceTime",new Date());
            addServiceMapper.put("serviceType",2);
            billMapper.insertOwServiceWater(addServiceMapper);
            Integer serviceId =  Integer.parseInt(addServiceMapper.get("id").toString());
            map.put(serviceId,0);


            //计算品项小计数据
            //每次循环新建营业流水下面的具体品项，先判断该品项是否已经在backDetails需退单品项中存在
            for(VariablePrice shamData:data){
                //得到该拆账下的某一个具体品项的制作方法/费用数据
                Integer shamDataServiceId = shamData.getServiceId(),
                        shamDataItemFileId = shamData.getItemFileId();
                Double shamNumber = shamData.getItemFileNum(),
                        backCosts = 0.0,backSubTotal = 0.0;

                Map<String,Integer> detailParam = new HashedMap();
                detailParam.put("serviceId",shamDataServiceId);
                detailParam.put("itemFileId",shamDataItemFileId);
                DgOwConsumerDetails currentItemFileDetail = apiCheckServiceMapper.selectDataByServiceIdAndOwId(detailParam); //得到当前拆账下具体品项的详细信息
                backSubTotal = -(shamData.getItemFileNum() * currentItemFileDetail.getItemFinalPrice());

                List<DgOwDetailsOther> dgOwDetailsOthers = apiCheckServiceMapper.selectDetailOtherInfo(detailParam);

                for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                    if(dgOwDetailsOther.getZzffSf() == 1){
                        if(dgOwDetailsOther.getZzffSfType() == 0){
                            backCosts += -dgOwDetailsOther.getOcosts();  //退单品项的制作费用累加
                            backSubTotal += -dgOwDetailsOther.getOcosts(); //退单品项的小计费用累加
                        }else{
                            backCosts += dgOwDetailsOther.getOcosts() * -shamNumber;//退单品项的制作费用累加
                            backSubTotal += dgOwDetailsOther.getOcosts() * -shamNumber;//退单品项的小计费用累加
                        }
                    }
                }
                currentItemFileDetail.setItemFileNumber(-shamNumber);
                currentItemFileDetail.setSubtotal(backSubTotal);
                currentItemFileDetail.setBackOwId(shamData.getServiceId());
                currentItemFileDetail.setProductionCosts(backCosts);
                currentItemFileDetail.setNotes("4");
                if(backDetails.containsKey(shamDataServiceId)){
                    List<DgOwConsumerDetails> maps = backDetails.get(shamDataServiceId);
                    if(checkBackHashItem(maps,currentItemFileDetail) != null){
                        DgOwConsumerDetails dgOwConsumerDetails = checkBackHashItem(maps, currentItemFileDetail);
                        dgOwConsumerDetails.setItemFileNumber(dgOwConsumerDetails.getItemFileNumber() + -shamNumber);
                        dgOwConsumerDetails.setSubtotal(dgOwConsumerDetails.getSubtotal() + backSubTotal);
                        dgOwConsumerDetails.setProductionCosts(dgOwConsumerDetails.getProductionCosts() + backCosts);
                        replaceDetailData(maps,dgOwConsumerDetails);
                    }else{
                        maps.add(currentItemFileDetail);
                    }
                }else{
                    List<DgOwConsumerDetails> addMap = new ArrayList<>();
                    addMap.add(currentItemFileDetail);
                    backDetails.put(shamDataServiceId,addMap);
                }

                //加入新的营业流水和服务流水之后，加入该条品项的加单品项数据
                Map<String,Integer> lastDetailParam = new HashedMap();
                lastDetailParam.put("serviceId",shamDataServiceId);
                lastDetailParam.put("itemFileId",shamDataItemFileId);
                DgOwConsumerDetails addItemDetail = apiCheckServiceMapper.selectDataByServiceIdAndOwId(lastDetailParam); //得到当前拆账下具体品项的详细信息
                DgOwConsumerDetails dgOwConsumerDetails = new DgOwConsumerDetails();

                BeanUtils.copyProperties(addItemDetail,dgOwConsumerDetails);

                dgOwConsumerDetails.setItemFileId(shamDataItemFileId);
                dgOwConsumerDetails.setItemFileNumber(shamNumber);
                Double addItemCosts = 0.0,addSubtotal = shamNumber * addItemDetail.getItemFinalPrice();
                for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                    if(dgOwDetailsOther.getZzffSf() == 1){
                        if(dgOwDetailsOther.getZzffSfType() == 0){
                            addItemCosts += dgOwDetailsOther.getOcosts();
                            addSubtotal += dgOwDetailsOther.getOcosts();
                        }else{
                            addItemCosts += dgOwDetailsOther.getOcosts() * shamNumber;
                            addSubtotal += dgOwDetailsOther.getOcosts() * shamNumber;
                        }
                    }
                }
                dgOwConsumerDetails.setProductionCosts(addItemCosts);
                dgOwConsumerDetails.setSubtotal(addSubtotal);
                dgOwConsumerDetails.setOwId(serviceId);
                dgOwConsumerDetails.setNotes("2");
                dgOwConsumerDetailsMapper.insertSelective(dgOwConsumerDetails);

                //插入拆账的具体的品项信息之后，查询出品项的其他信息、口味、要求、制作方法
                Map<String,Object> detailInfo = new HashedMap();
                detailInfo.put("itemFileId",shamDataItemFileId);
                detailInfo.put("serviceId",currentItemFileDetail.getOwId());
                if(dgOwDetailsOthers.size() > 0){
                    for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                        dgOwDetailsOther.setSfId(serviceId);
                    }
                    Map<String,List<DgOwDetailsOther>> otherMap = new HashedMap();
                    otherMap.put("list",dgOwDetailsOthers);
                    apiCheckServiceMapper.insertMultiOtherInfo(otherMap);
                }
            }
        }

        Double allBack = 0.0;
        //退单服务流水以及退单品项信息添加
        if(!backDetails.isEmpty()){
            //退单服务添加
            Map<String,Object> backServiceMapper = new HashMap<>();
            backServiceMapper.put("owId",openWaterInfo.get("id"));
            backServiceMapper.put("waiterId",openWaterInfo.get("waiter"));
            backServiceMapper.put("serviceTime",new Date());
            backServiceMapper.put("serviceType",4);
            billMapper.insertOwServiceWater(backServiceMapper);
            Integer backServiceId =  Integer.parseInt(backServiceMapper.get("id").toString());
            map.put(backServiceId,0);

            Iterator<Map.Entry<Integer, List<DgOwConsumerDetails>>> iterator = backDetails.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, List<DgOwConsumerDetails>> next = iterator.next();
                List<DgOwConsumerDetails> value = next.getValue();

                for(DgOwConsumerDetails addData:value){
                    allBack -= addData.getSubtotal();
                    addData.setOwId(backServiceId);
                    if(addData.getIsTc() != -1){
                        apiCheckServiceMapper.insertChargeback(addData);
                    }
                }
            }
        }

        Double openWaterItemSum = Double.parseDouble(openWaterInfo.get("item_costs_sum").toString()),
                openWaterSubtotal = Double.parseDouble(openWaterInfo.get("subtotal").toString());
        openWaterItemSum = MathExtend.subtract(openWaterItemSum,allBack);
        openWaterSubtotal = MathExtend.subtract(openWaterSubtotal,allBack);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("openWaterItemSum",openWaterItemSum);
        map1.put("openWaterSubtotal",openWaterSubtotal);
        map1.put("id",Integer.parseInt(openWaterInfo.get("id").toString()));
        apiCheckServiceMapper.modifyOpenWaterTeamInfo(map1);
        
        
        //插入日志
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById((Integer)openWaterInfo.get("seat_id"));
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 10, owNum, 
  				seat.getName(), "操作客位:"+seat.getName()+",做了拆账操作",new SimpleDateFormat("yyyy_MM").format(new Date())));

    }

    private DgOwConsumerDetails checkBackHashItem(List<DgOwConsumerDetails> dgOwConsumerDetailss,DgOwConsumerDetails dgOwConsumerDetails){
        for(DgOwConsumerDetails forData:dgOwConsumerDetailss){
            if(forData.getOwId() == dgOwConsumerDetails.getOwId() && forData.getItemFileId() == dgOwConsumerDetails.getItemFileId()){
                return forData;
            }
        }
        return null;
    }

    private void replaceDetailData(List<DgOwConsumerDetails> dgOwConsumerDetailss,DgOwConsumerDetails dgOwConsumerDetails){
        for(DgOwConsumerDetails forData:dgOwConsumerDetailss){
            if(forData.getOwId() == dgOwConsumerDetails.getOwId() && forData.getItemFileId() == dgOwConsumerDetails.getItemFileId()){
                forData.setItemFileNumber(dgOwConsumerDetails.getItemFileNumber());
                forData.setProductionCosts(dgOwConsumerDetails.getProductionCosts());
                forData.setSubtotal(dgOwConsumerDetails.getSubtotal());
            }
        }
    }

    @Override
    public void editServiceItemData(String owNum, String token, List<Map> splitOpenWaterInfo) {
        Map param = new HashedMap();
        param.put("owNum",owNum);
        Map openWaterInfo = apiCheckServiceMapper.selectOpenWaterByOwNUm(param);

        //插入新的退单服务流水
        Map chargebackMap = new HashMap();
        chargebackMap.put("owId", openWaterInfo.get("id"));
        chargebackMap.put("waiterId", Integer.parseInt(openWaterInfo.get("waiter").toString()));
        chargebackMap.put("serviceTime", new Date());
        chargebackMap.put("serviceType", 4);
        billMapper.insertOwServiceWater(chargebackMap); // 插入服务流水

        //先插入新的加单服务数据
        Map serviceMap = new HashMap();
        serviceMap.put("owId", openWaterInfo.get("id"));
        serviceMap.put("waiterId", Integer.parseInt(openWaterInfo.get("waiter").toString()));
        serviceMap.put("serviceTime", new Date());
        serviceMap.put("serviceType", 2);
        billMapper.insertOwServiceWater(serviceMap); // 插入服务流水

        Integer serviceId = Integer.parseInt(serviceMap.get("id").toString()),
                chargebackServiceId = Integer.parseInt(chargebackMap.get("id").toString());

        //根据拆分品项的数据进行拆分的数据插入，每个拆分的品项都根据拆分前的服务人员来重新创建新的服务数据
        for(Map splitOpenWaterItemFile:splitOpenWaterInfo){
            Map<String,Object> map = new HashMap<>();
            map.put("serviceId",splitOpenWaterItemFile.get("serviceId"));
            map.put("itemFileId",splitOpenWaterItemFile.get("itemFileId"));
            //循环出每一个品项的具体信息，用来初始化该品项的加单以及退单数据
            DgOwConsumerDetails dgOwConsumerDetails1 = dgOwConsumerDetailsMapper.selectDetailByServiceIdAndItemFileId(map);

            List<DgOwDetailsOther> dgOwDetailsOthers = apiCheckServiceMapper.selectDetailOtherInfo(map);

            //当前服务单的具体品项的品项数量
            Double itemFileNum = Double.parseDouble(splitOpenWaterItemFile.get("itemFileAmount").toString()),
                    subTotal = itemFileNum * dgOwConsumerDetails1.getItemFinalPrice(),proCosts = 0.0,backProCosts = 0.0,backSubTotal = -itemFileNum * dgOwConsumerDetails1.getItemFinalPrice();

            //制作费用
            for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                if(dgOwDetailsOther.getZzffSf() == 1){
                    if(dgOwDetailsOther.getZzffSfType() == 0){
                        proCosts += dgOwDetailsOther.getOcosts(); //加单的制作费用和
                        backProCosts += -dgOwDetailsOther.getOcosts(); //退单的制作费用和
                        subTotal += dgOwDetailsOther.getOcosts(); //加单的小计
                        backSubTotal += -dgOwDetailsOther.getOcosts(); //退单的小计
                    }else{
                        proCosts += dgOwDetailsOther.getOcosts() * itemFileNum;//加单的制作费用和
                        backProCosts += dgOwDetailsOther.getOcosts() * -itemFileNum;//退单的制作费用和
                        subTotal += dgOwDetailsOther.getOcosts() * itemFileNum;//加单的小计
                        backSubTotal += dgOwDetailsOther.getOcosts() * -itemFileNum;//退单的小计
                    }
                }
            }
            //只有当拆分的品项数量不大于原数量，才能进行拆分
            if(itemFileNum <= dgOwConsumerDetails1.getItemFileNumber()){
                //创建加单的具体品项信息
                DgOwConsumerDetails addDetail = new DgOwConsumerDetails();
                BeanUtils.copyProperties(dgOwConsumerDetails1,addDetail);
                addDetail.setItemFileNumber(itemFileNum);
                addDetail.setProductionCosts(proCosts);
                addDetail.setSubtotal(subTotal);
                addDetail.setOwId(serviceId);
                addDetail.setNotes("2");
                addDetail.setIsPriceCal(dgOwConsumerDetails1.getIsPriceCal());

                apiCheckServiceMapper.createSplitItemData(addDetail);

                //创建退单的具体品项信息
                DgOwConsumerDetails backDetetails = new DgOwConsumerDetails();
                BeanUtils.copyProperties(dgOwConsumerDetails1,backDetetails);

                //制作费用
                backDetetails.setItemFileNumber(-itemFileNum);
                backDetetails.setProductionCosts(backProCosts);
                backDetetails.setSubtotal(backSubTotal);
                backDetetails.setBackOwId(dgOwConsumerDetails1.getOwId());
                backDetetails.setOwId(chargebackServiceId);
                backDetetails.setNotes("4");
                addDetail.setIsPriceCal(dgOwConsumerDetails1.getIsPriceCal());
                apiCheckServiceMapper.insertChargeback(backDetetails);

                Map<String,Object> otherDetail = new HashedMap();
                if(dgOwDetailsOthers.size() > 0){
                    for(DgOwDetailsOther dgOwDetailsOther:dgOwDetailsOthers){
                        dgOwDetailsOther.setSfId(serviceId);
                    }

                    otherDetail.clear();
                    otherDetail.put("list",dgOwDetailsOthers);
                    apiCheckServiceMapper.insertMultiOtherInfo(otherDetail);
                }
            }
        }
    }

    @Override
    public List<Map> selectSeatClosedWater(Integer seatId) {
       return apiCheckServiceMapper.selectSeatClosedWater(seatId);
    }

    @Override
    public void modifyContinuedCheck(String clearingWater) {
        /*续单操作*/
        DgReceptionClearingWater clearingWater1 = apiCheckServiceMapper.selectClearingWaterByNum(clearingWater);

        Integer id = clearingWater1.getId();

        //查询出该结算流水下的所有营业流水
        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectopenWatersByClearingWaterId(id);

        //修改该结算流水下的所有营业流水状态为初始化状态
        apiCheckServiceMapper.modifyContinuedCheck(id);

        //循环营业流水，修改客座信息
        for(DgOpenWater dgOpenWater:dgOpenWaters){
            Integer seatId = dgOpenWater.getSeatId();
            apiCheckServiceMapper.modifyContinuedSeatState(seatId);
            DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(seatId);

            OnlineHttp.onlineSeatModify(seat.getUuidKey(), 2+"");
        }

        apiCheckServiceMapper.modifyContinuedClearingState(id);

    }

    @Override
    public void modifyHangingSBill(String userCode,String owNum) {
        Map param = new HashedMap();
        param.put("owNum",owNum);
        apiCheckServiceMapper.hangingSBill(param);
        Map map = apiCheckServiceMapper.selectOpenWaterByOwNUm(param);
        Integer seatId = Integer.parseInt(map.get("seat_id").toString());
        param.put("seatId",seatId);
        param.put("seatState",1);
        param.put("lastOpenTime",null);
        List<Map> maps = apiCheckServiceMapper.selectOpenWaterBySeatId(param);
        if(maps.size() < 1){
            apiCheckServiceMapper.setSeatState(param);
            DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(seatId);

            OnlineHttp.onlineSeatModify(seat.getUuidKey(), 1+"");

        }
        
        //插入日志
        DgOpenWater dow = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(dow.getSeatId());
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 12, owNum, 
  				seat.getName(), "操作客位:"+seat.getName()+",做了挂S账操作",new SimpleDateFormat("yyyy_MM").format(new Date())));
    }

    @Override
    public List<DgOpenWater> selectAllSBill() {
        return apiCheckServiceMapper.selectAllSBill();
    }

    @Override
    public void modifyCancelSBill(String userCode,String owNum) {
        Map param = new HashedMap();
        param.put("owNum",owNum);
        apiCheckServiceMapper.modifyCancelSBill(param);

        Map map = apiCheckServiceMapper.selectOpenWaterByOwNUm(param);
        Integer seatId = Integer.parseInt(map.get("seat_id").toString());
        param.put("seatId",seatId);
        param.put("seatState",2);
        param.put("lastOpenTime",new Date());
        DgConsumerSeat dgConsumerSeat = dgConsumerSeatMapper.selectByPrimaryKey(seatId);
        if(dgConsumerSeat.getSeatState() == 1){
            apiCheckServiceMapper.setSeatState(param);
            DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(seatId);

            OnlineHttp.onlineSeatModify(seat.getUuidKey(), 2+"");

        }
        
        //插入日志
        DgOpenWater dow = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(dow.getSeatId());
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 13, owNum, 
  				seat.getName(), "操作客位:"+seat.getName()+",做了撤销挂S账操作",new SimpleDateFormat("yyyy_MM").format(new Date())));
    }

    @Override
    public Object selectOpenClassInfo(String userCode, Integer loginPos,Integer type) {
        //班次登录信息查询
    	Map<String,Object> loginInfoParams = new HashMap<String,Object>();
    	loginInfoParams.put("userCode", userCode);
    	loginInfoParams.put("loginPos", loginPos);
        Map loginInfo = apiCheckServiceMapper.selectUserLoginInfo(loginInfoParams);
        Gson gson = new Gson();
        
        String loginTime = loginInfo.get("login_time").toString();
        String endTime =  DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(loginTime, endTime);
        Map<String,Object> map = new HashMap<>();
        map.put("tableDateList", tableDateList);
        map.put("startTime",loginTime);
        map.put("endTime",endTime);
        map.put("loginPos",loginInfo.get("login_pos"));
        map.put("loginUser",loginInfo.get("login_user"));

        if(type == 1){
            return apiCheckServiceMapper.selectBigTypeReport(map);
        }

        if(type == 2){
            return apiCheckServiceMapper.selectSmallTypeReport(map);
        }

        if(type == 3){
            return apiCheckServiceMapper.selectItemReport(map);
        }

        if(type == 4){
            //会员挂账数据
            List<Map> maps = apiCheckServiceMapper.selectMembersCredit(map);
            for(Map gzMap:maps){
                try {
					String memberList = OkHttpUtils.memberGetVipAllCopu(gzMap.get("clearingMember").toString());
					if(!memberList.isEmpty()){
						Map memberMap = gson.fromJson(memberList, Map.class);
						if(memberMap.get("msgCode").toString().equals("ok")){
							Map body = (Map) memberMap.get("body");
							Map vipCard = gson.fromJson(body.get("vipCard").toString(),Map.class);
							if(vipCard != null){
								gzMap.put("memberName",vipCard.get("custName"));
							}else{
								gzMap.put("memberName","未知用户");
							}
						}else{
							gzMap.put("memberName","未知用户");
						}
					}
				} catch (JsonSyntaxException e) {
					gzMap.put("memberName","未知用户");
					e.printStackTrace();
				}
            }
            return maps;
        }

        if(type == 5){
            //结算方式
        	List<DgSettlementWay> dgSettlementWays = dgSettlementWayMapper.getAllList(null);
        	List<DgOwClearingway> frequencys = new ArrayList<DgOwClearingway>();
        	DgOwClearingway dcw = null;
        	for (DgSettlementWay dgSettlementWay : dgSettlementWays) {
				map.put("cwCode", dgSettlementWay.getNumber());
        		DgOwClearingway frequency = apiCheckServiceMapper.selectFrequency(map);
        		dcw = new DgOwClearingway();
        		dcw.setSeName(dgSettlementWay.getName());
        		dcw.setSettlementAmount(frequency==null? 0.0 : frequency.getSettlementAmount());
        		frequencys.add(dcw);
			}
            return frequencys;
        }
        return null;
    }

    @Override
    public Map selectOpenClassReport(String userCode, Integer loginPos) {
    	Map<String,Object> loginInfoParams = new HashMap<String,Object>();
    	loginInfoParams.put("userCode", userCode);
    	loginInfoParams.put("loginPos", loginPos);
        Map loginInfo = apiCheckServiceMapper.selectUserLoginInfo(loginInfoParams);
        Map<String,Object> resMap = new HashedMap();
        Map<String,Object> map = new HashMap<>();
        
        String loginTime = loginInfo.get("login_time").toString();
        String endTime =  DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        loginTime = loginTime.substring(0, loginTime.length() - 2);
        loginInfo.put("login_time", loginTime);
        
        //封装查询条件
        map.put("startTime",loginTime);
        map.put("endTime",endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(loginTime, endTime);
        map.put("tableDateList", tableDateList);
        map.put("loginPos",loginInfo.get("login_pos"));
        map.put("loginUser",loginInfo.get("login_user"));
        map.put("pettyCash",loginInfo.get("petty_cash"));
        resMap.put("loginInfo",loginInfo);
        
        //查询前班数据
        Map lastLoginInfo = apiCheckServiceMapper.selectLastUserLoginInfo(loginTime);
        resMap.put("lastLoginInfo",lastLoginInfo);
        //开单人数以及开台总数
        resMap.put("bilingData",apiCheckServiceMapper.selectBilingData(map));
        //未结台数以及未结金额
        resMap.put("openData",apiCheckServiceMapper.selectOpenData(map));
        //未退押金
        resMap.put("depositData",apiCheckServiceMapper.selectWTDepositData(map));
        //已结台数以及已结人数
        resMap.put("closeData",apiCheckServiceMapper.selectCloseData(map));
        //本班赠单金额
        resMap.put("freeData",apiCheckServiceMapper.selectFreeData(map));
        //本班退单金额
        resMap.put("backData",apiCheckServiceMapper.selectBackData(map));

        //充值金额
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aa = loginInfo.get("login_time").toString();
        String bb = endTime;
        String cc = loginInfo.get("login_user").toString();
        String rechargeData = OkHttpUtils.memberNewGetRecharge(loginTime, endTime, loginInfo.get("login_user").toString());
        if(!StringUtils.isEmpty(rechargeData)){
            Map map1 = new Gson().fromJson(rechargeData, Map.class);
            if(map1.get("msgCode").toString().equalsIgnoreCase("OK")){
          		List<Map> recMap = (ArrayList<Map>)map1.get("body");
            	if(recMap.isEmpty()){
            		resMap.put("rechargeData",0.00);
            	} else {
                    resMap.put("rechargeData",recMap.get(0) == null ? 0.00 :recMap.get(0).get("payMoney"));
            	}
            }
        }
        
        //S账金额
        resMap.put("sbillData",apiCheckServiceMapper.selectSbillData(map));

        //消费金额、抹零金额、定额优惠、应收金额、实收金额
        Map clearingMoney = apiCheckServiceMapper.selectClearingWaterMoney(map);
        double shje = 0.0,pettyCash = 0.0,pledgeCash = 0.0;
        if(clearingMoney != null && clearingMoney.size() > 0){
        	if(loginInfo != null && loginInfo.size() > 0){
        		Object oshje = clearingMoney.get("shje");
        		if(oshje != null){
        			shje = Double.parseDouble(oshje+"");
        		}
        		//备用金
        		Object opettyCash = loginInfo.get("petty_cash");
        		if(opettyCash != null){
        			pettyCash = Double.parseDouble(opettyCash+"");
        		}
        		//押金
        		Map<String,Object> wtDepositDataMap = (Map) resMap.get("depositData");
        		Object wtDepositData = wtDepositDataMap.get("wtDepositData");
        		if(wtDepositData != null){
        			pledgeCash = Double.parseDouble(wtDepositData+"");
        		}
        		BigDecimal bdShje = new BigDecimal(shje);
        		BigDecimal bdPettyCash = new BigDecimal(pettyCash);
        		BigDecimal bdPledgeCash = new BigDecimal(pledgeCash);
        		BigDecimal total = new BigDecimal(0.0);
        		total = bdShje.add(bdPettyCash);
        		total = total.add(bdPledgeCash);
        		clearingMoney.put("shje", total.doubleValue());
        	}
        }else{
        	if(loginInfo != null && loginInfo.size() > 0){
        		Object opettyCash = loginInfo.get("petty_cash");
        		if(opettyCash != null){
        			pettyCash = Double.parseDouble(opettyCash+"");
        		}
        		BigDecimal total = new BigDecimal(pettyCash);
        		clearingMoney = new HashedMap();
        		clearingMoney.put("shje", total.doubleValue());
        	}
        }
        resMap.put("clearingMoney",clearingMoney);
        //服务费、最低消费补齐、包房费

        List<Map> maps = apiCheckServiceMapper.selectOpenWaterMoney(map);
        BigDecimal fwf = BigDecimal.ZERO,bff = BigDecimal.ZERO,zdxfbq = BigDecimal.ZERO,yhje = BigDecimal.ZERO;

        for(Map openWater:maps){
            if(!openWater.containsKey("free_servce_charge")){
                if(openWater.containsKey("modify_service_charge")){
                    BigDecimal b = new BigDecimal(openWater.get("modify_service_charge").toString());
                    fwf = fwf.add(b).setScale(2);
                }else{
                    if(openWater.containsKey("service_charge")){
                        BigDecimal b = new BigDecimal(openWater.get("service_charge").toString());
                        fwf = fwf.add(b).setScale(2);
                    }
                }
            }

            if(!openWater.containsKey("free_private_room")){
            	if(openWater.containsKey("modify_private_room")){
            		BigDecimal b = new BigDecimal(openWater.get("modify_private_room").toString());
            		bff = bff.add(b).setScale(2);
            	}else{
            		if(openWater.containsKey("private_room_cost")){
            			BigDecimal b = new BigDecimal(openWater.get("private_room_cost").toString());
            			bff = bff.add(b).setScale(2);
            		}
            	}
            }
            
            if(!openWater.containsKey("free_min_spend")){
                if(openWater.containsKey("minimum_charge_complete")){
                    BigDecimal b = new BigDecimal(openWater.get("minimum_charge_complete").toString());
                    zdxfbq = zdxfbq.add(b).setScale(2);
                }
            }
            
            if(openWater.containsKey("discount_costs")){
                BigDecimal b = new BigDecimal(openWater.get("discount_costs").toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                yhje = yhje.add(b).setScale(2);
            }

        }

        resMap.put("fwf",fwf);
        resMap.put("bff",bff);
        resMap.put("zdxfbq",zdxfbq);
        resMap.put("yhje",yhje);

        DgPos pos = new DgPos();
        pos.setId(loginPos);
        pos = dgPosMapper.getPosByID(pos);
        SysUser sysUser = sysUserMapper.selectByUsername(userCode);
        TbOrg org = new TbOrg();
        org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
        org = tbOrgMapper.getOrgByID(org);
        String openClassNumber = deskBusinessSettingService.createFlowNumber(org.getOrgCode(), pos.getNumber(),1,SerialRulEnum.JB);

        resMap.put("jbWater",openClassNumber);

        return resMap;
    }

    @Override
    public List<Map<String, Object>> selectCloseBillOpenWater(Integer seatId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("seatId", seatId);
        return apiCheckServiceMapper.selectCloseBillOpenWater(params);
    }

    @Override
    public Map<String, Object> checkCloseBillOpenWater(String owNum) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("owNum", owNum);
        return apiCheckServiceMapper.checkCloseBillOpenWater(params);
    }

    @Override
    public List<Map<String, Object>> checkCashPledgeOpenWater(String cpType,String owNum) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cpType", cpType);
        params.put("owNum", owNum);
        return apiCheckServiceMapper.checkCashPledgeOpenWater(params);
    }

    @Override
    public int closeBillForOpenWater(String userCode,Integer owState,String owNum) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("owState", owState);
        params.put("owNum", owNum);
        apiCheckServiceMapper.closeBillForOpenWater(params);
        //插入日志
        DgOpenWater dow = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(dow.getSeatId());
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 11, owNum, 
  				seat.getName(), "操作客位:"+seat.getName()+",做了关账操作",new SimpleDateFormat("yyyy_MM").format(new Date())));
        return 1;
    }

    @Override
    public int closeBillForSeat(Integer seatState,String seatId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("seatState", seatState);
        params.put("seatId", seatId);
        return apiCheckServiceMapper.closeBillForSeat(params);
    }

    @Override
    public Map selectAllDetails(String owNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("owNum",owNum);
        //先查询出向该营业流水转账的营业流水
        //TODO 缺少POS名字，服务名字，开单时间格式化 2016年12月27日11:42:26

        Map map1 = apiCheckServiceMapper.selectOpenWaterByOwNUm(map);

        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectAllOpenWaterNum(map);
        for(DgOpenWater dgOpenWater:dgOpenWaters){
            List<DgOwConsumerDetails> dgOwConsumerDetailss1 = selectOpenWaterWithService(dgOpenWater.getOwNum());
            dgOpenWater.setItemFileInfos(dgOwConsumerDetailss1);
            if(dgOpenWater.getTransferTarget() != null){
                dgOpenWater.setOwNum(dgOpenWater.getOwNum()+"（转）");
            }
        }
        map1.put("detailsList",dgOpenWaters);
        return map1;
    }

    /**
     *
     * @param dgOwConsumerDetailss 退单集合
     * @param checkObj 需要检测是品项数据
     * @return
     */
    private Map<String,Object> setItemFileNum(List<DgOwConsumerDetails> dgOwConsumerDetailss, DgOwConsumerDetails checkObj) {
        Map<String, Object> map = new HashMap<>();
        for (Iterator<DgOwConsumerDetails> it = dgOwConsumerDetailss.iterator(); it.hasNext(); ) {
            DgOwConsumerDetails dgOwConsumerDetails = it.next();
            if (dgOwConsumerDetails.getBackOwId().equals(checkObj.getOwId())) {
                if (dgOwConsumerDetails.getItemFileNumber() != null && checkObj.getItemFileNumber() != null) {
                    if (checkObj.getItemFileId().equals(dgOwConsumerDetails.getItemFileId())) {
                        //新增原始数量
                        checkObj.setSubNumber(dgOwConsumerDetails.getItemFileNumber());
                        checkObj.setItemFileNumber(checkObj.getItemFileNumber() + dgOwConsumerDetails.getItemFileNumber());
                        checkObj.setSubtotal(MathExtend.multiply(checkObj.getItemFileNumber(),checkObj.getItemFinalPrice()));
                        it.remove();
                    }
                }
            }
        }
        map.put("list", dgOwConsumerDetailss);
        map.put("obj", checkObj);
        return map;
    }

    private Map<String,Object> setItemFileNumShowTD(List<DgOwConsumerDetails> dgOwConsumerDetailss, DgOwConsumerDetails checkObj) {
        Map<String, Object> map = new HashMap<>();
        List<DgOwConsumerDetails> tdInfo = new ArrayList<>();
        for (Iterator<DgOwConsumerDetails> it = dgOwConsumerDetailss.iterator(); it.hasNext(); ) {
            DgOwConsumerDetails dgOwConsumerDetails = it.next();
            if (dgOwConsumerDetails.getBackOwId().equals(checkObj.getOwId())) {
                if (dgOwConsumerDetails.getItemFileNumber() != null && checkObj.getItemFileNumber() != null) {
                    if (checkObj.getItemFileId().equals(dgOwConsumerDetails.getItemFileId())) {
                        checkObj.setItemFileNumber(checkObj.getItemFileNumber() + dgOwConsumerDetails.getItemFileNumber());
                        checkObj.setSubtotal(MathExtend.multiply(checkObj.getItemFileNumber(),checkObj.getItemFinalPrice()));
                        tdInfo.add(dgOwConsumerDetails);
                        it.remove();
                    }
                }
            }
        }
        map.put("list", dgOwConsumerDetailss);
        map.put("obj", checkObj);
        map.put("tdList", tdInfo);
        return map;
    }

    private String getMealInt() {
        List<TbBis> tbBiss = tbBisMapper.selectAllBis();
        List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
        if (tbBiss.size() == 0) {
            return "请到后台设置市别";
        }
        for (int i = 0; i < tbBiss.size(); i++) {
            if (i != tbBiss.size() - 1) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(i).getBisTime());
                m.put("endTime", tbBiss.get(i + 1).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("TbBis", tbBiss.get(i));
                timeD.add(m);
            } else {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(i).getBisTime());
                m.put("endTime", tbBiss.get(0).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("TbBis", tbBiss.get(0));
                timeD.add(m);
            }
        }
        String TbisName = null; // 获取市别id
        for (int i = 0; i < timeD.size(); i++) {
            int count = tbBisMapper.calculateSJD(timeD.get(i));
            if (count > 0) {
                TbisName = ((TbBis) timeD.get(i).get("TbBis")).getBisName();
                break;
            }
        }
        if (TbisName == null) // 没有就是最后个时段
        {
            TbisName = ((TbBis) timeD.get(timeD.size() - 1).get("TbBis"))
                    .getBisName();
        }
        return TbisName;
    }

    /***************************************买单结算相关方法*****************************************/
    @Override
    public List<DgOpenWater> selectAllOpenWaterByOwNum(String owNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("owNum",owNum);
        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectAllOpenWaterByOwNum(map);
        if(dgOpenWaters.size() < 1){
            return null;
        }
        List<DgCashPledge> dgCashPledges = apiCheckServiceMapper.selectAllDepositByOwNums(dgOpenWaters);
        for (DgOpenWater dgOpenWater : dgOpenWaters) {
            if(dgOpenWater.getTransferTarget() != null && !StringUtils.isEmpty(dgOpenWater.getJoinTeamNotes())){
                String joinTeamNotes = dgOpenWater.getJoinTeamNotes();
//                dgOpenWater.setSeatName(joinTeamNotes.split("，")[1].split("：")[1]+"-"+joinTeamNotes.split("，")[2].split("：")[1]);
                dgOpenWater.setSeatName(joinTeamNotes.split("，")[2].split("：")[1]);
            }

            for (DgCashPledge dgCashPledge : dgCashPledges) {
                Double deposit = dgOpenWater.getDeposit() == null?0.:dgOpenWater.getDeposit();
                if(dgOpenWater.getOwNum().equals(dgCashPledge.getOwNum())){
                    if(dgCashPledge.getCpType().equals("0")){
                        dgOpenWater.setDeposit(MathExtend.add(deposit,dgCashPledge.getCpMoney()));
                    }else if(dgCashPledge.getCpType().equals("1")){
                        dgOpenWater.setDeposit(MathExtend.subtract(deposit,dgCashPledge.getCpMoney()));
                    }
                }
            }
        }
        return dgOpenWaters;
    }

    @Override
    public List<DgSettlementWay> selectAllPayWay(String userCode) {
        SysPerBusiness sysPerBusiness = businessPermissionService.selectBusinessByUserCode(userCode);
        if(sysPerBusiness == null){
            return null;
        }

        List<String> list = new ArrayList();

        if(sysPerBusiness.getXykQx() == null){//信用卡
            list.add("CREDIT_CARD");
        }

        if(sysPerBusiness.getZpQx() == null){//支票
            list.add("CHECK");
        }

        if(sysPerBusiness.getHygzQx() == null){//会员挂账以及非会员挂账
            list.add("HYGZ");
        }

        if(sysPerBusiness.getHykQx() == null){//会员支付
            list.add("HYZF");
        }

        if(sysPerBusiness.getQtOneQx() == null){//微信支付权限
            list.add("WECHAT");
        }

        if(sysPerBusiness.getQtTwoQx() == null){//支付宝权限
            list.add("ALIPAY");
        }

        return  apiCheckServiceMapper.selectAllPayWay(list);
    }

    @Override
    public void getOpenWaterTotalPrice(DgOwConsumerDetails dgOwConsumerDetails, String hyLevelId, Integer orgId,
                                       Double generalProportions, Double singleProportions) {
        Integer itemFileId = dgOwConsumerDetails.getItemFileId();


        //todo 此处品项循环查询待优化
        Map<Integer, Object> zyhdItemPrice = getZyhdItemPrice(dgOwConsumerDetails); //重要活动价格
        Map<Integer, Object> pxDzFaPrice = getPxDzFaPrice(dgOwConsumerDetails); // 品项打折价格
        //会员价格不参与比例优惠以及全单优惠，2017年8月15日10:07:31  以下代码暂时不执行
        if(!StringUtils.isEmpty(hyLevelId)){
            Map<Integer, Object> hyPrice = getHyPrice(itemFileId,hyLevelId,orgId); //会员价格
            if(hyPrice != null && hyPrice.size() > 0){
            	dgOwConsumerDetails.setHydzItemFilePrice(hyPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(hyPrice.get(itemFileId).toString()));
            }else{
            	dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
            }
        }else{
            dgOwConsumerDetails.setHydzItemFilePrice(0.);
        }
        //重要活动价格，如果是埋单，存在比例优惠以及全单优惠，将优惠后的价格设置xxxDiscount，并将优惠前的价格设置xxx
        if(generalProportions != null || singleProportions != null){
            dgOwConsumerDetails.setZyhdItemFilePriceDiscount(zyhdItemPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():priceCount(zyhdItemPrice.get(itemFileId).toString(),generalProportions,singleProportions));
            dgOwConsumerDetails.setPxdzItemFilePriceDiscount(pxDzFaPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():priceCount(pxDzFaPrice.get(itemFileId).toString(),generalProportions,singleProportions));

            //设置重要活动以及品项打折之后的品项小计，每一个具体品项三种不同单价 * 数量 + 制作费用
            dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getPxdzItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
            dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getZyhdItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
        }
        dgOwConsumerDetails.setZyhdItemFilePrice(zyhdItemPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(zyhdItemPrice.get(itemFileId).toString()));

        dgOwConsumerDetails.setPxdzItemFilePrice(pxDzFaPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(pxDzFaPrice.get(itemFileId).toString()));

        //设置每一个具体品项三种不同单价 * 数量 + 制作费用
        dgOwConsumerDetails.setPxdzItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getPxdzItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
        dgOwConsumerDetails.setZyhdItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getZyhdItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
        dgOwConsumerDetails.setHydzItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getHydzItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));

    }

    //全单,比例优惠
    public void getOpenWaterForDz(DgOwConsumerDetails dgOwConsumerDetails,Double generalProportions, Double singleProportions) {
		Integer itemFileId = dgOwConsumerDetails.getItemFileId();
		Integer yxdzInteger = dgOwConsumerDetails.getYxdz();
		//todo 此处品项循环查询待优化
		Map<Integer, Object> zyhdItemPrice = getZyhdItemPrice(dgOwConsumerDetails); //重要活动价格
		Map<Integer, Object> pxDzFaPrice = getPxDzFaPrice(dgOwConsumerDetails); // 品项打折价格
		dgOwConsumerDetails.setHydzItemFilePrice(0.);
		//重要活动价格，如果是埋单，存在比例优惠以及全单优惠，将优惠后的价格设置xxxDiscount，并将优惠前的价格设置xxx
		if(generalProportions != null){
			if(yxdzInteger.equals(1)){
				dgOwConsumerDetails.setZyhdItemFilePriceDiscount(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),MathExtend.divide(generalProportions,100,2)));
				dgOwConsumerDetails.setPxdzItemFilePriceDiscount(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),MathExtend.divide(generalProportions,100,2)));

				//设置重要活动以及品项打折之后的品项小计，每一个具体品项三种不同单价 * 数量 + 制作费用
				dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getPxdzItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
				dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getZyhdItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
			}
		}
		if(singleProportions != null){
			dgOwConsumerDetails.setZyhdItemFilePriceDiscount(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),MathExtend.divide(singleProportions,100,2)));
			dgOwConsumerDetails.setPxdzItemFilePriceDiscount(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),MathExtend.divide(singleProportions,100,2)));

			//设置重要活动以及品项打折之后的品项小计，每一个具体品项三种不同单价 * 数量 + 制作费用
			dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getPxdzItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
			dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getZyhdItemFilePriceDiscount(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
		}
		dgOwConsumerDetails.setZyhdItemFilePrice(zyhdItemPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(zyhdItemPrice.get(itemFileId).toString()));

		dgOwConsumerDetails.setPxdzItemFilePrice(pxDzFaPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(pxDzFaPrice.get(itemFileId).toString()));

		//设置每一个具体品项三种不同单价 * 数量 + 制作费用
		dgOwConsumerDetails.setPxdzItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getPxdzItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
		dgOwConsumerDetails.setZyhdItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getZyhdItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
		dgOwConsumerDetails.setHydzItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getHydzItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));


		if(dgOwConsumerDetails.getZyhdItemFilePriceDiscount() == null){
			dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getZyhdItemFilePrice());
			dgOwConsumerDetails.setZyhdItemCostsSumDiscount(dgOwConsumerDetails.getZyhdItemCostsSum());
		}

		if(dgOwConsumerDetails.getPxdzItemFilePriceDiscount() == null){
			dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getPxdzItemFilePrice());
			dgOwConsumerDetails.setPxdzItemCostsSumDiscount(dgOwConsumerDetails.getPxdzItemCostsSum());
		}
		
		if(dgOwConsumerDetails.getNotes().equals("赠单")){
			dgOwConsumerDetails.setZyhdItemFilePriceDiscount(0.0);
			dgOwConsumerDetails.setPxdzItemFilePriceDiscount(0.0);

			//设置重要活动以及品项打折之后的品项小计，每一个具体品项三种不同单价 * 数量 + 制作费用
			dgOwConsumerDetails.setPxdzItemCostsSumDiscount(0.0);
			dgOwConsumerDetails.setZyhdItemCostsSumDiscount(0.0);
			
			dgOwConsumerDetails.setZyhdItemFilePrice(0.0);
			dgOwConsumerDetails.setPxdzItemFilePrice(0.0);
			
			dgOwConsumerDetails.setPxdzItemCostsSum(0.0);
			dgOwConsumerDetails.setZyhdItemCostsSum(0.0);
			dgOwConsumerDetails.setHydzItemCostsSum(0.0);
		}

	}

    @Override
    public void getOpenWaterHyPrice(DgOwConsumerDetails dgOwConsumerDetails, String hyLevelId, Integer orgId) {
        Integer itemFileId = dgOwConsumerDetails.getItemFileId();
        //会员价格不参与比例优惠以及全单优惠
        if(!StringUtils.isEmpty(hyLevelId)){
            Map<Integer, Object> hyPrice = getHyPrice(itemFileId,hyLevelId,orgId); //会员价格
            dgOwConsumerDetails.setHydzItemFilePrice(hyPrice.containsKey(-1)?dgOwConsumerDetails.getItemFinalPrice():Double.parseDouble(hyPrice.get(itemFileId).toString()));
        }else{
            dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
        }

        dgOwConsumerDetails.setHydzItemCostsSum(MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getHydzItemFilePrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
    }

    Double priceCount(String price,Double generalProportions,Double singleProportions){
        Double douValue = Double.parseDouble(price);
        if(generalProportions != null){
            generalProportions = MathExtend.divide(generalProportions,100,2);
            return MathExtend.multiply(douValue,generalProportions);
        }
        if(singleProportions != null){
            singleProportions = MathExtend.divide(singleProportions,100,2);
            return MathExtend.multiply(douValue,singleProportions);
        }
        return douValue;
    }

    @Override
    public Double getOtherCost(DBSBillServDTO dbsBillServDTO, DgOpenWater dgOpenWater, String type) {
        Double zdxfbq = 0.0,fwf = 0.0,bff = 0.0,zdxf = 0.0, itemCostSum = dgOpenWater.getItemCostsSum();
        DgSeatManager dgSeatManagers = dgSeatManagerService.selectBySeatId(dgOpenWater.getSeatId());
        if(dgSeatManagers != null) {
            List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
            //收取最低消费,计算最低消费补齐价格
            if (dgSeatManagers.getZdxf() == 2) {
                zdxf = dgSeatManagers.getZdxfMoney();
                //最低消费类型为按客位
                if (dgSeatManagers.getZdxfType() == 1) {
                    if(itemCostSum < dgSeatManagers.getZdxfMoney()){
                        zdxfbq = MathExtend.subtract(dgSeatManagers.getZdxfMoney(),dgOpenWater.getItemCostsSum());
                    }
                } else if (dgSeatManagers.getZdxfType() == 2) { //最低消费类型为按人数
                    double multiply = MathExtend.multiply(dgSeatManagers.getZdxfMoney(), dgOpenWater.getPeopleCount());
                    zdxf = multiply;
                    if(itemCostSum < multiply){
                        zdxfbq =  MathExtend.subtract(zdxf,dgOpenWater.getItemCostsSum());
                    }
                }
            }

            //客位设置的消费满免服务费，默认为0
            Double fwfSx = dgSeatManagers.getFwfSx();
            //客位设置的消耗满免包房费，默认为0
            Double bffConFree = dgSeatManagers.getBffConFree();

            //客位设置不收取服务费，将消耗满免服务费设置为账单服务的额度
            if(dgSeatManagers.getFwf() == 1){
                Double noServiceChargeOverMoney = dbsBillServDTO.getNoServiceChargeOverMoney();
                if(noServiceChargeOverMoney != null){
                    fwfSx = noServiceChargeOverMoney;
                }
            }

            //客位设置不收取包房费，将消耗满免包房费设置为账单服务的额度
            if(dgSeatManagers.getBff() == 1){
                Double noRoomuseChargeOverMoney = dbsBillServDTO.getNoRoomuseChargeOverMoney();
                if(noRoomuseChargeOverMoney != null){
                    fwfSx = noRoomuseChargeOverMoney;
                }
            }else{
                Double itemSum = dgOpenWater.getItemCostsSum();
                //收取包房费
                Integer bffType = dgSeatManagers.getBff();

                if(bffType == 2){ //按固定金额收取
                    bff = dgSeatManagers.getBffGd() == null?0.:dgSeatManagers.getBffGd();
                }else if(bffType == 3){//按客位人数收取
                    bff = MathExtend.multiply(
                            dgSeatManagers.getBffPeople(),
                            dgOpenWater.getPeopleCount());
                }else if(bffType == 4){//固定包房收费方案
                    bff = apiModifyService.calculationBff(dgSeatManagers.getBffGdPro(), DateUtil.getDateByFormat(dgOpenWater.getFirstTime(),"yyyy-MM-dd HH:mm:ss"), new Date());
                }else if(bffType == 5){//一周内设置不同的包房收费方案
                    String bffWeekProD = dgSeatManagers.getBffWeekProD();
                    if(bffWeekProD.length() < 10){
                        bff = 0.0;
                    }else{
                        int dgBffWeekId = 0;
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
                        if (weekIndex < 0) {
                            weekIndex = 7;
                        }
                        JSONArray arr = JSONArray.fromObject(dgSeatManagers
                                .getBffWeekProD());
                        if (arr.size() > 0) {
                            for (int i = 0; i < arr.size(); i++) {
                                JSONObject wObject = arr.getJSONObject(i);
                                if (weekIndex == Integer.valueOf(wObject
                                        .getString("id"))) {
                                    dgBffWeekId = Integer.valueOf(wObject
                                            .getString("faId"));
                                }
                            }
                        }
                        bff = apiModifyService.calculationBff(dgBffWeekId,
                                DateUtil.getDateByFormat(dgOpenWater.getFirstTime(),"yyyy-MM-dd HH:mm:ss"),
                                new Date());
                    }
                }

                if(bffConFree != 0.){
                    if(itemSum >= bffConFree){
                        bff = 0.;
                        dgOpenWater.setFreePR(true);
                    }
                }
            }

            //收取服务费
            if (dgSeatManagers.getFwf() != 1) {
               Double fwfCanFrss = dgSeatManagers.getFwfConFree(),
                        itemSum = dgOpenWater.getItemCostsSum();
               if(dgSeatManagers.getFwf() != null ){
                   if ( dgSeatManagers.getFwf() == 2) {//服务费收取固定金额
                       if (itemSum < fwfCanFrss) {
                           fwf = checkFwfsx(fwfSx, dgSeatManagers.getFwfGd());
                       }
                   } else if (dgSeatManagers.getFwf() == 3) {//服务费按消费比例
                       if (itemSum < fwfCanFrss) {
                           BigDecimal itemS = new BigDecimal(itemSum.toString()).setScale(2);
                           BigDecimal fwfXfRagtio = new BigDecimal(dgSeatManagers.getFwfXfRatio()).setScale(2);
                           double blFwf = itemS.divide(fwfXfRagtio,2,BigDecimal.ROUND_HALF_UP).setScale(2).doubleValue();
                           fwf = checkFwfsx(fwfSx, blFwf);
                       }
                   } else if (dgSeatManagers.getFwf() == 4) {//服务费按客位人数
                       if (itemSum < fwfCanFrss) {
                           fwf = checkFwfsx(fwfSx,MathExtend.multiply(dgOpenWater.getPeopleCount(),dgSeatManagers.getFwfPeople()));
                       }
                   }
               }
            }
        }

        //营业流水的4种小计，计算方式都为最低消费补齐价格+营业流水下所有的品项价格+服务费+包房费
//        Double otherCosts = zdxfbq + fwf + bff;
//        Double otherCosts = zdxfbq + bff;
          Double otherCosts = zdxfbq;

        if(type.equalsIgnoreCase("all") || type.equalsIgnoreCase("hd") || type.equalsIgnoreCase("yxe")){
            dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdItemSubtotal());
//            dgOpenWater.setZyhdSubtotal(otherCosts + dgOpenWater.getZyhdItemSubtotal());
        }
        if(type.equalsIgnoreCase("all") || type.equalsIgnoreCase("px") || type.equalsIgnoreCase("yxe")){
            dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzItemSubtotal());
//            dgOpenWater.setPxdzSubtotal(otherCosts + dgOpenWater.getPxdzItemSubtotal());
        }
        if(type.equalsIgnoreCase("all") || type.equalsIgnoreCase("hy")){
            dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzItemSubtotal());
//            dgOpenWater.setHydzSubtotal(otherCosts + dgOpenWater.getHydzItemSubtotal());
        }

        dgOpenWater.setPrivateRoomCost(bff);
        dgOpenWater.setServiceCharge(fwf);
        dgOpenWater.setMinimumConsumption(zdxf);

        return fwf;
    }

    @Override
    public List<Map> selectIsZyhd() {
        return apiCheckServiceMapper.selectIsZyhd();
    }

    @Override
    public void modifyBingdingMember(String owNum, String crId) {
        Map<String,Object> map = new HashMap<>();
        map.put("owNum",owNum);
        map.put("crId",crId);
        apiCheckServiceMapper.modifyBingdingMember
                (map);
    }

    @Override
    public Integer modifyCheckOwNumHasItem(String owNums) {
        List<String> owNumList = new Gson().fromJson(owNums, new TypeToken<List<String>>() {
        }.getType());

        for(String owNum:owNumList){
            List<DgOwConsumerDetails> dgOwConsumerDetailss = selectOpenWaterWithService(owNum);
            if(dgOwConsumerDetailss.size() < 1){
                return -1;
            }
        }
        return null;
    }

    @Override
    public Integer modifyCheckOwNumHasItemWithOutJson(String owNum) {
        List<DgOwConsumerDetails> dgOwConsumerDetailss = selectOpenWaterWithService(owNum);
        if(dgOwConsumerDetailss.size() < 1){
            return -1;
        }
        return null;
    }

    @Override
    public void modifyOpenWaterLock(String owNums,String userCode,Integer type,Integer pos) {
        List<String> owNumList = new Gson().fromJson(owNums, new TypeToken<List<String>>() {
        }.getType());
        for(String owNum:owNumList){
            Map<String,Object> map = new HashMap<>();
            map.put("userCode",userCode);
            map.put("owNum",owNum);
            map.put("type",type);
            map.put("pos",pos);
            if(type == 2){//解除结算锁定，判断取消结算锁定的营业 流水是否被埋单
                DgOpenWater dgOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);
                if(dgOpenWater.getClearingWaterId() != null){//该营业流水被埋单
                    map.put("type",3);
                    if(dgOpenWater.getTransferTarget() != null){//该营业流水是转账的营业流水并且被埋单
                        map.put("type",5);
                    }
                }else if(dgOpenWater.getClearingWaterId() == null){
                    if(dgOpenWater.getTransferTarget() != null){//该营业流水是转账的营业流水未被埋单
                        map.put("type",4);
                    }
                }
            }
            apiCheckServiceMapper.modifyOpenWaterLock(map);
            if(type == 1){
                map.put("state",9);
                apiCheckServiceMapper.insertLockLog(map);
            }
        }
    }

    @Override
    public void modifyOpenWaterLock(List<DgOpenWater> dgOpenWaters,String userCode,Integer type,Integer pos) {
        for(DgOpenWater dgOpenWater:dgOpenWaters){
            Map<String,Object> map = new HashMap<>();
            map.put("userCode",userCode);
            map.put("owNum",dgOpenWater.getOwNum());
            map.put("type",type);
            map.put("pos",pos);
            apiCheckServiceMapper.modifyOpenWaterLock(map);
            if(type == 1){
                map.put("state",9);
                apiCheckServiceMapper.insertLockLog(map);
            }
        }
    }

    @Override
    public DgOwLockinfo selectOpenWaterLock(String owNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("owNum",owNum);
        return apiCheckServiceMapper.selectOpenWaterLock(map);
    }

    @Override
    public List<DgOpenWater> modifyPercentageDiscount(String openWaterData, Double proportion, Integer type) {
        Double percent = MathExtend.divide(proportion,100.);

        Gson gson = new Gson();
        List<DgOpenWater> dgOpenWaters = gson.fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
        }.getType());

        //常规优惠比例，考虑允许打折的品项数据
        List<DgItemFile> dgItemFiles = null;
        if(type == 1)dgItemFiles = apiCheckServiceMapper.selectAllCanDiscountItemData();

        for(DgOpenWater dgOpenWater:dgOpenWaters){
            Double  zyhdItemSubtotal = 0.0,pxdzItemSubtotal = 0.0;
            List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
            for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
                DgItemFile dgItemFile = dgItemFileMapper.selectByPrimaryKey(dgOwConsumerDetails.getItemFileId());

                if((type == 1 && dgItemFile.getYxdz() ==1)|| type == 2){
                    dgOwConsumerDetails.setDiscount(percent);

                    //单价折扣后的价格
                    dgOwConsumerDetails.setZyhdItemFilePriceDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),percent));
                    dgOwConsumerDetails.setPxdzItemFilePriceDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),percent));

                    //品项小计折扣后的而价格
                    dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                    dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));

                    //每一个营业流水下三种营业流水品项折扣后的小计
                    zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                    pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                }else{
                    //如果该品项不能优惠，但是传入的优惠值不是1,则将价格还原
                    if(dgOwConsumerDetails.getDiscount() != 1){

                        //将折扣值还原成1
                        dgOwConsumerDetails.setDiscount(1.0);

                        dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getZyhdItemFilePrice());
                        dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getPxdzItemFilePrice());

                        dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                        dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));

                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                    }else if(dgOwConsumerDetails.getDiscount() == 1){
                        dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getZyhdItemFilePrice());
                        dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getZyhdItemFilePrice());

                        dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                        dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));

                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                    }
                }
            }

            dgOpenWater.setItemFileInfos(itemFileInfos);

            //打折前原本的三种单价以及小计
            Double oldZyhdItemSubtotal = dgOpenWater.getZyhdItemSubtotal();
            Double oldPxdzItemSubtotal = dgOpenWater.getPxdzItemSubtotal();

            Double oldPxdzSubtotal = dgOpenWater.getPxdzSubtotal();
            Double oldZyhdSubtotal = dgOpenWater.getZyhdSubtotal();

            //设置打折后的三种单价以及小计
            dgOpenWater.setPxdzSubtotal(MathExtend.add(MathExtend.subtract(oldPxdzSubtotal,oldPxdzItemSubtotal),pxdzItemSubtotal));
            dgOpenWater.setZyhdSubtotal(MathExtend.add(MathExtend.subtract(oldZyhdSubtotal,oldZyhdItemSubtotal),zyhdItemSubtotal));

            dgOpenWater.setPxdzItemSubtotal(pxdzItemSubtotal);
            dgOpenWater.setZyhdItemSubtotal(zyhdItemSubtotal);

        }
        return dgOpenWaters;

    }

    @Override
    public List<DgOwDetailsOther> selectDetailOtherInfo(Integer itemFileId, Integer serviceId) {
        Map<String,Object> openWaterDetailParam = new HashMap<>();
        openWaterDetailParam.put("itemFileId",itemFileId);
        openWaterDetailParam.put("serviceId",serviceId);
        List<DgOwDetailsOther> dgOwDetailsOthers = apiCheckServiceMapper.selectDetailOtherInfo(openWaterDetailParam);
        return dgOwDetailsOthers;
    }

    @Override
    public List<Map> modifyCouponMoney(List<Map> map1, Double subtract, List<DgOpenWater> dgOpenWaters) {
    	if(map1 == null){
    		return map1;
    	}
        for(Map coupon:map1){
            if(coupon.get("typeId").equals("0")){//现金券
                BigDecimal couponAmount = new BigDecimal(coupon.get("preAmount").toString()).setScale(2);
                BigDecimal hyys = BigDecimal.valueOf(subtract).setScale(2);
                coupon.put("couponMoney",couponAmount);
                if(hyys.compareTo(couponAmount) >= 0){ //优惠券金额大于或者等于应收，
                    coupon.put("ysje",BigDecimal.ZERO);
                }else{//优惠券金额小于应收
                    coupon.put("ysje",couponAmount.subtract(hyys));
                }
            }else if(coupon.get("typeId").equals("1")){//打折券，根据品项来进行打折
                String dictId = coupon.get("dictId").toString();
                BigDecimal discount = new BigDecimal(coupon.get("preAmount").toString()).setScale(2);

                BigDecimal coupHyys = BigDecimal.ZERO,
                        coupMoney = BigDecimal.ZERO;

                for(DgOpenWater dgOpenWater:dgOpenWaters){
                    List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
                    for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
                        if(dgOwConsumerDetails.getIsPriceCal() == 0){
                            BigDecimal coupItemCostSum = BigDecimal.valueOf(dgOwConsumerDetails.getHydzItemCostsSum());//优惠前之后的金额
                            if(dictId.equals(dgOwConsumerDetails.getCouponItemType())){
                                BigDecimal afterDiscount = coupItemCostSum.multiply(discount);//优惠后的金额

                                BigDecimal discountMoney = coupItemCostSum.subtract(afterDiscount).setScale(2); //优惠的金额

                                coupHyys = coupHyys.add(afterDiscount); //每个营业流水下允许优惠后的金额累加
                                coupMoney = coupMoney.add(discountMoney); //每个营业流水下优惠的金额累加
                            }else{
                                coupHyys = coupHyys.add(coupItemCostSum);//每个营业流水下不允许优惠的金额累加
                                coupMoney = coupMoney.add(BigDecimal.ZERO); //每个营业流水下优惠的金额累加
                            }
                        }else{
                            coupHyys = coupHyys.add(BigDecimal.valueOf(dgOwConsumerDetails.getSubtotal()));//每个营业流水下不允许优惠的金额累加
                            coupMoney = coupMoney.add(BigDecimal.ZERO); //每个营业流水下优惠的金额累加
                        }
                    }
                }

                coupon.put("couponMoney",coupMoney);
                coupon.put("ysje",coupHyys);

            }
        }
        return map1;
    }

    @Override
    public List<Map> modifyCouponMoney(List<Map> map1, Double subtract, DgOpenWater dgOpenWater) {
        for(Map coupon:map1){
            if(coupon.get("typeId").equals("0")){//现金券
                BigDecimal couponAmount = new BigDecimal(coupon.get("preAmount").toString()).setScale(2);
                BigDecimal hyys = BigDecimal.valueOf(subtract).setScale(2);
                coupon.put("couponMoney",couponAmount);
                if(hyys.compareTo(couponAmount) >= 0){ //优惠券金额大于或者等于应收，
                    coupon.put("ysje",BigDecimal.ZERO);
                }else{//优惠券金额小于应收
                    coupon.put("ysje",couponAmount.subtract(hyys));
                }
            }else if(coupon.get("typeId").equals("1")) {//打折券，根据品项来进行打折
                String dictId = coupon.get("dictId").toString();
                BigDecimal discount = new BigDecimal(coupon.get("preAmount").toString()).setScale(2);

                BigDecimal coupHyys = BigDecimal.ZERO,
                        coupMoney = BigDecimal.ZERO;

                List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
                for (DgOwConsumerDetails dgOwConsumerDetails : itemFileInfos) {
                    if (dgOwConsumerDetails.getIsPriceCal() == 0) {
                        BigDecimal coupItemCostSum = BigDecimal.valueOf(dgOwConsumerDetails.getHydzItemCostsSum());//优惠前之后的金额
                        if (dictId.equals(dgOwConsumerDetails.getCouponItemType())) {
                            BigDecimal afterDiscount = coupItemCostSum.multiply(discount);//优惠后的金额

                            BigDecimal discountMoney = coupItemCostSum.subtract(afterDiscount).setScale(2); //优惠的金额

                            coupHyys = coupHyys.add(afterDiscount); //每个营业流水下允许优惠后的金额累加
                            coupMoney = coupMoney.add(discountMoney); //每个营业流水下优惠的金额累加
                        } else {
                            coupHyys = coupHyys.add(coupItemCostSum);//每个营业流水下不允许优惠的金额累加
                            coupMoney = coupMoney.add(BigDecimal.ZERO); //每个营业流水下优惠的金额累加
                        }
                    } else {
                        coupHyys = coupHyys.add(BigDecimal.valueOf(dgOwConsumerDetails.getSubtotal()));//每个营业流水下不允许优惠的金额累加
                        coupMoney = coupMoney.add(BigDecimal.ZERO); //每个营业流水下优惠的金额累加
                    }
                }

                coupon.put("couponMoney", coupMoney);
                coupon.put("ysje", coupHyys);
            }
        }
        return map1;
    }

    @Override
    public void openClassModify(String userCode, Integer loginPos) {

        Map<String,Object> map = new HashMap<>();

    	Map<String,Object> loginInfoParams = new HashMap<String,Object>();
    	loginInfoParams.put("userCode", userCode);
    	loginInfoParams.put("loginPos", loginPos);
        Map loginInfo = apiCheckServiceMapper.selectUserLoginInfo(loginInfoParams);

        map.putAll(loginInfo);

        Map map1 = selectOpenClassReport(userCode, loginPos);

        map.putAll(map1);

        map.put("endTime",new Date());

        apiCheckServiceMapper.modifyOpenClassWater(map);
        
        //插入日志
        DgPos dgPos = new DgPos();
        dgPos.setId(loginPos);
        dgPos = dgPosMapper.getPosByID(dgPos);
  		SysUser user= sysUserMapper.chekUserCode(userCode);
  		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName(),dgPos.getName(), new Date(), 14, "","",
  				user.getEmpName()+"做了结班操作,结班时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+",结班pos:"+dgPos.getName(),
  				new SimpleDateFormat("yyyy_MM").format(new Date())));

    }

    @Override
    public Integer modifyCheckOwNumHasDeposit(String s) {
        List<String> owNumList = new Gson().fromJson(s, new TypeToken<List<String>>() {
        }.getType());

        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectAllDepositByStringOwNums(owNumList);
        List<DgCashPledge> dgCashPledges = apiCheckServiceMapper.selectAllDepositByOwNums(dgOpenWaters);
        for (DgOpenWater dgOpenWater : dgOpenWaters) {
            for (DgCashPledge dgCashPledge : dgCashPledges) {
                Double deposit = dgOpenWater.getDeposit() == null?0.:dgOpenWater.getDeposit();
                if(dgOpenWater.getOwNum().equals(dgCashPledge.getOwNum())){
                    if(dgCashPledge.getCpType().equals("0")){
                        dgOpenWater.setDeposit(MathExtend.add(deposit,dgCashPledge.getCpMoney()));
                    }else if(dgCashPledge.getCpType().equals("1")){
                        dgOpenWater.setDeposit(MathExtend.subtract(deposit,dgCashPledge.getCpMoney()));
                    }
                }
            }
        }

        for (DgOpenWater dgOpenWater : dgOpenWaters) {
            if(dgOpenWater.getDeposit() != null &&  dgOpenWater.getDeposit() != 0){
                return -1;
            }
        }
        return null;
    }

    
    @Override
	public Double wxGetItemFileTypeTotal(List<DgOpenWater> dgOpenWaters,Integer purpose) {
		// TODO Auto-generated method stu
        //常规优惠比例，考虑允许打折的品项数据
        List<DgItemFile> dgItemFiles = null;
        
        List<String> ids = new ArrayList();
        if(purpose.equals(1)){ //菜品
        	ids.add("001");
        	ids.add("002");
        } else if(purpose.equals(2)){ //锅底
        	ids.add("004");
        } else if(purpose.equals(3)){ //酒水
        	ids.add("003");
        } else if(purpose.equals(4)){ //小吃
        	ids.add("005");
        }

        //防止空数据错误
        if(ids.isEmpty()){
        	ids.add("123");
        }

        dgItemFiles = apiCheckServiceMapper.selectWxAllCanDiscountItemData(ids);

        Double pxdzSubtotal = 0.0;

        for(DgOpenWater dgOpenWater:dgOpenWaters){
            List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
            for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
                DgItemFile dgItemFile = dgItemFileMapper.selectByPrimaryKey(dgOwConsumerDetails.getItemFileId());

                if(sgdsIsInList(dgItemFile.getId(),dgItemFiles)){

                	//计算合计
                    pxdzSubtotal = MathExtend.add(checkVluesIsNull(pxdzSubtotal),MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),dgOwConsumerDetails.getItemFileNumber()));
                }
            }
        }
        return pxdzSubtotal;
	}



    @Override
	public List<DgOpenWater> wxModifyPercentageDiscount(List<DgOpenWater> dgOpenWaters,
			Double minimum, Double Denomination, Integer purpose, Integer type,Map couponInfo) {

		//折扣消费 为1的情况外面计算
		if(type == 1){
			Double percent = Denomination;
			Double yhPercent = MathExtend.subtract(1, Denomination);
	        //常规优惠比例，考虑允许打折的品项数据
	        List<DgItemFile> dgItemFiles = null;

	        List<String> ids = new ArrayList();
	        if(purpose.equals(1)){ //菜品
	        	ids.add("001");
	        	ids.add("002");
	        } else if(purpose.equals(2)){ //锅底
	        	ids.add("004");
	        } else if(purpose.equals(3)){ //酒水
	        	ids.add("003");
	        } else if(purpose.equals(4)){ //小吃
	        	ids.add("005");
	        }

	        //防止空数据错误
	        if(ids.isEmpty()){
	        	ids.add("123");
	        }

	        dgItemFiles = apiCheckServiceMapper.selectWxAllCanDiscountItemData(ids);

	        Double yhjPxdzYhSutotal = 0.0,yhjZyhdYhSubtotal = 0.0;

	        for(DgOpenWater dgOpenWater:dgOpenWaters){
	            Double  zyhdItemSubtotal = 0.0,pxdzItemSubtotal = 0.0;
	            List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
	            for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
	                DgItemFile dgItemFile = dgItemFileMapper.selectByPrimaryKey(dgOwConsumerDetails.getItemFileId());

	                if(sgdsIsInList(dgItemFile.getId(),dgItemFiles) && dgOwConsumerDetails.getIsPriceCal() == 0){
	                    dgOwConsumerDetails.setDiscount(percent);


	                    //先计算优惠金额
	                    yhjPxdzYhSutotal = MathExtend.add(checkVluesIsNull(yhjPxdzYhSutotal),MathExtend.multiply(
	                    		MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),yhPercent),
	                    				dgOwConsumerDetails.getItemFileNumber()));

	                    yhjZyhdYhSubtotal = MathExtend.add(checkVluesIsNull(yhjZyhdYhSubtotal),MathExtend.multiply(
	                    		MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),yhPercent),
	                    				dgOwConsumerDetails.getItemFileNumber()));


                        dgOwConsumerDetails.setZyhdItemFilePrice(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),percent));
                        dgOwConsumerDetails.setZyhdItemCostsSum(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),dgOwConsumerDetails.getItemFileNumber()));
	                    if(dgOwConsumerDetails.getZyhdItemFilePriceDiscount() != null && dgOwConsumerDetails.getZyhdItemFilePriceDiscount() > 0){
                            dgOwConsumerDetails.setZyhdItemFilePriceDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),percent));
                            dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                            zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                        } else {
                            zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSum());
                        }

                        dgOwConsumerDetails.setPxdzItemFilePrice(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),percent));
                        dgOwConsumerDetails.setPxdzItemCostsSum(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),dgOwConsumerDetails.getItemFileNumber()));
                        if(dgOwConsumerDetails.getPxdzItemFilePriceDiscount() != null && dgOwConsumerDetails.getPxdzItemFilePriceDiscount() > 0){
                            dgOwConsumerDetails.setPxdzItemFilePriceDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),percent));
                            dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                            pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                        } else {
                            pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSum());
                        }
	                } else {
                        if(dgOwConsumerDetails.getZyhdItemFilePriceDiscount() != null && dgOwConsumerDetails.getZyhdItemFilePriceDiscount() > 0){
                            //每一个营业流水下三种营业流水品项折扣后的小计
                            zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                        } else {
                            zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSum());
                        }
                        if(dgOwConsumerDetails.getPxdzItemFilePriceDiscount() != null && dgOwConsumerDetails.getPxdzItemFilePriceDiscount() > 0){
                            pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                        } else {
                            pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSum());
                        }
	                }
	            }

	            dgOpenWater.setItemFileInfos(itemFileInfos);

	            //打折前原本的三种单价以及小计
	            Double oldZyhdItemSubtotal = dgOpenWater.getZyhdItemSubtotal();
	            Double oldPxdzItemSubtotal = dgOpenWater.getPxdzItemSubtotal();

	            Double oldPxdzSubtotal = dgOpenWater.getPxdzSubtotal();
	            Double oldZyhdSubtotal = dgOpenWater.getZyhdSubtotal();

	            //设置打折后的三种单价以及小计
	            dgOpenWater.setPxdzSubtotal(MathExtend.add(MathExtend.subtract(oldPxdzSubtotal,oldPxdzItemSubtotal),pxdzItemSubtotal));
	            dgOpenWater.setZyhdSubtotal(MathExtend.add(MathExtend.subtract(oldZyhdSubtotal,oldZyhdItemSubtotal),zyhdItemSubtotal));

	            dgOpenWater.setPxdzItemSubtotal(pxdzItemSubtotal);
	            dgOpenWater.setZyhdItemSubtotal(zyhdItemSubtotal);

	        }
            couponInfo.put("yhjPxdzYhSutotal",yhjPxdzYhSutotal);
            couponInfo.put("yhjZyhdYhSubtotal",yhjZyhdYhSubtotal);
		}

		//菜品抵扣
		if(type == 2){
		    if(couponInfo.containsKey("itemId")){
                String itemId = couponInfo.get("itemId").toString();
                if(StringUtil.isNotEmpty(itemId)){
                    boolean useCoupon = false;
                    DgOwConsumerDetails couponItem = new DgOwConsumerDetails();
                    Double yhjPxdzYhSutotal = 0.0,yhjZyhdYhSubtotal = 0.0;

                    for(DgOpenWater dgOpenWater:dgOpenWaters){
                        Double  zyhdItemSubtotal = 0.0,pxdzItemSubtotal = 0.0;
                        List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();
                        for(DgOwConsumerDetails dgOwConsumerDetails:itemFileInfos){
                            if(dgOwConsumerDetails.getItemFileId().equals(Integer.valueOf(itemId)) &&
                                    !useCoupon &&
                                    new BigDecimal(dgOwConsumerDetails.getPxdzItemFilePrice()).compareTo(new BigDecimal(Denomination)) > 0 &&
                                    StringUtils.isEmpty(dgOwConsumerDetails.getCouponVal())){
                                dgOwConsumerDetails.setDiscount(0.0);
                                if(new BigDecimal(dgOwConsumerDetails.getItemFileNumber()).compareTo(new BigDecimal(1)) >=0){
                                    if(dgOwConsumerDetails.getCouponNum() == null){
                                        dgOwConsumerDetails.setCouponNum(1);
                                    } else {
                                        dgOwConsumerDetails.setCouponNum(dgOwConsumerDetails.getCouponNum()+1);
                                    }

                                    BeanUtils.copyProperties(dgOwConsumerDetails, couponItem);
                                    couponItem.setItemFileNumber(1.0);
                                    couponItem.setItemFinalPrice(Denomination);
                                    couponItem.setSubtotal(Denomination);
                                    couponItem.setZyhdItemFilePrice(Denomination);
                                    couponItem.setPxdzItemFilePrice(Denomination);
                                    couponItem.setPxdzItemFilePriceDiscount(Denomination);
                                    couponItem.setZyhdItemFilePriceDiscount(Denomination);
                                    couponItem.setPxdzItemCostsSum(MathExtend.multiply(checkVluesIsNull(couponItem.getPxdzItemFilePrice()),1.0));
                                    couponItem.setZyhdItemCostsSum(MathExtend.multiply(checkVluesIsNull(couponItem.getZyhdItemFilePrice()),1.0));
                                    couponItem.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(couponItem.getPxdzItemFilePriceDiscount()),1.0));
                                    couponItem.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(couponItem.getZyhdItemFilePriceDiscount()),1.0));
                                    couponItem.setCouponVal(couponInfo.get("couponVal").toString());


                                    //先计算优惠金额
                                    yhjPxdzYhSutotal = MathExtend.add(checkVluesIsNull(yhjPxdzYhSutotal),MathExtend.multiply(
                                            MathExtend.subtract(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),Denomination),
                                            1.0));

                                    yhjZyhdYhSubtotal = MathExtend.add(checkVluesIsNull(yhjZyhdYhSubtotal),MathExtend.multiply(
                                            MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),Denomination),
                                            1.0));


                                    //原数据计算数量/金额
                                    dgOwConsumerDetails.setItemFileNumber(new BigDecimal(dgOwConsumerDetails.getItemFileNumber()).subtract(new BigDecimal(1.0)).doubleValue());
                                    dgOwConsumerDetails.setSubtotal(new BigDecimal(dgOwConsumerDetails.getItemFinalPrice()).multiply(new BigDecimal(dgOwConsumerDetails.getItemFileNumber())).doubleValue());


                                    //每一个营业流水下三种营业流水品项折扣后的小计
                                    dgOwConsumerDetails.setZyhdItemCostsSum(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePrice()),dgOwConsumerDetails.getItemFileNumber()));
                                    dgOwConsumerDetails.setPxdzItemCostsSum(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePrice()),dgOwConsumerDetails.getItemFileNumber()));
                                    if(dgOwConsumerDetails.getZyhdItemFilePriceDiscount() != null && dgOwConsumerDetails.getZyhdItemFilePriceDiscount() > 0){
                                        dgOwConsumerDetails.setZyhdItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getZyhdItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),couponItem.getZyhdItemCostsSumDiscount());
                                    } else {
                                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSum());
                                        zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),couponItem.getZyhdItemCostsSum());
                                    }

                                    if(dgOwConsumerDetails.getPxdzItemFilePriceDiscount() != null && dgOwConsumerDetails.getPxdzItemFilePriceDiscount() > 0){
                                        dgOwConsumerDetails.setPxdzItemCostsSumDiscount(MathExtend.multiply(checkVluesIsNull(dgOwConsumerDetails.getPxdzItemFilePriceDiscount()),dgOwConsumerDetails.getItemFileNumber()));
                                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),couponItem.getPxdzItemCostsSumDiscount());
                                    } else {
                                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSum());
                                        pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),couponItem.getPxdzItemCostsSum());
                                    }

                                    //标记为已使用
                                    useCoupon = true;
                                }
                            } else {
                                if(dgOwConsumerDetails.getZyhdItemFilePriceDiscount() != null && dgOwConsumerDetails.getZyhdItemFilePriceDiscount() > 0){
                                    //每一个营业流水下三种营业流水品项折扣后的小计
                                    zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSumDiscount());
                                } else {
                                    zyhdItemSubtotal = MathExtend.add(checkVluesIsNull(zyhdItemSubtotal),dgOwConsumerDetails.getZyhdItemCostsSum());
                                }
                                if(dgOwConsumerDetails.getPxdzItemFilePriceDiscount() != null && dgOwConsumerDetails.getPxdzItemFilePriceDiscount() > 0){
                                    pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                                } else {
                                    pxdzItemSubtotal = MathExtend.add(checkVluesIsNull(pxdzItemSubtotal),dgOwConsumerDetails.getPxdzItemCostsSum());
                                }
                            }
                        }

                        if(useCoupon){
                            //添加进去
                            itemFileInfos.add(couponItem);
                        }
                        dgOpenWater.setItemFileInfos(itemFileInfos);

                        //打折前原本的三种单价以及小计
                        Double oldZyhdItemSubtotal = dgOpenWater.getZyhdItemSubtotal();
                        Double oldPxdzItemSubtotal = dgOpenWater.getPxdzItemSubtotal();

                        Double oldPxdzSubtotal = dgOpenWater.getPxdzSubtotal();
                        Double oldZyhdSubtotal = dgOpenWater.getZyhdSubtotal();

                        //设置打折后的三种单价以及小计
                        dgOpenWater.setPxdzSubtotal(MathExtend.add(MathExtend.subtract(oldPxdzSubtotal,oldPxdzItemSubtotal),pxdzItemSubtotal));
                        dgOpenWater.setZyhdSubtotal(MathExtend.add(MathExtend.subtract(oldZyhdSubtotal,oldZyhdItemSubtotal),zyhdItemSubtotal));

                        dgOpenWater.setPxdzItemSubtotal(pxdzItemSubtotal);
                        dgOpenWater.setZyhdItemSubtotal(zyhdItemSubtotal);


//                        Iterator<DgOwConsumerDetails> it = itemFileInfos.iterator();
//                        while(it.hasNext()){
//                            DgOwConsumerDetails x = it.next();
//                            if(new BigDecimal(x.getItemFileNumber()).compareTo(new BigDecimal(0)) <= 0){
//                                it.remove();
//                            }
//                        }
                    }
                    couponInfo.put("yhjPxdzYhSutotal",yhjPxdzYhSutotal);
                    couponInfo.put("yhjZyhdYhSubtotal",yhjZyhdYhSubtotal);
                }
            }
        }
        return dgOpenWaters;
	}

	@Override
	public Map getHyPayVailCode(String mobile) {
		// TODO Auto-generated method stub
		try{
			return OkHttpUtils.getHyPayVailCode(mobile,dgUrlSettingMapper.selectByCode("member.comId").getValue());
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public void modifyKydPrintAjax(String openWaterIds) {
        List<Integer> list = StringUtil.arrayToList(openWaterIds);
        for(Integer id:list){
            apiCheckServiceMapper.modifyKydPrintAjax(id);
        }
    }

    @Override
    public void modifyYjdPrintAjax(String openWaterIds) {
        List<Integer> list = StringUtil.arrayToList(openWaterIds);
        for(Integer id:list){
            apiCheckServiceMapper.modifyYjdPrintAjax(id);
        }
    }

    @Override
    public void modifyJzdPrintAjax(Integer clearingWaterId) {
        apiCheckServiceMapper.modifyJzdPrintAjax(clearingWaterId);
    }
    
    @Override
    public Map modifyOnlineYDValidate(String id,String validateCode) {
    	Map str = OnlineHttp.onlineYDValidate(id, validateCode);
    	return str;
    }

    @Override
    public List<DgOpenWater> selectAllOpenWaterIsClosed(List<DgOpenWater> dgOpenWaters) {
        return apiCheckServiceMapper.selectAllOpenWaterIsClosed(dgOpenWaters);
    }

    @Override
    public DgOpenWater selectSingleOpenWaterByOwNum(String owNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("owNum",owNum);
        DgOpenWater dgOpenWaters = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);
        List<DgCashPledge> dgCashPledges = apiCheckServiceMapper.selectAllDepositByOwNum(owNum);
        for (DgCashPledge dgCashPledge : dgCashPledges) {
            Double deposit = dgOpenWaters.getDeposit() == null?0.:dgOpenWaters.getDeposit();
            if(dgCashPledge.getCpType().equals("0")){
                dgOpenWaters.setDeposit(MathExtend.add(deposit,dgCashPledge.getCpMoney()));
            }else if(dgCashPledge.getCpType().equals("1")){
                dgOpenWaters.setDeposit(MathExtend.subtract(deposit,dgCashPledge.getCpMoney()));
            }
        }
        return dgOpenWaters;
    }

    @Override
    public DgReceptionClearingWater selectClearingWaterById(Integer clearingWaterId) {
        return apiCheckServiceMapper.selectClearingWaterById(clearingWaterId);
    }

    @Override
    public List<DgOpenWater> selectOpenWaterByCwId(Integer clearingWaterId) {
        return apiCheckServiceMapper.selectOpenWaterByCwId(clearingWaterId);
    }

    @Override
    public List<DgOwClearingway> selectClearingWayByCwId(Integer clearingWaterId) {
        return apiCheckServiceMapper.selectClearingWayByCwId(clearingWaterId);
    }

    private Double checkFwfsx(Double fwfsx,Double price){
        if(fwfsx > 0){
            if(price <= fwfsx){
                return price;
            }else if(price > fwfsx){
                return fwfsx;
            }
        }
        return price;
    }

    Double checkVluesIsNull(Double value){
        if(value == null){
            return 0.;
        }
        return value;
    }

    /**
     * 重要活动打折
     */
    private Map<Integer, Object> getZyhdItemPrice(DgOwConsumerDetails dgOwConsumerDetails) {
        Map<Integer, Object> ret = new HashMap<>();
        Integer id = dgOwConsumerDetails.getItemFileId();

        Map<String, Object> zyhd = dgImportantAcitivityDiscountSMapper
                .selectByItemId(id);
        if (zyhd != null) {
            if (zyhd.containsKey("discount")) {
                if(dgOwConsumerDetails.getVariablePrice() != null && dgOwConsumerDetails.getVariablePrice() == 1){//存在品项变价
                    ret.put(id, MathExtend.multiply(MathExtend.divide(
                            dgOwConsumerDetails.getItemFinalPrice(), 100), (int) zyhd
                            .get("discount")));
                    return ret;
                }

                if(dgOwConsumerDetails.getZsItemFinalPrice() !=  null && dgOwConsumerDetails.getZsItemFinalPrice() != 0.0){//为赠单类型
                    ret.put(id, 0.);
                    return ret;
                }

                ret.put(id, MathExtend.multiply(MathExtend.divide(
                        (double) zyhd.get("standard_price"), 100), (int) zyhd
                        .get("discount")));
            } else {
                ret.put(-1, "当前品项在重要活动中不存在!");
            }
        } else {
            ret.put(-1, "当前品项在重要活动中不存在!");
        }
        return ret;
    }

    /**
     * 品项打折方案 先判断品项是否打折 根据星期几来获取打折方案 有就返回正确的,没有就就fail
     */
    private Map<Integer, Object> getPxDzFaPrice(DgOwConsumerDetails dgOwConsumerDetails) {
        Map<Integer, Object> ret = new HashMap<>();

        Integer id = dgOwConsumerDetails.getItemFileId();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        DgWeekDiscount dis;
        String name = null;
        if (weekIndex == 0) {
            name = "星期日";
        } else if (weekIndex == 1) {
            name = "星期一";
        } else if (weekIndex == 2) {
            name = "星期二";
        } else if (weekIndex == 3) {
            name = "星期三";
        } else if (weekIndex == 4) {
            name = "星期四";
        } else if (weekIndex == 5) {
            name = "星期五";
        } else if (weekIndex == 6) {
            name = "星期六";
        }
        dis = dgWeekDiscountMapper.selectByName(name);
        if (dis != null) {
            DgItemDiscountProgramme dp = dgItemDiscountProgrammeMapper
                    .selectByPrimaryKey(dis.getpId());
            if (dp == null) {
                ret.put(-1, "今日没有打折方案!");
                return ret;
            }
			Map orgs = new HashedMap();
			orgs.put("itemId",id);
			orgs.put("pId",dp.getId());
            if (dp.getDisable() != 1) {
                if (dp.getType().equals("1")) {
                    Map<String, Object> dps = dgItemDiscountProgrammeSMapper
                            .selectByPIdAndItemIdType1(orgs);
                    if (dps != null && dps.get("discount") != null) {
                        if(dgOwConsumerDetails.getVariablePrice() != null && dgOwConsumerDetails.getVariablePrice() == 1){
                            ret.put(id,
                                    MathExtend.multiply(
                                            MathExtend.divide(dgOwConsumerDetails.getItemFinalPrice(), 100),
                                            (int) dps.get("discount")));
                            return ret;
                        }

                        if(dgOwConsumerDetails.getZsItemFinalPrice() != null && dgOwConsumerDetails.getZsItemFinalPrice() != 0.0){
                            ret.put(id,0.0);
                            return ret;
                        }

                        ret.put(id,
                                MathExtend.multiply(
                                        MathExtend.divide((double) dps
                                                .get("standard_price"), 100),
                                        (int) dps.get("discount")));
                    } else {
                        ret.put(-1, "今日打折方案打折比例为空!");
                    }
                } else {
                    Map<String, Object> dps = dgItemDiscountProgrammeSMapper
                            .selectByPIdAndItemIdType2(orgs);
                    if (dps != null && dps.get("discount") != null) {
                        ret.put(id,
                                MathExtend.multiply(
                                        MathExtend.divide((double) dps
                                                .get("standard_price"), 100),
                                        (int) dps.get("discount")));
                    } else {
                        ret.put(-1, "今日打折方案打折比例为空!");
                    }
                }

            } else {
                ret.put(-1, "今日打折方案已停用!");
            }

        } else {
            ret.put(-1, "今日没有打折方案!");
        }

        return ret;
    }

    /**
     *
     * 会员打折 id 品项id hyLevel 会员登记 orgId 组织id
     *
     * 先查询会员等级 = hyLevel 下的方案
     */
    public Map<Integer, Object> getHyPrice(Integer id, String hyLevel,
                                          Integer orgId) {
        Map<Integer, Object> ret = new HashMap<>();
        DgItemMemberDiscount mDis = new DgItemMemberDiscount();
        mDis.setLevelId(hyLevel);
        mDis.setEnable(1);
        mDis.setPublish(1);
        mDis.setRecycleBin(0);
        mDis.setSearchDate(formatDate.format(new Date())); // 查询日期是否在打折日期内
        mDis.setSearchTime(formatTime.format(new Date())); // 查询时间是否在打折时间内
        mDis = dgItemMemberDiscountMapper.selectByItem(mDis);
        if (mDis != null) {
            String[] weeks = mDis.getWeek().split("-");
            // 先判断星期
            Calendar cal = Calendar.getInstance();
            int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (weekIndex < 0) {
                weekIndex = 0;
            }
            DgWeekDiscount dis;
            String nowWeekQx = null;
            if (weekIndex == 0) {
                nowWeekQx = weeks[6];
            } else if (weekIndex == 1) {
                nowWeekQx = weeks[0];
            } else if (weekIndex == 2) {
                nowWeekQx = weeks[1];
            } else if (weekIndex == 3) {
                nowWeekQx = weeks[2];
            } else if (weekIndex == 4) {
                nowWeekQx = weeks[3];
            } else if (weekIndex == 5) {
                nowWeekQx = weeks[4];
            } else if (weekIndex == 6) {
                nowWeekQx = weeks[5];
            }
            if (nowWeekQx.equals("0")) {
                ret.put(-1, "今天未启用会员打折!");
                return ret;
            }

            Map<String, Object> sm = new HashMap<String, Object>();
            sm.put("pId", mDis.getId());
            sm.put("itemId", id);
            DgItemMemberDiscountS diss = dgItemMemberDiscountSMapper
                    .selctByPIdAndItemID(sm);
            if (diss != null) {
                ret.put(id, diss.getPrice());
            } else {
                ret.put(-1, "方案下没有品项打折信息,或未启用!");
            }
                   
        } else {
            ret.put(-1, "今天未启用会员打折!");
        }
        return ret;
    }

	private boolean sgdsIsInList(Integer id,List<DgItemFile> list){
		for(DgItemFile f:list){
			if(id.equals(f.getId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<DgOpenWater> newModifyPercentageDiscount(
			List<DgOpenWater> dgOpenWaters, DBSBillServDTO dbsBillServDTO,Double proportion, Integer type) {
		// TODO Auto-generated method stub
		 //循环可以进行买单结算的营业流水
        for(DgOpenWater dgOpenWater:dgOpenWaters) {
    		DgPos pos = new DgPos();
    		pos.setId(dgOpenWater.getOpenPos());
    		pos = dgPosMapper.getPosByID(pos);
//
//            if (dgOpenWater.getClearingWaterId() != null) {
//                isAllAdvancePay = true;
//                clearingId = dgOpenWater.getClearingWaterId();
//            } else {
//                isAllAdvancePay = false;
//            }

            //循环获取所有营业流水下面的所有有效品项按照服务单分组
            List<DgOwConsumerDetails> dgOwConsumerDetailss = selectOpenWaterClearingWithService(dgOpenWater.getOwNum(),0);

            //重要活动价格，品项打折价格，会员打折价格
            Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

            //先计算出所有品项的小计
            for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                //开单没有优惠的品项的三种折扣价格，计算制作费用
//                if(dgOwConsumerDetails.getIsPriceCal() == 0){//只有当品项为未结算状态才计算
                	getOpenWaterForDz(dgOwConsumerDetails,type.equals(1)?proportion:null,type.equals(2)?proportion:null);
                    dgOwConsumerDetails.setDiscount(proportion);

                    //每一个营业流水下所有品项三种价格累加
                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
                    standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
                    standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getHydzItemCostsSum());
//                }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
//                    //如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
//                    dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
//                    dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());
//
//                    dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
//                    dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());
//
//                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
//                    standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
//                }
            }

            //品项信息
            dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

            dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
            dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
            dgOpenWater.setHydzItemSubtotal(standPriceHydz);

            //算出三种价格，返回服务费
            getOtherCost(dbsBillServDTO, dgOpenWater, "all");

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
        return dgOpenWaters;
	}

    @Override
    public Map selectOpenClassReportWithId(Integer id) {
        Map loginInfo = apiCheckServiceMapper.selectOpenClassWaterWithId(id);
        Map<String,Object> resMap = new HashedMap();
        Map<String,Object> map = new HashMap<>();

        String loginTime = loginInfo.get("login_time").toString();
        String endTime =   loginInfo.get("end_time").toString();
        loginTime = loginTime.substring(0, loginTime.length() - 2);
        loginInfo.put("login_time", loginTime);

        //封装查询条件
        map.put("startTime",loginTime);
        map.put("endTime",endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(loginTime, endTime);
        map.put("tableDateList", tableDateList);
        map.put("loginPos",loginInfo.get("login_pos"));
        map.put("loginUser",loginInfo.get("login_user"));
        map.put("pettyCash",loginInfo.get("petty_cash"));
        resMap.put("loginInfo",loginInfo);

        resMap.put("jbWater",loginInfo.get("open_class_water"));

        //查询前班数据
        Map lastLoginInfo = apiCheckServiceMapper.selectLastUserLoginInfo(loginTime);
        resMap.put("lastLoginInfo",lastLoginInfo);
        //开单人数以及开台总数
        resMap.put("bilingData",apiCheckServiceMapper.selectBilingData(map));
        //未结台数以及未结金额
        resMap.put("openData",apiCheckServiceMapper.selectOpenData(map));
        //未退押金
        resMap.put("depositData",apiCheckServiceMapper.selectWTDepositData(map));
        //已结台数以及已结人数
        resMap.put("closeData",apiCheckServiceMapper.selectCloseData(map));
        //本班赠单金额
        resMap.put("freeData",apiCheckServiceMapper.selectFreeData(map));
        //本班退单金额
        resMap.put("backData",apiCheckServiceMapper.selectBackData(map));

        //充值金额
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aa = loginInfo.get("login_time").toString();
        String bb = endTime;
        String cc = loginInfo.get("login_user").toString();
        String rechargeData = OkHttpUtils.memberNewGetRecharge(loginTime, endTime, loginInfo.get("login_user").toString());
        if(!StringUtils.isEmpty(rechargeData)){
            Map map1 = new Gson().fromJson(rechargeData, Map.class);
            if(map1.get("msgCode").toString().equalsIgnoreCase("OK")){
                List<Map> recMap = (ArrayList<Map>)map1.get("body");
                if(recMap.isEmpty()){
                    resMap.put("rechargeData",0.00);
                } else {
                    resMap.put("rechargeData",recMap.get(0) == null ? 0.00 :recMap.get(0).get("payMoney"));
                }
            }
        }

        //S账金额
        resMap.put("sbillData",apiCheckServiceMapper.selectSbillData(map));

        //消费金额、抹零金额、定额优惠、应收金额、实收金额
        Map clearingMoney = apiCheckServiceMapper.selectClearingWaterMoney(map);
        double shje = 0.0,pettyCash = 0.0,pledgeCash = 0.0;
        if(clearingMoney != null && clearingMoney.size() > 0){
            if(loginInfo != null && loginInfo.size() > 0){
                Object oshje = clearingMoney.get("shje");
                if(oshje != null){
                    shje = Double.parseDouble(oshje+"");
                }
                //备用金
                Object opettyCash = loginInfo.get("petty_cash");
                if(opettyCash != null){
                    pettyCash = Double.parseDouble(opettyCash+"");
                }
                //押金
                Map<String,Object> wtDepositDataMap = (Map) resMap.get("depositData");
                Object wtDepositData = wtDepositDataMap.get("wtDepositData");
                if(wtDepositData != null){
                    pledgeCash = Double.parseDouble(wtDepositData+"");
                }
                BigDecimal bdShje = new BigDecimal(shje);
                BigDecimal bdPettyCash = new BigDecimal(pettyCash);
                BigDecimal bdPledgeCash = new BigDecimal(pledgeCash);
                BigDecimal total = new BigDecimal(0.0);
                total = bdShje.add(bdPettyCash);
                total = total.add(bdPledgeCash);
                clearingMoney.put("shje", total.doubleValue());
            }
        }else{
            if(loginInfo != null && loginInfo.size() > 0){
                Object opettyCash = loginInfo.get("petty_cash");
                if(opettyCash != null){
                    pettyCash = Double.parseDouble(opettyCash+"");
                }
                BigDecimal total = new BigDecimal(pettyCash);
                clearingMoney = new HashedMap();
                clearingMoney.put("shje", total.doubleValue());
            }
        }
        resMap.put("clearingMoney",clearingMoney);
        //服务费、最低消费补齐、包房费

        List<Map> maps = apiCheckServiceMapper.selectOpenWaterMoney(map);
        BigDecimal fwf = BigDecimal.ZERO,bff = BigDecimal.ZERO,zdxfbq = BigDecimal.ZERO,yhje = BigDecimal.ZERO;

        for(Map openWater:maps){
            if(!openWater.containsKey("free_servce_charge")){
                if(openWater.containsKey("modify_service_charge")){
                    BigDecimal b = new BigDecimal(openWater.get("modify_service_charge").toString());
                    fwf = fwf.add(b).setScale(2);
                }else{
                    if(openWater.containsKey("service_charge")){
                        BigDecimal b = new BigDecimal(openWater.get("service_charge").toString());
                        fwf = fwf.add(b).setScale(2);
                    }
                }
            }

            if(!openWater.containsKey("free_private_room")){
                if(openWater.containsKey("modify_private_room")){
                    BigDecimal b = new BigDecimal(openWater.get("modify_private_room").toString());
                    bff = bff.add(b).setScale(2);
                }else{
                    if(openWater.containsKey("private_room_cost")){
                        BigDecimal b = new BigDecimal(openWater.get("private_room_cost").toString());
                        bff = bff.add(b).setScale(2);
                    }
                }
            }

            if(!openWater.containsKey("free_min_spend")){
                if(openWater.containsKey("minimum_charge_complete")){
                    BigDecimal b = new BigDecimal(openWater.get("minimum_charge_complete").toString());
                    zdxfbq = zdxfbq.add(b).setScale(2);
                }
            }

            if(openWater.containsKey("discount_costs")){
                BigDecimal b = new BigDecimal(openWater.get("discount_costs").toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
                yhje = yhje.add(b).setScale(2);
            }

        }

        resMap.put("fwf",fwf);
        resMap.put("bff",bff);
        resMap.put("zdxfbq",zdxfbq);
        resMap.put("yhje",yhje);
        return resMap;
    }

    @Override
    public Map selectOpenClassWaterHistory(int pageSize, int pageNum, String time, String userName, int posId) {
        Map<String,Object> map = new HashMap<>();
        if(pageNum < 2){
            map.put("startPage",0);
        }else{
            map.put("startPage",(pageNum-1)*pageSize);
        }
        map.put("time",time);
        map.put("userName",userName);
        map.put("posId",posId);
        map.put("pageSize",pageSize);

        //总记录数
        int count = apiCheckServiceMapper.selectOpenClassWaterHistoryCount(map);
        int totalPage = 0;

        //总页数判断
        if(count % pageSize == 0){
            totalPage = count / pageSize;
        }else{
            totalPage = (count / pageSize) + 1;
        }

        List<Map> maps = apiCheckServiceMapper.selectOpenClassWaterHistory(map);

        map.clear();
        map.put("totalPage",totalPage);
        map.put("dataList",maps);
        map.put("totalCount",count);

        return map;
    }

    @Override
    public Object selectOpenClassInfo(Map loginInfo, int type) {
        Gson gson = new Gson();
        String loginTime = loginInfo.get("login_time").toString();
        String endTime =  DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(loginTime, endTime);
        Map<String,Object> map = new HashMap<>();
        map.put("tableDateList", tableDateList);
        map.put("startTime",loginTime);
        map.put("endTime",endTime);
        map.put("loginPos",loginInfo.get("login_pos"));
        map.put("loginUser",loginInfo.get("login_user"));

        if(type == 1){
            return apiCheckServiceMapper.selectBigTypeReport(map);
        }

        if(type == 2){
            return apiCheckServiceMapper.selectSmallTypeReport(map);
        }

        if(type == 3){
            return apiCheckServiceMapper.selectItemReport(map);
        }

        if(type == 4){
            //会员挂账数据
            List<Map> maps = apiCheckServiceMapper.selectMembersCredit(map);
            for(Map gzMap:maps){
                try {
                    String memberList = OkHttpUtils.memberGetVipAllCopu(gzMap.get("clearingMember").toString());
                    if(!memberList.isEmpty()){
                        Map memberMap = gson.fromJson(memberList, Map.class);
                        if(memberMap.get("msgCode").toString().equals("ok")){
                            Map body = (Map) memberMap.get("body");
                            Map vipCard = gson.fromJson(body.get("vipCard").toString(),Map.class);
                            if(vipCard != null){
                                gzMap.put("memberName",vipCard.get("custName"));
                            }else{
                                gzMap.put("memberName","未知用户");
                            }
                        }else{
                            gzMap.put("memberName","未知用户");
                        }
                    }
                } catch (JsonSyntaxException e) {
                    gzMap.put("memberName","未知用户");
                    e.printStackTrace();
                }
            }
            return maps;
        }

        if(type == 5){
            //结算方式
            List<DgSettlementWay> dgSettlementWays = dgSettlementWayMapper.getAllList(null);
            List<DgOwClearingway> frequencys = new ArrayList<DgOwClearingway>();
            DgOwClearingway dcw = null;
            for (DgSettlementWay dgSettlementWay : dgSettlementWays) {
                map.put("cwCode", dgSettlementWay.getNumber());
                DgOwClearingway frequency = apiCheckServiceMapper.selectFrequency(map);
                dcw = new DgOwClearingway();
                dcw.setSeName(dgSettlementWay.getName());
                dcw.setSettlementAmount(frequency==null? 0.0 : frequency.getSettlementAmount());
                frequencys.add(dcw);
            }
            return frequencys;
        }
        return null;
    }

    @Override
    public void reductionPrice(String dgOpenWaters) {
        DgOpenWater dows= JSON.parseObject(dgOpenWaters,DgOpenWater.class);
        apiCheckServiceMapper.reductionPriceForAdd(dows);
        apiCheckServiceMapper.reductionPriceForGift(dows);
        apiCheckServiceMapper.updateWaterSubtotal(calItemStardPriceTotal(dows.getItemFileInfos()),dows.getId());
    }

    Double calItemStardPriceTotal(List<DgOwConsumerDetails> dods){
        Double subTotal= 0.0;
        for(DgOwConsumerDetails docd:dods){
            subTotal=MathExtend.add(subTotal,MathExtend.multiply(docd.getStandardPrice(),docd.getItemFileNumber()));
        }
        return subTotal;
    }

    @Override
    public void updateOpenWaterSubtotal(DgOpenWater dow) {
        Double subtotal=calItemStardPriceTotal(dow.getItemFileInfos());
        dow.setSubtotal(subtotal);
        dow.setItemCostsSum(subtotal);
        apiCheckServiceMapper.updateWaterSubtotal(subtotal,dow.getId());
    }
}