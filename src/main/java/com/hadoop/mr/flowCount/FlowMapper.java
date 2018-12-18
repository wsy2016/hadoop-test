package com.hadoop.mr.flowCount;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;

import java.io.IOException;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/13 22:26
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {



    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = StringUtils.split(value.toString(),"\t");
        String mobile = words[1];
        int length = words.length;
        Long upFlow = Long.valueOf(words[length-3]);
        Long downFlow = Long.valueOf(words[length-2]);
        Long sumFlow = upFlow+downFlow;
        FlowBean flowBean = new FlowBean(upFlow,downFlow,sumFlow);
        context.write(new Text(mobile),flowBean);


    }


}
