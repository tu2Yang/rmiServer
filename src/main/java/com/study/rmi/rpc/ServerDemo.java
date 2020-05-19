package com.study.rmi.rpc;

import com.study.rmi.rpc.zk.IRegisterCenter;
import com.study.rmi.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

public class ServerDemo {

    public static void main(String[] args) throws IOException {
        IStudyHello iStudyHello = new StudyHelloImpl();
        IRegisterCenter registerCenter = new RegisterCenterImpl();
        RpcServer rpcServer = new RpcServer(registerCenter, "127.0.0.1");
        rpcServer.bind(iStudyHello);
        rpcServer.publisher();
        // 主线程阻塞
        System.in.read();
    }

}
