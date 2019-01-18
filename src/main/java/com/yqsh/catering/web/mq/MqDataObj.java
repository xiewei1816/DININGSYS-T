package com.yqsh.catering.web.mq;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created on 2017-04-13 9:58
 *
 * @author zhshuo
 */
public class MqDataObj implements Serializable {
    private static final long serialVersionUID = 364731008402375473L;

    private String sendShop;

    private List<String> sendModel;

    private Date sendDate = new Date();

    public MqDataObj(String sendShop, List<String> sendModel) {
        this.sendShop = sendShop;
        this.sendModel = sendModel;
    }

    public String getSendShop() {
        return sendShop;
    }

    public void setSendShop(String sendShop) {
        this.sendShop = sendShop;
    }

    public List<String> getSendModel() {
        return sendModel;
    }

    public void setSendModel(List<String> sendModel) {
        this.sendModel = sendModel;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "MqDataObj{" +
                "sendShop='" + sendShop + '\'' +
                ", sendModel=" + sendModel +
                ", sendDate=" + sendDate +
                '}';
    }
}
