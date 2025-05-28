package com.leo.spring_crud_board.controller;

import com.leo.spring_crud_board.entity.Post;
import com.leo.spring_crud_board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;               // ← 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;  // ← 추가

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // 1) 목록 + 검색(q) + 페이징 + 최신순 정렬
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String q,
            Model model) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage = service.findAll(q, pageable);

        model.addAttribute("postPage", postPage);
        model.addAttribute("q", q);
        return "posts/list";
    }

    // 2) 글쓰기/수정 폼
    @GetMapping({"/new", "/{id}/edit"})
    public String form(
            @PathVariable(required = false) Long id,
            Model model) {

        if (id != null) {
            Post p = service.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("글이 없습니다. id=" + id));
            model.addAttribute("post", p);
        } else {
            model.addAttribute("post", new Post());
        }
        return "posts/form";
    }

    // 3) 저장 (신규 or 수정)
    @PostMapping
    public String save(Post post) {
        service.save(post);
        return "redirect:/posts";
    }

    // 4) 상세 보기
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Post p = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글이 없습니다. id=" + id));
        model.addAttribute("post", p);
        return "posts/view";
    }

    // 5) 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/posts";
    }
}
