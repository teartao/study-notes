package cn.neotao.hungry;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class StaticDataSource {
    private final static StaticDataSource INSTANCE;

    //初始化创建实例
    static {
        INSTANCE = new StaticDataSource();
    }

    private StaticDataSource() {
    }

    public static StaticDataSource getInstance() {
        return INSTANCE;
    }
}
