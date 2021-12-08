package es.jmc.practica.view.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.jmc.practica.view.database.entities.UserDAO;

public interface UserRepository extends JpaRepository<UserDAO, Long> {
}
