package es.jmc.practica.view.db.repositories;

import es.jmc.practica.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
