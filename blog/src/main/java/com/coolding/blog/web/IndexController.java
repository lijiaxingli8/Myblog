package com.coolding.blog.web;

import com.coolding.blog.service.BlogService;
import com.coolding.blog.service.TypeService;
import com.coolding.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName IndexController
 * @Author 酷酷的丁
 * @Date 2020-04-03 10:29
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String indexPage(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                            Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(6));
        model.addAttribute("types",typeService.listTypeTop(6));
        userService.addView();
        model.addAttribute("views",userService.viewShow());
        return "index";
    }
  //*文章详情*//*
    @GetMapping("/blog/{id}")
    public String detailPage(@PathVariable Long id,Model model) {
        model.addAttribute("blog",blogService.getAndConvert(id));
        blogService.addView(id);
        return "blogArticle";
    }


    /*全部文章*/
    @GetMapping("/blogs")
    public String allBlogs(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        return "article";
    }

    /*查询*/
    @PostMapping("/search")
    public String Search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query , Model model) {
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "article";
    }
}
