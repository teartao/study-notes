[英文原文链接](http://java-design-patterns.com/patterns/singleton/)
[单例模式样例代码](https://github.com/iluwatar/java-design-patterns/tree/master/singleton)

# Singleton 单例模式
 * difficulty-beginner   难度-初学者
 *  gang of four    四人帮
 *  java    java
 * creational 创造性的

## Intent 意图
Ensure a class only has one instance, and provide a global point of access to it.
保证一个类只有一个实例，并提供一个全局访问点以供访问。


## Explanation 解释
Real world example
现实生活中的例子


> There can only be one ivory tower where the wizards study their magic. 
> 只能有一个象牙塔供巫师们学习魔法使用
> The same enchanted ivory tower is always used by the wizards. Ivory tower here is singleton.
> 巫师们使用的象牙塔总是同样(迷人)的那一个，象牙塔在这里就是单例的


# In plain words 简单来说 
> Ensures that only one object of a particular class is ever created.
> 确保特定类的对象始终是已创建的那一个

# Wikipedia says 维基百科中的描述 
> In software engineering, the singleton pattern is a software design pattern that restricts the instantiation of a class to one object. This is useful when exactly one object is needed to coordinate actions across the system.
> 在软件工程中，单例模式是一种用来限制类的实例始终为同一个对象的软件设计模式。当需要一个对象来协调整个系统的行为时，这是非常有用的。

#  Programmatic Example 程序实例

Joshua Bloch, Effective Java 2nd Edition , p.18
<作者名>，<书名>，<页码>

> A single-element enum type is the best way to implement a singleton
> 单元素枚举类型是实现单例模式的最佳方式。

```java
public enum EnumIvoryTower {
	INSTANCE;
}
```

Then in order to use
然后试着使用一下

```java
EnumIvoryTower enumIvoryTower1 = EnumIvoryTower.INSTANCE;
EnumIvoryTower enumIvoryTower2 = EnumIvoryTower.INSTANCE;
assertEquals(enumIvoryTower1, enumIvoryTower2); // true
```

## Applicability 适用场景
Use the Singleton pattern when
当以下情况时使用单例模式

* there must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* 必须有一个类的一个实例，并且必须从一个众所周知的接入点访问客户端。
* when the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code
* 当通过子类化惟一实例应该是可扩展的，并且客户端应该能够在不修改其代码的情况下使用扩展实例时

## Typical Use Case 典型用例

* the logging class 登录(功能)类
* managing a connection to a database 管理与数据库的连接
* file manager 文件管理器

## Real world examples 现实生活中的例子


* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Consequences 使用后果/影响

* Violates Single Responsibility Principle (SRP) by controlling their own creation and lifecycle.
*  通过控制自己的创造和生命周期，违反了单一责任原则（SRP）
* Encourages using a global shared instance which prevents an object and resources used by this object from being deallocated.
* 鼓励使用全局共享实例，防止该对象使用的对象和资源被解除分配。

* Creates tightly coupled code. The clients of the Singleton become difficult to test.
* 创建紧密耦合的代码。单例模式的的客户端变得难以测试。
* Makes it almost impossible to subclass a Singleton.

## Credits 参考文献

* [Design Patterns: Elements of Reusable Object-Oriented Software](http://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612)
* [Effective Java (2nd Edition)](http://www.amazon.com/Effective-Java-Edition-Joshua-Bloch/dp/0321356683)

# 笔者简述
通过意图（Intent）可以发现，只要满足“单实例、统一访问入口”即为单例模式，因此可以联想工作中的一些场景，如：

 - 工具类 ：方法均为静态方法(static)，且必须通过【类名.方法()】来调用
 - 数据源：单项目中(分布式除外)全局只配置一个数据源，且提供统一的数据源连接池为整个系统提供数据库访问支持。

##思考
那么，如何保证只有单实例？如何使访问入口只有一个？
通过上文发现java中通过private实现构造私有。并在统一的地方(系统初始化时)提供一个访问入口，由于系统只会启动一次，因此可以保证访问入口只有一个。

这两个方式都有局限性，因此单例不是绝对的，当Utils工具类的私有构造并没有按常理写为private，而是写成public等开放较高的权限时，便可以被多次实例化，从定义上讲，它就不是单例了。
进一步来讲，即使工具类设置了构造方法私有，当遇到多线程的情况，或者通过反射，将构造方法setAccessible(true)，那么它同样会破坏原有的单例模式设计。
由于反序列化时会创建一个新的对象，因此反序列化也会破坏单例。

另一方面，如果在系统启动时由于某个多线程任务(等情况)的执行，使数据源的实例产生了多个，那么它也就不是单例了(也可以理解为局部单例，对于每个线程的数据源来说，它们仍然是单例的)。

## 防止单例被破坏
虽然单例会被破坏，但也不是没有解决方法，常见的是通过加锁校验的方式、或者通过枚举来解决反射的破坏。在反序列化时指定实例来避免再次实例化。
详情可以参考其它更多资料，本文不作深入探讨。

[反射/序列化/克隆对单例模式的破坏](https://blog.csdn.net/chao_19/article/details/51112962)
