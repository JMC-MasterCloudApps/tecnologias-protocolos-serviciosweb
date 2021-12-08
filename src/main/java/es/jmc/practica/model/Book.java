package es.jmc.practica.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
	
	private long id;
	private String title;
	private String summary;
	private String author;
	private String publishHouse;
	private int publishYear;
	private List<Comment> comments;
	
	public Book(
			long id,
			String title,
			String summary, 
			String author,
			String publishHouse,
			int publishYear) {
		
		this.id = id;
		this.title = title;       
		this.summary = summary;
		this.author = author;
		this.publishHouse = publishHouse;
		this.publishYear = publishYear;
		this.comments = Collections.synchronizedList(new ArrayList<>());
	}
	
	public boolean addComment(Comment comment) {
		return this.comments.add(comment);
	}

	@Override
	public String toString() {
	
		return "Book: [id:" + id + "]" + 
		" [title: " + title + "]";
	}
}       
