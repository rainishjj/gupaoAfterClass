package com.rainish.provider;

import com.rainish.api.IRpcService;

/**
 * @Author jiajiao
 * @Date 2019/6/18 15:07
 */
public class RpcServiceImpl implements IRpcService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }

    @Override
    public int mult(int a, int b) {
        return a*b;
    }

    @Override
    public int div(int a, int b) {
        return a/b;
    }
}
