package com.hadoop.mr.flowCount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/15 12:11
 */
public class FlowReduce extends Reducer<Text, FlowBean, Text, FlowBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        for (FlowBean flowBean :values) {
            context.write(key,flowBean);
        }

    }
}
