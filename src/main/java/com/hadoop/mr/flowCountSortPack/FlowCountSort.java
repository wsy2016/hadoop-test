package com.hadoop.mr.flowCountSortPack;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FilterFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * 功能：对流量日志进行按用户统计并按流量大小倒序输出
 * 注意： 这个需求并不能在一个mr程序中完成，它是分成了两个mr程序来实现
 * 第一个mr只实现流量汇总统计
 * 第二个mr是在前一个mr的输出结果之上再处理
 * 在真实的业务场景中，大部分时候，一个业务统计需求都需要很多个mr步骤串联起来完成
 * @author
 *
 */
public class FlowCountSort {

	static class FlowCountSortMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			// 13480253104 180 180 360
			String line = value.toString();

			String[] fields = line.split("\t");

			String phone = fields[0];

			long upAmount = Long.parseLong(fields[8]);
			long dAmount = Long.parseLong(fields[9]);

			FlowBean countBean = new FlowBean(upAmount, dAmount);

			// 以流量bean作为key输出，则会以流量bean的排序规则达到reducer
			context.write(countBean, new Text(phone));

		}

	}

	/**
	 * reducer类
	 * 
	 * @author
	 * 
	 */
	static class FlowCountSortReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

		@Override
		protected void reduce(FlowBean bean, Iterable<Text> values, Context context) throws IOException, InterruptedException {

			context.write(values.iterator().next(), bean);

		}

	}

	public static void run(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(FlowCountSort.class);

		job.setMapperClass(FlowCountSortMapper.class);
		job.setReducerClass(FlowCountSortReducer.class);

		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		/**
		 * hadoop中默认的输入输出组件就是TextInputformat和textoutputformat，所以，这两句代码也可以省略
		 */
		/*
		 * job.setInputFormatClass(TextInputFormat.class);
		 * job.setOutputFormatClass(TextOutputFormat.class);
		 */

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileSystem system = FileSystem.get(conf);
		Path output = new Path(args[1]);
		if(system.exists(output)){
			system.delete(output, true);
		}
		FileOutputFormat.setOutputPath(job,output );


		boolean res = job.waitForCompletion(true);
		System.exit(res ? 0 : 1);

	}
	public static void  main(String[] args){

		String path1 = "/Users/wensiyang/Downloads/HTTP_20130313143750.dat";
		String path2 = "/Users/wensiyang/Downloads/result";
		String[] argss = {path1, path2};
		try {
			run(argss);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
