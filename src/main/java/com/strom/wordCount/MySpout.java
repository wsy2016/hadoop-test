package com.strom.wordCount;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;

import java.util.Map;

/**
 * Description: bolt
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/18 21:40
 */
public class MySpout  extends BaseRichSpout{
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

    }

    @Override
    public void nextTuple() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
