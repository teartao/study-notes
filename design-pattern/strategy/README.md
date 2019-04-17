[英文原文链接](https://java-design-patterns.com/patterns/strategy/)

[策略模式样例代码](https://github.com/iluwatar/java-design-patterns/tree/master/strategy)

# Strategy 策略模式
 * difficulty-beginner   难度-初学者
 * gang of four    四人帮
 * java java
 * behavioral 行为性的

## as known as 又名
Policy 政策模式

## Intent 意图
Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.
定义一系列算法，封装每个算法，并使它们可互换。策略允许算法独立于使用它的客户端。

## Applicability 适用场景

Use the Strategy pattern when 

- many related classes differ only in their behavior. Strategies provide a way to configure a class either one of many behaviors
- 许多相关类只在他们的行为上有所不同。策略提供了一种方法，它可以将类配置为多种行为之一
- you need different variants of an algorithm. for example, you might define algorithms reflecting different space/time trade-offs. Strategies can be used when these variants are implemented as a class hierarchy of algorithms
- 你需要不同的算法变种。例如，您可以定义反映不同空间/时间权衡的算法。当这些变种算法以类的级别结构实现时，可以使用策略
- an algorithm uses data that clients shouldn't know about. Use the Strategy pattern to avoid exposing complex, algorithm-specific data structures
- 算法使用客户端不应该知道的数据。使用策略模式可避免暴露复杂的特定于算法的数据结构
- a class defines many behaviors, and these appear as multiple conditional statements in its operations. Instead of many conditionals, move related conditional branches into their own Strategy class
- 一个类定义了许多行为，这些行为在其操作中显示为多个条件语句，而不是很多条件，(这种情况下可以)将相关的条件分支移动到自己的Strategy(策略)类中

## Tutorial 教程、样例

- [Strategy Pattern Tutorial](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## Credits 参考文献
- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
- [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/ref=sr_1_1)


## 伪代码
```java
/**
* 这段是接口功能/算法的抽象，每种算法在一个接口里面(接口的一种实现为算法A，接口的另一种实现为算法B)，
* 它们共同实现（implements）了一个接口 （算法接口）
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
* 通过创建[算法接口]对象的实例"算法"变量(该变量可通过 [set算法] 方法改变)来调用[算法接口]中算法
* (算法已经被封装到了它的各种子类实现(算法A 算法B)中)
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
    
    调用接口.给外部对象调用的主要功能入口();//我是第 1 种算法(算法A)
    
    //换个算法
    调用接口.set算法(new 算法B());
    调用接口.给外部对象调用的主要功能入口();// 我是第 2 种算法(算法B)
}

```
