package cn.neotao;

import cn.neotao.entity.Cat;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/11/13
 */
public class SerializeApp {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.setColor("yellow");
        cat.setFootNum(4);

        MySerializer serializer = new JavaStringSerializer();
        System.out.println(new String(serializer.serialize(cat)));
    }
}
