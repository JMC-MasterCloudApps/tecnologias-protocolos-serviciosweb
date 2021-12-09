package es.jmc.practica.view.database.repositories;

import es.jmc.practica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  
}
