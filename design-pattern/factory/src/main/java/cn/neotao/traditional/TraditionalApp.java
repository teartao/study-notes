package cn.neotao.traditional;

import cn.neotao.IPhone;
import cn.neotao.Nokia;
import cn.neotao.Samsung;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class TraditionalApp {
    public static void main(String[] args) {
        new Nokia().getModel();
        new Samsung().getModel();
        new IPhone().getModel();
    }
}
