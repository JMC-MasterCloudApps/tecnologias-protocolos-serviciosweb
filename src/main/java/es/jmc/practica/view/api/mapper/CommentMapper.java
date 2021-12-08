package es.jmc.practica.view.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.CommentRequest;

@Mapper
public interface CommentMapper {

	final static CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);
	
	CommentRequest comment2dto(Comment comments);
	
	Collection<CommentRequest> comment2dto(Collection<Comment> comments);
}
