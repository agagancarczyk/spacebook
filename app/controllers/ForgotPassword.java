package controllers;

import models.User;
import play.*;
import play.mvc.*;

import java.util.*;

public class ForgotPassword extends Controller {

	public static void index() {
		render();
	}

	public static void getQuestion(String email) {
		Logger.info("Displaying question selected by user during registration "
				+ email);
		boolean found = false;
		User user = User.findByEmail(email);
		String question = email + " not found";

		if (user != null && email.equals(user.email)) {
			found = true;
			question = user.securityQuestion;
			session.put("email", email);
			Logger.info("getQuestion session email " + session.get("email"));
		}
		render(email, question, found);
	}

	public static void checkAnswer(String answer) {
		Logger.info("Displaying answer used by user during registration "
				+ answer);
		boolean correctAns = false;
		if (session.contains("email")) {
			String email = session.get("email");
			User user = User.findByEmail(email);
			if (user.answer.equals(answer)) {
				correctAns = true;
				render(user, correctAns);
			} else {
				render();
			}
		}
	}

	public static void changePassword(String password) {
		Logger.info("Displaying new password changed by user " + password);
		if (session.contains("email")) {
			String email = session.get("email");
			User user = User.findByEmail(email);
			Logger.info("user password " + user.password);
			Logger.info("passed in pwd " + password);
			user.password = password;
			user.save();
			Accounts.index();
		}
	}
}
