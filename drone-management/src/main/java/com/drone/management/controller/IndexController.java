package com.drone.management.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 首页 / 登录控制器
 */
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/login")
    public String loginPage() {
        // 已登录则直接跳转到列表页
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/drone/list";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();

        // 已登录先退出
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            log.info("用户登录成功: {}", username);
            return "redirect:/drone/list";
        } catch (UnknownAccountException e) {
            redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/login";
        } catch (IncorrectCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "密码错误");
            return "redirect:/login";
        } catch (LockedAccountException e) {
            redirectAttributes.addFlashAttribute("error", "账号已被锁定");
            return "redirect:/login";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("error", "登录失败: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("username",
                SecurityUtils.getSubject().getPrincipal());
        return "index";
    }
}
