package com.gssw.duoduomi.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static <T> T parseObject(String msg, Class<T> t){
        try {
            T message = JSON.parseObject(msg, t);

            return message;
        }catch (Exception e){
            return null;
        }
    }

    public static String toJson(Object resultMessage){
        try {
            String result = JSON.toJSONString(resultMessage);
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
