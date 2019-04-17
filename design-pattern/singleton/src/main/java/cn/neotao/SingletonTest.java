package cn.neotao;

import cn.neotao.hungry.SimpleSingleton;
import cn.neotao.hungry.StaticSingleton;
import cn.neotao.lazy.DoubleCheckSingleton;
import cn.neotao.lazy.InnerClassSingleton;

/**
 * @Author neotao
 * @Date 2019/3/15
 * @Version V0.0.1
 * @Desc
 */
public class SingletonTest {
    private static int count = 0;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            public void run() {
                while (count < 10) {
                    // initSimpleSingleton(++count);
                    // initStaticSingleton(++count);
                    // initDoubleCheckSingleton(++count);
                    initInnerClassSingleton(++count);
                }
            }
        }).start();

    }

    private static void initSimpleSingleton(int threadName) {
        SimpleSingleton singleton = SimpleSingleton.getInstance();
        System.out.println("[A_Simple]:" + threadName + singleton);
    }

    private static void initStaticSingleton(int threadName) {
        StaticSingleton singleton = StaticSingleton.getInstance();
        System.out.println("[B_Static]:" + threadName + singleton);
    }

    private static void initDoubleCheckSingleton(int threadName) {
        DoubleCheckSingleton singleton = DoubleCheckSingleton.getInstance();
        System.out.println("[C_Double]:" + threadName + singleton);
    }

    private static void initInnerClassSingleton(int threadName) {
        InnerClassSingleton singleton = InnerClassSingleton.getInstance();
        System.out.println("[D_Inner]:" + threadName + singleton);
    }

}
