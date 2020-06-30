package com.coolding.blog.service;

import com.coolding.blog.config.MyBeanUtils;
import com.coolding.blog.dao.BlogRepository;
import com.coolding.blog.exception.NotFoundException;
import com.coolding.blog.po.Blog;
import com.coolding.blog.po.BlogQuery;
import com.coolding.blog.po.Type;
import com.coolding.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @ClassName BlogServiceImpl
 * @Author 酷酷的丁
 * @Date 2020-03-29 9:20
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        blog.setUpdateTime(new Date());
        Blog save = blogRepository.save(blog);
        return save;
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null){
            throw  new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Blog queryBlog(Long id) {
        Blog blog = blogRepository.findById(id).get();
        return blog;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null ){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),  "%" + blog.getTitle() +"%" ));
                }
                if (blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        Page<Blog> byQuery = blogRepository.findByQuery(query, pageable);
        return byQuery;
    }

    @Override
    public Blog addBlog(Blog blog) {
        Blog save = blogRepository.save(blog);
        return save;
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findById(id).get();
        if(blog1 == null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        Blog updateBlog = blogRepository.save(blog1);
        return updateBlog;
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Long countBlog() {
        long count = blogRepository.count();
        return count;
    }

    @Override
    @Transactional
    public void addView(Long id) {
        blogRepository.addViewById(id);
    }


    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }
}
