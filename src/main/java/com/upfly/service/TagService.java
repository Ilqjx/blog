package com.upfly.service;

import java.util.List;

import com.upfly.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    public Tag saveTag(Tag tag);

    public Tag getTag(Long id);

    public Tag getTagByName(String name);

    public Tag updateTag(Long id, Tag tag);

    public void deleteTag(Long id);

    public List<Tag> listTag();

    public List<Tag> listTag(String ids);

    public List<Tag> listTagTop(Integer size);

    public Page<Tag> listTag(Pageable pageable);

}
