package queue;

import java.util.concurrent.TimeUnit;

/**
 * @program: algorithm
 * @ClassName VolatileTest
 * @description:
 * @author: 许
 * @create: 2020-04-21 22:27
 * @Version 1.0
 **/
class myData{

     int number = 0;

    public void add(){
        this.number = 60;
    }
}

public class VolatileTest {

    public static void main(String[] args) {
        myData myData = new myData();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.add();
            System.out.println(Thread.currentThread().getName()+"\t已经修改了值");
        },"AAA").start();

        while(myData.number == 0) {
            System.out.println( " 还没修改");
        }
        System.out.println(Thread.currentThread().getName() + " 获得修改后的值");
    }

}
