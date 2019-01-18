package com.yqsh.diningsys.web.mq;

import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.yqsh.diningsys.web.util.DATA_DOWN_UTIL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.yqsh.catering.web.mq.DepositOrderMessage;
import com.yqsh.catering.web.mq.MqDataObj;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;
import com.yqsh.diningsys.web.service.data_down.DataDownService;

/**
 * Created on 2017-04-13 10:21
 *
 * @author zhshuo
 */
@Component("onlineDataSenderListener")
public class OnlineDataSenderListener implements MessageListener {

    private Logger logger = Logger.getLogger(OnlineDataSenderListener.class);

    @Resource
    private DataDownService dataDownService;
    
    @Resource
    private BillService billService;
    
    @Resource
    private ReserveManagerService reserveManagerService;
    @Override
    public void onMessage(Message message) {
        try {
	    	if(!StringUtils.isEmpty(message.getJMSType())){
	    		if(message.getJMSType().equals("order")){
	    			TextMessage textMessage = (TextMessage) message;
	    			String msg = textMessage.getText();
	    			billService.insertOnlineOrderBill(msg);
	    		} else if(message.getJMSType().equals("reserve")){
	    			TextMessage textMessage = (TextMessage) message;
	    			Map msg = JSONObject.parseObject(textMessage.getText(), Map.class);
	    			DepositOrderMessage objDepositOrderMessage = JSONObject.parseObject(msg.get("message").toString(), DepositOrderMessage.class);
	    			if(objDepositOrderMessage.getOrderType() == 0){
		    			reserveManagerService.insertOnlineReserve(objDepositOrderMessage);	
	    			} else if(objDepositOrderMessage.getOrderType() == 1){
	    				reserveManagerService.deleteOnlineCancel(objDepositOrderMessage.getOrderId());
	    			}
	    		}
	    	} else {
		        ObjectMessage tm = (ObjectMessage) message;
	            MqDataObj object = (MqDataObj)tm.getObject();
	            dataDownService.DATA_DOWN_FILTER(object);
	            logger.error("接收到平台发送的数据下发信息:"+object.toString());
	    	}
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
