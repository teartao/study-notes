package cn.neotao;

import cn.neotao.entity.clone.Dog;
import cn.neotao.entity.clone.Food;

/**
 * @author neotao
 * @version v1.0.0
 * @date 2018/11/13
 */
public class CloneApp {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setName("哮天");

        Food food = new Food();
        food.setName("肉");
        dog.setFood(food);

        System.out.println(dog.getFood().getName());
        Dog haShiQi = null;
        try {
            haShiQi = dog.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("clone failed");
            haShiQi = new Dog();
        }
        haShiQi.getFood().setName("沙发");
        haShiQi.setFood(food);
        System.out.println(haShiQi.getFood().getName());

    }
}
