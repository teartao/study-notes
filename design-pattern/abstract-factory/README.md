[英文原文链接](http://java-design-patterns.com/patterns/factory-method/)
[单例模式样例代码](https://github.com/iluwatar/java-design-patterns/tree/master/factory-method)
# Factory Method 工厂方法
* difficulty-beginner   难度-初学者
*  gang of four    四人帮
*  java    java
* creational 创造型

# Also known as 别名
Virtual Constructor  虚拟构造器

# Intent 意图
Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

定义一个用于创建对象的接口，但让子类决定实例化哪个类。工厂方法允许类的实例化方法到子类中进行。

# Explanation 解释
Real world example
现实生活中的例子

>Blacksmith manufactures weapons. Elves require Elvish weapons and orcs require Orcish weapons. Depending on the customer at hand the right type of blacksmith is summoned.
> 铁匠制造武器。精灵需要精灵武器，兽人需要兽人武器。根据(手头)顾客(的不同类型)，铁匠给予不同的(武器)类型。

# In plain words 简单来说 
> It provides a way to delegate the instantiation logic to child classes.
> 它提供了一种将实例化逻辑委托给子类的方式。

#Wikipedia says 维基百科中的描述
>In class-based programming, the factory method pattern is a creational pattern that uses factory methods to deal with the problem of creating objects without having to specify the exact class of the object that will be created. This is done by creating objects by calling a factory method—either specified in an interface and implemented by child classes, or implemented in a base class and optionally overridden by derived classes—rather than by calling a constructor.
>在基于类的编程中，工厂方法模式是一种创造性模式，它使用工厂方法来处理创建对象的问题，而无需指定将要创建的对象的确切类。通过调用工厂方法(在接口中指定并由子类实现，或在基类中实现并可由派生类覆盖)来创建对象，而不是通过调用构造函数(来创建对象)。
> 上面这段长句不好理解，我给断开分成短句，英语好的自行忽略：
> 在基于类的编程中

#Programmatic Example 程序实例
以我们的铁匠例子为例。首先，我们有一个铁匠接口和一些实现方法。
```java
public interface Blacksmith {
  Weapon manufactureWeapon(WeaponType weaponType);
}

public class ElfBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return new ElfWeapon(weaponType);
  }
}

public class OrcBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return new OrcWeapon(weaponType);
  }
}
```
Now as the customers come the correct type of blacksmith is summoned and requested weapons are manufactured
现在，随着客户要求的不同，将召唤不同类型的铁匠来制造指定类型的武器。
```java
Blacksmith blacksmith = new ElfBlacksmith();
blacksmith.manufactureWeapon(WeaponType.SPEAR);
blacksmith.manufactureWeapon(WeaponType.AXE);
// Elvish weapons are created
```
#Applicability 适用场景
Use the Factory Method pattern when 

 - a class can't anticipate the class of objects it must create
 - a class wants its subclasses to specify the objects it creates
 - classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate

当遇到下列场景时使用工厂方法模式

 - 一个类不能预知它所需创建的对象类型
 - 一个类想要让它的子类来确定所需创建的对象
 - 类将责任委托给几个helper子类中的一个，您希望将所委托helper子类的信息本地化。

#Presentations 演示文稿
[Factory Method Pattern](http://java-design-patterns.com/patterns/factory-method/etc/presentation.html)
该文稿内容和本文大体一致，仅多了Diagram部分内容，详情如下

##Diagram 图解
![Factory Method](http://java-design-patterns.com/patterns/factory-method/etc/diagram1.png)

#Real world examples 现有样例

 - [java.util.Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--) 
 - [java.util.ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-) 
 - [java.text.NumberFormat](https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
 - [java.nio.charset.Charset](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)     
 - [java.net.URLStreamHandlerFactory](https://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
 - [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
 - [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)

#Credits 参考文献
Design Patterns: Elements of Reusable Object-Oriented Software

# 笔者简述
通过意图(Intent)发现，工厂方法的满足条件为“父类提供规范，让子类进行实例化”。

## 为什么要使用工厂方法模式

// todo 

虽然啰嗦了一点，但是把抽象类都给串起来了，有没有好理解一点？

# 参考
[ 深入浅出设计模式——工厂方法模式（Factory Method） ](https://www.cnblogs.com/Bobby0322/p/4179921.html)



------

# Thinking （关于工厂模式的思考）

## 概述
用户想要得到手机经历了以下几个过程：

1. 自己生产手机（new创建手机）
2. 手机结构复杂后：工厂购买手机（简单工厂：作坊生产各种手机）
3. 各种品牌手机崛起：各厂商生产各自的手机（工厂方法：每个厂商生产一种手机）
4. 厂商太多后，统一规范：商场购买手机(抽象工厂:商场不是工厂却能为你提供手机，统一获取途径)

总结：
- 简单工厂是为了简化手机制造过程，避免每次使用时“重新生产”；
- 工厂方法是为了统一作坊式的生产，标准化生产各种手机，避免了作坊不断“根据需求定制”；
- 抽象工厂是为了对工厂进行抽象，避免了过于复杂的“厂商抉择”；


## 传统方式

当需要某对象时，通过new关键字创建指定对象
```java
public static void main(String[] args) {
    new Nokia().getModel();
    new Samsung().getModel();
    new IPhone().getModel();
}
```
当对象的实例化比较复杂时，每次调用对象都需要通过'new'手动进行实例化，这显然会增加大量重复代码，可能像下面这样：

```java
public void getNokia(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
    nokia.getModel();
}

public void getNokiaAgain(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
    nokia.getModel();
}
```

这种方式显然不是我们想看到的，否则一旦手机参数变更了，那么我们可能不止要修改2份相同/类似的实例化对象代码。这时使用工厂模式将实例化代码放入工厂中就显得有必要了。我们只需将大量重复的、类似的代码放在工厂中，便不再需要考虑实例化过程了。

假如我们把实例化代码放到构造函数中呢？这样会导致构造函数过于复杂，它可能要顾及这个场景的实例化，还得顾及另一个场景的实例化，这样设计显然也不是那么友好。

基于这种场景，简单工厂就诞生了。

SimpleFactory:

```java
public Nokia getBaseNokia(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
}

public void getNokia(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}
public void getNokiaAgain(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}
```

这时候，我们的手机已经由Nokia升级为了Andriod系统，不再用Symbian了，它多了很多功能，比如触控，比如切水果，如果我们想修改对象的功能，那么我们就要在上面的getBaseNokia里面修改了。

```java
public Nokia getBaseNokia(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
    nokia.setTouch(true);//我们有触控功能了
}

public void getNokia(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}
public void getNokiaAgain(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}
```

但是，还有很多农村小伙子，他们依然在用Symbian，他们并不会用触控，这样一改，大家就都要把手机返厂去安装触控屏幕吗？这显然不行。最好的方法是不动它(getBaseNokia)。

这时，我们的Factory有了两个产品，一种是旧版Symbian的BaseNokia，还有一种是新版的SmartNokia，所以Factory多了一个方法getSmartNokia:

```java
/**
 * Factory
 */
public Nokia getBaseNokia(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
}

// smart Nokia
public Nokia getSmartNokia(){
    nokia.setTouch(true);//我们有触控功能了
    nokia.setOperationSystem('Android');
    // ... other new feature
}

public void getNokia(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}
public void getNokiaAgain(){
    Nokia nokia = getBaseNokia();
    nokia.getModel();
}

public void getLatestNokia(){
    Nokia nokia = getSmartNokia();
    nokia.getModel();
}
```


// to be continued
