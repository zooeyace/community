package com.zyy.community.controller;

import com.zyy.community.dto.NotificationDTO;
import com.zyy.community.dto.PaginationDTO;
import com.zyy.community.dto.QuestionDTO;
import com.zyy.community.entity.User;
import com.zyy.community.service.NotificationService;
import com.zyy.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Resource
    private QuestionService questionService;

    @Resource
    private NotificationService notificationService;

    @GetMapping(value = "/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO<QuestionDTO> pagination = questionService.listQuestionsByUserId(user.getId(), page, size);
            model.addAttribute("pageInfo", pagination);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            // notification
            PaginationDTO<NotificationDTO> pagination = notificationService.list(user.getId(), page, size);
            model.addAttribute("pageInfo", pagination);
        }
        return "profile";
    }

}
