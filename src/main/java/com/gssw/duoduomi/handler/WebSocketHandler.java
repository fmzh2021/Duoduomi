package com.gssw.duoduomi.handler;

import com.gssw.duoduomi.model.Message;
import com.gssw.duoduomi.rabbitmq.Receiver;
import com.gssw.duoduomi.utils.JsonUtils;
import com.gssw.duoduomi.websocket.Global;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WebSocketHandler {
    protected final static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private boolean isAll = true;
    private boolean execAfter = true;
    private ChannelHandlerContext ctx;

    @Autowired
    private SocketHandlerImpl socketHandler;
    @Autowired
    private Receiver receiver;

    public void execute(String msg){
        String result = msg;

        Message message = JsonUtils.parseObject(msg, Message.class);
        // 供测试使用
        /*if(message==null){
            message = new Message();
            message.setType(1);
            message.setBusiType("test");
            message.setData(msg);
        }*/

        if(message!=null) {
            Message resultMessage = socketHandler.socketHandler(this, message);

            result = JsonUtils.toJson(resultMessage);
        }

        if(execAfter){

            if (isAll) {
                this.sendAll(result);
            } else {
                this.send(result);
            }
        }
    }

    /**
     * 群发
     *
     * @param result
     */
    public void sendAll(String result){
        logger.info(String.format("websocket服务端返回全部: %s %s", isAll, ctx.channel(), result));
        TextWebSocketFrame tws = new TextWebSocketFrame(result);
        Global.group.writeAndFlush(tws);
    }

    /**
     * 单独返回
     *
     * @param result
     */
    public void send(String result){
        logger.info(String.format("websocket服务端返回客户端: %s %s", ctx.channel(), result));
        TextWebSocketFrame tws = new TextWebSocketFrame(result);
        ctx.channel().writeAndFlush(tws);
    }


    //----------set/get方法---------------

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public boolean isExecAfter() {
        return execAfter;
    }

    public void setExecAfter(boolean execAfter) {
        this.execAfter = execAfter;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
