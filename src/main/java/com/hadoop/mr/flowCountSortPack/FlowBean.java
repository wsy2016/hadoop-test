package com.hadoop.mr.flowCountSortPack;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 * hadoop中序列化框架的应用示例
 * 反序列化时，反射机制会调用类的无参构造函数
 * 所以，如果你在类中定义了有参构造，就一定要记得显式定义一下无参构造函数
 * @author
 *
 */
public class FlowBean implements WritableComparable<FlowBean> {

	private long upflow;
	private long dflow;
	private long sumflow;
	

	/**
	 * 显式定义无参构造
	 */
	public FlowBean() {}

	public FlowBean(long upflow, long dflow) {
		this.upflow = upflow;
		this.dflow = dflow;
		this.sumflow = upflow + dflow;
	}

	public long getUpflow() {
		return upflow;
	}

	public void setUpflow(long upflow) {
		this.upflow = upflow;
	}

	public long getDflow() {
		return dflow;
	}

	public void setDflow(long dflow) {
		this.dflow = dflow;
	}

	public long getSumflow() {
		return sumflow;
	}

	public void setSumflow(long sumflow) {
		this.sumflow = sumflow;
	}

	/**
	 * 反序列化的方法，反序列化时，从流中读取到的各个字段的顺序应该与序列化时写出去的顺序保持一致
	 */
	public void readFields(DataInput in) throws IOException {
		
		upflow = in.readLong();
		dflow = in.readLong();
		sumflow = in.readLong();
		

	}

	/**
	 * 序列化的方法
	 */
	public void write(DataOutput out) throws IOException {

		out.writeLong(upflow);
		out.writeLong(dflow);
		//可以考虑不序列化总流量，因为总流量是可以通过上行流量和下行流量计算出来的
		out.writeLong(sumflow);

	}
	
	
	@Override
	public String toString() {
		 
		return  upflow + "\t" + dflow + "\t" + sumflow;
	}

	

	public int compareTo(FlowBean o) {
		
		//实现按照sumflow的大小倒序排序
		return sumflow>o.getSumflow()?-1:1;
	}
	
	
	
	

}
