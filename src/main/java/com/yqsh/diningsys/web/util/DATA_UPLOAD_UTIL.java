package com.yqsh.diningsys.web.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.web.service.data_upload.DataUploadService;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-21 11:09
 * 数据上传
 * @author zhshuo
 */
public class DATA_UPLOAD_UTIL extends BaseHttp {

    private static Logger logger = Logger.getLogger(DATA_DOWN_UTIL.class);

    private static DataUploadService dataUploadService;

    static {
        dataUploadService = SpringContextUtil.getApplicationContext().getBean(DataUploadService.class);
    }

    /**
     * 员工管理
     */
    public static Map DATA_UPLOAD_YGGL(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_YGGL.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传员工管理数据出错", e.getCause());
        }
        return null;
    }

    
    /**
     * 品项管理(1)
     */
    public static Map DATA_UPLOAD_PXGL_ONE(String pxgl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxgl_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXGL_ONE.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项管理管理数据(1)出错", e.getCause());
        }
        return null;
    }
    
    /**
     * 品项管理(2)
     */
    public static Map DATA_UPLOAD_PXGL_TWO(String pxgl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxgl_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXGL_TWO.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项管理管理数据(2)出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_SCZT(String sczt_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,sczt_json_data, "/franchise/dataReceive/DATA_RECEIVE_SCZT.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传上菜状态数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_ZDYY(String zdyy_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zdyy_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZDYY.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传赠单原因数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_BMGL(String bmgl_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,bmgl_json_data, "/franchise/dataReceive/DATA_RECEIVE_BMGL.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传部门管理数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_YYSB(String yysb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yysb_json_data, "/franchise/dataReceive/DATA_RECEIVE_YYSB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传营业市别数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_TCYY(String tcyy_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,tcyy_json_data, "/franchise/dataReceive/DATA_RECEIVE_TCYY.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传退菜原因数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_ZDBZ(String zdbz_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zdbz_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZDBZ.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传整单备注数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_GGDM(String ggdm_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,ggdm_json_data, "/franchise/dataReceive/DATA_RECEIVE_GGDM.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传公共代码数据出错", e.getCause());
        }
        return null;
    }

    /** ********************************************BY XW S********************************************** */

    public static Map DATA_UPLOAD_PXGQ(String pxgq_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxgq_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXGQ.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项沽清数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXXLZB(String pxxlzb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxxlzb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXXLZB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项限量（主表）数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXXLFB(String pxxlfb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxxlfb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXXLFB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项限量（附表）数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXTY(String pxty_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxty_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXTY.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项停用数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_TJCP(String tjcp_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,tjcp_json_data, "/franchise/dataReceive/DATA_RECEIVE_TJCP.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传推荐菜品数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_ZXCP(String zxcp_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zxcp_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZXCP.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传最新菜品数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXCPBMZB(String pxcpbmzb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxcpbmzb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXCPBMZB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项出品部门（主表）数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXCPBMFB(String pxcpbmfb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxcpbmfb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXCPBMFB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项出品部门（附表）数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_TSPX(String tspx_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,tspx_json_data, "/franchise/dataReceive/DATA_RECEIVE_TSPX.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传特殊品项数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXCPCS(String pxcpcs_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxcpcs_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXCPCS.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项出品厨师数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_ZDYJEPXXL(String zdyjepxxl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zdyjepxxl_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZDYJEPXXL.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传自定义金额小类数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOADZDYJEPX(String zdyjepx_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zdyjepx_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZDYJEPX.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传自定义金额品项数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_ZDYJEMC(String zdyjemc_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,zdyjemc_json_data, "/franchise/dataReceive/DATA_RECEIVE_ZDYJEMC.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传自定义金额名称数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXCXFAZB(String pxcxfazb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxcxfazb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXCXFAZB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项促销方案（主表）数据出错", e.getCause());
        }
        return null;
    }

    public static Map DATA_UPLOAD_PXCXFAFB(String pxcxfafb_json_data) {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,pxcxfafb_json_data, "/franchise/dataReceive/DATA_RECEIVE_PXCXFAFB.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传品项促销方案（附表）数据出错", e.getCause());
        }
        return null;
    }

    /** ********************************************BY XW D********************************************** */


    /**
     * 费用登记
     */
    public static Map DATA_UPLOAD_FYDJ(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_FYDJ.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传费用登记数据出错", e.getCause());
        }
        return null;
    }

    /**
     * 消费品项显示
     */
    public static Map DATA_UPLOAD_XFPXXS(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_XFPXXS.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传消费品项显示数据出错", e.getCause());
        }
        return null;
    }

    /**
     * 服务员服务客位
     */
    public static Map DATA_UPLOAD_FWYFWKW(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_FWYFWKW.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传服务员服务客位数据出错", e.getCause());
        }
        return null;
    }

    /**
     * 消费区域和客位管理
     */
    public static Map DATA_UPLOAD_XFQYHKWGL(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_XFQYHKWGL.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传消费区域和客位管理出错", e.getCause());
        }
        return null;
    }


    /**
     * 前台营业设置
     */
    public static Map DATA_UPLOAD_QTYYSZ(String yggl_json_data){
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost,yggl_json_data, "/franchise/dataReceive/DATA_RECEIVE_QTYYSZ.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            return responseEntity(HttpClients.createDefault().execute(httpPost));
        } catch (IOException e) {
            logger.error("上传前台营业设置出错", e.getCause());
        }
        return null;
    }

    private static UrlEncodedFormEntity setHttpParams(HttpPost httpPost,String jsonData, String url) {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("shopKey", getKey()));
        formParams.add(new BasicNameValuePair("jsonData", jsonData));
        httpPost.setURI(URI.create(getUrl() + url));
        return new UrlEncodedFormEntity(formParams, Consts.UTF_8);
    }

    private static String getKey() {
//        return CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY");
        return "36456b2c-723a-4570-bc27-bb5e7a491200";
    }

    private static String getUrl() {
        return "http://localhost:8080/cateringChain";

//        return CacheUtil.getURLInCache("DOWN_DATA_URL");
//        return "http://localhost:8080/cateringChain";
    }

    private static Map responseEntity(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String resStr =  statusCode == HttpStatus.SC_OK ? EntityUtils.toString(httpResponse.getEntity()) : null;
        if(StringUtils.isEmpty(resStr)){
            return null;
        }
        return JSON.parseObject(resStr, Map.class);
    }
}
