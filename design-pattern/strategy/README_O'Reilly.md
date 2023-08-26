# [O'Reilly]策略模式总结整理



O'Reilly策略模式核心围绕选择+算法来实现，并遵循设计原则，通过继承、实现接口、组合、面向接口编程等方式实现动态可扩展鸭子，学习可以分几个阶段：

1. 领略设计模式核心：选择+算法+动态更换
2. 设计原则实践，体会并理解样例中组合、面向接口编程方式方法在代码中的体现

掌握了这几个要点，基本就可以在实践中灵活运用了，并且可以根据自己需要，判断哪些是否确实会变化，不会变化的部分可以适当精简设计。



## 1. 选择+算法

策略模式首先要将算法进行封装，并提供选择功能，才能算是策略，先通过简单例子来体会下选择和算法

```java
public class Test{
  public static void main(String[] args){
    if(条件1){
      算法1();
    }else if(条件2){
      算法2();
    }
  }
  public void 算法1(){
  }
  public void 算法2(){
  }
}
```

这个样例表达出了在不同情况下选择不同算法(上面这个显然还不算是设计模式)，而策略模式正是想达到这种目的，且为了遵循设计原则，将选择和算法放到了特定类中，并且可以在运行时通过给变量赋值进行动态地改变。

为了进一步体现不同情况下的算法，用下面代码做个简单的场景抽象：

```java
public class Test{
  public static void main(String[] args){
    Student s = new Student();
    Teacher t = new Teacher();
    if(isStudent){
      s.say()
    }else if(isTeacher){
      t.say();
    }
  }
}

public class Student{
  /*这里把算法假想成说话/问候功能*/
  void say(){
    // 暂不考虑实现，因为这不重要
  }
}
public class Teacher{
  void say(){
  }
}
```

这样，在不同场景下，`Student`和`Teacher`虽然是不同类型，但他们已有了各自`算法 say()`

此时，student和teacher和say有明显的耦合绑定关系，为了解耦，我们将say抽取出来

```java
public class Test{
  public static void main(String[] args){
    Greenting stu = new Student();
    Greenting teac = new Student();
    stu.say();
    teac.say()
  }
}
/* 这里有了变化，抽出了接口 */
public interface Greeting{
  void say();
} 
public class Student implements Greeting{
}
public class Teacher implements Greeting{
}
```

此时Student和teacher已经有了共同的算法抽象`Greenting#say`虽然我们还没实现它，但是已经可以通过接口来实现不同人问候不同的内容。但是这样还是将Greeting和Student/teacher强行绑定了，如果改动了Greeting里面的内容(尤其是新增接口的话)Student和Teacher可能就都要改了，还必须得实现新的接口才行。而且这时候的实现都放在Greeting里面，一旦改动可能还会影响到Teacher

所以这时候可以将代码改成如下结构

```java
public class Test{
  public static void main(String[] args){
    Person stu = new Student();
    Person teac = new Student();
    stu.say();
    teac.say()
  }
}
/** 这里有了变化,抽出了接口和抽象类：
 * 抽象类(提取了stu&teac的共性)用于组合行为(Person和say之间的关系)，
 * 接口用于隔离算法(使say的实现不在Person也不在student和teacher中，而是通过接口实例作为变量传入) 
 */
public interface Greetable{
  void greet();
} 
public abstract class Person{
  Greetable greeting;
  void say(){
    greeting.greet()
  }
  void setGreetingWay(Greetable greet);
}

public class Student extends Person {
	public Student(){
    /* 在实现类中初始化默认算法逻辑*/
    Greetable speakChinese = new ChineseGreeting(); // 暂未实现Greetable接口
    setGreetingWay(speakChinese)
  }
}
public class Teacher extends Person{
  public Teacher(){
    Greetable speakEnglish = new EnglishGreeting(); // 暂未实现Greetable接口
    setGreetingWay(speakEnglish)
  }
}
```

这样一来，Person变成了Student和Teacher的抽象，它具有say方法，同时也有Greeting的功能，并且这时候如果想要更换Student的Say方法，只要在student中更换greeting就可以实现不同的say了

```java
public class Test{
  public static void main(String[] args){
    Person stu = new Student();
    Person teac = new Student();
    // 默认用中文问候
    stu.say();
    // 这里有了变化：用法语问候
    Greetable speakFrench = new FrenchGreeting();
    stu.setGreetingWay(speakFrench);
    
    teac.setGreetingWay()
  }
}
```

最后补充下并不重要的算法实现，写个完整的代码样例，仅表达下实现在"包装算法"中的关系，其实现内容本身在教程中并没有意义，因此我们只是简单的打印问候内容

