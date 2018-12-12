package com.hadoop.hdfss;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/12 17:37
 */
public class HdfsClient {


    //初始化参数
    public void init()throws Exception {

        Configuration conf = new Configuration();

        FileSystem ds = FileSystem.get(conf);
    }

}
