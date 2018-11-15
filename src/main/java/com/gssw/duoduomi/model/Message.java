package com.gssw.duoduomi.model;

import lombok.Data;

@Data
public class Message {
    //服务端、客户端消息类型：服务端-0；客户端-1
    private Integer type;
    //消息业务类型：
    private String busiType;
    private Object data;
}
