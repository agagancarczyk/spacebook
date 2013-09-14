package models;

import javax.persistence.Entity;

import play.Logger;
import play.db.jpa.Model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Blob;

@Entity
public class User extends Model {
	public String firstName;
	public String secondName;
	public String email;
	public String password;
	public String statusText;
	public Blob profilePicture;
	public Integer age;
	public String nationality;
	public Blob profileThumbnail;
	public String securityQuestion;
	public String answer;
	public Date date;

	@OneToMany(mappedBy = "sourceUser")
	public List<Friendship> friendships = new ArrayList<Friendship>();

	@OneToMany(mappedBy = "to")
	public List<Message> inbox = new ArrayList<Message>();

	@OneToMany(mappedBy = "from")
	public List<Message> outbox = new ArrayList<Message>();
	
	@OneToMany
	public List<Post> posts = new ArrayList<Post>();

	
	

	public User(String firstName, String secondName, String email,
			String password, Integer age, String nationality,
			String securityQuestion, String answer) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.password = password;
		this.age = age;
		this.nationality = nationality;
		this.securityQuestion = securityQuestion;
		this.answer = answer;
	}

	public static User findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	public void befriend(User friend) {
		Friendship friendship = new Friendship(this, friend);
		friendships.add(friendship);
		friendship.save();
		save();
	}

	public void unfriend(User friend) {
		Friendship thisFriendship = null;

		for (Friendship friendship : friendships) {
			if (friendship.targetUser == friend) {
				thisFriendship = friendship;
			}
		}
		friendships.remove(thisFriendship);
		thisFriendship.delete();
		save();
	}

	public void sendMessage(User to, String messageText) {
		Message message = new Message(this, to, messageText);
		outbox.add(message);
		to.inbox.add(message);
		message.save();
	}
	
	public void newPost(String postText) {
		Post post = new Post(postText, this);
		post.save();
		posts.add(post);
		this.save();
	}
}
