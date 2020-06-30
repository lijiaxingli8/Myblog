package com.coolding.blog.service;

import com.coolding.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    //保存
    Tag saveTags(Tag tag);

    Tag queryTag(Long id);

    Tag queryTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Tag tag,Long id);

    void deleteTag(Long id);


}
