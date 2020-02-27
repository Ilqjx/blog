package com.upfly.controller;

import com.upfly.po.Blog;
import com.upfly.service.BlogService;
import com.upfly.service.TagService;
import com.upfly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Blog> page = blogService.listBlog(pageable);
        model.addAttribute("page", page);
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "admin/blogs-input";
    }

}
