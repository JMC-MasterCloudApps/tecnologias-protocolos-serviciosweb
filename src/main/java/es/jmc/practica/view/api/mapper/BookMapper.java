package es.jmc.practica.view.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.model.Book;
import es.jmc.practica.view.api.dtos.BookRequest;

@Mapper
public interface BookMapper {

	final static BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);
	
	BookRequest book2dto(Book book);
	
	Collection<BookRequest> book2dto(Collection<Book> books);
}
