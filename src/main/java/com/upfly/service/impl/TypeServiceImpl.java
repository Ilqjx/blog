package com.upfly.service.impl;

import com.upfly.dao.TypeRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.pojo.Type;
import com.upfly.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type tempType = typeRepository.getOne(id);
        if (tempType == null) {
            throw new NotFoundException("该分类不存在");
        }
        BeanUtils.copyProperties(type, tempType);
        return typeRepository.save(tempType);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeRepository.delete(typeRepository.getOne(id));
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

}
