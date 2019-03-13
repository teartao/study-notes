package cn.neotao.lazy;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class BeanFactory {

    private static BeanFactory INSTANCE;


    private BeanFactory() {
        if (INSTANCE != null) {
            throw new RuntimeException("只能存在一个工厂");
        }
    }

    private static BeanFactory getFactory() {
        /*
         * 实例不存在时才创建
         * 如果只加sync内部if，不加此处if会导致：
         * 大量代码尝试创建时被锁定，依次排队等待获取锁
         * 进入锁时发现对象已存在，不需要再次创建,白等了获取锁的时间
         * 在外部加上if可以避免不必要的锁竞争，适当提升多线程环境下性能
         */
        if (INSTANCE == null) {

            //创建时防止多线程进入，加上synchronized锁定创建代码
            synchronized (BeanFactory.class) {
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
                    INSTANCE = new BeanFactory();
                }
            }
        }
        return INSTANCE;
    }
}
