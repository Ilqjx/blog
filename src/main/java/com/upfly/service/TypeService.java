package com.upfly.service;

import com.upfly.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeService {

    public Type saveType(Type type);

    public Type getType(Long id);

    public Type updateType(Long id, Type type);

    public void deleteType(Long id);

    public Page<Type> listType(Pageable pageable);

}
