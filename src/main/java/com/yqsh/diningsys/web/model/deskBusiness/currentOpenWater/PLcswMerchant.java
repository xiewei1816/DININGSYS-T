package com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater;

public class PLcswMerchant {
    private Integer id;

    private String merchantno;

    private String payver;

    private String servicejspayid;

    private String terminalid;

    private String accesstoken;

    private String serviceslotcard;

    private String serviceprepay;

    private String servicequery;

    private String zfbpaytype;

    private String wxpaytype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno == null ? null : merchantno.trim();
    }

    public String getPayver() {
        return payver;
    }

    public void setPayver(String payver) {
        this.payver = payver == null ? null : payver.trim();
    }

    public String getServicejspayid() {
        return servicejspayid;
    }

    public void setServicejspayid(String servicejspayid) {
        this.servicejspayid = servicejspayid == null ? null : servicejspayid.trim();
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid == null ? null : terminalid.trim();
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken == null ? null : accesstoken.trim();
    }

    public String getServiceslotcard() {
        return serviceslotcard;
    }

    public void setServiceslotcard(String serviceslotcard) {
        this.serviceslotcard = serviceslotcard == null ? null : serviceslotcard.trim();
    }

    public String getServiceprepay() {
        return serviceprepay;
    }

    public void setServiceprepay(String serviceprepay) {
        this.serviceprepay = serviceprepay == null ? null : serviceprepay.trim();
    }

    public String getServicequery() {
        return servicequery;
    }

    public void setServicequery(String servicequery) {
        this.servicequery = servicequery == null ? null : servicequery.trim();
    }

    public String getZfbpaytype() {
        return zfbpaytype;
    }

    public void setZfbpaytype(String zfbpaytype) {
        this.zfbpaytype = zfbpaytype == null ? null : zfbpaytype.trim();
    }

    public String getWxpaytype() {
        return wxpaytype;
    }

    public void setWxpaytype(String wxpaytype) {
        this.wxpaytype = wxpaytype == null ? null : wxpaytype.trim();
    }
}