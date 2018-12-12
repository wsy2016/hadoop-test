package com.bigdata.hadoop;


import java.io.*;

/**
 * Description:
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/8 22:36
 */
public class CreateFile {
    public static final String The_man_of_property = "/Users/wensiyang/Downloads/The_man_of_property.txt";



    public void test() {

        method1(The_man_of_property,"aa aa aa aa aa aa");
    }


    /**
     *     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *     *
     *     * @param fileName
     *     * @param content
     *     
     */

    public void method1(String file, String conent) {
        BufferedWriter out = null;
        try {

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            StringBuffer bs = new StringBuffer();
            for (int i = 0; i < 1000; i++) {
                bs.append(conent);
            }
            out.write(bs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param oldFileName
     * @param newFileName
     * @function 通过bufferedReader和bufferedWriter 拷贝文件
     */
    public void CopyFile(String oldFileName, String newFileName) {
        BufferedWriter bWriter = null;
        BufferedReader bReader = null;
        try {
            //读文件     hs_err_pid3420.log
            File oldFile = new File(oldFileName);
            //写文件
            File newFile = new File(newFileName);
            FileReader fReader = new FileReader(oldFile);
            bReader = new BufferedReader(fReader);

            FileWriter newFWrite = new FileWriter(newFile);
            bWriter = new BufferedWriter(newFWrite);

            String s = null;
            while ((s = bReader.readLine()) != null) {
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < 100; i++) {
                    sb.append(s);
                }
                bWriter.write(sb.toString());
                bWriter.newLine();
                bWriter.flush();

            }
            if (bWriter != null) {
                bWriter.close();
            }
            if (bReader != null) {
                bReader.close();
            }
            System.out.println("复制文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
