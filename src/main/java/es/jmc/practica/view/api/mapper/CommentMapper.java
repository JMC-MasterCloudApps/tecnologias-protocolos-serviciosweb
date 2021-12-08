package es.jmc.practica.view.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.model.Comment;
import es.jmc.practica.model.Score;
import es.jmc.practica.view.api.dtos.CommentRequest;

@Mapper
public interface CommentMapper {

	final static CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);
	
	CommentRequest comment2dto(Comment comment);
	
	Collection<CommentRequest> comment2dto(Collection<Comment> comments);
	
	Comment dto2comment(CommentRequest commentRequest);
	
	Collection<Comment> dto2comment(Collection<CommentRequest> commentRequests);
	
	default Score map(int score) {
		return Score.findByValue(score);
	}
	
	default int map(Score score) {
		return score.getValue();
	}
}
