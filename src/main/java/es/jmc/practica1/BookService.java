package es.jmc.practica1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	Logger log = LoggerFactory.getLogger(BookService.class);

	private List<Book> books;
	private AtomicLong idCounter = new AtomicLong();
	
	@PostConstruct
	private void setUp() {
		books = Collections.synchronizedList(new ArrayList<>());
		
		var book = new Book(idCounter.getAndIncrement(),
				"Clean Code",
				"A Handbook of Agile Software Craftsmanship",
				"Robert C. Martin",
				"Pearson",
				2008);
		books.add(book);
		log.info(book.toString());
		
		book = new Book(
				idCounter.getAndIncrement(),
				"Extreme Programming Explained",
				"Embrace Change (XP Series)",
				"Kent Beck",
				"Addison Wesley",
				2004);
		var comment = new Comment(0, "Ed Yourdon",
				"This book is dynamite!",
				5); 
		
		book.addComment(comment);
		log.info(book.toString());
		books.add(book);
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

	public Book create(BookDTO dto) {
		
		var book = new Book(
				idCounter.getAndIncrement(),
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
