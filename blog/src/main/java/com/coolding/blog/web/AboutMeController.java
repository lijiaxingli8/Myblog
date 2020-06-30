package com.coolding.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName AboutMeController
 * @Author 酷酷的丁
 * @Date 2020-04-16 19:45
 */
@Controller
public class AboutMeController {
    @GetMapping("/about")
    public String aboutMe() {
        return "aboutMe";
    }
}
