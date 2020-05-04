package productAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: algorithm
 * @ClassName BlockingQueueTest
 * @description:
 * @author: 许
 * @create: 2020-04-30 14:42
 * @Version 1.0
 **/

class NR{

    AtomicInteger atomicInteger = new AtomicInteger(0);
    BlockingQueue<String> blockingQueue = null;
    boolean isRunning = true;
    public NR(BlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
    }
    public void p() throws InterruptedException {
        String data = null;
        while(isRunning){
            //获取队列中的数据
           data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (data == null) {
                //2S都没有数据了，那就退出消费吧
                isRunning = false;
                System.out.println(Thread.currentThread().getName()+"\t退出消费");
                System.out.println("");
                return;
            }else{
                System.out.println(Thread.currentThread().getName()+"\t消费\t"+data);
            }
        }
    }

    public void v() throws InterruptedException {
        String data = null;
        boolean offer = false;
        while (isRunning) {
            data = atomicInteger.getAndIncrement()+"";
            offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (offer) {
                System.out.println(Thread.currentThread().getName()+"\t插入"+data+"到队列\t");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t失败操作");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t停止生产");
    }

    public void stop(){
        this.isRunning = false;
    }

}

public class BlockingQueueTest {

    public static void main(String[] args) {
        NR myResource = new NR(new LinkedBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.v();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费者线程启动");
            try {
                myResource.p();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Cons").start();

        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName()+"\t 叫停了");
            System.out.println("5S时间到，停止");
            myResource.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
