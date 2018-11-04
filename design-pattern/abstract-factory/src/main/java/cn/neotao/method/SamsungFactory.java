package cn.neotao.method;

import cn.neotao.Phone;
import cn.neotao.Samsung;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class SamsungFactory implements Factory {
    public Phone getPhone() {
        return new Samsung();
    }
}
