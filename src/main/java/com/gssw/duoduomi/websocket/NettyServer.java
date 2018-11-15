package com.gssw.duoduomi.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;

@Component
public class NettyServer {

    @Value("${spring.websocket.port}")
    private Integer port;

    @Autowired
    private NettyServer nettyServer;


    protected final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @PostConstruct
    public void initNetty(){
        new Thread(){
            public void run() {
                nettyServer.run();
            }
        }.start();
    }

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChildChannelHandler());

            Channel ch = b.bind(port).sync().channel();
            logger.info("========WebSocket(Netty)服务端开启(ws://"+ InetAddress.getLocalHost().getHostAddress()+":"+port+")========");
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
