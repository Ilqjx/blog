package com.upfly.dao;

import com.upfly.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {

    public Type findByName(String name);

}
