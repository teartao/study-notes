package cn.neotao;

import java.io.*;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/11/13
 */
public class JavaFileSerializer implements MySerializer {

    private static final String serializedFilePath = "java-serialize";

    public <T> byte[] serialize(T obj) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(serializedFilePath)));
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public <T> T deSerialize(byte[] data, Class<T> clazz) {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File("java-serialize")));
            return (T) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
