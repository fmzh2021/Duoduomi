package com.gssw.duoduomi.handler;

import com.gssw.duoduomi.model.Message;
import com.gssw.duoduomi.rabbitmq.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SocketHandlerImpl implements SocketHandler {
    @Autowired
    private Receiver receiver;

    private WebSocketHandler handler;

    @Override
    public Message socketHandler(Message message) {

        receiver.receiveMessage(message.getBusiType(), new MessageHandler() {
            @Override
            public void handler(String msg) {
                handler.send(msg);
            }
        });

        return message;
    }

    public Message socketHandler(WebSocketHandler handler, Message message) {
        this.handler = handler;

        //不执行返回的内容
        this.handler.setExecAfter(false);

        return this.socketHandler(message);
    }
}
