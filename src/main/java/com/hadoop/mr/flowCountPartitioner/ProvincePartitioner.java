package com.hadoop.mr.flowCountPartitioner;

import clojure.lang.IFn;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * Description: 将map阶段的key安装一点规则分区,<k,v> 按照map输出的
 * <将手机号安装不同的省份分区>
 * Author: wsy
 * <p>
 * Date: 2019/1/2 21:11
 */
public class ProvincePartitioner extends Partitioner<Text, LongWritable> {


    /**
     * mr默认的分区方式:HashPartitioner
     * key的hashCode对分区数量进行取余
     * return (key.hashCode() & 2147483647) % numReduceTasks;
     */
    @Override
    public int getPartition(Text text, LongWritable longWritable, int numReduceTasks) {
        return matchProvince(text) % numReduceTasks;
    }


    /**
    *
    * */
    public int matchProvince(Text text) {
        System.out.println(text.toString().trim());
        Long phone = Long.parseLong(text.toString().trim());
        return phone % 2 == 0 ? 0 : 1;
    }
    
    public static void  main(String[] args){

        Long phone = Long.parseLong("\uFEFF1363157985066");
        System.out.println(phone);
    
    }
}
