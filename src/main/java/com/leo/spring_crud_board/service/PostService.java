package com.leo.spring_crud_board.service;

import com.leo.spring_crud_board.entity.Post;
import com.leo.spring_crud_board.repository.PostRepository;
import com.leo.spring_crud_board.service.PostService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PostService {
    private final PostRepository repo;

    // 검색어가 있을 때/ 없을 때 분기
    public Page<Post> findAll(String keyword, Pageable pageable){
        if (keyword != null && !keyword.isBlank()){
            return repo.findByTitleContainingInoreCase(Keyword, pageable);
        }
        return repo.findAll(pageable);
    }
    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public List<Post> findAll(){
        return repo.findAll();
    }
    // 추가: 페이징 처리
    public Page<Post> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }


    public Optional<Post> findById(Long id) {
        return repo.findById(id);
    }

    public Post save(Post post){
        return repo.save(post);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }
}

