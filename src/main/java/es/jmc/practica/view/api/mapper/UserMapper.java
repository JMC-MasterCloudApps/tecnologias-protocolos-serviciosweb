package es.jmc.practica.view.api.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.model.User;
import es.jmc.practica.view.api.dtos.UserRequest;

@Mapper
public interface UserMapper {

	final static UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
	
	UserRequest user2dto(User user);
	
	Collection<UserRequest> user2dto(Collection<User> users);
	
	User dto2user(UserRequest request);
	
	Collection<User> dto2user(Collection<UserRequest> requests);
	
}
