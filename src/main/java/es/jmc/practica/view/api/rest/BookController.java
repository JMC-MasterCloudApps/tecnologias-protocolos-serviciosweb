package es.jmc.practica.view.api.rest;

import static es.jmc.practica.view.api.mapper.BookMapper.BOOK_MAPPER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.jmc.practica.view.api.dtos.LiteBookRequest;
import java.net.URI;
import java.util.Collection;

import java.util.Optional;
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
import es.jmc.practica.model.Book;
import es.jmc.practica.view.api.dtos.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController implements BookRestApi {

	private final BookService service;

	@GetMapping("/")
	public Collection<LiteBookRequest> getBooks() {
		
		Collection<Book> books = service.getBooks();
		
		return BOOK_MAPPER.book2dto(books);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(long id) {

		Optional<Book> book = service.getBook(id);

		return ResponseEntity.of(book);
	}

	@PostMapping("/")
	public ResponseEntity<Book> createBook(BookRequest newBook) {

		Book book = BOOK_MAPPER.dto2book(newBook);
		book = service.create(book);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

		return ResponseEntity.created(location).body(book);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(long id) {

		Optional<Book> book = service.getBook(id);
		book.ifPresent(service::delete);

		return ResponseEntity.ok().build();
	}

}

interface BookRestApi {	
	
	@Operation(summary = "Get all books")
	@ApiResponse(
			description = "Books returned",
			responseCode = "200",
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = LiteBookRequest.class)))
	Collection<LiteBookRequest> getBooks();
	
	@Operation(summary = "Get book by ID")
	@ApiResponses({
		@ApiResponse(
				description = "Book returned", 
				responseCode = "200",
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Book.class))),
		@ApiResponse(
				description = "Book not found", 
				responseCode = "404",
				content = @Content)
	})	
	ResponseEntity<Book> getBook(
			@Parameter(description = "ID of the book to be searched") @PathVariable long id);
	
	@Operation(summary = "Create new book")
	@ApiResponse(
			description = "Book created", 
			responseCode = "201", 
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Book.class)))
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Book to be created", 
			required = true, 
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookRequest.class)))
	ResponseEntity<Book> createBook(
			@RequestBody BookRequest newBook);
	
	@Operation(summary = "Delete book")
	@ApiResponse(description = "Book deleted", responseCode = "200", content = @Content)
	ResponseEntity<Void> deleteBook(
			@Parameter(description = "ID of the book") 
			@PathVariable long id);
}

