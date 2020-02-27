package com.upfly.service.impl;

import java.util.List;
import java.util.Optional;

import com.upfly.dao.TypeRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.po.Type;
import com.upfly.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        Optional<Type> typeOptional = typeRepository.findById(id);
        try {
            typeOptional.get();
        } catch (Exception e) {
            return null;
        }
        return typeOptional.get();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Type updateType(Long id, Type type) {
        Type tempType = getType(id);
        if (tempType == null) {
            throw new NotFoundException("该分类不存在");
        }
        BeanUtils.copyProperties(type, tempType);
        return typeRepository.save(tempType);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.delete(getType(id));
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

}
