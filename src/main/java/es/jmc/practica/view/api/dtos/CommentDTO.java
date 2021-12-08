package es.jmc.practica.view.api.dtos;

public record CommentDTO (
		long bookId,
		String author,
		String content,
		int score) { }
