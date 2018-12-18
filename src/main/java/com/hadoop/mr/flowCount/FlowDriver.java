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

        //
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job,args[0]);
        job.setMapperClass(FlowMapper.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapOutputKeyClass(FlowBean.class);

        Path output = new Path(args[1]);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }

        // 告诉文件输出组件，输出结果放在哪里
        FileOutputFormat.setOutputPath(job, output);
        job.setOutputKeyClass(TextInputFormat.class);
        job.setReducerClass(FlowReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputKeyClass(FlowBean.class);

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
