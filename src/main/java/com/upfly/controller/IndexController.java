package com.upfly.controller;

import java.util.List;

import com.upfly.exception.NotFoundException;
import com.upfly.po.Blog;
import com.upfly.service.BlogService;
import com.upfly.service.TagService;
import com.upfly.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Blog> page = blogService.listBlog(pageable);
        model.addAttribute("page", page);
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        String queryContent = query.trim();
        if ("".equals(queryContent)) {
            return "redirect:/";
        }

        Page<Blog> page = blogService.listBlog("%" + queryContent + "%", pageable);
        model.addAttribute("page", page);
        model.addAttribute("query", queryContent);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable String id, Model model) {
        Long blogId = null;
        try {
            blogId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            logger.info("BlogURL: {}", "/blog/" + id);
            throw new NotFoundException("该博客不存在");
        }

        model.addAttribute("blog", blogService.getAndConvertBlog(blogId));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model) {
        List<Blog> blogList = blogService.listRecommendBlogTop(3);
        model.addAttribute("newBlogs", blogList);
        return "_fragments :: newBlogList";
    }

}
