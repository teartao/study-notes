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
        cat.setFavourite("fish");

        System.out.println(cat);
        // Cat: {name='null', footNum=4, color='yellow'}
        //  superClass didn't [ implements Serializable ] so [ name = null ]
        //  transient provided , so [ favourite ] haven't shown

        MySerializer serializer = new JavaStringSerializer();
        byte[] catByte = serializer.serialize(cat);
        System.out.println(new String(catByte));
        Cat deSerializedCat = serializer.deSerialize(catByte, Cat.class);
        System.out.println(deSerializedCat);

        // TODO: 2018/11/15 usage of serialVersionUID in DTO


    }
}
