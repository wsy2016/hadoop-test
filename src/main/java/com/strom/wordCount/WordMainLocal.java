package com.strom.wordCount;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/18 22:02
 */
public class WordMainLocal {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        MySpout mySpout = new MySpout();
        MyBolt myBolt = new MyBolt();

        //一个进程里面启动n(parallelism_hint)个线程 ,每个线程里面有n个task任务
        builder.setSpout("myspout",mySpout,2).setNumTasks(2);
        //一个进程里面启动3个线程 ,每个线程里面有5个task任务
        builder.setBolt("mybolt",myBolt,3).shuffleGrouping("myspout").setNumTasks(4);


        //配置
        Config config = new Config();
        //此topo的进程数量
        config.setNumWorkers(2);
        //debug模式开启,线上关闭影响性能
        config.setDebug(true);
        //config 相当于配置全局变量
        config.put("filePath","/storm-test");
    }


}
