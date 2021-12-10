package es.jmc.practica.controller;

import es.jmc.practica.model.Book;
import es.jmc.practica.model.Comment;
import es.jmc.practica.model.Score;
import es.jmc.practica.model.User;
import es.jmc.practica.view.api.dtos.BookRequest;
import es.jmc.practica.view.db.repositories.BookRepository;
import es.jmc.practica.view.db.repositories.CommentRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

	private List<Book> books;
	private AtomicLong idCounter = new AtomicLong();

	private final BookRepository repository;
	private final CommentRepository commentRepository;

	@PostConstruct
	private void setUp() {
		books = Collections.synchronizedList(new ArrayList<>());
		
		var book = new Book("Clean Code",
				"A Handbook of Agile Software Craftsmanship",
				"Robert C. Martin",
				"Pearson",
				2008);
		books.add(book);
		repository.save(book);

		book = new Book(
				"Extreme Programming Explained",
				"Embrace Change (XP Series)",
				"Kent Beck",
				"Addison Wesley",
				2004);
		books.add(book);
		repository.save(book);

		var user = new User("Johnson", "johnson@mail.com");
		var comment = new Comment("Baby steps are great", Score.FOUR);
		comment.setAuthor(user);
		comment.setBook(book);
		commentRepository.save(comment);
	}
	
	public Collection<Book> getBooks() {
		return books;
	}
	
	public Book getBook(long id) {
		
		for (Book book : books) {
			if (book.getId() == id) {
				return book;
			}
		}
		
		return null;
	}

	public Book create(BookRequest dto) {
		
		var book = new Book(
				dto.title(),
				dto.summary(),
				dto.author(),
				dto.publishHouse(),
				dto.publishYear());
		books.add(book);
		
		return book;
	}
	
	public void delete(Book book) {
		books.remove(book);
	}

	public Comment findCommentById(long id) {
		
		for (Book book : books) {
			for (Comment comment : book.getComments()) {
				if (comment.getId() == id) {
					return comment;
				}
			}
		}
		
		return null;
	}
	
	public void deleteComment(Comment commentToDelete) {
	
		for (Book book : books) {
			for (Comment comment : book.getComments()) {
				if (comment.equals(commentToDelete)) {
					book.getComments().remove(commentToDelete);
					return;
				}
			}
		}
	}
}
