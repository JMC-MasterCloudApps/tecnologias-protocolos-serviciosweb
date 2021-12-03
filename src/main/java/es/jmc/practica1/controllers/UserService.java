package es.jmc.practica1.controllers;

import es.jmc.practica1.models.Book;
import es.jmc.practica1.models.User;
import es.jmc.practica1.view.api.dtos.UserRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

  public Collection<User> getUsers() {

    return users;
  }

  public Optional<User> getUser(long id) {

    return users.stream()
        .filter(user -> user.getId() == id)
        .findFirst();
  }

  public User create(UserRequest userRequest) {

    var user = new User(
        idCounter.getAndIncrement(),
        userRequest.nick(),
        userRequest.email());

    users.add(user);

    return user;
  }

  public void delete(User user) {

    // TODO remove if there is no comments from this user
    users.remove(user);
  }
}
