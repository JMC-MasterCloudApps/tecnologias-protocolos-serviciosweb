package es.jmc.practica.view.api.rest;

import static es.jmc.practica.view.api.mapper.CommentMapper.COMMENT_MAPPER;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

import es.jmc.practica.controller.BookService;
import es.jmc.practica.controller.CommentService;
import es.jmc.practica.model.Book;
import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.CommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
	public ResponseEntity<Comment> createComment(CommentRequest dto) {

		Book book = bookService.getBook(dto.bookId());
		if (book == null) {
			return ResponseEntity.noContent().build();
		}
		
		Comment comment = COMMENT_MAPPER.dto2comment(dto);
		comment = service.create(comment);
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

interface CommentRestApi {

	@Operation(summary = "Get comment by ID")
	@ApiResponses({
		@ApiResponse(
				description = "Comment returned", 
				responseCode = "200", 
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))), 
		@ApiResponse(
				description = "Comment not found", 
				responseCode = "204", 
				content = @Content)		
	})
	ResponseEntity<Comment> getComment(
			@Parameter(description = "ID of the comment to be searched") 
			@PathVariable long id);
	
	@Operation(summary = "Create new comment")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Comment to be created", 
			required = true,
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentRequest.class)))
	@ApiResponses({	
		@ApiResponse(
				description = "Comment created", 
				responseCode = "201", 
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
		@ApiResponse(
				description = "Book not found", 
				responseCode = "204", 
				content = @Content)})	
	ResponseEntity<Comment> createComment(@RequestBody CommentRequest dto);

	@Operation(summary = "Delete comment")
	@ApiResponse(description = "Comment deleted", responseCode = "200", content = @Content)
	ResponseEntity<Void> deleteComment(
			@Parameter(description = "ID of the comment to be deleted") 
			@PathVariable long id);

}