package cn.neotao.entity;

import java.io.Serializable;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/10/14
 */
public class Cat extends Animal implements Serializable {
    private static final long serialVersionUID = 123123123123123123L;

    private String color;

    private int footNum;

    private transient String favourite;

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

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
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
