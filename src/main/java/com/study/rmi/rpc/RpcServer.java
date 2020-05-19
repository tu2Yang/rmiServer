package com.study.rmi.rpc;

import com.study.rmi.rpc.anno.RpcAnnotation;
import com.study.rmi.rpc.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private IRegisterCenter registerCenter;
    private String serviceAddress;
    // 存放服务名称和服务对象之间的关系
    Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    public void bind(Object... services) {
        for (Object service: services) {
            RpcAnnotation rpcAnnotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = rpcAnnotation.value().getName();
            handlerMap.put(serviceName, service);
        }
    }

    public void publisher() {

        ServerSocket serverSocket = null;
        try {
            // 启动一个服务监听
            String[] addrs = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(addrs[1]));

            for (String interfaceName: handlerMap.keySet()) {
                registerCenter.register(interfaceName, serviceAddress);
            }
            System.out.println("监听启动");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          if(null != serverSocket) {
              try {
                  serverSocket.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }

    }

}
