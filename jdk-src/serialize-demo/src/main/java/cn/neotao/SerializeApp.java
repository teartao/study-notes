package cn.neotao;

import cn.neotao.entity.Cat;

import java.io.InvalidClassException;

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
        // serialVersionUID : static field cannot be serialized

        MySerializer stringSerializer = new JavaStringSerializer();
        byte[] catByte = stringSerializer.serialize(cat);
        System.out.println(new String(catByte));
        Cat deSerializedCat = stringSerializer.deSerialize(catByte, Cat.class);
        System.out.println(deSerializedCat);


        MySerializer fileSerializer = new JavaFileSerializer();

        byte[] fileSerializedCatByte = fileSerializer.serialize(cat);
        /*
         * usage of serialVersionUID in DTO
         * 1. delete cat serialVersionUID
         * 2. serialized to file ，
         * 3. add cat serialVersionUID
         * 4. deSerialize cat from file , will cause InvalidClassException
         */

        Cat fileDeSerializedCat = fileSerializer.deSerialize(null, Cat.class);
        System.out.println(fileDeSerializedCat);



    }
}
