package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: algorithm
 * @ClassName SpinLockDemo02
 * @description:
 * @author: 许
 * @create: 2020-04-21 21:47
 * @Version 1.0
 **/
public class SpinLockDemo02 {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"\t 进程已经进来");
        //第一次进来，本来就是空，则返回true，但第一次要跳过循环，所以用非
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t 进程出去了");
    }
    public static void main(String[] args) {
        SpinLockDemo02 spinLockDemo02 = new SpinLockDemo02();
        new Thread(()->{
            spinLockDemo02.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLockDemo02.myUnlock();
            }
        },"AA").start();
        new Thread(()->{
            spinLockDemo02.myLock();
            spinLockDemo02.myUnlock();
        },"BB").start();
    }

}
