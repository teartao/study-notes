package cn.neotao.abst;

import cn.neotao.Phone;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public interface Factory {
    Phone getIPhone();

    Phone getSamsung();

    Phone getNokia();
}
