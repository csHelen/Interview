package productAndConsumer;

/**
 * @program: algorithm
 * @ClassName ProdConsumer_traditionDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 15:01
 * @Version 1.0
 **/

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：一个初始值为零的变量，两个线程对齐交互操作，一个加1，一个减1，来5轮
 *   1.   线程      操作（方法）      资源类
 *   2.   判断      干活              通知
 *   3.   严防多线程并发状态下的虚假唤醒   判断+while判断
 */
class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

   public void increament() throws Exception {
       lock.lock();
       try {
           // 判断
           while(number != 0){
               //等待，不能生产
               condition.await();
           }
           //干活
           number++ ;
           System.out.println(Thread.currentThread().getName()+" "+number);
           //通知唤醒
           condition.signalAll();
       }catch (Exception e){
           e.printStackTrace();;
       } finally {
           lock.unlock();
       }

   }
    public void decreament() throws Exception {
        lock.lock();
        try {
            // 判断
            while(number == 0){
                //等待，不能生产
                condition.await();
            }
            //干活
            number-- ;
            System.out.println(Thread.currentThread().getName()+" "+number);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();;
        } finally {
            lock.unlock();
        }

    }

}

public class ProdConsumer_traditionDemo {


    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.increament();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.decreament();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.increament();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();
        new Thread(()->{
            for (int i = 0; i <= 5 ; i++) {
                try {
                    shareData.decreament();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();

    }

}
