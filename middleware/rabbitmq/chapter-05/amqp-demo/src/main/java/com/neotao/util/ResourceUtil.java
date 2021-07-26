package com.neotao.util;

import java.util.ResourceBundle;

/**
 * @author neotao
 * @date 2020/11/13 上午11:24
 */
public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("rabbitmq");
    }

    public static String getKey(String key) {
        return resourceBundle.getString(key);
    }

}
