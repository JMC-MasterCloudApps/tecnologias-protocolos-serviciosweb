package es.jmc.practica1;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/books")
public class BookController {
	
	interface BookDetail extends Book.Full, Comment.Full { }

	@Autowired
	private BookService service;
	
	@GetMapping("/")
	@JsonView(Book.Lite.class)
	@Operation(summary = "Get all books")
	@ApiResponse (description = "Books returned", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))) 
	public Collection<Book> getBooks() {
		return service.getBooks();
	}
	
	@GetMapping("/{id}")
	@JsonView(BookDetail.class)
	@Operation(summary = "Get book by ID")
	@ApiResponse(description = "Book returned", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
	@ApiResponse(description = "Book not found", responseCode = "204", content = @Content)
	public ResponseEntity<Book> getBook(@PathVariable long id) {
		
		final Book book = service.getBook(id);
		
		if (book == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(book);
	}

	@PostMapping("/")
	@Operation(summary = "Create new book")
	@ApiResponse(description = "Book created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
	public ResponseEntity<Book> createBook(@RequestBody BookDTO newBook) {
		
		Book book = service.create(newBook);
		
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
		
		return ResponseEntity.created(location).body(book);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete book")
	@ApiResponse(description = "Book deleted", responseCode = "200", content = @Content)
	public ResponseEntity<?> deleteBook(@PathVariable long id) {
		
		Book book = service.getBook(id);
		if (book != null) {
			service.delete(book);
		}

		return ResponseEntity.ok().build();
	}
}
