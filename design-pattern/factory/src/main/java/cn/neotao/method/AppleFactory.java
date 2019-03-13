package cn.neotao.method;

import cn.neotao.IPhone;
import cn.neotao.Phone;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class AppleFactory implements Factory {
    public Phone getPhone() {
        return new IPhone();
    }
}
