package cn.neotao.simple;

import cn.neotao.IPhone;
import cn.neotao.Nokia;
import cn.neotao.Phone;
import cn.neotao.Samsung;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class SimpleFactory {
    public static final String nokia = "NOKIA";
    public static final String samsung = "Samsung";
    public static final String iPhone = "iPhone";

    private static final Map<String, Phone> phoneMap = new HashMap<String, Phone>();

    public SimpleFactory() {
        phoneMap.put("NOKIA", new Nokia());
        phoneMap.put("Samsung", new Samsung());
        phoneMap.put("iPhone", new IPhone());
    }

    public Phone getPhone(String brand) {
        return phoneMap.get(brand);
    }
}
