package cn.neotao.method;

import cn.neotao.Nokia;
import cn.neotao.Phone;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class NokiaFactory implements Factory {
    public Phone getPhone() {
        return new Nokia();
    }
}
