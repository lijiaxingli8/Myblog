package com.coolding.blog.service;

import com.coolding.blog.dao.UserRepository;
import com.coolding.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author 酷酷的丁
 * @Date 2020-03-24 18:12
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
     private UserRepository userRepository;

    @Override
    public User CheckUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

    @Override
    public List<User> viewShow() {
        List<User> all = userRepository.findAll();
        return all;
    }

    @Override
    @Transactional
    public void addView() {
        userRepository.addView();
    }
}
