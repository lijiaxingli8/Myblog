package com.coolding.blog.web.admin;

import com.coolding.blog.po.Type;
import com.coolding.blog.service.TypeService;
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

import javax.validation.Valid;

/**
 * @ClassName TypeController
 * @Author 酷酷的丁
 * @Date 2020-03-25 18:43
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    /*
     *     去往 type的列表页
     **/
    @GetMapping("/types")
    public String listType(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    /*
     *     去往 type的添加页面
     **/
    @GetMapping("/types/input")
    public String addType(Model model) {
        model.addAttribute("type",new Type());
        return "admin/types_add";
    }
    /*
     *     去往 type的更新页面
     **/
    @GetMapping("/types/{id}/edit")
    public String editType(@PathVariable Long id,Model model) {
        model.addAttribute("type",typeService.queryType(id));
        return "admin/types_add";
    }
    /*
     *   执行添加操作
     **/
    @PostMapping("/types/doAdd")
    public String doAdd(@Valid Type type, BindingResult bindingResult,RedirectAttributes redirectAttributes){

        Type typeFindByName = typeService.queryTypeByName(type.getName());
        if (typeFindByName != null){
            bindingResult.rejectValue("name","nameError","不能提交重复的名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/types_add";
        }

        Type type1 = typeService.saveType(type);
        if(type1 == null){
            redirectAttributes.addFlashAttribute("message","操作失败,不能为空或已存在类型");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

        /*
         *     执行更新操作
         **/
    @PostMapping("/types/edit/{id}")
    public String doEdit(@Valid Type type, BindingResult bindingResult,@PathVariable Long id,RedirectAttributes redirectAttributes){

        Type typeFindByName = typeService.queryTypeByName(type.getName());
        if (typeFindByName != null){
            bindingResult.rejectValue("name","nameError","不能提交重复的名称");
        }
        if(bindingResult.hasErrors()){
            return "admin/types_add";
        }

        Type type1 = typeService.updateType(id,type);
        if(type1 == null){
            redirectAttributes.addFlashAttribute("message","操作失败,不能为空或已存在类型");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /*
     *    执行删除操作
     **/
    @GetMapping("/types/{id}/delete")
    public String doDel(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
