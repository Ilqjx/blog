package com.upfly.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.UUID;

import com.upfly.po.User;
import com.upfly.service.UserService;
import com.upfly.util.AES256EncryptionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guozhenwei")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(HttpServletRequest request) {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().substring(0, 16);
        HttpSession session = request.getSession();
        session.setAttribute("key", key);
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, RedirectAttributes attributes) {
        HttpSession session = request.getSession();
        String key = (String) session.getAttribute("key");
        if (key == null || "".equals(key)) {
            return "redirect:/guozhenwei";
        }
        String decryptPassword = AES256EncryptionUtil.decryptStr(key, password);
        if (decryptPassword == null) {
            return "redirect:/guozhenwei";
        }

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, decryptPassword);
        try {
            currentUser.login(token);
            User user = userService.getUserForShow(username);
            session.setAttribute("user", user);
            return "admin/index";
        } catch (AuthenticationException e) {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/guozhenwei";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/guozhenwei";
    }

}
