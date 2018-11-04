package cn.neotao.abst;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class AbstractFactoryApp {
    public static void main(String[] args) {
        Factory phoneFactory = new PhoneFactory();
        phoneFactory.getNokia().getModel();
        phoneFactory.getSamsung().getModel();
        phoneFactory.getIPhone().getModel();
    }
}
