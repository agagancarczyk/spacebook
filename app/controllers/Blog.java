package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Blog extends Controller {
	public static void blog() {
		User user = Accounts.getLoggedInUser();
		render(user);
	}

	public static void newPost(String postText) {
		User author = Accounts.getLoggedInUser();
		author.newPost(postText);

		Logger.info("Post from user " + author.firstName + ' '+
				"post txt.: "+postText);
		Logger.info("blog size "+author.posts.size());

		blog();
	}
}