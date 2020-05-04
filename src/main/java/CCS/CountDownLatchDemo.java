package CCS;

import interview.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * @program: algorithm
 * @ClassName CountDownLatchDemo
 * @description:
 * @author: 许
 * @create: 2020-04-21 09:49
 * @Version 1.0
 **/
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国，被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ....秦统一天下");
    }

}
