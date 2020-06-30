package com.coolding.blog.dao;

import com.coolding.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @ClassName UserRepository
 * @Author 酷酷的丁
 * @Date 2020-03-24 17:10
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

    @Modifying
    @Query("update t_user u set u.views = u.views + 1 where u.id = 1")
    void addView();
}
