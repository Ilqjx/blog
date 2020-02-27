package com.upfly.dao;

import java.util.List;

import com.upfly.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeRepository extends JpaRepository<Type, Long> {

    public Type findByName(String name);

    @Query("SELECT t FROM Type t")
    public List<Type> findTop(Pageable pageable);

}
