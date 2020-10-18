package com.neotao.rabbitmq.samples.ch03;

import com.neotao.rabbitmq.samples.ch03.producer.Producer01;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 容器启动程序，类似于项目中的tomcat
 * xml demo生产者测试
 *
 * @author neotao
 */
public class ApplicationCh03XmlTest {

    private static ApplicationContext context = null;

    public static void sendMessage() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Producer01 producer01 = (Producer01) context.getBean("producer01");
        int k = 20;
        while (k > 0) {
            producer01.sendMessage("01生产者。第" + k + "次发送的消息");
            k--;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        sendMessage();
    }
}
