package com.yqsh.diningsys.web.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.ShopOnline;
import com.yqsh.diningsys.web.model.archive.*;
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

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemFromCook;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import com.yqsh.diningsys.web.service.data_down.DataDownService;


/**
 * Created on 2017-04-13 13:12
 * 数据下载类
 * @author zhshuo
 */
public class DATA_DOWN_UTIL extends BaseHttp {

    private static Logger logger = Logger.getLogger(DATA_DOWN_UTIL.class);

    private static Gson gson = new Gson();

    private static DataDownService dataDownService;

    static {
        dataDownService = SpringContextUtil.getApplicationContext().getBean(DataDownService.class);
    }

    public static final Integer DOWN_SCZT() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_sczt.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgServing> list = JSON.parseObject(resData, new TypeToken<List<DgServing>>() {
                }.getType());

                if (list.size() > 0) {
                    return dataDownService.insertOrEdit_SCZT(list);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载上菜状态数据出错", e.getCause());
        }
        return -1;
    }

    public static final Integer DOWN_YGGL() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_yggl.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<SysUser> list = JSON.parseObject(resData, new TypeToken<List<SysUser>>() {
                }.getType());

                if (list.size() > 0) {
                    return dataDownService.insertOrEdit_YGGL(list);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载员工管理数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZDYY() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zdyy.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String, Object> resMap = JSON.parseObject(resData, new TypeToken<Map<String, Object>>() {
                }.getType());

                if (resMap != null && !resMap.isEmpty()) {
                    return dataDownService.insertOrEdit_ZDYY(resMap);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载赠单原因数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_BMGL() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_bmgl.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<TbDep> tbDeps = JSON.parseObject(resData, new TypeToken<List<TbDep>>() {
                }.getType());

                if (tbDeps.size() > 0) {
                    return dataDownService.insertOrEdit_BMGL(tbDeps);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载部门管理数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_YYSB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_yysb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<TbBis> tbDeps = JSON.parseObject(resData, new TypeToken<List<TbBis>>() {
                }.getType());

                if (tbDeps.size() > 0) {
                    return dataDownService.insertOrEdit_YYSB(tbDeps);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载营业市别数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_TCYY() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_tcyy.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> resMap = JSON.parseObject(resData, new TypeToken<Map<String, Object>>() {
                }.getType());


                if (resMap != null && !resMap.isEmpty()) {
                    return dataDownService.insertOrEdit_TCYY(resMap);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载退菜原因数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZDBB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zdbb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<TbZdbz> tbZdbzs = JSON.parseObject(resData, new TypeToken<List<TbZdbz>>() {
                }.getType());

                if (tbZdbzs.size() > 0) {
                    return dataDownService.insertOrEdit_ZDBB(tbZdbzs);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载整单备注数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_FYXM() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_fyxm.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String, Object> maps = JSON.parseObject(resData, new TypeToken<Map<String, Object>>() {
                }.getType());

                if (maps != null && !maps.isEmpty()) {
                    return dataDownService.insertOrEdit_FYXM(maps);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载费用项目数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_POSDA() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_posda.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgPos> dgPos = JSON.parseObject(resData, new TypeToken<List<DgPos>>() {
                }.getType());

                if (dgPos.size() > 0) {
                    return dataDownService.insertOrEdit_POSDA(dgPos);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载费用项目数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_JSFS() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_jsfssj.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgSettlementWay> dgSettlementWays = JSON.parseObject(resData, new TypeToken<List<DgSettlementWay>>() {
                }.getType());

                if (dgSettlementWays.size() > 0) {
                    return dataDownService.insertOrEdit_JSFS(dgSettlementWays);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载结算方式数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZZFF() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zzff.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String, Object> map = JSON.parseObject(resData, new TypeToken<Map<String, Object>>() {
                }.getType());

                if (map != null && !map.isEmpty()) {
                    return dataDownService.insertOrEdit_ZZFF(map);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载制作方法数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_KWYQ() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_kwyq.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgFlavor> dgFlavors = JSON.parseObject(resData, new TypeToken<List<DgFlavor>>() {
                }.getType());

                if (dgFlavors.size() > 0) {
                    return dataDownService.insertOrEdit_KWYQ(dgFlavors);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载制作方法数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_FYDJ() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_fydj.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<TbFydj> tbFydjs = JSON.parseObject(resData, new TypeToken<List<TbFydj>>() {
                }.getType());

                if (tbFydjs.size() > 0) {
                    for(TbFydj tbFydj : tbFydjs){
                    	tbFydj.setStartTime(tbFydj.getStartTime().equals("")?null:tbFydj.getStartTime());
                    	tbFydj.setEndTime(tbFydj.getEndTime().equals("")?null:tbFydj.getEndTime());
                    }
                    return dataDownService.insertOrEdit_FYDJ(tbFydjs);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载费用登记数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_SFPXXSSZ() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_xfpxxssz.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemShowRank> dgItemShowRanks = JSON.parseObject(resData, new TypeToken<List<DgItemShowRank>>() {
                }.getType());
                
                dataDownService.insertOrEdit_SFPXXSSZ(dgItemShowRanks);
                return dgItemShowRanks.size();
            }
        } catch (IOException e) {
            logger.error("下载消费品项显示数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_FWYFWKW() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_fwyfwkw.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<ServiceClass> serviceClasses = JSON.parseObject(resData, new TypeToken<List<ServiceClass>>() {
                }.getType());

                dataDownService.insertOrEdit_FWYFWKW(serviceClasses);
                return serviceClasses.size();
            }
        } catch (IOException e) {
            logger.error("下载服务员服务客位数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_XFQYYKWGL() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_xfqyykwgl.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> map = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (map != null && !map.isEmpty()) {
                    return dataDownService.insertOrEdit_XFQYYKWGL(map);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载服务员服务客位数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_QTYYSZ() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_qtyysz.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DeskBusinessSetting> deskBusinessSettings = JSON.parseObject(resData, new TypeToken<List<DeskBusinessSetting>>() {
                }.getType());

                dataDownService.insertOrEdit_QTYYSZ(deskBusinessSettings);
                return deskBusinessSettings.size();
            }
        } catch (IOException e) {
            logger.error("下载服务员服务客位数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_QXGL() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_qxgl.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> qxglMap = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (qxglMap != null && !qxglMap.isEmpty()) {
                    return dataDownService.insertOrEdit_QXGL(qxglMap);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载权限管理数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_GGDM() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_ggdm.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgPublicCode0> dgPublicCode0s = JSON.parseObject(resData, new TypeToken<List<DgPublicCode0>>() {
                }.getType());

                if (dgPublicCode0s.size() > 0) {
                    return dataDownService.insertOrEdit_GGDM(dgPublicCode0s);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载公共代码数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXDA() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxda.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> map = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (map != null && !map.isEmpty()) {
                    return dataDownService.insertOrEdit_PXDA(map);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项档案数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXGQ() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxgq.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
            	List<DgItemOutofStock> dgItemOutofStock = JSON.parseObject(resData, new TypeToken<List<DgItemOutofStock>>() {
                }.getType());

//                if (dgItemOutofStock.size() > 0) {
                    return dataDownService.insertOrEdit_PXGQ(dgItemOutofStock);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项限量（主表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXXLZB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxxlzb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemSaleLimit> dgItemSaleLimit = JSON.parseObject(resData, new TypeToken<List<DgItemSaleLimit>>() {
                }.getType());

//                if (dgItemSaleLimit.size() > 0) {
                    return dataDownService.insertOrEdit_PXXLZB(dgItemSaleLimit);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项限量（主表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXXLFB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxxlfb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemSaleLimitS> dgItemSaleLimits = JSON.parseObject(resData, new TypeToken<List<DgItemSaleLimitS>>() {
                }.getType());

//                if (dgItemSaleLimits.size() > 0) {
                    return dataDownService.insertOrEdit_PXXLFB(dgItemSaleLimits);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项限量（附表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXTY() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxty.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemDisable> dgItemDisable = JSON.parseObject(resData, new TypeToken<List<DgItemDisable>>() {
                }.getType());

//                if (dgItemDisable.size() > 0) {
                    return dataDownService.insertOrEdit_PXTY(dgItemDisable);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项停用数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_TJCP() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_tjcp.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgRecommendItem> dgRecommendItem = JSON.parseObject(resData, new TypeToken<List<DgRecommendItem>>() {
                }.getType());

//                if (dgRecommendItem.size() > 0) {
                    return dataDownService.insertOrEdit_TJCP(dgRecommendItem);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载推荐菜品数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZXCP() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zxcp.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgNewestItem> dgNewestItem = JSON.parseObject(resData, new TypeToken<List<DgNewestItem>>() {
                }.getType());

//                if (dgNewestItem.size() > 0) {
                    return dataDownService.insertOrEdit_ZXCP(dgNewestItem);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载最新菜品数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXCPBMZB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxcpbuzb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemProDep> dgItemProDep = JSON.parseObject(resData, new TypeToken<List<DgItemProDep>>() {
                }.getType());

//                if (dgItemProDep.size() > 0) {
                    return dataDownService.insertOrEdit_PXCPBMZB(dgItemProDep);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项出品部门（主表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXCPBMFB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxcpbufb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemProDepS> dgItemProDeps = JSON.parseObject(resData, new TypeToken<List<DgItemProDepS>>() {
                }.getType());

//                if (dgItemProDeps.size() > 0) {
                    return dataDownService.insertOrEdit_PXCPBMFB(dgItemProDeps);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项出品部门（附表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_TSPX() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_tspx.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgSpecialItem> dgSpecialItem = JSON.parseObject(resData, new TypeToken<List<DgSpecialItem>>() {
                }.getType());

//                if (dgSpecialItem.size() > 0) {
                    return dataDownService.insertOrEdit_TSPX(dgSpecialItem);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载特殊品项数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXCPCS() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxcpcs.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemFromCook> dgItemFromCook = JSON.parseObject(resData, new TypeToken<List<DgItemFromCook>>() {
                }.getType());

                if (dgItemFromCook.size() > 0) {
                    return 0;
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项出品厨师数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZDYJEPXXL() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zdyjepxxl.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgGateItemAllowCustom> dgGateItemAllowCustom = JSON.parseObject(resData, new TypeToken<List<DgGateItemAllowCustom>>() {
                }.getType());

//                if (dgGateItemAllowCustom.size() > 0) {
                    return dataDownService.insertOrEdit_ZDYJEPXXL(dgGateItemAllowCustom);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载自定义金额品项小类数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZDYJEMC() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zdyjemc.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<BgItemCustomMoneyName> bgItemCustomMoneyName = JSON.parseObject(resData, new TypeToken<List<BgItemCustomMoneyName>>() {
                }.getType());

//                if (bgItemCustomMoneyName.size() > 0) {
                    return dataDownService.insertOrEdit_ZDYJEMC(bgItemCustomMoneyName);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载自定义金额名称数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZDYJEPX() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zdyjepx.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemCustomMoney> dgItemCustomMoney = JSON.parseObject(resData, new TypeToken<List<DgItemCustomMoney>>() {
                }.getType());

//                if (dgItemCustomMoney.size() > 0) {
                    return dataDownService.insertOrEdit_ZDYJEPX(dgItemCustomMoney);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载自定义金额品项数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXCXFAZB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxcxfazb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemGiftPlan> dgItemGiftPlan = JSON.parseObject(resData, new TypeToken<List<DgItemGiftPlan>>() {
                }.getType());

                if (dgItemGiftPlan.size() > 0) {
                    return dataDownService.insertOrEdit_PXCXFAZB(dgItemGiftPlan);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项促销方案（主表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXCXFAFB() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxcxfafb.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                List<DgItemGiftPlanS> dgItemGiftPlans = JSON.parseObject(resData, new TypeToken<List<DgItemGiftPlanS>>() {
                }.getType());

//                if (dgItemGiftPlans.size() > 0) {
                    return dataDownService.insertOrEdit_PXCXFAFB(dgItemGiftPlans);
//                }
//                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项促销方案（附表）数据出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXGL_ONE() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxgl_one.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> pxgl1 = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (pxgl1.size() > 0) {
                    return dataDownService.insertOrEdit_PXGL_ONE(pxgl1);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项管理数据包1出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_PXGL_TWO() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_pxgl_two.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> pxgl2 = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (pxgl2.size() > 0) {
                    return dataDownService.insertOrEdit_PXGL_TWO(pxgl2);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载品项管理数据包2出错", e.getCause());
        }
        return -1;
    }


    public static Integer DOWN_XFQUHKE() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_xfqyykw.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                Map<String,Object> xfquhke = JSON.parseObject(resData, new TypeToken<Map<String,Object>>() {
                }.getType());

                if (xfquhke.size() > 0) {
                    return dataDownService.insertOrEdit_XFQUHKW(xfquhke);
                }
                return 0;
            }
        } catch (IOException e) {
            logger.error("下载消费区域和客位出错", e.getCause());
        }
        return -1;
    }

    public static Integer DOWN_ZZJG() {
        HttpPost httpPost = new HttpPost();
        UrlEncodedFormEntity entity = setHttpParams(httpPost, "/SYSTEM/DATA_SENDER/send_zzjg.jspx");
        httpPost.setEntity(entity);
        setTimeOut(httpPost);
        try {
            String resData = responseEntity(HttpClients.createDefault().execute(httpPost));
            if (!StringUtils.isEmpty(resData)) {
                ShopOnline shopOnline = JSON.parseObject(resData, ShopOnline.class);
                return dataDownService.insertOrEdit_ZZJG(shopOnline);
            }
        } catch (IOException e) {
            logger.error("下载组织机构出错", e.getCause());
        }
        return -1;
    }

    private static UrlEncodedFormEntity setHttpParams(HttpPost httpPost, String url) {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("shopKey", getKey()));
        httpPost.setURI(URI.create(getUrl() + url));
        return new UrlEncodedFormEntity(formParams, Consts.UTF_8);
    }

    private static String getKey() {
        return CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY");
    }

    private static String getUrl() {
        return CacheUtil.getURLInCache("DOWN_DATA_URL");
    }

   static String responseEntity(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String resStr = statusCode == HttpStatus.SC_OK ? EntityUtils.toString(httpResponse.getEntity()) : null;
        Map map = gson.fromJson(resStr, Map.class);
        if (map.get("status").toString().equals("200")) {
            return map.get("resData").toString();
        }
        return null;
    }

}