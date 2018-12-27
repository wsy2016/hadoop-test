package com.hadoop.mr.flowCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Description:
 * 统计每个用户的下行流量,上行流量,总流量之和
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/15 10:35
 */
public class FlowDriver {

    public static void hh (String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //
        job.setJarByClass(FlowDriver.class);
        job.setJobName("FlowCount");

        //设置mapper
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapperClass(FlowMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);




        // 设置reduce
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setReducerClass(FlowReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置输入输出文件路径
        FileInputFormat.setInputPaths(job,args[0]);
        Path output = new Path(args[1]);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }
        FileOutputFormat.setOutputPath(job, output);


        //参数verbose : 是否要在客户端显示进度
        boolean res = job.waitForCompletion(true);


    }

    public static void main(String[] args) {
        String path1 = "/Users/wensiyang/Downloads/HTTP_20130313143750.dat";
        String path2 = "/Users/wensiyang/Downloads/result";
        String[] argss = {path1, path2};
        try {
            hh(argss);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
