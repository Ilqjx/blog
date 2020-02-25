package com.upfly.controller.admin;

import com.upfly.po.Blog;
import com.upfly.service.BlogService;
import com.upfly.service.TypeService;
import com.upfly.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page", page);
        model.addAttribute("types", typeService.listType());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page", page);
        // 返回blogs页面下的blogList片段
        return "admin/blogs :: blogList";
    }

}
