package com.upfly.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.upfly.dao.TagRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.po.Tag;
import com.upfly.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
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
    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag tempTag = getTag(id);
        if (tempTag == null) {
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag, tempTag);
        return tagRepository.save(tempTag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.delete(getTag(id));
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (ids != null && !"".equals(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if(!isExist(id)) {
                    Tag tag = new Tag();
                    tag.setName(id);
                    Tag tempTag = tagRepository.save(tag);
                    id = tempTag.getId().toString();
                }
                list.add(new Long(id));
            }
        }
        return list;
    }

    private boolean isExist(String id) {
        List<Tag> tagList = listTag();
        for (Tag tag : tagList) {
            if (id.equals(tag.getId().toString())) {
                return true;
            }
        }
        return false;
    }

}
