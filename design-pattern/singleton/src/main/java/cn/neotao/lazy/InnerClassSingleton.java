package cn.neotao.lazy;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class InnerClassSingleton {
    private InnerClassSingleton() {
        if (InnerInstanceHolder.INSTANCE != null) {
            new RuntimeException("已存在，不能重复创建");
        }
    }

    public static InnerClassSingleton getInstance() {
        return InnerInstanceHolder.INSTANCE;
    }

    private static class InnerInstanceHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }
}
