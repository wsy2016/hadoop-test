package com.strom.wordCount;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Values;

import java.io.*;
import java.util.Map;

/**
 * Description: bolt
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/18 21:40
 */
public class MySpout extends BaseRichSpout {


    private SpoutOutputCollector collector;

    private BufferedReader reader;

    private String str;

    /**
     * Description:初始化代码
     * Author: wsy
     * Date: 2018/12/20 21:42
     * Param: [map, topologyContext, spoutOutputCollector]
     * Return: void
     */

    @Override
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/storm-test"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.collector = collector;

    }

    @Override
    public void nextTuple() {
        try {
            while ((str = reader.readLine()) != null) {
                collector.emit(new Values(str, 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
