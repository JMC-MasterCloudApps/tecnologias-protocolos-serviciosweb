package es.jmc.practica.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Book {
	
	private final long id;
	private final String title;
	private final String summary;
	private final String author;
	private final String publishHouse;
	private final int publishYear;
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
