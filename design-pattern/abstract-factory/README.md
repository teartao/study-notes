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
