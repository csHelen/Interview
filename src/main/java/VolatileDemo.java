import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @program: algorithm
 * @ClassName VolatileDemo
 * @description:
 * @author: 许
 * @create: 2020-04-20 19:25
 * @Version 1.0
 **/

class MyData{
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);
    public void addTo60(){
        this.num = 60;
    }
    public synchronized void addPlusPlus(){
        num++;
    }
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }

}

public class VolatileDemo {

    public static void main(String[] args) {
        testVolatile();
//        MyData myData = new MyData();
//
//
//
//        for (int i = 0; i < 20; i++) {
//            new Thread(()->{
//                for (int j = 0; j < 1000; j++) {
//                    myData.addTo60();
//                }
//            }).start();
//        }
//
//        while(Thread.activeCount() > 2){
//            Thread.yield();
//        }
//
//        System.out.println(Thread.currentThread().getName()+"修改完成:"+myData.atomicInteger.get());

//        new ReentrantLock();


    }

    public static void testVolatile(){
        MyData myData = new MyData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"修改完成");
        },"AAA").start();

        while(myData.num == 0){

        }
        System.out.println(Thread.currentThread().getName());
    }

}
