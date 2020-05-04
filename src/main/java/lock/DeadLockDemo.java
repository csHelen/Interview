package lock;

/**
 * @program: algorithm
 * @ClassName DeadLockDemo
 * @description:
 * @author: 许
 * @create: 2020-04-22 21:36
 * @Version 1.0
 **/

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 *  线程 操作 资源类
 */
class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized(lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有"+lockA+"\t尝试持有"+lockB);
             try {
                 TimeUnit.SECONDS.sleep(2);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             } finally {
             }
            synchronized(lockB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有"+lockB+"\t尝试持有"+lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA,lockB),"AAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"BBB").start();
    }
}
