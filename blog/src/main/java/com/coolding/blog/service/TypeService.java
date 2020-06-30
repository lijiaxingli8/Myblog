package com.coolding.blog.service;

import com.coolding.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //添加type
    Type saveType(Type type);
    //查询type
    Type queryType(Long id);
    //根据名字查
    Type queryTypeByName(String name);
    //分页查询
    Page<Type> listType(Pageable pageable);
    //
    List<Type> listType();

    List<Type> listTypeTop(Integer size);
    //更改type
    Type updateType(Long id,Type type);
    //删除type
    void deleteType(Long id);


}
