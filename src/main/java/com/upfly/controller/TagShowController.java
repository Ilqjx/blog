package com.upfly.controller;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.po.Tag;
import com.upfly.service.BlogService;
import com.upfly.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        List<Tag> tagList = tagService.listTagTop(1000);
        if (id == -1) {
            id = tagList.get(0).getId();
        }
        Page<Blog> page = blogService.listBlog(id, pageable);
        model.addAttribute("page", page);
        model.addAttribute("tags", tagList);
        model.addAttribute("activeTagId", id);
        return "tags";
    }

}
