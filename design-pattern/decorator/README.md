[英文原文链接](https://java-design-patterns.com/patterns/decorator/)

[装饰器模式样例代码](https://github.com/iluwatar/java-design-patterns/tree/master/decorator)

# Decorator 装饰器模式
 * difficulty-beginner   难度-初学者
 * gang of four    四人帮
 * java java
 * Structural 行为性的

## as known as 又名
Wrapper 包装器模式

## Intent 意图
Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

## Explanation

Real world example

> There is an angry troll living in the nearby hills. Usually it goes bare handed but sometimes it has a weapon. To arm the troll it's not necessary to create a new troll but to decorate it dynamically with a suitable weapon.

In plain words

> Decorator pattern lets you dynamically change the behavior of an object at run time by wrapping them in an object of a decorator class.

Wikipedia says

> In object-oriented programming, the decorator pattern is a design pattern that allows behavior to be added to an individual object, either statically or dynamically, without affecting the behavior of other objects from the same class. The decorator pattern is often useful for adhering to the Single Responsibility Principle, as it allows functionality to be divided between classes with unique areas of concern.

**Programmatic Example**

Let's take the troll example. First of all we have a simple troll implementing the troll interface

```java
public interface Troll {
  void attack();
  int getAttackPower();
  void fleeBattle();
}

public class SimpleTroll implements Troll {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTroll.class);

  @Override
  public void attack() {
    LOGGER.info("The troll tries to grab you!");
  }

  @Override
  public int getAttackPower() {
    return 10;
  }

  @Override
  public void fleeBattle() {
    LOGGER.info("The troll shrieks in horror and runs away!");
  }
}
```

Next we want to add club for the troll. We can do it dynamically by using a decorator

```java
public class ClubbedTroll implements Troll {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClubbedTroll.class);

  private Troll decorated;

  public ClubbedTroll(Troll decorated) {
    this.decorated = decorated;
  }

  @Override
  public void attack() {
    decorated.attack();
    LOGGER.info("The troll swings at you with a club!");
  }

  @Override
  public int getAttackPower() {
    return decorated.getAttackPower() + 10;
  }

  @Override
  public void fleeBattle() {
    decorated.fleeBattle();
  }
}
```

Here's the troll in action

```java
// simple troll
Troll troll = new SimpleTroll();
troll.attack(); // The troll tries to grab you!
troll.fleeBattle(); // The troll shrieks in horror and runs away!

// change the behavior of the simple troll by adding a decorator
Troll clubbedTroll = new ClubbedTroll(troll);
clubbedTroll.attack(); // The troll tries to grab you! The troll swings at you with a club!
clubbedTroll.fleeBattle(); // The troll shrieks in horror and runs away!
```

## Applicability 适用场景

Use Decorator

- To add responsibilities to individual objects dynamically and transparently, that is, without affecting other objects
- For responsibilities that can be withdrawn
- When extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of subclasses to support every combination. Or a class definition may be hidden or otherwise unavailable for subclassing

## Tutorial 教程、样例

- [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## Real world examples
 * [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
    [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) and [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
 * [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
 * [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
 * [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)

## Credits 参考文献
* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](http://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/ref=sr_1_1)
* [J2EE Design Patterns](http://www.amazon.com/J2EE-Design-Patterns-William-Crawford/dp/0596004273/ref=sr_1_2)

## 思考

```java
public interface 旧版接口 {
  void 接口功能1();
  void 接口功能2();
}

public class 旧接口实现 implements 旧版接口 {
    
  @Override
  public void 接口功能1() {
    // 功能1的实现代码
  }
    
  @Override
  public void 接口功能2() {
    // 功能2的实现代码
  }
}
```

使用装饰器/包装器模式后的接口设计（改需求，需要增强、修改部分功能）

```java
public class 新的增强的接口实现 implements 旧版接口 {

  private 旧版接口 旧版接口实例;//比如 上面的 "旧接口实现.class"

  public 包装旧版接口(旧版接口 旧版接口实例) {
    this.旧版接口实例 = 旧版接口实例;
  }

  @Override
  public void 接口功能1() {
      //增强了旧版功能1的新版实现
  }

  @Override
  public void 接口功能2() {
      //增强了旧版功能2的新版实现
  }
}
```

增强后的调用方式

```java
//想要用原功能写法仍然不变
旧版接口 旧实例 = new 旧接口实现();
旧实例.接口功能1(); // 功能1还是旧版代码的实现方式
旧实例.接口功能2(); // 功能2也还是旧版代码的实现方式

// 如果旧代码不在满足条件，可以用新的代码实现
旧版接口 新接口的实例 = new 新的增强的接口实现(旧实例);// 创建新接口时，传入上面声明的"旧实例"变量
新接口的实例.接口功能1(); // 这种方式就能调用到增强后的功能1
新接口的实例.接口功能2(); // 这种方式可以调用到增强后的功能2

```

如果不想再使用或者不再建议使用旧接口，可以打上```@Deprecated```，这样既不会影响旧代码，新的接口中也依然可以使用旧的代码