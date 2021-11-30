package es.jmc.practica1;

public record CommentDTO (
		long bookId,
		String author,
		String content,
		int score) {

}
