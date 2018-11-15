package com.gssw.duoduomi.rabbitmq;

import com.gssw.duoduomi.handler.MessageHandler;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    protected final static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private ConnectionFactory factory;

    public void receiveMessage(String exchangeName, MessageHandler handler) {
        Connection connection = null;
        try {
            connection = factory.newConnection();

            final Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "fanout", true);
            channel.basicQos(1);

            //产生一个随机的队列 该队列用于从交换器获取消息
            String queueName = channel.queueDeclare().getQueue();
            //将队列和某个交换机丙丁 就可以正式获取消息了 routingkey和交换器的一样都设置成空
            channel.queueBind(queueName, exchangeName, "");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    logger.debug("客户端从【"+exchangeName+"】接收数据"+message);
                    if(message!=null)
                        handler.handler(message);

                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            //参数2 表示手动确认
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("接收数据异常", e);
        }
    }
}
