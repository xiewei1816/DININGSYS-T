package com.yqsh.diningsys.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yqsh.catering.web.mq.DepositOrderMessage;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.sevlet.CacheInit;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-12 13:17
 * 与线上接口对接的工具类
 * @author zhshuo
 */
public class OnlineHttp extends BaseHttp{
    private static Logger logger = Logger.getLogger(OnlineHttp.class);

    public static Map onlineSeatModify(final String id,final String status){
        //访问开关
        if(!CacheInit.isNet){
            return null;
        }
        //通过线程来处理
        new Thread( new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                formParams.add(new BasicNameValuePair("ids", id));
                formParams.add(new BasicNameValuePair("status", status));
                formParams.add(new BasicNameValuePair("token", tokenGenerate()));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("queuingSYS_URL")+"/offline/table/changeStatus");
                httppost.setEntity(entity);
                setTimeOut(httppost);
                try {
                    String s = responseEntity(HttpClients.createDefault().execute(httppost));
                    //return new Gson().fromJson(s, Map.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("云平台排号系统修改客座状态接口调用错误",e.getCause());
                }
            }
        }).start();
        return null;
    }

    /**
     * 排号验证
     * @param id
     * @param validateCode
     * @return
     */
    public static Map onlineYDValidate(String id,String validateCode){
        try {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("code", validateCode));
            formParams.add(new BasicNameValuePair("tableid", id));
            formParams.add(new BasicNameValuePair("token", tokenGenerate()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("queuingSYS_URL")+"/offline/queue/proving");
            httppost.setEntity(entity);
            setTimeOut(httppost);
            String s = responseEntity(HttpClients.createDefault().execute(httppost));
            return new Gson().fromJson(s, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("云平台排号系统消费验证接口调用错误",e.getCause());
        }
        return null;
    }

    /**
     * 更新所有桌子信息
     * @param sendInfo
     * @return
     */
    public static Map onlineAllSeatModify(String sendInfo){
        try {
            //访问开关  UPDATE_SET_STATE
            if(CacheUtil.getURLInCache("UPDATE_SET_STATE") == null || !"1".equals(CacheUtil.getURLInCache("UPDATE_SET_STATE"))){
                return null;
            }
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("data", sendInfo));
            formParams.add(new BasicNameValuePair("token", tokenGenerate()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("queuingSYS_URL")+"/offline/table/changeStatusForJson");
            httppost.setEntity(entity);
            setTimeOut(httppost);
            String s = responseEntity(HttpClients.createDefault().execute(httppost));
            return new Gson().fromJson(s, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("云平台排号系统修改客座状态接口调用错误",e.getCause());
        }
        return null;
    }


    /**
     * 获取线上外卖信息
     * @return
     */
    public static String onlineTakeOutOrderOnline(){
        try {
        //访问开关 外卖
            if(CacheUtil.getURLInCache("IS_WM") == null || !"1".equals(CacheUtil.getURLInCache("IS_WM"))){
                return null;
            }
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("status", "2"));
            formParams.add(new BasicNameValuePair("page", "1"));
            formParams.add(new BasicNameValuePair("pageSize", "20"));
            formParams.add(new BasicNameValuePair("token", tokenGenerate()));
            HttpGet httpget = new HttpGet(CacheUtil.getURLInCache("queuingSYS_URL")+"/instore/shop/order/takeoutOrder"+ "?"+URLEncodedUtils.format( formParams, "utf-8" ));
            return responseEntity(HttpClients.createDefault().execute(httpget));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("云平台排号系统获取线上外卖信息接口调用错误",e.getCause());
        }
        return null;
    }


    /**
     * 确定线上外卖信息
     * @return
     */
    public static Map onlineTakeOutOrderOnlineYr(String orderId){
        try {
            //访问开关 外卖
            if(CacheUtil.getURLInCache("IS_WM") == null || !"1".equals(CacheUtil.getURLInCache("IS_WM"))){
                return null;
            }
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("orderId", orderId));
            formParams.add(new BasicNameValuePair("flag", "1"));
            formParams.add(new BasicNameValuePair("token", tokenGenerate()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("queuingSYS_URL")+"/instore/shop/order/sendOrderForInstore");
            httppost.setEntity(entity);
            setTimeOut(httppost);
            String s = responseEntity(HttpClients.createDefault().execute(httppost));
            return new Gson().fromJson(s, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("云平台排号系统确定线上外卖信息接口调用错误",e.getCause());
        }
        return null;
    }

    /**
     * 确定线上订单信息
     * @return
     */
    public static String onlineOrderInfo(String orderId) throws Exception{
        //访问开关 外卖
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("orderid", orderId));
        formParams.add(new BasicNameValuePair("type", "instoreOrder"));
        formParams.add(new BasicNameValuePair("token", tokenGenerate()));
        HttpGet httpget = new HttpGet("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/instore/shop/order/getOrder"+ "?"+URLEncodedUtils.format( formParams, "utf-8" ));
        return responseEntity(HttpClients.createDefault().execute(httpget));
    }
    
    

    /**
     * 返回订单信息
     * @return
     */
    public static Map onlineOrderBack(String data) throws Exception{
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("data", data));
        formParams.add(new BasicNameValuePair("type", "instoreOrder"));
        formParams.add(new BasicNameValuePair("token", tokenGenerate()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost httppost = new HttpPost("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/instore/shop/order/conform");
        httppost.setEntity(entity);
        setTimeOut(httppost);
        String s = responseEntity(HttpClients.createDefault().execute(httppost));
        if(s == null){
        	throw new Exception();
        }
        return new Gson().fromJson(s, Map.class);
    }
    
    /**
     * 获取线上预定数据列表
     * @return
     */
    public static String onlineReserveInfo(int page,int pagesize,String freeText,String state) throws Exception{
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("page", page+""));
        formParams.add(new BasicNameValuePair("freeText", freeText));
        formParams.add(new BasicNameValuePair("status", state));
        formParams.add(new BasicNameValuePair("pageSize", pagesize+""));
        HttpGet httpget = new HttpGet("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/instore/shop/order/queryDepositOrder/"+CacheUtil.getURLInCache("queuingSYS_ID")+"?"+URLEncodedUtils.format( formParams, "utf-8" ));
        return responseEntity(HttpClients.createDefault().execute(httpget));
    }
    
    /**
     * 确定线上预定信息
     * @return
     */
    public static String onlineReserveInfo(String orderId,boolean isAccept,String note){
        //访问开关 外卖
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("orderId", orderId)); //订单id
        formParams.add(new BasicNameValuePair("isAccept", isAccept?"true":"false")); //是否接受
        formParams.add(new BasicNameValuePair("type", "deposit"));
        formParams.add(new BasicNameValuePair("note", note));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost httppost = new HttpPost("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/instore/shop/order/conform.json");
        httppost.setEntity(entity);
        setTimeOut(httppost);
        String s = null;
		try {
			s = responseEntity(HttpClients.createDefault().execute(httppost));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return s;
    }
    
    /**
     * 确定线上预定信息10分钟未到提醒
     * @return
     */
    public static String onlineSendRemindersInfo(String orderId){
        //访问开关 外卖
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("orderId", orderId)); //订单id
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost httppost = new HttpPost("http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/instore/shop/order/sendReminders.json");
        httppost.setEntity(entity);
        setTimeOut(httppost);
        String s = null;
		try {
			s = responseEntity(HttpClients.createDefault().execute(httppost));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return s;
    }


    public static String sbHttpPost(String gateway,String jsonParam){
        String xmlText = "";
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(gateway);
        httpPost.addHeader("charset", "UTF-8");
        System.out.println(jsonParam.toString());
        StringEntity stentity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
        stentity.setContentEncoding("UTF-8");
        stentity.setContentType("application/json");
        httpPost.setEntity(stentity);
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                String text;
                while ((text = bufferedReader.readLine()) != null) {
                    xmlText = xmlText + text;
                }
            }
            EntityUtils.consume(entity);
            response.close();
            httpclient.close();
            System.out.println(xmlText);
        } catch (Exception e){
            logger.error("ERROR WHILE HTTP POST REQUEST(IOException) WITH URL: sbHttpPost"+gateway,e);
        }
        return xmlText;
    }
    
    static String tokenGenerate(){
//        return EncryptInstore.encryptData(DgConstants.getApplicationPropertiesValue("online_shopKey"),
//        		DgConstants.getApplicationPropertiesValue("online_key"));
        return EncryptInstore.encryptData(CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY"),
                CacheUtil.getURLInCache("queuingSYS_key"));
    }

    private static String responseEntity(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        return statusCode == HttpStatus.SC_OK ? EntityUtils.toString(httpResponse.getEntity()) : null;
    }

}
