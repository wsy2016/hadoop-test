package com.hadoop.zook.Demo;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2019/1/9 11:44
 */
public class ZooKeeperDemo {
    ZooKeeper zk = null;

    @Before
    public void initZK() throws IOException {
        zk = new ZooKeeper("master:2181", 3000, new Watcher() {

            /**
             * 监听事件发生时候的回调方法
             * */
            @Override
            public void process(WatchedEvent watchedEvent) {
                //对一些事件进行过滤
                if (watchedEvent.getType() == Event.EventType.None) {
                    return;
                }
                //操作的Type和nodePath
                System.out.println(watchedEvent.getType());
                System.out.println(watchedEvent.getPath());
                try {
                    //获取数据和子node
                    zk.getData("/wsy",true,null);
                    //zk.getChildren("/wsy",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Test
    public void testCreate()  {
        try {
            zk.create("/wsy1","我的第一个node".getBytes("UTF-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        } catch (KeeperException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }


    }

    @Test
    public void testWatch(){

        try {
           //在获取znode注册监听
            byte[] rootNode = zk.getData("/",true,null);

            byte[] node = zk.getData("/wsy",true,null);
            System.out.println(new String(rootNode));
            System.out.println(new String(node));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
