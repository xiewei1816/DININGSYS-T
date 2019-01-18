package com.yqsh.diningsys.api.util;

import com.alibaba.fastjson.JSONObject;
import com.yqsh.diningsys.web.cache.CacheUtil;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OkHttpUtils{

    private static Logger logger = Logger.getLogger(OkHttpUtils.class);

    private static String JSON_ACCEPT = "application/json";
    private static String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 获取会员列表
     *
     */
    public static String getMemberList(String body){
        if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
            return null;
        }
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipParams";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipParams.htm";
        }
        HttpPost method = new HttpPost(url);
        setJsonHeader(method);
        try {
            if (body != null) method.setEntity(new StringEntity(body));
            return responseEntity(HttpClients.createDefault().execute(method));
        } catch (Exception e) {
            logger.error("获取会员列表接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 获取会员等级明细
     *
     * @return
     */
    public static String getMemberLevel(String encDog){
        if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
            return null;
        }
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("comId", CacheUtil.getURLInCache("memberZDKey")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipGradeData";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipGradeData.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("营业报表获取会员信息接口错误",e.getCause());
        }
        return null;
    }

    public static String saveMember(String body) {
        if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
            return null;
        }
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/saveCust";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/saveCust.htm";
        }
        HttpPost method = new HttpPost(url);
        setJsonHeader(method);
        try {
            if (body != null) method.setEntity(new StringEntity(body,Consts.UTF_8));
            return responseEntity(HttpClients.createDefault().execute(method));
        } catch (IOException e) {
            logger.error("保存会员信息接口错误",e.getCause());
        }
        return null;
    }

    public static String memberRechargeBefore(String body){
        if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
            return null;
        }
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getProm";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getProm.htm";
        }
        HttpPost method = new HttpPost(url);
        setJsonHeader(method);
        try {
            if (body != null) method.setEntity(new StringEntity(body));
            return responseEntity(HttpClients.createDefault().execute(method));
        } catch (IOException e) {
            logger.error("获取会员卡信息接口错误",e.getCause());
        }
        return null;
    }

    public static String memberRecharge(String orgs){
        if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
            return null;
        }
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/topUp";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/topUp.htm";
        }
        HttpPost method = new HttpPost(url);
        setJsonHeader(method);
        try {
            if (orgs != null) method.setEntity(new StringEntity(orgs));
            return responseEntity(HttpClients.createDefault().execute(method));
        } catch (IOException e) {
            logger.error("会员卡充值接口错误",e.getCause());
        }
        return null;
    }

    public static String memberGetVipAllCopu(String cardId){
        try {
            if(CacheUtil.getURLInCache("USE_MEMBER") == null || !CacheUtil.getURLInCache("USE_MEMBER").equals("1")){
                return null;
            }
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("cardId", cardId));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            String url = "";
            if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
                url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipAllCopu";
            }else{
                url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipAllCopu.htm";
            }
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(entity);
            setTimeOut(httppost);

            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("会员卡充值接口错误",e.getCause());
        }
        return null;
    }

    public static String memberGetRecharge(String startTime,String endTime,String operUser){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("startTime", startTime));
        formParams.add(new BasicNameValuePair("endTime", endTime));
        formParams.add(new BasicNameValuePair("operUser", operUser));
        formParams.add(new BasicNameValuePair("comId", CacheUtil.getURLInCache("encDog")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getPayWater";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getPayWater.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取会员充值统计信息接口错误",e.getCause());
        }
        return null;
    }

    public static String memberPayMent(String jsonData, String sha1, String applyId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("applyId", applyId));
        formParams.add(new BasicNameValuePair("sign", sha1));
        formParams.add(new BasicNameValuePair("cipInfo", jsonData));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
