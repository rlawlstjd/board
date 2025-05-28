package com.leo.spring_crud_board.service;

import com.leo.spring_crud_board.entity.Post;
import com.leo.spring_crud_board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    // 검색어가 있을 때/없을 때 분기
    public Page<Post> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isBlank()) {
            return repo.findByTitleContainingIgnoreCase(keyword, pageable);
        }
        return repo.findAll(pageable);
    }

    // 페이징 없이 전체 리스트
    public List<Post> findAll() {
        return repo.findAll();
    }

    // 페이징 처리만
    public Page<Post> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Post> findById(Long id) {
        return repo.findById(id);
    }

    public Post save(Post post) {
        return repo.save(post);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
