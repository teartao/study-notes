package cn.neotao.abst;

import cn.neotao.IPhone;
import cn.neotao.Nokia;
import cn.neotao.Phone;
import cn.neotao.Samsung;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class PhoneFactory implements Factory {
    public Phone getIPhone() {
        return new IPhone();
    }

    public Phone getSamsung() {
        return new Samsung();
    }

    public Phone getNokia() {
        return new Nokia();
    }
}
