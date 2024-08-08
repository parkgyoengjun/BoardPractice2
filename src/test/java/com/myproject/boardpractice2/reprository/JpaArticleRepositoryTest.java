package com.myproject.boardpractice2.reprository;


import com.myproject.boardpractice2.config.JpaConfig;
import com.myproject.boardpractice2.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DisplayName("JPA 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaArticleRepositoryTest {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    public JpaArticleRepositoryTest(@Autowired ArticleRepository articleRepository,
                                    @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void given_whenSelecting_thenWorkFine() {
        // 다 찾아보는걸로 해보자
        // given

        // when
          List<Article> all = articleRepository.findAll();

        // then
            Assertions.assertThat(all).isNotNull().hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void given_whenInserting_thenWorkFine() {
        // 갯수가 늘면 insert 된것
        // given
          long previousCount = articleRepository.count();

        // when
            articleRepository.saveAndFlush(Article.of("new title", "new content", "new hashtag"));

        // then
           Assertions.assertThat(articleRepository.count()).isEqualTo(previousCount+1);

    }

    @DisplayName("update 테스트")
    @Test
    void given_whenUpdating_thenWorkFine() {
        // 임의로 하나 꺼내서 업데이트 시키고 확인하면 된다. 저장된거랑 db에꺼랑 확인해보면 된다.
        // given
            Article article = articleRepository.findById(1L).orElseThrow();
            String newHashtag = "newSpring";
            article.setHashtag(newHashtag);
        // when
            Article savedArticle = articleRepository.saveAndFlush(article);

        // then
          Assertions.assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", newHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void given_whenDeleting_thenWorkFine() {
        // delete 는 삭제한 갯수가 같으면 될듯
        // given
            Article article = articleRepository.findById(1L).orElseThrow();
            long articleCount = articleRepository.count();
            long articleCommentCount = articleCommentRepository.count();
            int deletedCount = article.getArticleComments().size();

        // when
            articleRepository.delete(article);

        // then
            Assertions.assertThat(articleRepository.count()).isEqualTo(articleCount - 1);
            Assertions.assertThat(articleCommentRepository.count()).isEqualTo(articleCommentCount - deletedCount);
    }
}