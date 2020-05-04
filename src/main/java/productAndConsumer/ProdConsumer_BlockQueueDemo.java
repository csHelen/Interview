package productAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: algorithm
 * @ClassName ProdConsumer_BlockQueueDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 18:11
 * @Version 1.0
 **/
class MyResource{
    private volatile boolean FLAG = true;       //默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private BlockingQueue<String> blockingQueue = null;
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void myProd() throws Exception {
        String data =null;
        boolean retValue;
        while(FLAG){
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列 : "+data+" 成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列: "+data+" 失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t停止生产");
    }

    public void muConsumer() throws Exception {
        String result = null;
        while(FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"  超过2秒没有渠道蛋糕，消费退出");
                System.out.println("");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列 " + result + "  成功");

        }
    }
    public void stop() throws Exception {
        this.FLAG = false;
    }

}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new LinkedBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费者线程启动");
            try {
                myResource.muConsumer();
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
