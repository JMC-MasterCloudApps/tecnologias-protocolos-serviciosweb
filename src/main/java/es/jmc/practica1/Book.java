package es.jmc.practica1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class Book {

	public interface Lite { }
	
	public interface Full { }
	
	@JsonView(value = {Lite.class, Full.class})
	private final long id;
	
	@JsonView(value = {Lite.class, Full.class})
	private final String title;

	@JsonView(Full.class)
	private final String summary;
	
	@JsonView(Full.class)
	private final String author;
	
	@JsonView(Full.class)
	private final String publishHouse;

	@JsonView(Full.class)
	private final int publishYear;
	
	@JsonView(Full.class)
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
