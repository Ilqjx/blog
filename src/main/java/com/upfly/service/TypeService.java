package com.upfly.service;

import java.util.List;

import com.upfly.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeService {

    public Type saveType(Type type);

    public Type getType(Long id);

    public Type getTypeByName(String name);

    public Type updateType(Long id, Type type);

    public void deleteType(Long id);

    public List<Type> listType();

    public Page<Type> listType(Pageable pageable);

}
