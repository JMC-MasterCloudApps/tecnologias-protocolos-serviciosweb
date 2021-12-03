package es.jmc.practica1.view.database.repositories;

import es.jmc.practica1.view.database.entities.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
}
