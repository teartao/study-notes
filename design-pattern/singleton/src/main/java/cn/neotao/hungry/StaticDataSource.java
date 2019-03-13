package cn.neotao.hungry;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class StaticDataSource {
    /*
     * private 私有变量存储实例对象，
     * final防止子孙、反射、外部方法修改
     * static初始化存储
     */
    private final static StaticDataSource INSTANCE;

    //初始化创建实例
    static {
        INSTANCE = new StaticDataSource();
    }

    private StaticDataSource() {
    }

    /*
     * 提供全局访问点
     */
    public static StaticDataSource getInstance() {
        return INSTANCE;
    }
}
