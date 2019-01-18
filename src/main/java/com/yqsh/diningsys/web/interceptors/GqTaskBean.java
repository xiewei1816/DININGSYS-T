package com.yqsh.diningsys.web.interceptors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOffsetMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOffset;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.model.print.DgPrintData;
import com.yqsh.diningsys.web.service.api.DgCallServiceService;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemOutofStockService;
import com.yqsh.diningsys.web.service.online.DgTakeoutByonlineService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.util.EncryptInstore;
import com.yqsh.diningsys.web.util.OnlineHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.MediaType;

/**
 * 用于沽清市别/日期类型 定时删除
 * 打印轮循
 *
 * @author Heshuai
 */
//@Component
public class GqTaskBean {

    public static final MediaType JSONGBK = MediaType
            .parse("application/json; charset=gbk");

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private static SimpleDateFormat formatDate = new SimpleDateFormat(
            "yyyy-MM-dd"); // 年月日

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss"); //年月日 时分秒

    private static Object lock = new Object();//锁对象

    private static Object storeLock = new Object();//锁对象

    private static Object onlineLock = new Object();//锁对象

    private static Object cxptLock = new Object();//创享平台锁

    private static DgItemOutofStockService dgItemOutofStockService;

    private static TbBisService tbBisService;

    private static DgPrintDataService dgPrintDataService;

    private static ReserveManagerService reserveManagerService;

    private static DgConsumerSeatService dgConsumerSeatService;

    @Autowired
    private DgUrlSettingService dgUrlSettingService;

	@Autowired
	private TableInfoService tableInfoService;

	@Autowired
	private DgTakeoutByonlineService dgTakeoutByonlineService;

	@Autowired
    private DgOffsetMapper dgOffsetMapper;
	private static DgCallServiceService dgCallServiceService;
	
	static{
		tbBisService = (TbBisService) SpringContextUtil.getBean("tbBisServiceImpl");
		dgItemOutofStockService = (DgItemOutofStockService) SpringContextUtil.getBean("dgItemOutofStockServiceImpl");
		dgPrintDataService = (DgPrintDataService) SpringContextUtil.getBean("dgPrintDataServiceImpl");
		reserveManagerService = (ReserveManagerService) SpringContextUtil.getBean("reserveManagerServiceImpl");
		dgConsumerSeatService = (DgConsumerSeatService) SpringContextUtil.getBean("dgConsumerSeatServiceImpl");
		dgCallServiceService = (DgCallServiceService) SpringContextUtil.getBean("dgCallServiceServiceImpl");
		
	}

    static String tokenGenerate(){
      return EncryptInstore.encryptData(CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY"),
              CacheUtil.getURLInCache("queuingSYS_key"));
    }

    @Scheduled(cron = "0 0/2 *  * * ? ")   //每2分钟执行一次
    public void checkTime() throws Exception  {
        List<TbBis> tbBiss = tbBisService.selectAllBis();
        List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
        for (int j = 0; j < tbBiss.size(); j++) {
            if (j != tbBiss.size() - 1) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(j).getBisTime());
                m.put("endTime", tbBiss.get(j + 1).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("id", tbBiss.get(j).getId());
                timeD.add(m);
            } else {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(j).getBisTime());
                m.put("endTime", tbBiss.get(0).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("id", tbBiss.get(j).getId());
                timeD.add(m);
            }
        }
        int TbisID = 0; // 获取市别id
        for (int j = 0; j < timeD.size(); j++) {
            int count = tbBisService.calculateSJD(timeD.get(j));
            if (count > 0) {
                TbisID = (int) timeD.get(j).get("id");
                break;
            }
        }
        if (TbisID == 0 && timeD.size() >= 1) // 没有就是最后个时段
        {
            TbisID = (int) timeD.get(timeD.size() - 1).get("id");
        }
        DgItemOutofStock bisDel = new DgItemOutofStock();
        bisDel.setmBis(TbisID);
        bisDel.setmType(2);
        dgItemOutofStockService.deleteByType(bisDel);

