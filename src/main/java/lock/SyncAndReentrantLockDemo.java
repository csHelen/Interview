package lock;

//import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: algorithm
 * @ClassName SyncAndReetrantLockDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 15:45
 * @Version 1.0
 **/

/**
 * 题目：多线程之间按顺序调用，实现A-B-B三个线程启动，要求如下：
 *      AA打印5次，BB打印10次，CC打印15次  重复10轮
 */

class ShareResource{

    private int number = 1; //A 1 B 2 C 3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void printf1(){
        lock.lock();
        try {
            //判断
            while(number != 1){
                c1.await();
            }
            //干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printf2(){
        lock.lock();
        try {
            //判断
            while(number != 2){
                c2.await();
            }
            //干活
            for (int i = 1; i <= 10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printf3(){
        lock.lock();
        try {
            //判断
            while(number != 3){
                c3.await();
            }
            //干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {

        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printf1();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printf2();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printf3();
            }
        },"C").start();

    }


}
