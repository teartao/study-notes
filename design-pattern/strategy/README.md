[英文原文链接](https://java-design-patterns.com/patterns/strategy/)

[单例模式样例代码](https://github.com/iluwatar/java-design-patterns/tree/master/strategy)

# Strategy 策略模式
 * difficulty-beginner   难度-初学者
 *  gang of four    四人帮
 *  java    java
 * creational 创造性的

## Intent 意图


## Explanation 解释

## Real world example 现实生活中的例子



# In plain words 简单来说 

# Wikipedia says 维基百科中的描述 

#  Programmatic Example 程序实例

## Applicability 适用场景

## Typical Use Case 典型用例

## Real world examples 现实生活中的例子


## Consequences 使用后果/影响

## Credits 参考文献

# 笔者简述

## 思考



```java
/**
* 这段是接口功能/算法的抽象，每种算法在一个接口里面(接口A的一种实现 接口A的另一种实现)，
* 他们共同实现（implements）了一个接口 （算法接口）
*/
public interface 算法接口{
    void 执行算法();
}

public class 算法A implements 算法接口{
    void 执行算法(){
        System.out.println("我是第 1 种算法");
    }
}

public class 算法B implements 算法接口{
    void 执行算法(){
        System.out.println("我是第 2 种算法");
    }
}

/**
* 这段是对算法接口的调用，并以接口的形式展现，
* 通过定义接口对象a(可通过 [set接口对象] 方法改变)来调用【业务中可能变化的功能接口A】中算法
* (算法已经被封装到了它的各种子类实现中)
*/
public abstract class 算法调用接口{
    算法接口  算法;
    
    void set算法(算法接口 算法实例);
    
    void 给外部对象调用的主要功能入口(){
        算法.执行算法();
    }
}

public class 算法调用接口实例A extends 算法调用接口 {
    void set算法(算法接口 算法实例){
        super.算法 = new 算法A();
    }
}


/**
* 策略模式调用实例
* @param args
*/
public static void main(String[] args){
    算法调用接口  调用接口 = new 算法调用接口实例A();
    
    调用接口.给外部对象调用的主要功能入口();//我是第 1 种算法
    
    //换个算法
    调用接口.set算法(new 算法B());
    调用接口.给外部对象调用的主要功能入口();// 我是第 2 种算法
}

```
