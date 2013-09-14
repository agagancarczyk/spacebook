package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EditProfile extends Controller {
	public static void index() {
		User user = Accounts.getLoggedInUser();
		render(user);
	}

	public static void editDetails(String firstName, String secondName,
			String email, String password, Integer age, String nationality) {
		User user = Accounts.getLoggedInUser();

		if (firstName.length() > 0) {
			user.firstName = firstName;
		}
		if (secondName.length() > 0) {
			user.secondName = secondName;
		}
		if (email.length() > 0) {
			user.email = email;
		}
		if (password.length() > 0) {
			user.password = password;
		}

		user.age = age;

		if (nationality.length() > 0) {
			user.nationality = nationality;
		}
		user.save();

		Logger.info("First Name changed to " + firstName
				+ "Second Name changed to " + secondName + "Email changed to "
				+ email + "Age changed to" + age + "Nationality changed to"
				+ nationality);
		index();

	}
}
