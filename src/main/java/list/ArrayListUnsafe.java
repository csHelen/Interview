package list;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: algorithm
 * @ClassName ArrayListUnsafe
 * @description:
 * @author: 许
 * @create: 2020-04-20 11:11
 * @Version 1.0
 **/
public class ArrayListUnsafe {

    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<>();



    }

    public static void ArrayList不安全手写实例(){
        //        List list = new ArrayList();
//        List list = new Vector();
        List list = Collections.synchronizedList(new ArrayList<>());
//        List list = new CopyOnWriteArrayList();

//        List list = new ArrayList();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().subSequence(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}



