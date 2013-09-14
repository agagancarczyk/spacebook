package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class PublicProfile extends Controller {
	public static void index() {
		render();
	}

	public static void visit(Long id) {
		User user = User.findById(id);
		Logger.info("Just visiting the page for " + user.firstName + " "
				+ user.secondName);
		render(user);
	}

	public static void sendMessage(Long id, String messageText) {
		String userId = session.get("logged_in_userid");
		User fromUser = User.findById(Long.parseLong(userId));
		User toUser = User.findById(id);

		Logger.info("Message from user " + fromUser.firstName + ' '
				+ fromUser.secondName + " to " + toUser.firstName + ' '
				+ toUser.secondName + ": " + messageText);

		fromUser.sendMessage(toUser, messageText);
		visit(id);
	}
}