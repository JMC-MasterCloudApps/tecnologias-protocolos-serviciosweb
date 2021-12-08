package es.jmc.practica.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

  private long id;
  private String nick;  // unique
  private String email;

}
