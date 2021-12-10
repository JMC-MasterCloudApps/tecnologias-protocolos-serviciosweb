package es.jmc.practica.view.db.repositories;

import es.jmc.practica.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
