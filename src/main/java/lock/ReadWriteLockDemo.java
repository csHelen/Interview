package lock;

/**
 * @program: algorithm
 * @ClassName ReadWriteLockDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 08:51
 * @Version 1.0
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行读或写
 *      读-读  可
 *      读-写  不可
 *      写-写  不可
 */
class MyCache{

    private volatile Map<String, Object> map = new HashMap<>();
    //替代synchronized，但是这个不太符合，因为这个不能满足读的并发需求，只允许一个线程
//    private Lock lock = new ReentrantLock();
    //高级~~~~
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 正在完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void put2(String key, Object value){
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 正在完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public Object get(String key)  {
        System.out.println(Thread.currentThread().getName()+"\t 正在读取");
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName()+"\t 读取完成");

        return o;
    }
    public void get2(String key)  {
        rwlock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            TimeUnit.MILLISECONDS.sleep(300);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put2(tempInt+"",tempInt+"");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get2(tempInt+"");
            }, String.valueOf(i)).start();
        }

    }


}
