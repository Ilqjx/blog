package com.upfly.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.po.Tag;
import com.upfly.po.Type;
import com.upfly.po.User;
import com.upfly.service.BlogService;
import com.upfly.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

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

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String saveBlog(Blog blog, RedirectAttributes attributes, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Type type = typeService.getType(blog.getType().getId());
        List<Tag> tagList = tagService.listTag(blog.getTagIds());

        blog.setUser(user);
        blog.setType(type);
        blog.setTagList(tagList);

        Blog tempBlog = blogService.saveBlog(blog);
        if (tempBlog == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/{id}")
    public String editBlog(@PathVariable Long id) {
        return null;
    }

}
