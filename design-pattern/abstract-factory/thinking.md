# thinking in abstract factory 关于工厂模式的思考

## 传统方式

当需要某对象时，通过new关键字创建指定对象
```java
public static void main(String[] args) {
    new Nokia().getModel();
    new Samsung().getModel();
    new IPhone().getModel();
}
```
当对象的实例化比较复杂时，每次调用对象都需要进行实例化，这显然会增加大量重复代码，可能像下面这样：

```java
public void getWhiteNokia(){
    Nokia nokia = new Nokia();
    nokia.setColor("white");
    nokia.setWidth("60mm");
    nokia.setHeight("110mm");
    // ... too many other information 
}

public void getBlackNokia(){
    IPhone iphone = new IPhone();
    iphone.setColor("black");
    iphone.setWidth("70mm");
    iphone.setHeight("20mm");
    // ... too many other information 
}
```



这种方式显然不是我们想看到的，使用工厂模式将实例化代码放入工厂中就显得有必要了。我们只需将大量重复的、类似的代码放在工厂中，便不再需要考虑实例化过程了



// todo