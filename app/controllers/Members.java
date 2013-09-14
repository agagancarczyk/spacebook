package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Members extends Controller {
	public static void index() {
		User me = Accounts.getLoggedInUser();

		List<User> friends = new ArrayList<User>();
		List<User> users = User.findAll();
		users.remove(me);

		for (User user : users) {
			for (Friendship friendship : me.friendships) {
				if (friendship.targetUser == user) {
					Logger.info("Friend: " + user.firstName);
					friends.add(user);
				}
			}
		}
		users.removeAll(friends);
		render(users);
	}

	public static void follow(Long id) {
		User friend = User.findById(id);
		User me = Accounts.getLoggedInUser();
		me.befriend(friend);
		Home.index();
	}
}