package es.jmc.practica.view.api.mapper;

import java.util.Collection;

import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.jmc.practica.view.api.dtos.UserRequest;

@Mapper
public interface UserMapper {

	final static UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
	
	UserRequest book2dto(User user);
	
	Collection<UserRequest> book2dto(Collection<User> users);
}
