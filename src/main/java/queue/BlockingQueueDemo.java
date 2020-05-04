package queue;

/**
 * @program: algorithm
 * @ClassName BlockingQueueDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 13:16
 * @Version 1.0
 **/

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * ArrayBlockingQueue
 * LinkedBlockingQueue
 * SynchronousQueue
 *
 *
 * 1 队列
 *
 * 2 阻塞队列
 *   2.1 阻塞队列有没有好的一面？
 *
 *   2.2 不得不阻塞，你如何管理？
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {


    }
    public void goodFunction() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.offer("a",2L, TimeUnit.SECONDS);
        blockingQueue.offer("a",2L, TimeUnit.SECONDS);
        blockingQueue.offer("a",2L, TimeUnit.SECONDS);
        blockingQueue.offer("a",2L, TimeUnit.SECONDS);

    }

    public void block() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
//        blockingQueue.put("c");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
    }

    public void returnBoolean(){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //不会抛出异常，插入失败返回为空
        System.out.println(blockingQueue.offer("e"));
        //为空，返回null
        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //为空，返回null
        System.out.println(blockingQueue.poll());

    }
    public void exceptionList(){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //Exception in thread "main" java.lang.IllegalStateException: Queue full
//        System.out.println(blockingQueue.add("e"));

        System.out.println(blockingQueue.element());

        //队列先进先出
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        //Exception in thread "main" java.util.NoSuchElementException
//        blockingQueue.remove();
    }

}
