package com.hadoop.mr;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;

import java.io.IOException;

/**
 * Description:
 * <p>
     * KEYIN: maptask读到那一行的起始偏移量
     * VALUEIN:maptask这个程序读到的那一行的内容
     * KEYOUT: 是我们的处理逻辑生成的结果（键值对）中的key
     * VALUEOUT：是我们的处理逻辑生成的结果（键值对）中的value
 * <p>
 * Author: wsy
 * Date: 2018/11/28 22:14
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {


    /**
     * Description:
     * <p>
         * 复写map
         * 该map的业务逻辑是把读到的每行的内容value 分割成(wordKey:1)
     * <p/>
     * Author: wsy
     * Date: 2018/11/28 22:26
     * Param: [key, value, context]
     * Return: void
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String lines = value.toString();
        String[] words = lines.split(" ");
        for(String word : words) {
            context.write(new Text(word),new IntWritable(1));
        }


    }
}