```java
public class StrategyDemo {
    public static void main(String[] args) {
        Person stu = new Student();
        // 默认用中文问候
        stu.say();
        // 用法语问候
        Greetable speakFrench = new FrenchGreeting();
        stu.setGreetingWay(speakFrench);
        stu.say();
        Person teac = new Student();
        teac.say();
    }
}

interface Greetable {
    void greet();
}

abstract class Person {
    Greetable greeting;

    void say() {
        greeting.greet();
    }

    abstract void setGreetingWay(Greetable greet);
}

class Student extends Person {
    public Student() {
        Greetable speakChinese = new ChineseGreeting();
        setGreetingWay(speakChinese);
    }

    @Override
    void setGreetingWay(Greetable greet) {
        this.greeting = greet;
    }
}

class Teacher extends Person {
    public Teacher() {
        Greetable speakEnglish = new EnglishGreeting();
        setGreetingWay(speakEnglish);
    }

    @Override
    void setGreetingWay(Greetable greet) {
        this.greeting = greet;
    }
}

/*这里补充了接口实现*/
class EnglishGreeting implements Greetable {
    @Override
    public void greet() {
        // 可能是控制台问候，也可能是调用群聊机器人api在群聊中发起问候？
        // 是不是怎么样都行？好像怎么做都不太重要
        System.out.println("Hello !");
    }
}

class ChineseGreeting implements Greetable {
    @Override
    public void greet() {
        System.out.println("你好 !");
    }
}

class FrenchGreeting implements Greetable {
    @Override
    public void greet() {
        System.out.println("Bonjour !");
    }
}
```

至此，策略模式大体上已经结束了，不过为了理解单一指责，还需看下下面例子

```java
public interface Greetable{
  void greet();
  /* 如果这里加了个talk 会怎样？是不是违反了单一指责原则？ 为什么？*/
  void talk();
} 
```

如果加了talk接口，意味着当student.set(xxx)更换Greetable实现逻辑时，会同时更换掉talk的逻辑，虽然talk内部可以写成一样的逻辑，这导致我们还需要关注变化可能引发的风险点，这明显不符合单一指责原则，当我们仅想要更换greet逻辑时，带来了talk变更的风险，这肯定不是我们想要的。

因此这里的接口最好设计成单一指责(此处方法仅一个，但不代表只能是1个，如果Greetable确实有多种问候方式，且他们会作为一个整体，同时被替换，那么都在这个接口中定义是没有问题的)



这样最终得出了和可能样例中类似的代码结构，再根据后面的 体会下设计原则



## 2.设计原则实践总结

1. 组合：通过Person中加入Greentable，这种方式组合 来实现student和Greentable的解耦，虽然它们看上去只是转移到了Person中，但是由于Person中没有实质性的`greet`方法因此在修改代码实现时，并不会真正改动到这里，而更换算法真正要改动的是setGreetingWay中Greetable的实例
2. 单一职责：Person仅用于对Student Teacher的行为进行抽象，抽取出say方法
3. 接口隔离原则：Greentable接口只干一件事，让接口足够灵活，需要时implement一下即可，不需要的时候，将greeting保留为空逻辑即可。这样做将Student和Teacher的say包装到了Person#say方法中，同时保留了它们之间的调用关系，且由于greeting是变量，还可以动态赋值来替换，实现了接口隔离
4. 其它原则在这里暂未有明显体现。



## 3.策略模式总结

讲到这里，能不能体会到3个要点：

1. 选择(通过实现类交给用户选择，把变化交给调用方)
2. 包装算法(抽取接口和实现，将接口独立成单一职责)
3. 动态替换(将接口作为成员变量，便于更换实现类)

另外的：接口隔离、组合、单一职责 等原则/做法，能否体会得到？

如果能体会到，并且下次遇到时，尝试用一下，看看自己是否真的理解了。



## 4.练习

1. 对着最终的代码样例，每个接口、抽象类、类的继承实现关系，尝试说出这样设计的原因，和不这样设计的坏处。
2. 在你平时编码中，哪些逻辑是可能变化的？有没有想过把它抽成一个接口，并通过接口实例变量的形式传入来实现灵活的替换？
3. 有没有想过用抽象类来提炼业务对象，并用于组合对象和接口？有没有注意到不能别引入具体的算法？否则组合在后续的变化中将不再灵活，组合也失去了原有的"抽象"作用

如果你能够回答/理解这三个练习的内容，那么恭喜你已经掌握了策略模式，并且你已经掌握了一半的设计原则。
