package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Accounts extends Controller {

	public static void signup(boolean found) {
		Logger.info("Signup " + found);
		render(found);
	}

	public static void login() {
		String str = "";
		if (session.contains("Wrong Login")) {
			str = session.get("Wrong Login");
		}
		boolean wrongLogin = (str.equals("true")) ? true : false;

		render(wrongLogin);
	}

	public static void logout() {
		session.clear();
		index();
	}

	public static void index() {
		render();
	}

	public static void policy() {
		render();
	}

	public static void register(String firstName, String secondName,
			String email, String password, Integer age, String nationality,
			String securityQuestion, String answer) {
		Logger.info(firstName + " " + secondName + " " + email + " " + password
				+ " " + age + " " + nationality);

		boolean found = true;
		User user = User.findByEmail(email);
		if (user == null) {
			found = false;
			User newUser = new User(firstName, secondName, email, password,
					age, nationality, securityQuestion, answer);
			newUser.save();
			Accounts.authenticate(email, password);

		} else {
			Logger.info("This email has already been taken", email);
			Accounts.signup(found);
		}
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + password);
		User user = User.findByEmail(email);

		if ((user != null) && (user.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_userid", user.id);
			Home.index();
		} else {
			Logger.info("Authentication failed");
			boolean wrongLogin = true;
			session.put("Wrong Login", wrongLogin);
			Accounts.login();
		}
	}

	public static User getLoggedInUser() {
		User user = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			user = User.findById(Long.parseLong(userId));
		} else {
			login();
		}
		return user;
	}
}
