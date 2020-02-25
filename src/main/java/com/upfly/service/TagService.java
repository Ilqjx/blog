package com.upfly.service;

import com.upfly.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    public Tag saveTag(Tag tag);

    public Tag getTag(Long id);

    public Tag getTagByName(String name);

    public Tag updateTag(Long id, Tag tag);

    public void deleteTag(Long id);

    public Page<Tag> listTag(Pageable pageable);

}
