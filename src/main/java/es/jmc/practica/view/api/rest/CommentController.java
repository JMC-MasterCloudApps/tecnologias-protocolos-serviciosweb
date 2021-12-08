package es.jmc.practica.view.api.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jmc.practica.controller.BookService;
import es.jmc.practica.controller.CommentService;
import es.jmc.practica.model.Book;
import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.CommentDTO;

@RestController
@RequestMapping("/comments")
public class CommentController implements CommentRestApi {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private CommentService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getComment(long id) {
		
		final Comment comment = service.getComment(id);
		
		if (comment == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(comment);
	}
	
	@PostMapping("/")
	public ResponseEntity<Comment> createComment(CommentDTO dto) {

		Book book = bookService.getBook(dto.bookId());
		if (book == null) {
			return ResponseEntity.noContent().build();
		}
		
		Comment comment = service.create(dto);
		book.addComment(comment);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();

		return ResponseEntity.created(location).body(comment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(long id) {
		
		Comment comment = service.getComment(id);
		if (comment != null) {
			service.delete(comment);
		}

		return ResponseEntity.ok().build();
	}
}
