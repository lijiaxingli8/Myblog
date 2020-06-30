package com.coolding.blog.service;

import com.coolding.blog.dao.TagRepository;
import com.coolding.blog.exception.NotFoundException;
import com.coolding.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Author 酷酷的丁
 * @Date 2020-03-28 15:07
 */
@Service
public class TagServiceImpl implements  TagService{
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTags(Tag tag) {
        Tag saveTag  = tagRepository.save(tag);
        return saveTag;
    }

    @Transactional
    @Override
    public Tag queryTag(Long id) {
        Tag resultByID = tagRepository.findById(id).get();
        return resultByID;
    }

    @Transactional
    @Override
    public Tag queryTagByName(String name) {
        Tag ResultByName = tagRepository.findByName(name);
        return ResultByName;
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        Page<Tag> listTag = tagRepository.findAll(pageable);
        return listTag;
    }

    @Override
    public List<Tag> listTag() {
        List<Tag> all = tagRepository.findAll();
        return all;
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(converToList(ids));
    }
    private List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i = 0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag, Long id) {
        Tag tag1 = tagRepository.findById(id).get();
        if(tag1 == null){
            throw new NotFoundException();
        }
        BeanUtils.copyProperties(tag,tag1);
        Tag updateResult = tagRepository.save(tag1);
        return updateResult;
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
