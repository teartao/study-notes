package cn.neotao.lazy;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class InnerClassSingleton {

    public static InnerClassSingleton getInstance() {
        return InnerInstanceHolder.INSTANCE;
    }

    public static class InnerInstanceHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }
}
