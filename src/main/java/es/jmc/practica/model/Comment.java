package es.jmc.practica.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
	
	private long id;
	private String author;
	private String content;
	private int score;

}
