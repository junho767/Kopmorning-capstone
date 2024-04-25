package me.junholee.springbootdeveloper.dto.Articles;

import lombok.Getter;
import me.junholee.springbootdeveloper.domain.Article;
import me.junholee.springbootdeveloper.domain.User;

import java.time.LocalDateTime;

@Getter
public class ArticleListViewResponse {
    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createdAt;
    private int likeCount;
    private int viewCount;
    public ArticleListViewResponse(Article article){
        this.id=article.getId();
        this.title=article.getTitle();
        this.content=article.getContent();
        this.user = article.getUser();
        this.createdAt=article.getCreatedAt();
        this.likeCount = article.getLikeCount();
        this.viewCount = article.getViewCount();
    }
}