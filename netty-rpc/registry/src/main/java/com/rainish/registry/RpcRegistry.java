package com.rainish.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * @Author jiajiao
 * @Date 2019/6/18 15:11
 */
public class RpcRegistry {
    private int port;

    public RpcRegistry(int port){
        this.port = port;
    }

    public void start(){
    try {


        // 线程池
        EventLoopGroup loopGroup = new NioEventLoopGroup(); // 主线程池
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();// 子线程池

        ServerBootstrap server = new ServerBootstrap();
        server.group(loopGroup,workLoopGroup).channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();

                //对于自定义协议要进行编解码
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                pipeline.addLast(new LengthFieldPrepender(4));

                pipeline.addLast("encoder",new ObjectEncoder());
                pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                pipeline.addLast(new RegistryHandler());
            }
        })
        .option(ChannelOption.SO_BACKLOG,128)
        .childOption(ChannelOption.SO_KEEPALIVE,true);

        ChannelFuture future = server.bind(this.port).sync();
        System.out.println("GP RPC Registry start listen at " + this.port);
        future.channel().closeFuture().sync();
    }catch (Exception e){
        e.printStackTrace();
    }

    }

    public static void main( String[] args )
    {
        new RpcRegistry(8080).start();
    }
}
