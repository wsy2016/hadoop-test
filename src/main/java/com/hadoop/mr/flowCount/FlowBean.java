package com.hadoop.mr.flowCount;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Description: 用于保存上下流量,下行流量,总流量
 *
 *
 * Java的序列化是一个重量级序列化框架（Serializable），
 * 一个对象被序列化后，会附带很多额外的信息（各种校验信息，header，继承体系。。。。），
 * 所以很臃肿，不便于在网络中高效传输；
   因此，hadoop自己开发了一套序列化机制（Writable），精简，高效
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/15 10:45
 */
public class FlowBean implements WritableComparable<FlowBean> {

    private Long upFlow;

    private Long downFlow;

    private Long sumFlow;

    /**
     * 显式定义无参构造
     */
    public FlowBean() {}

    public FlowBean(Long upFlow, Long downFlow, Long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public Long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(Long sumFlow) {
        this.sumFlow = sumFlow;
    }

    /**
     * Description:序列化
     * Author: wsy
     * Date: 2018/12/15 11:03
     * Param: [out]
     * Return: void
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);

    }

    /**
     * Description:反序列化
     * Author: wsy
     * Date: 2018/12/15 11:03
     * Param: [out]
     * Return: void
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        //和序列化顺序一致
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    @Override
    public int compareTo(FlowBean o) {
        return 0;
    }
}
