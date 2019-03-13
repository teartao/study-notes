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
     * //=new DataSource();下方static代码快可以改为此处实例化
     */
    private final static DataSource INSTANCE;

    //初始化创建实例
    static {
        INSTANCE = new DataSource();
    }

    //下面这段不必要，因为static INSTANCE全局只有一个
   /*
   private DataSource() {
        if (INSTANCE != null) {
            throw new RuntimeException("DataSource已存在，不能重复创建");
        }
    }
    */

    /*
     * 提供全局访问点
     */
    public static DataSource getInstance() {
        return INSTANCE;
    }
}
