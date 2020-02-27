package com.upfly.dao;

import java.util.List;

import com.upfly.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public Tag findByName(String name);

    @Query("SELECT t FROM Tag t")
    public List<Tag> findTop(Pageable pageable);

}
