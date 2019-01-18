package com.yqsh.diningsys.web.mq;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * 店内软件数据上传到平台
 */
//@Service
public class ProducerService {

    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

      /**
       * 向指定队列发送消息
       */
      public void sendMessage(Destination destination, final String msg) {
        jmsTemplate.send(destination, new MessageCreator() {
          public Message createMessage(Session session) throws JMSException {
            return session.createTextMessage(msg);
          }
        });
      }

    /**
     * 向默认队列发送消息
     */
      public void sendMessage(final String msg) {
        jmsTemplate.send(new MessageCreator() {
          public Message createMessage(Session session) throws JMSException {
            return session.createTextMessage(msg);
          }
        });

      }

}