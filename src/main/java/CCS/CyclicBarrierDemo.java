package CCS;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: algorithm
 * @ClassName CyclicBarrierDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 12:32
 * @Version 1.0
 **/
public class CyclicBarrierDemo {


    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()-> System.out.println("巴拉啦"));
        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" \t收集了第:"+tempInt+"颗龙珠");
                try {
                    //等了七个之后一起执行
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }, String.valueOf(i)).start();
        }

    }


}
