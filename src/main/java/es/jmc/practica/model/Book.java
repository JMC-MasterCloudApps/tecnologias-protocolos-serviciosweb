package es.jmc.practica.model;

import java.util.ArrayList;
import java.util.Collections;
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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column	private String title;
	@Column private String summary;
	@Column private String author;
	@Column private String publishHouse;
	@Column private int publishYear;

	@OneToMany(
			mappedBy = "book",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER)
	private List<Comment> comments;
	
	public Book(
			String title,
			String summary, 
			String author,
			String publishHouse,
			int publishYear) {

		this.title = title;       
		this.summary = summary;
		this.author = author;
		this.publishHouse = publishHouse;
		this.publishYear = publishYear;
		this.comments = Collections.synchronizedList(new ArrayList<>());
	}
	
	public boolean addComment(Comment comment) {
		return this.comments.add(comment);
	}

	@Override
	public String toString() {
	
		return "Book: [id:" + id + "]" + 
		" [title: " + title + "]";
	}
}       
