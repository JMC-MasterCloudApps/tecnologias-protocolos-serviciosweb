package es.jmc.practica.view.api.rest;

import static es.jmc.practica.view.api.mapper.BookMapper.BOOK_MAPPER;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jmc.practica.controller.BookService;
import es.jmc.practica.model.Book;
import es.jmc.practica.view.api.dtos.BookDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController implements BookRestApi {

	private final BookService service;

	@GetMapping("/")
	public Collection<BookDTO> getBooks() {
		
		Collection<Book> books = service.getBooks();
		
		return BOOK_MAPPER.book2dto(books);
	}

	@GetMapping("/{id}")	
	public ResponseEntity<Book> getBook(long id) {

		final Book book = service.getBook(id);

		if (book == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(book);
	}

	@PostMapping("/")
	public ResponseEntity<Book> createBook(BookDTO newBook) {

		Book book = service.create(newBook);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

		return ResponseEntity.created(location).body(book);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(long id) {

		Book book = service.getBook(id);
		if (book != null) {
			service.delete(book);
		}

		return ResponseEntity.ok().build();
	}
}
