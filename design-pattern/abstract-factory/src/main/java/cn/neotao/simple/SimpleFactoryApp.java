package cn.neotao.simple;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class SimpleFactoryApp {
    public static void main(String[] args) {
        SimpleFactory phoneSimpleFactory = new SimpleFactory();
        phoneSimpleFactory.getPhone(SimpleFactory.nokia).getModel();
        phoneSimpleFactory.getPhone(SimpleFactory.samsung).getModel();
        phoneSimpleFactory.getPhone(SimpleFactory.iPhone).getModel();
    }
}
