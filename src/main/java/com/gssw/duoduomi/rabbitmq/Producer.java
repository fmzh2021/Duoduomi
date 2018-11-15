package com.gssw.duoduomi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Producer {

    protected final static Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private ConnectionFactory factory;

    public void sendMessage(String exchangeName, String message){

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "fanout",true);
            channel.basicPublish(exchangeName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));

            logger.info("服务端向【"+exchangeName+"】 发送消息："+message);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("发送消息失败", e);
        } finally {
            try {
                if(channel!=null)
                    channel.close();
            }catch (IOException e){
                e.printStackTrace();
                logger.error("channel关闭失败", e);
            }

            try {
                if(connection!=null)
                    connection.close();
            }catch (IOException e){
                e.printStackTrace();
                logger.error("connection关闭失败", e);
            }
        }
    }
}
