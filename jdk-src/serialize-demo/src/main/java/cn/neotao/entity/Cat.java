package cn.neotao.entity;

import java.io.Serializable;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class Cat extends Animal implements Serializable {

    private String color;

    private int footNum;

    public int getFootNum() {
        return footNum;
    }

    public void setFootNum(int footNum) {
        this.footNum = footNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cat: {" +
                "name='" + name + '\'' +
                ", footNum=" + footNum +
                ", color='" + color + '\'' +
                '}';
    }
}
