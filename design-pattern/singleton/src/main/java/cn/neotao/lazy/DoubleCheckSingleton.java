package cn.neotao.lazy;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class DoubleCheckSingleton {

    /**
     * volatile避免内存指令重排序 [more](https://blog.csdn.net/UnknownZYB/article/details/81436060)
     */
    private volatile static DoubleCheckSingleton INSTANCE;


    private DoubleCheckSingleton() {
        //防止反射修改private为public后，重复创建对象，覆盖INSTANCE
        if (INSTANCE != null) {
            throw new RuntimeException("只能存在一个工厂");
        }
    }

    public static DoubleCheckSingleton getInstance() {
        /*
         * 实例不存在时才创建
         * 如果只加sync内部if，不加此处if会导致：
         * 大量代码尝试创建时被锁定，依次排队等待获取锁
         * 进入锁时发现对象已存在，不需要再次创建,白等了获取锁的时间
         * 在外部加上if可以避免不必要的锁竞争，适当提升多线程环境下性能
         */
        if (INSTANCE == null) {

            //创建时防止多线程进入，加上synchronized锁定创建代码
            synchronized (DoubleCheckSingleton.class) {
                /*
                 * 再次判断，否则可能多线程同时通过外层null判断进来后，
                 * 先后创建多个实例，重复创建场景如下：
                 * (1)线程1 外部INSTANCE == null，进入
                 * (2)线程2 外部INSTANCE == null，进入
                 * (3)线程1进入sync创建对象，线程2排队(1、2顺序随机)
                 * (4)线程2等待1完成后，再进入sync创建对象
                 * 因此需要在sync中再次判断对象是否存在
                 */
                if (INSTANCE == null) {
                    /*
                     * 1.分配内存给这个对象
                     * 2.初始化对象
                     * 3.设置lazy指向刚分配的内存地址
                     * 4.初次访问对象
                     *
                     * JIT编译器上可能存在指令重排序，真实执行结果顺序为1324，
                     * 导致实例化对象为null，因此需要为INSTANCE加上volatile避免重排序
                     */
                    INSTANCE = new DoubleCheckSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
