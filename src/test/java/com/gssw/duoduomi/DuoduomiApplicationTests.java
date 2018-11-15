package com.gssw.duoduomi;

import com.gssw.duoduomi.model.Message;
import com.gssw.duoduomi.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DuoduomiApplicationTests {

    @Test
    public void contextLoads() {
        Message message = new Message();
        message.setType(1);
        message.setBusiType("test");
        message.setData("msg");

        String result = JsonUtils.toJson(message);

        System.out.println(result);

        /**
         *
         * {"busiType":"test","data":"msg","type":1}
         *
         */
    }

}
