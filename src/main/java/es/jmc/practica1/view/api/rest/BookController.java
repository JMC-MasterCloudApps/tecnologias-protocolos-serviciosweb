package es.jmc.practica1.view.api.rest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.jmc.practica1.view.api.dtos.BookDTO;
import java.net.URI;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.jmc.practica1.models.Book;
import es.jmc.practica1.controllers.BookService;
import es.jmc.practica1.models.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
	
	interface BookDetail extends Book.Full, Comment.Full { }

	private final BookService service;
	
	@GetMapping("/")
	@JsonView(Book.Lite.class)
	@Operation(summary = "Get all books")
	@ApiResponse (description = "Books returned", responseCode = "200", 
	content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))) 
	public Collection<Book> getBooks() {
		return service.getBooks();
	}
	
	@GetMapping("/{id}")
	@JsonView(BookDetail.class)
	@Operation(summary = "Get book by ID")
	@ApiResponse(description = "Book returned", responseCode = "200", 
	content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
	@ApiResponse(description = "Book not found", responseCode = "204", content = @Content)
	public ResponseEntity<Book> getBook(
			@Parameter(description = "ID of the book to be searched")
			@PathVariable long id) {
		
		final Book book = service.getBook(id);
		
		if (book == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(book);
	}

	@PostMapping("/")
	@Operation(summary = "Create new book")
	@ApiResponse(description = "Book created", responseCode = "201", content = 
	@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to be created", required = true,
	content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class)))
	public ResponseEntity<Book> createBook(@RequestBody BookDTO newBook) {
		
		Book book = service.create(newBook);
		
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
		
		return ResponseEntity.created(location).body(book);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete book")
	@ApiResponse(description = "Book deleted", responseCode = "200", content = @Content)
	public ResponseEntity<Void> deleteBook(
			@Parameter(description = "ID of the book")
			@PathVariable long id) {
		
		Book book = service.getBook(id);
		if (book != null) {
			service.delete(book);
		}

		return ResponseEntity.ok().build();
	}
}
