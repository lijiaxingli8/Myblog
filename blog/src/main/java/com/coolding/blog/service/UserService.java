package com.coolding.blog.service;

import com.coolding.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService{
    User CheckUser(String username,String password);

    List<User>  viewShow();

    void addView();
}
