package com.upfly.service.impl;

import java.util.Optional;

import com.upfly.dao.TagRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.po.Tag;
import com.upfly.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        try {
            tagOptional.get();
        } catch (Exception e) {
            return null;
        }
        return tagOptional.get();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tempTag = getTag(id);
        if (tempTag == null) {
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag, tempTag);
        return tagRepository.save(tempTag);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(getTag(id));
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

}
