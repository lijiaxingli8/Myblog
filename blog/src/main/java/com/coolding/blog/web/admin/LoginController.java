package com.coolding.blog.web.admin;

import com.coolding.blog.po.User;
import com.coolding.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Author 酷酷的丁
 * @Date 2020-03-24 17:16
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

     @Autowired
     private UserService userService;
    //进入登陆页面
    @GetMapping
    public String LoginPage() {
        return "admin/Login";
    }

 //登陆判断
    @PostMapping("/login")
    public String DoLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes attributes) {

        User user = userService.CheckUser(username,password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "/admin/index";
        }
        else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String LogOUt(){
        return "redirect:/";
    }


}
