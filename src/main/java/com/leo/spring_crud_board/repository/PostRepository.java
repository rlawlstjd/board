package com.leo.spring_crud_board.repository;

// src/main/java/com/leo/spring_crud_board/repository/PostRepository.java

import com.leo.spring_crud_board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    // 제목에 키워드 포함 검색, 페이징+정렬 지원
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
