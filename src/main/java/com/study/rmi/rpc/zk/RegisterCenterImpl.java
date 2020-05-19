package com.study.rmi.rpc.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        // 注册相应服务
        String servicePath = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            if(null == curatorFramework.checkExists().forPath(servicePath)) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath, "0".getBytes());
            }
            String addressPath = servicePath + "/" + serviceAddress;
            String reNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath, "0".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
