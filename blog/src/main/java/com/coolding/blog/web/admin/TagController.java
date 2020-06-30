package com.coolding.blog.web.admin;

import com.coolding.blog.po.Tag;
import com.coolding.blog.po.Type;
import com.coolding.blog.service.TagService;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @ClassName TagController
 * @Author 酷酷的丁
 * @Date 2020-03-28 15:01
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /*
    * 去往tag 列表页面
    * */
    @GetMapping("/tags")
    public String listTags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    /*
    *  去往tag 添加页面
    * */
    @GetMapping("/tags/input")
    public String addTag(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags_add";
    }

    /*
    *  执行添加操作
    * */
    @PostMapping("tags/doAdd")
    public String doAdd(Tag tag , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Tag tagFindByName = tagService.queryTagByName(tag.getName());
        if (tagFindByName != null){
            bindingResult.rejectValue("name","nameError","不能提交重复的标签");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags_add";
        }

        Tag tag1 = tagService.saveTags(tag);
        if(tag1 == null){
            redirectAttributes.addFlashAttribute("message","操作失败,不能为空或已存在类型");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    /*
    *  去往更新页面
    * */

    @GetMapping("/tags/{id}/edit")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag",tagService.queryTag(id));
        return "admin/tags_add";
    }

    @PostMapping("/tags/edit/{id}")
    public String doEdit(@PathVariable Long id,  Tag tag ,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        Tag tagFindByName = tagService.queryTagByName(tag.getName());
        if (tagFindByName != null){
            bindingResult.rejectValue("name","nameError","不能提交重复的标签");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags_add";
        }

        Tag tag1 = tagService.updateTag(tag,id);
        if(tag1 == null){
            redirectAttributes.addFlashAttribute("message","操作失败,不能为空或已存在类型");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    /*
    *  删除操作
    * */

    @GetMapping("/tags/{id}/delete")
    public String delTag(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }


}
