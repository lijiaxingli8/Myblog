package com.coolding.blog.service;

import com.coolding.blog.dao.TypeRepository;
import com.coolding.blog.exception.NotFoundException;
import com.coolding.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TypeServiceImpl
 * @Author 酷酷的丁
 * @Date 2020-03-25 17:58
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        Type saveResult = typeRepository.save(type);
        return saveResult;
    }

    @Transactional
    @Override
    public Type queryType(Long id) {
        Type queryResult = typeRepository.findById(id).get();
        return queryResult;
    }

    @Override
    public Type queryTypeByName(String name) {
        Type byName = typeRepository.findByName(name);
        return byName;
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        Page<Type> allResult = typeRepository.findAll(pageable);
        return allResult;
    }

    @Override
    public List<Type> listType() {
        List<Type> all = typeRepository.findAll();
        return all;
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
        Pageable pageable =  PageRequest.of(0,size,sort);
        List<Type> top = typeRepository.findTop(pageable);
        return top;
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();
        if(t == null){
           throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
