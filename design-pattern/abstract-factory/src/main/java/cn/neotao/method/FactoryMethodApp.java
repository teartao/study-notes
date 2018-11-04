package cn.neotao.method;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class FactoryMethodApp {
    public static void main(String[] args) {
        Factory iPhoneFactory = new AppleFactory();
        iPhoneFactory.getPhone().getModel();
        Factory samsungFactory = new SamsungFactory();
        samsungFactory.getPhone().getModel();
        Factory nokiaFactory = new NokiaFactory();
        nokiaFactory.getPhone().getModel();
    }
}
