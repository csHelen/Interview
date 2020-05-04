package lock;

import java.util.concurrent.TimeUnit;

/**
 * @program: algorithm
 * @ClassName 可重入锁
 * @description:
 * @author: 许
 * @create: 2020-04-21 22:12
 * @Version 1.0
 **/
public class 可重入锁_递归锁 {

    public synchronized void fun1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"\t fun1");
        fun2();
    }

    public synchronized void fun2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"\t fun2");
        TimeUnit.SECONDS.sleep(3);

    }


    public static void main(String[] args) {
        可重入锁_递归锁 s = new 可重入锁_递归锁();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                s.fun1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AA").start();

        new Thread(()->{
            try {
                s.fun2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BB").start();
    }

}
