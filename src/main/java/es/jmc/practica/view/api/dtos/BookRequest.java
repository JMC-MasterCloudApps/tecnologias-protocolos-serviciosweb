package es.jmc.practica.view.api.dtos;

import java.util.List;

public record BookRequest (
		String title,
		String summary,
		String author,
		String publishHouse,
		int publishYear,
		List<CommentRequest> comments) { }