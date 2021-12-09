package es.jmc.practica.controller;

import es.jmc.practica.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jmc.practica.model.Comment;
import es.jmc.practica.model.Score;

@Service
public class CommentService {

	@Autowired
	private BookService bookService;
	
	private AtomicLong idCounter = new AtomicLong(1);
	
	public Comment getComment(long id) {
		return bookService.findCommentById(id);
	}
	
	public Comment create(Comment comment) {
		
		comment.setId(idCounter.getAndIncrement());
		return comment;
	}
	
	public void delete(Comment comment) {
		bookService.deleteComment(comment);
	}

	public Collection<Comment> findCommentsByUser(long id) {
		
		if (Math.random() % 2 != 0)
			return Collections.emptySet();
		
		return Collections.singleton(new Comment(55,
				new User(id, "Joey", "joey@mail.com"),
				"This is just fire!",
				Score.FOUR));
	}
}
