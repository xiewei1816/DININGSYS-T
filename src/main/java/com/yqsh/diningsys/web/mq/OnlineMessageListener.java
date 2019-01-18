package com.yqsh.diningsys.web.mq;

import com.google.gson.Gson;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.util.OnlineHttp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * 线上消息监听，修改店内客座状态，调用线上接口
 */
@Component(value = "onineMSGListener")
public class OnlineMessageListener implements MessageListener {

    private static Logger logger = Logger.getLogger(OnlineMessageListener.class);

    static Gson gson = new Gson();

    @Autowired
    private DgConsumerSeatService dgConsumerSeatService;

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            String msg = tm.getText();
            if(!StringUtils.isEmpty(msg)){
                Map map = gson.fromJson(msg, Map.class);
                if(map.containsKey("message")){
                    String message1 = map.get("message").toString();
                    Map updateSeat = gson.fromJson(message1, Map.class);
                   dgConsumerSeatService.updateSeatPDState(updateSeat);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}