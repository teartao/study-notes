package com.neotao.springsecurity.controller;

import com.neotao.springsecurity.config.MvcConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 使用{@link MvcConfig}配置，因此这里 @Controller() 注释掉，
 * 如需使用：可注释掉{@link MvcConfig}中@Configuration 或 删除{@link MvcConfig}
 * 并放开此处@Controller即可
 */
// @Controller
public class MainController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
