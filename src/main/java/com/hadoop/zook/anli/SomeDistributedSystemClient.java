package com.hadoop.zook.anli;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Description:客户端
 * <p>
 * Author: wsy
 * <p>
 * Date: 2019/1/15 19:31
 */
public class SomeDistributedSystemClient {
    static ZooKeeper zk;
    static String watchPath = "/app";

    public static void main(String[] args) {
        //对 /app (保存服务器信息)znode进行监听

        try {
            zk = new ZooKeeper("master:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType() == Event.EventType.None) {
                        return;
                    }
                    //监听"/app"路径
                    if (StringUtils.equals(watchedEvent.getPath(), watchPath)) {
                        try {
                            byte[] nodeContent = getZondeContent(zk,watchPath);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static byte[] getZondeContent(ZooKeeper zk, String watchPath) throws KeeperException, InterruptedException {

        if(null != zk && StringUtils.isNoneBlank(watchPath) ){
            return zk.getData(watchPath,false,null);
        }
        return null;
    }

}
