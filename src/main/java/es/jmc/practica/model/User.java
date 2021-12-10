package es.jmc.practica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @JsonIgnore
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Comment> comments;
  @Column private String nick;  // unique
  @Column private String email;

  public User(String nick, String email) {

    this.comments = new ArrayList<>();
    this.nick = nick;
    this.email = email;
  }
}
