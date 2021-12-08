package es.jmc.practica.view.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.jmc.practica.model.Book;
import es.jmc.practica.view.api.dtos.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

interface BookRestApi {	
	
	@Operation(summary = "Get all books")
	@ApiResponse(
			description = "Books returned", 
			responseCode = "200",
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookDTO.class)))
	Collection<BookDTO> getBooks();
	
	@Operation(summary = "Get book by ID")
	@ApiResponses({
		@ApiResponse(
				description = "Book returned", 
				responseCode = "200",
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Book.class))),
		@ApiResponse(
				description = "Book not found", 
				responseCode = "204", 
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
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookDTO.class)))
	ResponseEntity<Book> createBook(@RequestBody BookDTO newBook);
	
	@Operation(summary = "Delete book")
	@ApiResponse(description = "Book deleted", responseCode = "200", content = @Content)
	ResponseEntity<Void> deleteBook(@Parameter(description = "ID of the book") @PathVariable long id);
}
