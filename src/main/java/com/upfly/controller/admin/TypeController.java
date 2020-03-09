package com.upfly.controller.admin;

import javax.validation.Valid;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.po.Type;
import com.upfly.service.BlogService;
import com.upfly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Type> page = typeService.listType(pageable);
        model.addAttribute("page", page);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    @PostMapping("/types")
    // @Valid 校验type对象
    // BindingResult 接受校验信息
    public String saveType(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            // 向BindingResult添加分类已存在的校验错误
            result.rejectValue("name", "nameError", "不能添加重复的分类");
            return "admin/types-input";
        }

        Type tempType = typeService.saveType(type);
        if (tempType == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editType(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
            return "admin/types-input";
        }

        Type tempType = typeService.updateType(id, type);
        if (tempType == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        List<Blog> blogList = blogService.listBlogByTypeId(id);
        if (!blogList.isEmpty()) {
            attributes.addFlashAttribute("message", "删除失败，该分类下有博客存在不能删除");
            return "redirect:/admin/types";
        }

        typeService.deleteType(id);
        Type type = typeService.getType(id);
        if (type == null) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/types";
    }

}
