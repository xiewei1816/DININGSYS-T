package com.yqsh.diningsys.web.service.report.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.CompareInteger;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.dao.archive.DgSettlementWayMapper;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.dao.report.BackReportMapper;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.report.Payway;
import com.yqsh.diningsys.web.model.report.Statement;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.report.BackReportService;
import com.yqsh.diningsys.web.util.TableQueryUtil;

/**
 * Created on 2017-02-08 14:08
 *
 * @author zhshuo
 */
@Service
public class BackReportServiceImpl implements BackReportService {

    @Resource
    private BackReportMapper backReportMapper;

    @Resource
    private TbBisMapper tbBisMapper;

    @Resource
    private DgSettlementWayMapper dgSettlementWayMapper;

    @Autowired
    private TbOrgService tbOrgService;
    
    @Autowired
    private DgItemFileMapper dgItemFileMapper;

    @Override
    public List<Map> queryNumberOfMealsBy24Hour(String startTime, String endTime) {

        List<Map> maps = new ArrayList<>();

        Map<String, Object> param = new HashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	if(tableDateList.size() == 0){
            return maps;
        }
        param.put("tableDateList", tableDateList);

        Double peopleCunt = backReportMapper.countNumberOfMeals(param)*1.0;

        maps = backReportMapper.countNumberOfMealsBy24Hour(param);

        for(Map map:maps){
            if(!map.containsKey("peopleCount")){
                map.put("peopleCount",0);
            }

            Double hoursPeople = Integer.parseInt(map.get("peopleCount").toString()) * 1.0;

            Double divide = hoursPeople/peopleCunt;

            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMinimumFractionDigits(2);

            map.put("peoplePercent",nf.format(divide));
        }

        List<TbBis> tbBiss = bisTimeSet(tbBisMapper.selectAllBis());

        compareTime(maps,tbBiss);

        return maps;

        /*Map<String, Object> bisTimeMap = new HashMap<>();

        List<Integer> bisTimeLinkedList = new LinkedList<>();

        for (TbBis tbBis : tbBiss) {

            int bisTime = Integer.parseInt(tbBis.getBisTime().split(":")[0]);

            bisTimeLinkedList.add(bisTime);

            bisTimeMap.put(tbBis.getBisName(), bisTime);
        }*/

        /*for (Map map : maps) {
            Boolean flag = compareTime(bisTime, map.get("hours").toString());
            if (flag) {
                map.put("bisName", bisName);
            }
            Map<String, Object> attrMap = new HashMap<>();
            Map<String, Object> attrValueMap = new HashMap<>();
            attrValueMap.put("display", "none");
            attrMap.put("bisName", attrValueMap);
            map.put("attr", attrMap);
        }*/
    }

    @Override
    public List<Map> queryItemFileTypeIndex(String startTime,String endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);

        List<Map> maps = new ArrayList<>();

        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	if(tableDateList.size() == 0){
            return maps;
        }
        param.put("tableDateList", tableDateList);

        if(tableDateList.size() > 0){
            maps = backReportMapper.queryItemFileTypeIndex(param);
        }
        return maps;

