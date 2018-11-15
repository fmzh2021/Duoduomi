package com.gssw.duoduomi.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ProducerData {

    protected final static Logger logger = LoggerFactory.getLogger(ProducerData.class);

    @Autowired
    private Producer producer;

    @PostConstruct
    public void init(){
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String msg = "product"+ new Date();
                producer.sendMessage("test", msg);
            }
        };

        timer.schedule(task, new Date(),5000);
    }
}
