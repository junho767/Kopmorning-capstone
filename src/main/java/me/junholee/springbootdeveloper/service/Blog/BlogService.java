package me.junholee.springbootdeveloper.service.Blog;

import lombok.RequiredArgsConstructor;
import me.junholee.springbootdeveloper.domain.Article;
import me.junholee.springbootdeveloper.domain.User;
import me.junholee.springbootdeveloper.dto.Articles.AddArticleRequest;
import me.junholee.springbootdeveloper.dto.Articles.UpdateArticleRequest;
import me.junholee.springbootdeveloper.dto.ImageDTO.ArticleImageUploadDTO;
import me.junholee.springbootdeveloper.repository.ArticleImageRepository;
import me.junholee.springbootdeveloper.repository.BlogRepository;
import me.junholee.springbootdeveloper.service.Member.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserDetailService userDetailService;
    private final ArticleImageRepository articleImageRepository;

    @Value("${file.ArticleImagePath}")
    private String uploadFolder;
    @Transactional
    public Article save(AddArticleRequest request, ArticleImageUploadDTO imageUploadDTO, User user) {
        Article result = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        blogRepository.save(result);

//        if(imageUploadDTO.getFiles() != null && !imageUploadDTO.getFiles().isEmpty()){
//            System.out.println("왔냐?");
//            for(MultipartFile file : imageUploadDTO.getFiles()) {
//                UUID uuid = UUID.randomUUID();
//                String article_image_name = uuid + "_" + file.getOriginalFilename();
//                File destinationFile = new File(uploadFolder + article_image_name);
//                try {
//                    file.transferTo(destinationFile);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                ArticleImage image = ArticleImage.builder()
//                        .url(article_image_name)
//                        .article(result)
//                        .build();
//                articleImageRepository.save(image);
//            }
//        }
        return result;
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void updateView(long id){
        Optional<Article> article = this.blogRepository.findById(id);
        Article article1 = article.get();
        article1.setViewCount(article1.getViewCount()+1);
        this.blogRepository.save(article1);
    }
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDetailService.loadUserByUsername(userName);
        if (!article.getUser().getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}