package me.junholee.springbootdeveloper.repository;

import me.junholee.springbootdeveloper.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentRepository extends JpaRepository<Comment,String> {
}
