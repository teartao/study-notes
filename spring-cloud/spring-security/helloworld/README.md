# hello world

spring security 官网样例demo

来源：
- [OSC China](http://www.spring4all.com/article/429)
- [spring-security](https://spring.io/guides/gs/securing-web/)

## 项目说明
> 官网是基于 MvcConfig.java配置的，常见的方式还是使用Controller(MainController.java)，两种方法均可。
>(注:为了避免冲突MainController的@Controller注解已被注释，需要时放开注释即可)

demo 演示了 spring security对url的拦截、重定向、登录验证功能
其中拦截/重定向请求 代码位于`com.neotao.springsecurity.config.WebSecurityConfig.configure()`
登录验证代码 `com.neotao.springsecurity.config.WebSecurityConfig.configureGlobal()`

由于需要表单登录，因此还使用了thymeleaf。

springboot会自动读取`resources/templates`
文件夹下html文件作为thymeleaf模板，并可以在controller中以templates为相对根路径进行引用，
如：`return "hello";` 即应用将 `resources/templates/hello.html`页面作为响应体发送给客户端。
(springboot、thymeleaf详细用法请自行学习)

## spring security核心配置
本项目中spring security 核心配置为`com.neotao.springsecurity.config.WebSecurityConfig`
详细说明见注释