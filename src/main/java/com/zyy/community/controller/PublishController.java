package com.zyy.community.controller;

import com.zyy.community.dto.QuestionDTO;
import com.zyy.community.entity.Question;
import com.zyy.community.entity.User;
import com.zyy.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model
    ) {

        // 回显到页面
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        // error信息
        if ("".equals(title) || title == null) {
            model.addAttribute("error", "请输入标题");
            return "publish";
        }
        if ("".equals(description) || description == null) {
            model.addAttribute("error", "请输入描述信息");
            return "publish";
        }
        if ("".equals(tag) || tag == null) {
            model.addAttribute("error", "请输入标签");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }


        // 验证全通过，创建question
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        // 创建或修改
        questionService.createOrUpdate(question);

        return "redirect:/";
    }


    /**
     * 编辑
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model) {

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        return "publish";
    }

}
