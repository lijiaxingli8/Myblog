package com.coolding.blog.web.admin;

import com.coolding.blog.po.Blog;
import com.coolding.blog.po.BlogQuery;
import com.coolding.blog.po.User;
import com.coolding.blog.service.BlogService;
import com.coolding.blog.service.TagService;
import com.coolding.blog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName BlogController
 * @Author 酷酷的丁
 * @Date 2020-03-25 12:40
 */
@Controller
@RequestMapping("/admin")

public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String BlogList(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, BlogQuery blog) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String serach(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, BlogQuery blog) {
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: bloglist";
    }

    @GetMapping("/blogs/add")
    public String blog_add(Model model) {
        model.addAttribute("blog",new Blog());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        return "admin/blogs_add";
    }
    
    @PostMapping("/blogs/doAdd")
    public String blogDoAdd(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.queryType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if (blog.getId() == null){
            blog1 = blogService.saveBlog(blog);
        } else {
            blog1 = blogService.updateBlog(blog.getId(),blog);
        }
        if(blog1 == null){
            redirectAttributes.addFlashAttribute("message","操作失败,不能为空或已存在类型");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }

        return "redirect:/admin/blogs";
    }

    /* 修改博客信息 */
    @GetMapping("/blogs/{id}/edit")
    public String blog_update(@PathVariable Long id, Model model) {
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());

        Blog blog =  blogService.queryBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs_edit";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delTag(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

}
