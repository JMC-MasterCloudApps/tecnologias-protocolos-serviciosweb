package es.jmc.practica1.controllers;

import es.jmc.practica1.view.api.dtos.CommentDTO;
import es.jmc.practica1.models.Comment;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

	@Autowired
	private BookService bookService;
	
	private AtomicLong idCounter = new AtomicLong(1);
	
	public Comment getComment(long id) {
		return bookService.findCommentById(id);
	}
	
	public Comment create(CommentDTO dto) {
		
		return new Comment(
				idCounter.getAndIncrement(),
				dto.author(),
				dto.content(),
				dto.score());
	}
	
	public void delete(Comment comment) {
		bookService.deleteComment(comment);
	}
}
