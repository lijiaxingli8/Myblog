package com.coolding.blog.service;

import com.coolding.blog.po.Blog;
import com.coolding.blog.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    // save blog
    Blog saveBlog(Blog blog);

    Blog getAndConvert(Long id);
    // query blog by id
    Blog queryBlog(Long id);
    // index page's blogs
    List<Blog> listRecommendBlogTop(Integer size);

    // list blog
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);
    /*查询*/
    Page<Blog> listBlog(String query,Pageable pageable);
    // insert a new info to blog
    Blog addBlog(Blog blog);
    // update blog
    Blog updateBlog(Long id,Blog blog);
    //delete blog
    void deleteBlog(Long id);

    Long countBlog();

    void addView(Long id);


    /*归档展示*/
    Map<String,List<Blog>> archiveBlog();
}
