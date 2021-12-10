package es.jmc.practica.view.db.repositories;

import es.jmc.practica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
