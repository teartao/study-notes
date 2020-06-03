package com.neotao.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 使用MvcConfig配置，因此这里@Controller注释掉，
 * 如需使用，请注释掉MvcConfig中@Configuration或删除MvcConfig，
 * 并放开此处@Controller即可
 */
// @Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "login";
    }


}
