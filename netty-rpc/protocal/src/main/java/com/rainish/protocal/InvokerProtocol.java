package com.rainish.protocal;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author jiajiao
 * @Date 2019/6/18 14:59
 */
@Data
public class InvokerProtocol implements Serializable {

    private String className;

    private String methodName;

    private Class<?> [] params;//形参列表

    private Object[] values; //实参列表
}