//        HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("member.url") + "/ext/custMange/getPayWater.htm");
        //2017年9月21日14:56:15 会员支付接口更改
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipCons";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipCons.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("会员卡支付接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 微信二维码支付
     * @param jsonData
     * @param sha1
     * @param applyId
     * @return
     */
    public static String memberWxPayMent(String jsonData, String sha1, String applyId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("applyId", applyId));
        formParams.add(new BasicNameValuePair("sign", sha1));
        formParams.add(new BasicNameValuePair("cipInfo", jsonData));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/vipConsByPayCode";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/vipConsByPayCode.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("会员卡支付接口错误",e.getCause());
        }
        return null;
    }

    public static String yxePayMent(String cipInfo,String sign){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("cipInfo", cipInfo));
        formParams.add(new BasicNameValuePair("sign", sign));
        formParams.add(new BasicNameValuePair("applyId", CacheUtil.getURLInCache("yxe.applyId")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/yxeQrCodePay";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/yxeQrCodePay.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("易小二支付接口错误",e.getCause());
        }
        return null;
    }

    public static String yxeLoopPayStateQuery(String cipInfo, String sign) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("cipInfo", cipInfo));
        formParams.add(new BasicNameValuePair("sign", sign));
        formParams.add(new BasicNameValuePair("applyId", CacheUtil.getURLInCache("yxe.applyId")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(CacheUtil.getURLInCache("yxe.url") + "/frontDesk/yxe/csPolling.htm");
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("易小二支付查询错误",e.getCause());
        }
        return null;
    }

    public static String openDayReportVipInfo(String startTime,String endTime){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("startTime", startTime));
        formParams.add(new BasicNameValuePair("endTime", endTime));
        formParams.add(new BasicNameValuePair("encDog", CacheUtil.getURLInCache("encDog")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipData";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getVipData.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("营业报表获取会员信息接口错误",e.getCause());
        }
        return null;
    }

    //获取卡券信息
    public static String getWxCouponInfo(String couponId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("couponId", couponId));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/getCouponById";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/getCouponById.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取卡券接口错误",e.getCause());
        }
        return null;
    }

    //核销卡券
    public static String writeOffWxCouponInfo(String couponValues,String orderWater,String consComId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("couponValues", couponValues));
        formParams.add(new BasicNameValuePair("orderWater", orderWater));
        formParams.add(new BasicNameValuePair("consComId", consComId));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/updateCouponStateById";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/updateCouponStateById.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取卡券接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 获取手机验证码
     * @return
     */
    public static Map getHyPayVailCode(String mobile,String comId) throws Exception{
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("mobile", mobile));
        formParams.add(new BasicNameValuePair("comId", comId));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipPayVerificationCode";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipPayVerificationCode.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        String s = responseEntity(HttpClients.createDefault().execute(httppost));
        if(s == null){
            throw new Exception();
        }
        return JSONObject.parseObject(s, Map.class);
    }

    /**
     * 会员手机消费
     * @param jsonData
     * @param sha1
     * @param applyId
     * @return
     */
    public static String memberPayMentByMobile(String jsonData, String sha1, String applyId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("applyId", applyId));
        formParams.add(new BasicNameValuePair("sign", sha1));
        formParams.add(new BasicNameValuePair("cipInfo", jsonData));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipConsByMobile";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/vipConsByMobile.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("会员卡支付接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 获取会员充值信息
     * @param startTime
     * @param endTime
     * @param operUser
     * @return
     */
    public static String memberNewGetRecharge(String startTime,String endTime,String operUser){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("startTradeTime", startTime));
        formParams.add(new BasicNameValuePair("endTradeTime", endTime));
        formParams.add(new BasicNameValuePair("operUser", operUser));
        formParams.add(new BasicNameValuePair("comId", CacheUtil.getURLInCache("member.comId")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/newGetPayWater";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/newGetPayWater.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取会员充值统计信息接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 获取会员充值信息
     * @param startTime
     * @param endTime
     * @param operUser
     * @return
     */
    public static String memberPayTypeRecharge(String startTime,String endTime,String operUser){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("startTradeTime", startTime));
        formParams.add(new BasicNameValuePair("endTradeTime", endTime));
        formParams.add(new BasicNameValuePair("operUser", operUser));
        formParams.add(new BasicNameValuePair("comId", CacheUtil.getURLInCache("member.comId")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getSumPayWater";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/getSumPayWater.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取会员充值统计信息接口错误",e.getCause());
        }
        return null;
    }

    /**
     * 会员返回结算
     * @param ids
     * @return
     */
    public static String memberRConsWater(String ids){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("ids", ids));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/reConsWater";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/custMange/reConsWater.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("会员卡支付接口错误",e.getCause());
        }
        return null;
    }


    //卡券返位
    public static String writeOffWxFwCouponInfo(String couponValues,String orderWater,String consComId){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("couponValues", couponValues));
        formParams.add(new BasicNameValuePair("orderWater", orderWater));
        formParams.add(new BasicNameValuePair("consComId", consComId));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        String url = "";
        if(CacheUtil.getURLInCache("member.source").equals("cxpt")){
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/fwCouponStateById";
        }else{
            url = CacheUtil.getURLInCache("member.url") + "/ext/wxMange/fwCouponStateById.htm";
        }
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        setTimeOut(httppost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httppost));
        } catch (IOException e) {
            logger.error("获取卡券接口错误",e.getCause());
        }
        return null;
    }

    private static void setJsonHeader(HttpPost httpPost){
        setTimeOut(httpPost);
        httpPost.addHeader("Content-type", JSON_CONTENT_TYPE);
        httpPost.setHeader("Accept", JSON_ACCEPT);
    }

    private static void setTimeOut(HttpPost httpPost) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(5000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000).build();
        httpPost.setConfig(requestConfig);
    }

    private static String responseEntity(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        return statusCode == HttpStatus.SC_OK ? EntityUtils.toString(httpResponse.getEntity()) : null;
    }

}
