package com.strom.wordCount;

/**
 * Description: 长时间,费时间的操作
 * <p>
 * Author: wsy
 * <p>
 * Date: 2018/12/20 22:00
 */
public class LongTimeTask {


    private Object data = null;

    private boolean completed = false;


    public void startBigTask() {
        Thread t1 = new Thread("线程1") {
            @Override
            public void run() {
                System.out.println("--线程1--" + getData());
            }
        };
        t1.start();

        new Thread("线程2") {

            @Override
            public void run() {
                System.out.println("--线程2--" + getData());
            }
        }.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("--线程3--" + getData());
            }
        }).start();


        new Runnable() {

            @Override
            public void run() {
                System.out.println("--线程4--" + getData());

            }
        }.run();

    }


    private synchronized Object getData() {
        while (!this.completed) {
            try {
                wait();
                return this.data;

            } catch (Throwable t) {
                return null;
            }finally {
                this.data = null;
            }
        }
        return null;


    }

    /**
     * 模拟费时间操作
     */
    private synchronized void makeRealData() {
        if (this.completed) {
            return;
        }
        //获取数据的耗时操作.这里用Sleep代替
        try {
            Thread.sleep(10000);
        } catch (Throwable t) {

        }
        this.data = "返回的数据内容";
        this.completed = true;
        notifyAll();
    }

}
