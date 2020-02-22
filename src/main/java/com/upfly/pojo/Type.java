package com.upfly.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id; // id
    @NotBlank(message = "分类名称不能为空")
    private String name; // 分类名

    @OneToMany(mappedBy = "type") // one 是关系被维护端(被动维护Type和Blog之间的关系)
    private List<Blog> blogList = new ArrayList<>();

    public Type() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
