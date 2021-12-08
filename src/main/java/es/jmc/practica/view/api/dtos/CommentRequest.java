package es.jmc.practica.view.api.dtos;

public record CommentRequest (
		long bookId,
		String author,
		String content,
		int score) { }