package es.jmc.practica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column private String content;
	@Column private Score score;

	@ManyToOne @JsonIgnore
	private Book book;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User author;

	public Comment (String content, Score score) {

		this.content = content;
		this.score = score;
	}
}