        //符合条件的营业流水ID
        /*List<Integer> openWaterId = backReportMapper.selectOpenWaterId(param);

        param.put("openWaterId",openWaterId);


        if(openWaterId.size() > 0){
            List<Integer> serviceId = backReportMapper.selectServiceId(param);

            param.put("serviceId",serviceId);
        }*/

    }

    @Override
    public List<Map> queryItemFileTypeSmall(String bigNum,String startTime,String endTime) {
        Map<String, Object> param = new HashMap<>();

        param.put("bigNum", bigNum);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	if(tableDateList.size() == 0){
            return null;
        }
        param.put("tableDateList", tableDateList);
        return backReportMapper.queryItemFileTypeSmall(param);
    }

    @Override
    public List<Map> queryItemFileTypeItem(String smallNum,String startTime,String endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("smallNum", smallNum);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	if(tableDateList.size() == 0){
            return null;
        }
        param.put("tableDateList", tableDateList);
        return backReportMapper.queryItemFileTypeItem(param);
    }

    @Override
    public List<Map> queryItemFileTypeOpenWaters(String itemFileNum,String startTime,String endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("itemFileNum", itemFileNum);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        List<String> tableDateList = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
    	if(tableDateList.size() == 0){
            return null;
        }
        param.put("tableDateList", tableDateList);
        return backReportMapper.queryItemFileTypeOpenWaters(param);
    }

    @Override
    public List<Integer> dataSearchDetailsIndex(Integer serviceId,String clearingTime) {
    	Calendar calendar = Calendar.getInstance();
        Map<String,Object> map = new HashMap<>();
        //一共有多少条服务流水
        String YEAR = calendar.get(Calendar.YEAR)+"-";
        String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(YEAR+clearingTime);
    	if(StringUtils.isEmpty(tableDate)){
            return null;
        }
        List<Integer> serviceIdList = backReportMapper.selectCountServiceNumByServiceWater(serviceId,tableDate);
        CompareInteger compareInteger = new CompareInteger();
        Collections.sort(serviceIdList,compareInteger);
        Iterator<Integer> ids = serviceIdList.iterator();
        while(ids.hasNext()){
            Integer id = ids.next();
            if(id.equals(serviceId)){
                ids.remove();
            }
        }
        serviceIdList.add(0, serviceId);
        return serviceIdList;
    }

    @Override
    public List<Integer> selectServiceDataByOwnum(String openWater, String startTime, String endTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("openWater",openWater);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        //一共有多少条服务流水

        List<String> tableSuffixList = TableQueryUtil.tableNameUtilWithMonthRange(startTime,endTime);
    	if(tableSuffixList.size() == 0){
            return null;
        }
        map.put("tableSuffixList",tableSuffixList);

        List<Integer> serviceIdList = backReportMapper.selectCountServiceNumByOpenWater(map);
        CompareInteger compareInteger = new CompareInteger();
        Collections.sort(serviceIdList,compareInteger);
        return serviceIdList;
    }

    @Override
    public Map dataSearchDetailsNext(Integer serviceId,String tableDate) {
    	Calendar calendar = Calendar.getInstance();
        Map<String,Object> map = new HashMap<>();
        String YEAR = calendar.get(Calendar.YEAR)+"-";
        tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(YEAR+tableDate);
    	if(StringUtils.isEmpty(tableDate)){
            return null;
        }
        Map headData = backReportMapper.selectServiceDetailByServiceId(serviceId,tableDate);
        List<DgOwConsumerDetails> dgOwConsumerDetailss = backReportMapper.dataSearchDetailsNext(serviceId,tableDate);
        map.put("headData",headData);
        map.put("tableData",dgOwConsumerDetailss);
        return map;
    }

    @Override
    public Map dataSearchDetailsNext_new(Integer serviceId, String startTime, String endTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("serviceId",serviceId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        List<String> tableSuffixList = TableQueryUtil.tableNameUtilWithMonthRange(startTime,endTime);
    	if(tableSuffixList.size() == 0){
            return null;
        }
        map.put("tableSuffixList",tableSuffixList);

        Map headData = backReportMapper.selectServiceDetailByServiceId_new(map);
        List<DgOwConsumerDetails> dgOwConsumerDetailss = backReportMapper.dataSearchDetailsNext_new(map);
        map.put("headData",headData);
        map.put("tableData",dgOwConsumerDetailss);
        return map;
    }

	@Override
	public Page<Payway> getStatementPageList(Statement statement) {
		Integer totle = backReportMapper.countStatementListByPage(statement);
		List<Payway> list = backReportMapper.selectStatementListByPage(statement);
		return PageUtil.getPage(totle, statement.getPage(),list, statement.getRows());
	}

    @Override
    public List<Map> statementQuery(String startTime, String endTime, Integer consumerArea, Integer bis, Integer pos, String clearingNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("consumerArea",consumerArea);
        map.put("bis",bis);
        map.put("pos",pos);
        map.put("clearingNum",clearingNum);

        Boolean flag = TableQueryUtil.openDayReportCheck(startTime, endTime);

        map.put("flag",flag);

        if(!flag){
            List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
        	if(strings.size() == 0){
                return null;
            }
            map.put("tableSuffixList",strings);
        }

        List<Map> maps = backReportMapper.statementQuery(map);
        return maps;
    }

    @Override
    public Map openDayReportDataSearch(String startTime, String endTime, String orgCode, Integer bis,
                                       Integer pos, Integer timeType, Integer moneyType, Integer area) {
        Gson gson = new Gson();

        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("orgCode",orgCode);
        map.put("bis",bis);
        map.put("pos",pos);
        map.put("timeType",timeType);
        map.put("moneyType",moneyType);
        map.put("area",area);

        Boolean flag = TableQueryUtil.openDayReportCheck(startTime,endTime);

        map.put("flag",flag);

        if(!flag){
            List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
            map.put("tableSuffixList",strings);

            if(strings.size() < 1){
                return null;
            }
        }

        //营业报表营业总额查询
        double jr = 0.0;
        double bjr = 0.0;
        Map<String,Object> openingAmount = backReportMapper.openDayOpeningAmount(map);
        //获取找零金额
        double zl = 0.0;
        if(openingAmount != null){
            if(openingAmount.containsKey("zl")){
                zl = Double.parseDouble(openingAmount.get("zl").toString());
            }
        }

        //结算方式
//        Map<String,Object> clearingWay = backReportMapper.openDayClearingWay(map);
        List<DgSettlementWay> dgSettlementWays = dgSettlementWayMapper.getAllList(null);
        List<DgOwClearingway> clearingWay = new ArrayList<DgOwClearingway>();
        DgOwClearingway dcw = null;
        for (DgSettlementWay dgSettlementWay : dgSettlementWays) {
            map.put("cwCode", dgSettlementWay.getNumber());
            DgOwClearingway frequency = backReportMapper.openDayClearingWay(map);
            dcw = new DgOwClearingway();
            //计入、不计入实际收入比例
            double actualIncomeRatio = dgSettlementWay.getActualIncomeRatio();
            double notActualIncomeRatio = dgSettlementWay.getNotActualIncomeRatio();
            dcw.setActualIncomeRatio(actualIncomeRatio);
            dcw.setNotActualIncomeRatio(notActualIncomeRatio);
            dcw.setSeName(dgSettlementWay.getName());
            //如果结算方式为RMB减去找零金额
            if((dgSettlementWay.getNumber()).equals("RMB")){
                dcw.setSettlementAmount(frequency==null? 0.0 : frequency.getSettlementAmount() - zl);
            }else{
                dcw.setSettlementAmount(frequency==null? 0.0 : frequency.getSettlementAmount());
            }

            //过滤为0的结算方式
            if(!(dcw.getSettlementAmount()).equals(0.0)){
                clearingWay.add(dcw);
            }
        }

        //计算计入、不计入实际收入金额
        for (DgOwClearingway dgOwClearingway : clearingWay) {
        	double actualIncomeRatio = dgOwClearingway.getActualIncomeRatio();
        	double notActualIncomeRatio = dgOwClearingway.getNotActualIncomeRatio();
            if(actualIncomeRatio == 0 && notActualIncomeRatio != 100){
            	actualIncomeRatio = 100;
            	jr += dgOwClearingway.getSettlementAmount();
            }else{
            	jr += dgOwClearingway.getSettlementAmount() * (actualIncomeRatio/100);
            	bjr += dgOwClearingway.getSettlementAmount() * (notActualIncomeRatio/100);
            }
        }
        if(openingAmount != null){
            openingAmount.put("jr", jr);
            openingAmount.put("bjr", bjr);
        }

        //账单信息
        Map<String,Object> billInfo = backReportMapper.openDayBillInfo(map);

        //登记押金以及已退押金
//        Map<String,Object> cashPledgeQuery = backReportMapper.openDayCashPledgeQuery(map);

        //免除的最低消费，免除的包房费，包房费总金额，免除的服务费,品项和
        Map<String,Object> specialItem = backReportMapper.openDaySpecialItem(map);

        //消费区域包房费
//        List<Map> speedAreaRoomCosts = backReportMapper.openDaySpeedAreaRoomCosts(map);

        //就餐人数统计
//        List<Map> openDayNumberOfMeals = backReportMapper.openDayNumberOfMeals(map);

        /*//品项类别现金销售统计
        List<Map> dgItemFilesForCash = backReportMapper.openDayItemSaleRMBInfo(map);
        List<Map> itemSaleForCash = mapFill(dgItemFilesForCash);

        //品项销售统计
        List<Map> dgItemFiles = backReportMapper.openDayItemSaleInfo(map);
        List<Map> itemSale = mapFill(dgItemFiles);*/

//        Map<String,String> vipInfo = new HashMap<>();
//        String s = OkHttpUtils.openDayReportVipInfo(startTime, endTime);
//        if(!StringUtils.isEmpty(s)){
//            Map res = gson.fromJson(s,Map.class);
//            if(res.get("msgCode").toString().equalsIgnoreCase("ok")){
//                String valueString = res.get("body").toString();
//
//                Map<String, String> o = gson.fromJson(valueString, new TypeToken<Map<String, String>>() {
//                }.getType());
//
//                vipInfo.putAll(o);
//            }
//        }

        map.clear();

        if(openingAmount != null){
            map.putAll(openingAmount);
        }
        if(billInfo != null){
            map.putAll(billInfo);
        }
        if(clearingWay.size() > 0){
            map.put("clearingWay",clearingWay);
        }
//        if(cashPledgeQuery != null){
//            map.putAll(cashPledgeQuery);
//        }
        if(specialItem != null){
            map.putAll(specialItem);
        }
        
        if(billInfo != null){
        	map.put("yhze",new BigDecimal(map.get("itemCostsSum").toString()).subtract(
        			new BigDecimal(map.get("yeze") == null ? "0.00" :(map.get("yeze").toString()))));
        }
//        if(vipInfo != null){
//            map.putAll(vipInfo);
//        }
//        if(openDayNumberOfMeals.size() > 0){
//            map.put("numberOfMeals",openDayNumberOfMeals);
//        }
//        if(speedAreaRoomCosts.size() > 0){
//            map.put("areaSpeed",speedAreaRoomCosts);
//            map.put("loopLength",speedAreaRoomCosts.size() / 4);
//            map.put("loopSize",speedAreaRoomCosts.size() % 4);
//        }
        /*if(itemSale.size() > 0){
            map.put("itemSaleList",itemSale);
        }
        if(itemSaleForCash.size() > 0){
            map.put("itemSaleForCashList",itemSaleForCash);
        }*/

        //返回打印查询条件数据
        Map<String,Object> searchMap = new HashMap<>();
        searchMap.put("startTime", startTime);
        searchMap.put("endTime", endTime);
        //获取店铺名称
        TbOrg tbOrg = new TbOrg();
        tbOrg.setOrgCode(orgCode);
        List<TbOrg> orgList = tbOrgService.getAllList(tbOrg);
        for (TbOrg org : orgList) {
        	searchMap.put("orgName", org.getOrgName());
		}
        if(searchMap != null){
            map.putAll(searchMap);
        }
        
        List<Map> smallSell = dgItemFileMapper.selectItemFileSellOpenDaySummaryList(map);
        
        map.put("smallSell",smallSell);
        
        //退单金额合计
        
        Map backSubtotal = dgItemFileMapper.selectItemFileSellOpenDaybackSubotal(map);
        
        map.put("backSubtotal",backSubtotal == null ? 0.00 :Double.valueOf(backSubtotal.get("subtotal").toString()));
        //充值金额
        String rechargeData = OkHttpUtils.memberPayTypeRecharge(startTime, endTime, "001");
        if(!StringUtils.isEmpty(rechargeData)){
            Map map1 = new Gson().fromJson(rechargeData, Map.class);
            if(map1.get("msgCode").toString().equalsIgnoreCase("OK")){
          		List<Map> recMap = (ArrayList<Map>)map1.get("body");
            	if(recMap.isEmpty()){
            		map.put("rechargeData",0.00);
            	} else {
            		BigDecimal recharge = new BigDecimal(0.0);
            		for(Map r:recMap){
            			recharge = recharge.add(new BigDecimal(r.get("payMoney").toString()));
            		}
            		map.put("rechargeData",recharge);
            	}
          		map.put("rechargeDatas",recMap);
            }
        }
        return map;
    }

    List<Map> mapFill(List<Map> dgItemFiles){
        List<Map> itemSale = new ArrayList<>();
        for(Map dgItemFile:dgItemFiles){
            String pxdlName = dgItemFile.get("pxdlName").toString();
            Map judgeResultMap = checkListMapContainsKey(itemSale, pxdlName);
            Map<String,Object> tempMap = new HashMap<>();
            if(judgeResultMap == null){
                List<Map> pxSaleList = new ArrayList<>();
                tempMap.put("pxdlName",pxdlName);
                pxSaleList.add(dgItemFile);
                tempMap.put("pxSaleList",pxSaleList);
            }else{
                List<Map> pxSaleList = (List<Map>)judgeResultMap.get("pxSaleList");
                List<Map> tempListMap = new ArrayList<>();
                pxSaleList.add(dgItemFile);
                removeMapByKeyInListMap(itemSale,pxdlName);
                tempMap.put("pxdlName",pxdlName);
                tempListMap.add(dgItemFile);
                tempMap.put("pxSaleList",pxSaleList);
            }
            itemSale.add(tempMap);
        }
        return itemSale;
    }

    Map checkListMapContainsKey(List<Map> list,String judgeKey){
        if(list == null || list.size() < 1){
            return null;
        }
        for(Map map:list){
            if(map.get("pxdlName").toString().equals(judgeKey)){
                return map;
            }
        }
        return null;
    }

    void removeMapByKeyInListMap(List<Map> list,String judgeKey){
        if(list == null || list.size() < 1){
            return;
        }

        Iterator<Map> iterator = list.iterator();

        while (iterator.hasNext()){
            Map next = iterator.next();
            if(next.get("pxdlName").toString().equals(judgeKey)){
                iterator.remove();
            }
        }
    }

    List<TbBis> bisTimeSet(List<TbBis> tbBiss) {
        Integer bisLength = tbBiss.size();
        for(int i = 0; i < bisLength; i++){
            TbBis tbBis = tbBiss.get(i);
            if(i == bisLength-1){ //最后一个市别的开始时间与结束时间
                TbBis beginBis = tbBiss.get(0);
                tbBis.setBeginTime(tbBis.getBisTime());
                tbBis.setEndTime(beginBis.getBisTime());
            }else{ //不是最后一个市别的开始时间与结束时间
                TbBis beginBis = tbBiss.get(i+1);
                tbBis.setBeginTime(tbBis.getBisTime());
                tbBis.setEndTime(beginBis.getBisTime());
            }
        }
        return tbBiss;
    }

    Boolean compareTime(List<Map> maps,List<TbBis> tbBiss) {
        for(Map map:maps){
            String hours = map.get("hours").toString(); //查询就餐人数情况的时间段
            String[] split = hours.split("-");
            String beginHour = split[0],endHour = split[1];
            for(TbBis tbBis:tbBiss){

            }
        }
        return true;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> itemFileDataSaleStatistical(String startTime, String endTime, Integer pos, Integer area,
			Integer timeType) {
		Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("area",area);
        map.put("timeType",timeType);
        map.put("pos",pos);

        Boolean flag = TableQueryUtil.openDayReportCheck(startTime,endTime);

        map.put("flag",flag);

        if(!flag){
            List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);
            map.put("tableSuffixList",strings);

            if(strings.size() < 1){
                return null;
            }
        }
        
        Map<String,Object> data = new HashMap<>();
        
        map.put("searchType",1);
        List<Map> data1 = backReportMapper.itemFileDataSaleStatistical(map);
        data.put("data1", data1);
        map.put("searchType",2);
        List<Map> data2 = backReportMapper.itemFileDataSaleStatistical(map);
        data.put("data2", data2);
        map.put("searchType",3);
        List<Map> data3 = backReportMapper.itemFileDataSaleStatistical(map);
        data.put("data3", data3);
        
        for (Map map2 : data2) {
        	String itemfileName2 = map2.get("itemFileName").toString();
			if(data1 != null && (data1.toString()).contains(itemfileName2)){
				for (Map map1 : data1) {
					if(map1.containsValue(itemfileName2)){
						map1.put("itemFileName", map2.get("itemFileName"));
						map1.put("itemFileNumber2", map2.get("itemFileNumber")==null?0:map2.get("itemFileNumber"));
						map1.put("finalPrice2", map2.get("standardPrice")==null?0.0:map2.get("standardPrice"));
					}
				}
			}else{
				Map<String,Object> nmap = new HashMap<>();
				nmap.put("itemFileName", map2.get("itemFileName"));
				nmap.put("itemFileNumber2", map2.get("itemFileNumber")==null?0:map2.get("itemFileNumber"));
				nmap.put("finalPrice2", map2.get("standardPrice")==null?0.0:map2.get("standardPrice"));
				data1.add(nmap);
			}
		}

        for (Map map3 : data3) {
        	String itemfileName3 = map3.get("itemFileName").toString();
        	if(data1 != null && (data1.toString()).contains(itemfileName3)){
        		for (Map map1 : data1) {
					if(map1.containsValue(itemfileName3)){
						map1.put("itemFileName", map3.get("itemFileName"));
						map1.put("itemFileNumber3", map3.get("itemFileNumber")==null?0:map3.get("itemFileNumber"));
						map1.put("finalPrice3", map3.get("finalPrice")==null?0.0:map3.get("finalPrice"));
					}
				}
        	}else{
        		Map<String,Object> nmap = new HashMap<>();
				nmap.put("itemFileName", map3.get("itemFileName"));
				nmap.put("itemFileNumber3", map3.get("itemFileNumber")==null?0:map3.get("itemFileNumber"));
				nmap.put("finalPrice3", map3.get("finalPrice")==null?0.0:map3.get("finalPrice"));
				data1.add(nmap);
        	}
        }

        return data1;
	}

    /*Boolean judgeTime(String beginTime,String endTime,String timeInterval) throws ParseException{

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String[] timeInterValArray = timeInterval.split("-");

        String inBeginTime = timeInterValArray[0],inEndTime = timeInterValArray[1],
                jbTime = "23:59",jeTime = "00:00";

        Date bTime = sdf.parse(beginTime),eTime = sdf.parse(endTime),
            cbTime = sdf.parse(inBeginTime),ceTime = sdf.parse(inEndTime),
                jbTimeD = sdf.parse(jbTime),jeTimeD = sdf.parse(jeTime);

        //如果开始时间大于结束时间，如：21:00-7:00
        if(bTime.compareTo(eTime) > 0){

            //判断开始时间是否大于等于，判断结束时间是否小于23
            if(cbTime.compareTo(bTime) > -1 && cbTime.compareTo(jbTimeD) < 1){

            }
        }else{
            if(cbTime.compareTo(bTime) > -1 && ceTime.compareTo(eTime) < 0){
                return true;
            }
        }
    }*/

}
