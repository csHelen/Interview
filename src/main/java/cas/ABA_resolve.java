package cas;

/**
 * @program: algorithm
 * @ClassName ABA_resolve
 * @description:
 * @author: 许
 * @create: 2020-04-20 09:52
 * @Version 1.0
 **/

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS 的 ABA问题
 *      线程A和B去改C， 初始C为0，A改成1 ，A又改回0，，B再来修改发现值还是一样，但是还是能够处理，但不是原始的值了
 *          处理：原子引用 + 版本号
 *          AtomicStampedReference  带有版本号！！！可以解决ABA问题
 */
public class ABA_resolve {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100,1);
    public static void main(String[] args) {


    }
    public static void atomicStampedReferenceTest(){
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("第一次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100, 101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
            System.out.println(atomicStampedReference.compareAndSet(101, 100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
        }).start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("第一次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100, 101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
        }).start();
    }
    public static void atomicReferenceTest(){
        new Thread(()->{
            System.out.println(atomicReference.compareAndSet(100, 101));
            System.out.println(atomicReference.compareAndSet(101, 100));
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 101));
        }).start();
    }
}
