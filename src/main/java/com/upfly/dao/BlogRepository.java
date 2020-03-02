package com.upfly.dao;

import java.util.List;

import com.upfly.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("SELECT b FROM Blog b WHERE b.recommend = true")
    public List<Blog> findTop(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.title LIKE :query OR b.content like :query")
    public Page<Blog> findByQuery(@Param("query") String query, Pageable pageable);

    @Modifying
    @Query("UPDATE Blog b SET b.views = b.views + 1 WHERE b.id = :id")
    public int updateViews(@Param("id") Long id);

    @Query("SELECT function('DATE_FORMAT', b.updateTime, '%Y') AS year FROM Blog b GROUP BY function('DATE_FORMAT', b.updateTime, '%Y') ORDER BY year DESC")
    public List<String> findGroupByYear();

    @Query("SELECT b FROM Blog b WHERE function('DATE_FORMAT', b.updateTime, '%Y') = :year")
    public List<Blog> findByYear(@Param("year") String year);

}
