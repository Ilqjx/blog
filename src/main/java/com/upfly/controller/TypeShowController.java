package com.upfly.controller;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.po.Type;
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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) {
        List<Type> typeList = typeService.listTypeTop(1000);
        if (id == -1) {
            id = typeList.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page", page);
        model.addAttribute("types", typeList);
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
