package me.junholee.springbootdeveloper.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.junholee.springbootdeveloper.domain.Article;
import me.junholee.springbootdeveloper.domain.SessionUser;
import me.junholee.springbootdeveloper.domain.User;
import me.junholee.springbootdeveloper.dto.*;
import me.junholee.springbootdeveloper.service.BlogService;
import me.junholee.springbootdeveloper.service.UserDetailService;
import me.junholee.springbootdeveloper.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;
    private final UserDetailService userDetailService;
    private final UserService userService;
    private final HttpSession httpSession;
    // "/api/articles" 경로로 POST 요청이 들어왔을 때 실행되는 메서드입니다. 여기서 주요한 기능은 새로운 글을 추가하고 그 결과를 응답하는 것입니다.
    @PostMapping("/api/articles") // 글 생성
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        User user = userDetailService.loadUserByUsername(principal.getName());
        Article savedArticle = blogService.save(request, user.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
    @GetMapping("/api/articles") // 글 조회
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles= blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/api/articles/{id}")  //
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){ // @PathVariable 에너테이션은  URL에서 값을 가져오는 것.
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
    @PutMapping("/api/myProFil")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest request){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String email = user.getEmail();
        System.out.println(request.getNickname());
        User updateUser = userService.UpdateUser(email, request);

        return ResponseEntity.ok()
                .body(updateUser);
    }
}