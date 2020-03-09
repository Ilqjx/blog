package com.upfly.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.UUID;

import com.upfly.po.User;
import com.upfly.service.UserService;
import com.upfly.util.AES256EncryptionUtil;
import com.upfly.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(Model model) {
        UUID uuid = UUID.randomUUID();
        UUIDUtil.setUuid(uuid);
        String key = uuid.toString().substring(0, 16);
        model.addAttribute("key", key);
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, RedirectAttributes attributes) {
        String key = UUIDUtil.getUuid().toString().substring(0, 16);
        String pwd = AES256EncryptionUtil.decryptStr(key, password);

        if (pwd == null) {
            return "redirect:/admin";
        }

        User user = userService.checkUser(username, pwd);
        if (user != null) {
            user.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "admin/index";

        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
