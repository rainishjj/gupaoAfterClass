package com.rainish.proxy;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author jiajiao
 * @Date 2019/6/18 17:39
 */
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {
    private Object response;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception");
        ctx.close();
    }

    public Object getResponse() {
        return response;
    }
}
