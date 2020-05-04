package productAndConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: algorithm
 * @ClassName TranditionTest
 * @description:
 * @author: 许
 * @create: 2020-04-30 11:13
 * @Version 1.0
 **/

class R{
    int num = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void p(){
        lock.lock();
        try {
            // 判断
            while (num != 0) {
                condition.await();
            }
            //干活
            num++;
            System.out.println(Thread.currentThread().getName()+" "+num);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();;
        } finally {
            lock.unlock();
        }
    }
    public void v(){
        lock.lock();
        try {
            // 判断
            while(num == 0){
                condition.await();
            }
            //干活
            num--;
            System.out.println(Thread.currentThread().getName()+" "+num);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();;
        } finally {
            lock.unlock();
        }
    }
}

public class TranditionTest {
    public static void main(String[] args) {
        R shareData = new R();

        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.p();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.v();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
