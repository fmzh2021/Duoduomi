package com.gssw.duoduomi.config;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    protected final static Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        logger.info("========rabbitmq地址："+host+":"+port+" 用户名："+username+"========");
        logger.info("webhook github success");

        return factory;
    }
}
