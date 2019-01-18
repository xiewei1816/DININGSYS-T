package com.yqsh.diningsys.web.util;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.core.util.pay.DgConstants;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.api.APIMainMapper;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysDatauploadLog;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.service.data_upload.BusinessDataUploadService;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/** * Created on 2017-04-26 11:11
 * 自动上传分店营业数据
 * @author zhshuo业营业数据上传
 */
//@Component
public class BusinessDataUpload extends BaseHttp {

    private static BusinessDataUploadService businessDataUploadService;

	static{
		businessDataUploadService = (BusinessDataUploadService) SpringContextUtil.getBean("businessDataUploadServiceImpl");
	}

    /**
     * 定时上传分店中的营业流水数据
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void autoDataUpload(){
        try {
            Integer uploadDataSize = Integer.parseInt(DgConstants.getApplicationPropertiesValue("dataUploadSize"));

            String normalLastTime = businessDataUploadService.selectLastUploadTime(1);

            Integer integer = businessDataUploadService.selectCountClearingWater(uploadDataSize,normalLastTime);

            Integer startLimit = 0;
            String status = "";

            for(int i = 1; i <= integer; i++){
                startLimit = i == 1?0:uploadDataSize*(i-1);

                Map<String, Object> map = businessDataUploadService.selectClearingWater(i,startLimit,uploadDataSize,normalLastTime);
                String lastTime = MapUtils.getString(map,"lastTime");

                status = executeHttpPost(map);

                if(status.equals("200")){
                    businessDataUploadService.insertBusinessDataUploadLog(new SysDatauploadLog(UUID.randomUUID().toString(), lastTime,1));
                }else{
                    break;
                }
            }

            //返位结算数据上传
            String returnLastTime = businessDataUploadService.selectLastUploadTime(2);
            LinkedList<DgReceptionClearingWater> fwjsIds = businessDataUploadService.selectReturnSettle(returnLastTime);
            if(fwjsIds != null && !fwjsIds.isEmpty()){
                Map<String,Object> returnSettlemap = new HashMap<>();
                returnSettlemap.put("fwjsId",fwjsIds);
                returnSettlemap.put("shopKey",CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY"));
                returnSettlemap.put("uploadFlag","3");
                status = executeHttpPost(returnSettlemap);
                if(status.equals("200")){
                    businessDataUploadService.insertBusinessDataUploadLog(new SysDatauploadLog(UUID.randomUUID().toString(), fwjsIds.getLast().getReturnSettleTime(),2));
                }
            }

           //正常结算相关数据上传完毕之后，废单查询上传，只上传营业流水，其余数据不上传
           String trashLastTime = businessDataUploadService.selectLastUploadTime(3);
           LinkedList<DgOpenWater> sBillOpenWater = businessDataUploadService.selectSBillOpenWater(trashLastTime);
           if(sBillOpenWater != null && !sBillOpenWater.isEmpty()){
               Map<String,Object> sBillMap = new HashMap<>();
               sBillMap.put("shopKey",CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY"));
               sBillMap.put("trashWater",sBillOpenWater);
               sBillMap.put("uploadFlag","2");
               status = executeHttpPost(sBillMap);
               if(status.equals("200")){
                   businessDataUploadService.insertBusinessDataUploadLog(new SysDatauploadLog(UUID.randomUUID().toString(), sBillOpenWater.getLast().getHandingsbillTime(),3));
               }
           }

            //上传授权码日志数据
            String authCodeLastTime = businessDataUploadService.selectLastUploadTime(4);
            LinkedList<SysAuthorizationLog> sysAuthorizationLogs = businessDataUploadService.selectAuthCodeLogData(authCodeLastTime);
            if(sysAuthorizationLogs != null && !sysAuthorizationLogs.isEmpty()){
                Map<String,Object> authCodeMap = new HashMap<>();
                authCodeMap.put("shopKey",CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY"));
                authCodeMap.put("authCodeData",sysAuthorizationLogs);
                authCodeMap.put("uploadFlag","4");
                status = executeHttpPost(authCodeMap);
                if(status.equals("200")){
                    businessDataUploadService.insertBusinessDataUploadLog(new SysDatauploadLog(UUID.randomUUID().toString(), sysAuthorizationLogs.getLast().getAuthTime(),4));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String executeHttpPost(Map map){
        try {
            HttpPost httpPost = new HttpPost();
            UrlEncodedFormEntity entity = setHttpParams(httpPost,map, "/system/directStoreUp/receive.jspx");
            httpPost.setEntity(entity);
            setTimeOut(httpPost);
            Map resMap = responseEntity(HttpClients.createDefault().execute(httpPost));
            return MapUtils.getString(resMap, "status");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "500";
    }

    private static Map responseEntity(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String resStr =  statusCode == HttpStatus.SC_OK ? EntityUtils.toString(httpResponse.getEntity()) : null;
        if(StringUtils.isEmpty(resStr)){
            return null;
        }
        return JSON.parseObject(resStr, Map.class);
    }

    private static UrlEncodedFormEntity setHttpParams(HttpPost httpPost, Map<String,Object> param, String url) {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("jsonData", JSON.toJSONString(param)));
        httpPost.setURI(URI.create(getUrl() + url));
        return new UrlEncodedFormEntity(formParams, Consts.UTF_8);
    }

    private static String getUrl() {
       return CacheUtil.getURLInCache("DOWN_DATA_URL");
//       return "http://localhost:8090/cateringChain";
    }

}