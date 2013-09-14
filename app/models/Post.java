package models;

import java.util.Date;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Post extends Model {
	@Lob
	public String postText;
	public Date date;

	@ManyToOne
	public User author;


	public Post(String postText, User author) {
		this.postText = postText;
		this.date = new Date();
		this.author=author;
	}
}
