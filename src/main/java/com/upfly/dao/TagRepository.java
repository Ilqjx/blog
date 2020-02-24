package com.upfly.dao;

import com.upfly.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public Tag findByName(String name);

}
