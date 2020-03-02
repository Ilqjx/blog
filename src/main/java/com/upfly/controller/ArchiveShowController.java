package com.upfly.controller;

import java.util.List;
import java.util.Map;

import com.upfly.po.Blog;
import com.upfly.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        Map<String, List<Blog>> archiveMap = blogService.listArchiveBlog();
        Long blogCount = blogService.countBlog();
        model.addAttribute("archiveMap", archiveMap);
        model.addAttribute("blogCount", blogCount);
        return "archives";
    }

}
