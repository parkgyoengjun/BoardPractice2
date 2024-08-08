package com.myproject.boardpractice2.reprository;

import com.myproject.boardpractice2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}