package es.jmc.practica.controller;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jmc.practica.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private List<User> users;
  private AtomicLong idCounter = new AtomicLong();
  
  @PostConstruct
  private void setUp() {

    users = Collections.synchronizedList(new ArrayList<>());

    var user = new User(idCounter.getAndIncrement(), "Joe", "joe@mail.com");
    users.add(user);

    user = new User(idCounter.getAndIncrement(), "Ann", "ann@mail.com");
    users.add(user);
  }

  public User create(User user) {

	user.setId(idCounter.getAndIncrement());
    
    log.info("User created: {}", users.add(user));

    return user;
  }
  
  public Collection<User> getUsers() {

    return users;
  }

  public Optional<User> getUser(long id) {

    return users.stream()
        .filter(user -> user.getId() == id)
        .findFirst();
  }

  public Optional<User> update(User user) {
	  
	  for(User item : users) {
		  if (item.getId() == user.getId()) {
			  log.info("Patching user...");
			  log.info(user.toString());
			  item.setEmail(user.getEmail());
			  log.info(item.toString());
			  return of(item);
		  }
	  }
	  
	  return empty();
  }

  public void delete(User user) {

    users.remove(user);
  }
  
}
