package cn.neotao.hungry;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class StaticSingleton {
    private final static StaticSingleton INSTANCE;

    //初始化创建实例
    static {
        INSTANCE = new StaticSingleton();
    }

    private StaticSingleton() {
    }

    public static StaticSingleton getInstance() {
        return INSTANCE;
    }
}
