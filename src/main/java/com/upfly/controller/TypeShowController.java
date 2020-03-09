package com.upfly.controller;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.po.Type;
import com.upfly.service.BlogService;
import com.upfly.service.TypeService;
import com.upfly.vo.BlogQuery;
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

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable String id, Model model) {
        Long typeId = null;
        try {
            typeId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            logger.info("TypeURL: {}", "/types/" + id);
            typeId = Long.valueOf(-1);
        }

        Type type = typeService.getType(typeId);
        if (type == null) {
            typeId = Long.valueOf(-1);
        }

        List<Type> typeList = typeService.listTypeTop(1000);
        if (typeId == -1 && typeList != null && !typeList.isEmpty()) {
            typeId = typeList.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(typeId);
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("page", page);
        model.addAttribute("types", typeList);
        model.addAttribute("activeTypeId", typeId);
        return "types";
    }

}
