package es.jmc.practica1.view.api.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jmc.practica1.models.Book;
import es.jmc.practica1.controllers.BookService;
import es.jmc.practica1.models.Comment;
import es.jmc.practica1.view.api.dtos.CommentDTO;
import es.jmc.practica1.controllers.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private CommentService service;
	
	@GetMapping("/{id}")
	@Operation(summary = "Get comment by ID")
	@ApiResponse (description = "Comment returned", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))) 
	@ApiResponse(description = "Comment not found", responseCode = "204", content = @Content)
	public ResponseEntity<Comment> getComment(
			@Parameter(description = "ID of the comment to be searched") 
			@PathVariable long id) {
		
		final Comment comment = service.getComment(id);
		
		if (comment == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(comment);
	}
	
	@PostMapping("/")
	@Operation(summary = "Create new comment")
	@ApiResponse(description = "Comment created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
	@ApiResponse(description = "Book not found", responseCode = "204", content = @Content)
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment to be created", required = true,
	content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class)))
	public ResponseEntity<Comment> createComment(
			@RequestBody CommentDTO dto) {

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
	@Operation(summary = "Delete comment")
	@ApiResponse(description = "Comment deleted", responseCode = "200", content = @Content)
	public ResponseEntity<Void> deleteComment(
			@Parameter(description = "ID of the comment to be deleted") 
			@PathVariable long id) {
		
		Comment comment = service.getComment(id);
		if (comment != null) {
			service.delete(comment);
		}

		return ResponseEntity.ok().build();
	}
}
