package es.jmc.practica.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.CommentRequest;

@Service
public class CommentService {

	@Autowired
	private BookService bookService;
	
	private AtomicLong idCounter = new AtomicLong(1);
	
	public Comment getComment(long id) {
		return bookService.findCommentById(id);
	}
	
	public Comment create(CommentRequest dto) {
		
		return new Comment(
				idCounter.getAndIncrement(),
				dto.author(),
				dto.content(),
				dto.score());
	}
	
	public void delete(Comment comment) {
		bookService.deleteComment(comment);
	}

	public Collection<Comment> findCommentsByUser(long id) {
		
		if (Math.random() % 2 == 0)
			return Collections.emptySet();
		
		return Collections.singleton(new Comment(55, "Joey", "This is just fire!", 4)); 
	}
}
