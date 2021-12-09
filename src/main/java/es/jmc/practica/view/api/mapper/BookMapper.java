package es.jmc.practica.view.api.mapper;

import es.jmc.practica.view.api.dtos.LiteBookRequest;
import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.model.Book;
import es.jmc.practica.model.Comment;
import es.jmc.practica.model.Score;
import es.jmc.practica.view.api.dtos.BookRequest;
import es.jmc.practica.view.api.dtos.CommentRequest;

@Mapper
public interface BookMapper {

	BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);
	
	BookRequest book2dto(Book book);
	
	Collection<LiteBookRequest> book2dto(Collection<Book> books);

	default CommentRequest map(Comment comment) {
		return CommentMapper.COMMENT_MAPPER.comment2dto(comment);
	}
	
	default Comment map(CommentRequest commentRequest) {
		return CommentMapper.COMMENT_MAPPER.dto2comment(commentRequest);
	}
	
	default Score map(int score) {
		return Score.findByValue(score);
	}
	
	default int map(Score score) {
		return score.getValue();
	}
}
