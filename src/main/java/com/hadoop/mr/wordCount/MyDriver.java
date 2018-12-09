package com.hadoop.mr.wordCount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.InvalidInputException;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Description: 定义一个dirver
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/11/28 23:11
 */
public class MyDriver {

    /**
     * 要处理的数据在哪里，输出的结果放哪里。。。。。。
     * 那个是map,那个是reduce
     * 封装成一个job
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 2) {
            System.err.println("Usage: 参数不全");
            System.exit(2);
        }
        //创建一个job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //告述hadoop这个jar的位置
        //job.setJar();
        job.setJobName("wordCountMR-wsy");
        job.setJarByClass(MyDriver.class);


        //设置map
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReduce.class);

        job.setOutputKeyClass(TextInputFormat.class);
        job.setInputFormatClass(TextInputFormat.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置reduce
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //指定要处理的数据的位置
        FileInputFormat.setInputPaths(job, args[0]);
        Path output = new Path(args[1]);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }

        // 告诉文件输出组件，输出结果放在哪里
        FileOutputFormat.setOutputPath(job, output);

        boolean res = job.waitForCompletion(true);
        // 如果job在hadoop集群中执行成功，则我这个客户端程序正常退出，否则异常退出
        System.exit(res ? 0 : 1);
    }
}
