package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @program: algorithm
 * @ClassName CallableDemo
 * @description:
 * @author: 许
 * @create: 2020-04-22 12:23
 * @Version 1.0
 **/
//实现线程的第三种方式
class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+" \t  call方法");
         try {
             TimeUnit.SECONDS.sleep(5);
         } catch (InterruptedException e) {
             e.printStackTrace();
         } finally {
         }
        return 1024;
    }
}


public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTash = new FutureTask<>(new MyThread());
        //多个线程公用一个 future 只会打印一次 AA 	  call方法
        new Thread(futureTash,"AA").start();
        new Thread(futureTash,"BB").start();

        System.out.println("result ==="+futureTash.get());  //建议放在最后
    }


}
