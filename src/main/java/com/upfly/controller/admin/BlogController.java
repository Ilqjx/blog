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
@RequestMapping("/guozhenwei")
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
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
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
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/guozhenwei/blogs";
    }

    @PostMapping("/blogs/{id}")
    public String editBlog(@PathVariable Long id, Blog blog, RedirectAttributes attributes, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Type type = typeService.getType(blog.getType().getId());
        List<Tag> tagList = tagService.listTag(blog.getTagIds());

        blog.setUser(user);
        blog.setType(type);
        blog.setTagList(tagList);

        Blog tempBlog = blogService.updateBlog(blog.getId(), blog);
        if (tempBlog == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/guozhenwei/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/guozhenwei/blogs";
    }

}
