package com.yqsh.diningsys.web.util;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;

import java.util.Arrays;

/**
 * Created on 2017-04-21 11:14
 *
 * @author zhshuo
 */
public class BaseHttp {

    static final String JSON_ACCEPT = "application/json";
    static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    static void setJsonHeader(HttpPost httpPost) {
        setTimeOut(httpPost);
        httpPost.addHeader("Content-type", JSON_CONTENT_TYPE);
        httpPost.setHeader("Accept", JSON_ACCEPT);
    }

    static void setTimeOut(HttpPost httpPost) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(120000)
                .setConnectTimeout(120000)
                .setConnectionRequestTimeout(120000).build();
        httpPost.setConfig(requestConfig);
    }

}
