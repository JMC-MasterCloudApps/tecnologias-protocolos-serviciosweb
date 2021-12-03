package es.jmc.practica1.models;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class User {

  public interface Lite { }

  private final long id;
  @JsonView(value = {Lite.class})
  private final String nick;

  @JsonView(value = {Lite.class})
  private final String email;

}
