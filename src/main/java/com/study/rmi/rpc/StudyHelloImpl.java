package com.study.rmi.rpc;

import com.study.rmi.rpc.anno.RpcAnnotation;

@RpcAnnotation(IStudyHello.class)
public class StudyHelloImpl implements IStudyHello {

    @Override
    public String sayHello(String msg) {
        return "服务端返回: hello " + msg;
    }

}
