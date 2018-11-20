package cn.neotao.entity.clone;

import cn.neotao.entity.serialize.Animal;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/11/20
 */
public class Dog implements Cloneable {

    private Food food;
    private String name;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Dog clone() throws CloneNotSupportedException {
        return (Dog) super.clone();
    }
}
