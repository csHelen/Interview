package thread;

import java.util.concurrent.*;

/**
 * @program: algorithm
 * @ClassName MyThreadPoolDemo
 * @description:
 * @author: 许
 * @create: 2020-04-22 13:41
 * @Version 1.0
 **/

/**
 *
 * 第4中获得/试用java多线程的方式，线程池
 *
 *
 */
public class MyThreadPoolDemo {


    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{

            for (int i = 0; i <80 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public void baseJDKThread(){
        //一池固定5个处理线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一次一个线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        //一次看情况使用多少个线程，如果执行需要，就一个线程也行，如果很多，可以很多线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        //关闭比使用更重要




        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{

            for (int i = 0; i <100 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }


}
