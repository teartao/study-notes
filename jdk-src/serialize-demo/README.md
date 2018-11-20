# example of Serialize an object

## 自定义序列化、反序列化
- jdk 序列化
- fastjson 序列化
- xml 序列化

### serialVersionUID
两次序列化的UID 不一致将抛出InvalidClassException

### transient
加了该关键字的字段不被序列化

### 序列化两次 
对序列化对象再次序列化，则将会把序列化对象字节长度+5，其长为指针长度，指向前一次序列化所生对象

### 序列化Clone对象

- 深克隆(深拷贝)
- 浅克隆(浅拷贝)
- 设计模式之原型模式

## 