package es.jmc.practica.view.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.jmc.practica.controller.BookService;
import es.jmc.practica.controller.CommentService;
import es.jmc.practica.model.Book;
import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.BookDTO;
import es.jmc.practica.view.api.dtos.CommentRequest;

@Controller
public class WebController {

	@Autowired
	BookService bookService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	private UserSession session;
	
	@GetMapping("/app")
	public String homepage(Model model) {
		
		model.addAttribute("books", bookService.getBooks());
		
		return "homepage";
	}
	
	@GetMapping("/book-detail/{id}")
	public String bookDetail(Model model, @PathVariable long id ) {
		
		model.addAttribute("book", bookService.getBook(id));
		model.addAttribute("username", session.getUserName());
		
		return "bookDetail";
	}
	
	@GetMapping("/new-book")
	public String newBook() {
		
		return "newBook";
	}
		
	@PostMapping("/new-book")
	public String createBook(Model model, 
			@RequestParam String title,
			@RequestParam String summary,
			@RequestParam String author,
			@RequestParam String publishHouse,
			@RequestParam int publishYear) {
		
		var dto = new BookDTO(title, summary, author, publishHouse, publishYear, new ArrayList<>());
		
		bookService.create(dto);
		
		model.addAttribute("books", bookService.getBooks());
		
		return "homepage";
	}

	@PostMapping("/new-comment")
	public String createComment(Model model,
			@RequestParam long bookId,
			@RequestParam String author,
			@RequestParam String content,
			@RequestParam int score) {

		session.setUserName(author);
		var dto = new CommentRequest(bookId, author, content, score);

		Comment comment = commentService.create(dto);
		Book book = bookService.getBook(bookId);
		book.addComment(comment);

		model.addAttribute("book", book);
		model.addAttribute("username", author);

		return "bookDetail";
	}

	@GetMapping("/delete-comment/{id}/{bookId}")
	public String deleteComment(Model model, @PathVariable long id, @PathVariable long bookId) {
		
		Comment comment = commentService.getComment(id);
		commentService.delete(comment);
		
		model.addAttribute("book", bookService.getBook(bookId));
		
		return "bookDetail";
	}
}
