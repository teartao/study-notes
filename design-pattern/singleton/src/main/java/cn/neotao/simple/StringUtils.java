package cn.neotao.simple;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
}
