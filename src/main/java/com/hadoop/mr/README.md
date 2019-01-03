-- hadoop jar /usr/local/src/data/jar/hadoop-test.jar com.hadoop.mr.wordCount.MyDriver /input/soBigFile.txt /output


**hadoop组件**
       --| Mapper  maptask
       --| combiner
       --| Reducer  reducetask
       
       
**集群运行流程**
       --| 提交一个job(submit/waitForCompletion)
       --| 启动mrAppMatser      
       --| 启动maptask      
       --| 启动reducetask  
       
 ************************** **MapReduce的核心组件** ************************** 
         
**mapreduce中的Combiner**
      --| combiner是MR程序中Mapper和Reducer之外的一种组件
      --| combiner组件的父类就是Reducer
      --| combiner和reducer的区别在于运行的位置：
            --| Combiner是在每一个maptask所在的节点运行
            --| Reducer是接收全局所有Mapper的输出结果；
      --| `combiner的意义就是对每一个maptask的输出进行局部汇总，以减小网络传输量`
              具体实现步骤：
              --|	自定义一个combiner继承Reducer，重写reduce方法
              --|   在job中设置：job.setCombinerClass(CustomCombiner.class)
      --| combiner能够应用的前提是不能影响最终的业务逻辑
      --| 而且，combiner的输出kv应该跟reducer的输入kv类型要对应起来
    
      
**mapreduce中的shuffle(洗牌)**
功能 :数据分区,排序,缓存(将maptask输出的结果,分发给reducetask)
流程:
       --| 收集map()方法输出kv对,放入内存缓存区(大小可以设置)减少IO传输
       --| 内存缓存不短溢出到磁盘文件,可能会出现多个文件
       --| 多个溢出的文件会被合并成大的文件
       --| 溢出过程中,合并文件都要调用partitoner(可以自定义分区)和key的排序(WritableComparable实现类)
       --| reducetask
      
************************** **MapReduce的优化** ************************** 
