package com.rainish.registry;

import com.rainish.protocal.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author jiajiao
 * @Date 2019/6/18 15:39
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    private List<String> classNames = new ArrayList<>();

    private Map<String,Object> registryMap = new ConcurrentHashMap<>();

    public RegistryHandler(){
        scannerClass("com.rainish.provider");
        doRegistry();
    }

    private void doRegistry() {
        if(classNames.isEmpty()){
            return;
        }
        for(String className : classNames){
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0];
                String serviceName = i.getName();
                registryMap.put(serviceName,clazz.newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void scannerClass(String packageName) {
        URL url =this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()){
            if(file.isDirectory()){
                scannerClass(packageName + "." + file.getName());
            }else {
                classNames.add(packageName + "." + file.getName().replace(".class",""));
            }
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol)msg;
        if(registryMap.containsKey(request.getClassName())){
            Object service = registryMap.get(request.getClassName());
            Method method = service.getClass().getMethod(request.getMethodName(),request.getParams());
            result = method.invoke(service,request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
