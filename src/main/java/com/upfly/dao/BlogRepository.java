package com.upfly.dao;

import java.util.List;

import com.upfly.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("SELECT b FROM Blog b WHERE b.recommend = true")
    public List<Blog> findTop(Pageable pageable);

}
