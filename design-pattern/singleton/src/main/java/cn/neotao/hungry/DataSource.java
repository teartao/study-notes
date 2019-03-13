package cn.neotao.hungry;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class DataSource {
    /*
     * private 私有变量存储实例对象，
     * final防止子孙、反射、外部方法修改
     * static初始化存储
     */
    private final static DataSource INSTANCE;

    //初始化创建实例
    static {
        INSTANCE = new DataSource();
    }

    //private避免外部重复实例化
    private DataSource() {
        //判断对象null避免反射修改private为public后，重复创建实例
        if (INSTANCE != null) {
            throw new RuntimeException("DataSource已存在，不能重复创建");
        }
    }

    /*
     * 提供全局访问点
     */
    public static DataSource getInstance() {
        return INSTANCE;
    }
}
