# jdk源码学习

## 基本数据对象源码

### Object
  - native hashcode 生成算法
  

### String
  - hashCode 方法算法
```java
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;

        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];
        }
        hash = h;
    }
    return h;
}
```
  - hashCode 生成算法


## 常见集合源码

- HashMap

- ArrayList


