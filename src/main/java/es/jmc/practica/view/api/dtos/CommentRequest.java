package es.jmc.practica.view.api.dtos;

public record CommentRequest (
		long bookId,
		UserRequest author,
		String content,
		int score) { }