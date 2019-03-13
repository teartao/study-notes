package cn.neotao.hungry;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class SimpleSingleton {
    /*
     * private 私有变量存储实例对象，
     * final防止子孙、反射、外部方法修改
     * static初始化存储
     */
    private final static SimpleSingleton INSTANCE = new SimpleSingleton();

    private SimpleSingleton() {
        /*
         *下面这段代码不必要，因为static INSTANCE全局只有一个
         * 即使构造方法被反射改为public，获取的INSTANCE也是初始化创建的那个
         */
        // if (INSTANCE != null) {
        //     throw new RuntimeException("DataSource已存在，不能重复创建");
        // }
    }

    /*
     * 提供全局访问点
     */
    public static SimpleSingleton getInstance() {
        return INSTANCE;
    }
}
