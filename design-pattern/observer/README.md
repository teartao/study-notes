```java
/**
 * 订阅（观察者）接口
 */
interface Observer{
    //收到通知后主动更新
    void update();
}

/**
 * 主题（被观察者）接口
 */
interface Subject{
    void addObserver();
    void notify();
}

class Employee implements Observer{
    void update(){
        System.out.println("I'v received notification ！");
    }
}

class Leader implements Subject{
    List<Observer> employeeList = new ArrayList();
    void addObserver(Observer employee){
        employeeList.add(employee);
    }
    void notify(){
        for(int i=0;i<employeeList.size();i++){
            employeeList[i].update();
        }
    } 
}


```

