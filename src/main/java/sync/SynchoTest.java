package sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @program: algorithm
 * @ClassName SynchoTest
 * @description:
 * @author: 许
 * @create: 2020-04-29 15:30
 * @Version 1.0
 **/
public class SynchoTest {
    static L  l = new L();
    public static void main(String[] args) {
        l.hashCode();
        System.out.println(ClassLayout.parseClass(L.class).toPrintable(l));
        synchronized(l){
            System.out.println(ClassLayout.parseClass(L.class).toPrintable(l));
        }
        System.out.println(ClassLayout.parseClass(L.class).toPrintable(l));


    }
/**
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           43 c1 00 20 (01000011 11000001 00000000 00100000) (536920387)
 *      12     4        (loss due to the next object alignment)
 *
 *      上锁之后
 *
 *   OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           08 f1 00 03 (00001000 11110001 00000000 00000011) (50393352)    这里改变了
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           43 c1 00 20 (01000011 11000001 00000000 00100000) (536920387)
 *      12     4        (loss due to the next object alignment)
 *
 *      gc后
 *    OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           09 00 00 00 (00001001 00000000 00000000 00000000) (9)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           43 c1 00 20 (01000011 11000001 00000000 00100000) (536920387)
 *      12     4        (loss due to the next object alignment)
 *
 *      加volatile无效果
 *       l.hashCode()
 *       OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           01 b6 27 73 (00000001 10110110 00100111 01110011) (1931982337)
 *       4     4        (object header)                           67 00 00 00 (01100111 00000000 00000000 00000000) (103)
 *       8     4        (object header)                           43 c1 00 20 (01000011 11000001 00000000 00100000) (536920387)
 *      12     4        (loss due to the next object alignment)
 */
}