        DgItemOutofStock dayDel = new DgItemOutofStock();
        dayDel.setDate(new Date());
        dayDel.setmType(3);
        dgItemOutofStockService.deleteByType(dayDel);

    }

    /**
     * 打印轮循,是否存在数据
     * 存在发送数据
     *
     * @throws Exception
     */
    @Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
    public void printLoop() throws Exception {
        synchronized (lock) {
            int printCount = dgPrintDataService.getCount();
            while (printCount > 0) {
                DgPrintData pd = dgPrintDataService.getFirstItem();
                HttpClient httpClient = HttpClients.createDefault();
                String url = CacheUtil.getURLInCache("print.url");
                HttpPost method = new HttpPost(url);
                method.addHeader("Content-type", "application/json; charset=gbk");
                method.setEntity(new StringEntity(pd.getContent(), "GBK"));
                HttpResponse response = httpClient.execute(method);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    dgPrintDataService.deleteByPrimaryKey(pd.getId());
                }
                printCount = dgPrintDataService.getCount();
            }
        }
    }



    /**
     * 更新库存轮循,是否存在数据
     * 存在发送数据
     *
     * @throws Exception
     */
    @Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
    public void updateStoreLoop() throws Exception {
        String isOffset = CacheUtil.getURLInCache("IS_OFFSET");
        if(isOffset != null && isOffset.equals("1")){
            synchronized (storeLock) {
                int offsetCount = dgOffsetMapper.getCount();
                while (offsetCount > 0) {
                    DgOffset pd = dgOffsetMapper.getFirstItem();
                    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                    formParams.add(new BasicNameValuePair("orgs", pd.getContent()));
                    formParams.add(new BasicNameValuePair("type", pd.getType().toString()));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                    HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("store_url")+"/yqshapi/offset.htm");
                    httppost.setEntity(entity);
                    CloseableHttpResponse response = HttpClients.createDefault().execute(httppost);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        dgOffsetMapper.deleteByPrimaryKey(pd.getId());
                    }
                    offsetCount = dgOffsetMapper.getCount();
                }
            }
        }
    }


    /**
     * 刷新平台客位
     * 存在发送数据
     * @throws Exception
     */
    @Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
    public void updateSeatFree() throws Exception {
        String IS_UPDATE_CXPT = CacheUtil.getURLInCache("IS_UPDATE_CXPT");
        if(StringUtil.isNotEmpty(IS_UPDATE_CXPT) && IS_UPDATE_CXPT.equals("1")){
            synchronized (cxptLock) {
                int cxptCount = dgCallServiceService.selectCxptCount();
                while (cxptCount > 0) {
                    DgCallService pd = dgCallServiceService.getFirstCxptItem();
                    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                    formParams.add(new BasicNameValuePair("seatUuidKey", pd.getContent()));
                    formParams.add(new BasicNameValuePair("shopKey", CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY")));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                    HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("CXPTURL")+"/h5/cashier/updateSeatFree.json");
                    httppost.setEntity(entity);
                    CloseableHttpResponse response = HttpClients.createDefault().execute(httppost);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        dgCallServiceService.deleteByPrimaryKey(pd.getId());
                    }
                    cxptCount = dgCallServiceService.selectOnlineCount();
                }
            }
        } else {
            DgCallService pd = dgCallServiceService.getFirstCxptItem();
            if(pd != null){
                dgCallServiceService.deleteByPrimaryKey(pd.getId());
            }
        }
    }

    /**
     * 打印轮循,是否存在数据
     * 存在发送数据
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 */1 *  * * ? ")
    public void yd() throws Exception {
        //预定操作
        reserveManagerService.updateSeatToYd(simpleDateFormat.format(new Date()));
        
        //通知10分钟内未到客户
        reserveManagerService.noticeNotArrive(simpleDateFormat.format(new Date()));
    }

    /**
     * 清扫状态修改和轮训平台座位状态
     * @throws Exception
     */
    @Scheduled(cron = "0 0/1 *  * * ? ")
    public void clearingStateReset() throws Exception {

        //清扫状态
        try{
            List<DgConsumerSeat> dgConsumerSeats = dgConsumerSeatService.selectAllClearingSeat();
            if(dgConsumerSeats.size() > 0){
                for(DgConsumerSeat dgConsumerSeat:dgConsumerSeats){
                    DgReceptionClearingWater clearingWater = dgConsumerSeatService.selectEndTime(dgConsumerSeat.getId());
                    String clearingTime = clearingWater.getClearingTime();
                    long l = DateUtil.dateIntervalMin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(clearingTime), new Date());
                    if(l >= dgConsumerSeat.getQssc()){//*1000*60
                        dgConsumerSeatService.modifySeatSeat(dgConsumerSeat.getId());
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //线下结账,发送清理台位线上点菜缓存信息
        try{
            //发送清理缓存数据到线上
            if(CacheUtil.getURLInCache("ONLINE_ORDER") != null && "1".equals(CacheUtil.getURLInCache("ONLINE_ORDER"))){
                synchronized (onlineLock) {
                    int onlineCount = dgCallServiceService.selectOnlineCount();
                    while (onlineCount > 0) {
                          DgCallService pd = dgCallServiceService.getFirstItem();
                          List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                          formParams.add(new BasicNameValuePair("tableid", pd.getOwNum()));
                          formParams.add(new BasicNameValuePair("token", tokenGenerate()));
                          HttpGet httpget = new HttpGet("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/instore/shop/order/clearTable"+ "?"+URLEncodedUtils.format( formParams, "utf-8" ));
                          CloseableHttpResponse response = HttpClients.createDefault().execute(httpget);
    	                  int statusCode = response.getStatusLine().getStatusCode();
    	                  if (statusCode == HttpStatus.SC_OK) {
    	                	  dgCallServiceService.deleteByPrimaryKey(pd.getId());
    	                  }
    	                  onlineCount = dgCallServiceService.selectOnlineCount();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

//      //实时获取外卖信息到先下,打印,发送前台
//        try{
//            String src = OnlineHttp.onlineTakeOutOrderOnline();
//            if(!StringUtils.isEmpty(src)) {
//               	JSONObject  body = JSONObject.fromObject(src);
//                String status = body.get("status").toString();
//                if(Double.valueOf(status) == 200){
//                	String content = body.get("content").toString();
//                   	JSONObject  con = JSONObject.fromObject(content);
//                    List<Map<String, Object>> rows= JSON.parseObject(con.get("rows").toString(), new TypeToken<List<Map<String, Object>>>(){}.getType());
//                	if(rows.size() > 0){
//                		String orderIds = "";
//                		for(Map<String, Object> r:rows){
//                			r.put("orderDetails",r.get("orderDetails").toString());
//                			orderIds += r.get("id").toString()+",";
//                		}
//                		orderIds = orderIds.substring(0, orderIds.length()-1);
//                		dgTakeoutByonlineService.insertOrEdit_takeout_online(rows);
////                		dgPrintDataService.insertWmBill(rows);
//                		OnlineHttp.onlineTakeOutOrderOnlineYr(orderIds);
//                		CacheUtil.setWmCache("1");
//                	}
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }


        //实时发送客位信息到线上,线上开台下面同步数据
        try{
            List<Map> dgConsumerSeats = dgConsumerSeatService.selectAllSeat();
            String sendInfo = com.alibaba.fastjson.JSON.toJSONString(dgConsumerSeats);
            Map map = OnlineHttp.onlineAllSeatModify(sendInfo);
            if(map != null){
            	String status = map.get("status").toString();
                if(Double.valueOf(status) == 200){
                	String content = map.get("content").toString();
                	JSONObject obj = JSONObject.fromObject(content);
                	JSONArray arr = JSONArray.fromObject(obj.getString("result"));
                	List<Map> backList = new ArrayList<Map>();
                	if(arr.size() > 0){
                		for(int i=0;i<arr.size();i++){
                			JSONObject seat = arr.getJSONObject(i);
                			Map seatInfo = new HashMap();
                			seatInfo.put("uuidKey",seat.getString("id"));
                			seatInfo.put("status",seat.getInt("status"));
                			seatInfo.put("num",seat.get("num") == null ? "0":seat.getString("num"));
                			backList.add(seatInfo);
                		}
                		for(Map oldSeatInfo : dgConsumerSeats){
                			for(Map newSeatInfo : backList){
                				String oldSeatInfoUuidKey = oldSeatInfo.get("id").toString();
                				String newSeatInfoUuidKey = newSeatInfo.get("uuidKey").toString();
                				int oldSeatStates = Integer.valueOf(oldSeatInfo.get("status").toString());
                				int newSeatStates = Integer.valueOf(newSeatInfo.get("status").toString());
                				if(oldSeatInfoUuidKey.equals(newSeatInfoUuidKey)){
                					//店内空闲  线上绑定
                					if(oldSeatStates == 1 && newSeatStates == 6){
                	            		dgConsumerSeatService.updateSeatByUuidKey(newSeatInfo);
                					}
                					//店内绑定  线上空闲
                					if(oldSeatStates == 6 && newSeatStates == 1){
                	            		dgConsumerSeatService.updateSeatByUuidKey(newSeatInfo);
                					}
                					//店内绑定  线上开台
                					if(oldSeatStates == 6 && newSeatStates == 99){
                						Map<String, Object> ret = tableInfoService.openBill(newSeatInfo.get("num").toString(),
                								"yxe_water", ""+dgConsumerSeatService.selectSeatIdByUuidKey(newSeatInfoUuidKey).getId(), "yxe_pos", null,null,true);

                						//返回值ret暂时不处理
                					}
                					break;
                				}
                			}
                		}
                	}
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
