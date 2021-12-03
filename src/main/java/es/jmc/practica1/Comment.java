package es.jmc.practica1;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class Comment {

	public interface Full { }
	
	@JsonView(Full.class)
	private final long id;

	@JsonView(Full.class)
	private final String author;

	@JsonView(Full.class)
	private final String content;

	@JsonView(Full.class)
	private final int score;

}
