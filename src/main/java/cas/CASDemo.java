package cas;

/**
 * @program: algorithm
 * @ClassName cas
 * @description:
 * @author: 许
 * @create: 2020-04-20 07:35
 * @Version 1.0
 **/

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS:
 *      面试：CAS你知道吗？
 *              比较并交换    CompareAndSwap
 *              CPU并发原语：原语属于草最系统范畴，由若干条指令组成，用于完成某个功能，
 *                      而且“原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU原子指令，不会造成所谓的数据不一致问题”
 *              功能：判断内存某个位置的值是否为预期值，如果是则修改为新值，这个过程是原子的
 *            CAS底层原理？
 *              谈谈对Unsafe的理解
 *
 *            为什么用CAS而不用synchronize
 */
/**
 *
 *     this：当前对象
 *     valueOffset:内存偏移量（内存地址）
 *     1：写死加一
 *
 *     unsafe 在 rt.jar  运行时环境
 *
 *     private static final Unsafe unsafe = Unsafe.getUnsafe();
 *     private static final long valueOffset;
 *     valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
 *     private volatile int value;
 *
 *
 *     1 Unsafe：是CAS的核心类，由于java方法无法直接访问底层系统，需要通过本地native方法来访问，Unsafe相当于一个后门
 *     基于该类可以直接操作特定内存的数据，Unsafe在 rt.jar的sun.misc包中，其内部方法操作可以像C指针一样直接操作内存
 *     因为java中CAS操作的执行依赖于Unsafe类方法
 *      Unsafe类中方法都是native修饰的，说明方法都是直接调用操作系统底层资源执行相应任务
 *      CAS线程安全是因为使用了Unsafe类
 *     2：变量valueOffset表示在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的
 *               public final int getAndIncrement() {
 *                 return unsafe.getAndAddInt(this, valueOffset, 1);
 *              }
 *     3：变量value用volatile修饰，保证了多线程之间的内存可见性
 */

//多线程也能正常 +1
/**
 *      return unsage.getAndAddInt(this,valueOffset,1)
 *        -> int getAndAddInt(Object var1,long var2,int var4){
 *              int var5;
 *              do{
 *                  var5 = this.getInVolatile(var1,var2)
 *              }while(!this.compareAndSwapInt(var1,var2,var5,var5+var4))
 *              return var5;
 *           }
 *           //没有加锁！！适合并发
 */

//  ====================================================
//                      CAS 缺点
//  =====================================================

/**             循环时间长，开销很大
 *              只能保证一个共享变量的原子操作
 *              印出来的ABA问题
 */

//    CAS   -->  Unsafe   -> CAS底层思想   -> ABA  -> 原子引用更新 -> 如何规避ABA问题
//    ABA：狸猫换太子
public class CASDemo {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            new ConcurrentHashMap<>();
            while(true){
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicInteger.getAndIncrement();
                System.out.println(Thread.currentThread().getName()+":"+atomicInteger.get());
            }

        }).start();

        while(true){
            TimeUnit.SECONDS.sleep(1);
            atomicInteger.getAndIncrement();
            System.out.println(Thread.currentThread().getName()+":"+atomicInteger.get());
        }
    }
}
