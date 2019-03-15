package cn.neotao.crack;

import cn.neotao.lazy.InnerClassSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author neotao
 * @Date 2019/3/15
 * @Version V0.0.1
 * @Desc
 */
public class CrackSingleton {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(InnerClassSingleton.getInstance());

        // 修改字段可访问
        Class innerClazz = InnerClassSingleton.class.getDeclaredClasses()[1];
        Field innerClazzField = innerClazz.getDeclaredField("INSTANCE");
        innerClazzField.setAccessible(true);

        //修改构造方法可访问
        Constructor constructorMethod = InnerClassSingleton.class.getDeclaredConstructor();
        constructorMethod.setAccessible(true);

        //重新创建实例
        InnerClassSingleton newSingleton = (InnerClassSingleton)constructorMethod.newInstance();
        System.out.println(newSingleton);
    }
}

