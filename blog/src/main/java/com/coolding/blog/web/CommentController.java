package com.coolding.blog.web;

import com.coolding.blog.po.Comment;
import com.coolding.blog.service.BlogService;
import com.coolding.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName CommentController
 * @Author 酷酷的丁
 * @Date 2020-04-10 11:21
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        System.out.println("asd");
        return "blogArticle";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.queryBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        System.out.println(111);
        return "redirect:/comments/" + blogId;
    }
}
