package com.coolding.blog.web;

import com.coolding.blog.service.BlogService;
import com.coolding.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName TimeShowController
 * @Author 酷酷的丁
 * @Date 2020-04-16 17:40
 */
@Controller
public class TimeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/time")
    public String showPage(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("views",userService.viewShow());
        return "time";
    }
}
