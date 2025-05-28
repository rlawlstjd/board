package com.leo.spring_crud_board.controller;

import com.leo.spring_crud_board.entity.Post;
import com.leo.spring_crud_board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // 목록
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = service.findAll(pageable);

        model.addAttribute("postPage", postPage);
        return "posts/list";
    }

    //글쓰기 폼
    @GetMapping("/new")
    public String createForm (Model model){
        model.addAttribute("post", new Post());
        return "posts/form";
    }

    // 글 등록 & 수정 처리
    @PostMapping
    public String save(@ModelAttribute Post post){
        service.save(post);
        return "redirect:/posts";
    }

    // 상세보기
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        service.findById(id).ifPresent(p -> model.addAttribute("post", p ));
        return "posts/view";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        service.findById(id).ifPresent(p -> model.addAttribute("post", p));
        return "posts/edit";
    }

    //삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "redirect:/posts";
    }
}
