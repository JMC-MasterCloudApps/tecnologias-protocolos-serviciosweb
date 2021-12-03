package es.jmc.practica1;

import java.util.List;

public record BookDTO (
		String title,
		String summary,
		String author,
		String publishHouse,
		int publishYear,
		List<CommentDTO> comments) { }
