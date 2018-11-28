package com.hadoop.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Description:
 *
 * KEYIN：  reduce阶段输入数据（键值对）的key     ----对应着Mapper中输出的数据中的key
 * VALUEIN：reduce阶段输入数据（键值对）的value   ----对应着Mapper中输出的数据中的value
 * KEYOUT： reduce阶段输出数据（键值对）的key
 * VALUEOUT：reduce阶段输出数据（键值对）的value
 *
 * Author: wsy
 * <p>
 * Date: 2018/11/28 22:49
 */
public class MyReduce extends Reducer<Text, IntWritable, Text, LongWritable> {


    /**
     *
     * Description: 复写reduce
     *             业务场景(keyword,1)->(keyword,n)
     * Author: wsy
     * Date: 2018/11/28 22:52
     * Param: [key, values, context]
     * Return: void
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Long count = 0l;
        for(IntWritable value : values){
            count++;
        }
        context.write(key,new LongWritable(count));


    }
}
