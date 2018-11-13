package cn.neotao;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/11/13
 */
public interface MySerializer {
    <T> byte[] serialize(T obj);

    <T> T deSerialize(byte[] data, Class<T> clazz);
}
