package com.rainish.consumer;

import com.rainish.api.IRpcHelloService;
import com.rainish.api.IRpcService;
import com.rainish.provider.RpcHelloServiceImpl;
import com.rainish.provider.RpcServiceImpl;
import com.rainish.proxy.RpcProxy;

/**
 * @Author jiajiao
 * @Date 2019/6/18 17:19
 */
public class RpcConsumer {
    public static void main( String[] args )
    {
        IRpcHelloService helloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(helloService.hello("Rainish"));

        IRpcService service = RpcProxy.create(IRpcService.class);
        System.out.println("8+2= " +service.add(8,2));
    }
}
