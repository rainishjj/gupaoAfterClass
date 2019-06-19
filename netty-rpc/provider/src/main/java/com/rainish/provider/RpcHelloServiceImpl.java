package com.rainish.provider;

import com.rainish.api.IRpcHelloService;

/**
 * @Author jiajiao
 * @Date 2019/6/18 15:06
 */
public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        return "hello"+name;
    }
}
