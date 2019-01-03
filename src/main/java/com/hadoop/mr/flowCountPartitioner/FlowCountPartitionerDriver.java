package com.hadoop.mr.flowCountPartitioner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Description: 统计的流量之和,并且按照手机号所属的省份来分组
 * <p>
 * Author: wsy
 * <p>
 * Date: 2019/1/2 20:55
 */
public class FlowCountPartitionerDriver {

    public static void driver(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJobName("hhhh");
        job.setJarByClass(FlowCountPartitionerDriver.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setMapperClass(FlowCountPartitionerMapper.class);

        job.setPartitionerClass(ProvincePartitioner.class);
        job.setReducerClass(FlowCountPartitionerReduce.class);
        job.setNumReduceTasks(2);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));

        org.apache.hadoop.fs.FileSystem system = org.apache.hadoop.fs.FileSystem.get(conf);
        if (system.exists(new Path(args[1]))) {
            system.delete(new Path(args[1]));
        }
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);

    }

    static class FlowCountPartitionerMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split("\t");

            String phone = fields[0];
            long upAmount = Long.parseLong(fields[8]);
            long dAmount = Long.parseLong(fields[9]);
            long sumAmount = upAmount + dAmount;
            context.write(new Text(phone), new LongWritable(sumAmount));
        }
    }

    static class FlowCountPartitionerReduce extends Reducer<Text, LongWritable, Text, LongWritable> {

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            for (LongWritable value : values) {
                context.write(key, value);
            }
        }
    }

    public static void main(String[] args) {

        String path1 = "/Users/wensiyang/Downloads/HTTP_20130313143750.dat";
        String path2 = "/Users/wensiyang/Downloads/result";
        String[] argss = {path1, path2};
        try {
            driver(argss);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
