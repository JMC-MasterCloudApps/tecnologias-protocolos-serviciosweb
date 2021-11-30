package es.jmc.practica1;

import com.fasterxml.jackson.annotation.JsonView;

public class Comment {

	interface Full { }
	
	@JsonView(Full.class)
	private final long id;

	@JsonView(Full.class)
	private final String author;

	@JsonView(Full.class)
	private final String content;

	@JsonView(Full.class)
	private final int score;
	
	public Comment(long id, String author, String content, int score) {
		
		this.id = id;
		this.author = author;
		this.content = content;
		this.score = score;
	}
	
	public long getId() {
		return id;
	}
}
