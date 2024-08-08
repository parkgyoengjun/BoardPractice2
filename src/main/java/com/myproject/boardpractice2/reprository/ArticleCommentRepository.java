package com.myproject.boardpractice2.reprository;

import com.myproject.boardpractice2.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
