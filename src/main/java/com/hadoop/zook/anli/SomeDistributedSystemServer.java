package com.hadoop.zook.anli;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Description: 服务端
 * <p>
 * Author: wsy
 * <p>
 * Date: 2019/1/15 19:30
 */
public class SomeDistributedSystemServer {

    static ZooKeeper zk ;
    static {
        try {
            zk = new ZooKeeper("master:2181",3000,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        System.out.println(args[0]+args[1]);
          //zk.setData();
    }
}
