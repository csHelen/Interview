package design;

/**
 * @program: algorithm
 * @ClassName SingletonDemo
 * @description:
 * @author: 许
 * @create: 2020-04-20 21:12
 * @Version 1.0
 **/

//这是单线程下的单例模式
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;
    private SingletonDemo(){
        System.out.println("我被创建了");
    }
    //DCL Double Check Lock双端检测锁机制
    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized(SingletonDemo.class){
                if (instance == null) {
                    instance =  new SingletonDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            }).start();
        }
    }
}
