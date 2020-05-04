package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: algorithm
 * @ClassName SpinLockDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 08:09
 * @Version 1.0
 **/
public class SpinLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void mylock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in oo");
        //自旋锁核心
        //第一次进来，thread是null,则这个结果返回true，再取反则退出循环
        //其他人进来发现不是null这一直在这循环
        while(!atomicReference.compareAndSet(null,thread)){
        }
    }
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName() + " invoke myUnlock()");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        },"AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            spinLockDemo.mylock();
            spinLockDemo.myUnlock();

        },"ABB").start();
    }


}
