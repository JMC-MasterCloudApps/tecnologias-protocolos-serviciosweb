package es.jmc.practica1.view.api.dtos;

public record CommentDTO (
		long bookId,
		String author,
		String content,
		int score) { }
