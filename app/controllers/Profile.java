package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import play.db.jpa.Blob;

public class Profile extends Controller {
	public static void index() {
		User user = Accounts.getLoggedInUser();
		render(user);
	}

	public static void changeStatus(String statusText) {
		User user = Accounts.getLoggedInUser();
		user.statusText = statusText;
		user.save();
		Logger.info("Status changed to " + statusText);
		index();
	}

	public static void getPicture(Long id) {
		User user = User.findById(id);
		Blob picture = user.profilePicture;
		if (picture.exists()) {
			response.setContentTypeIfNotSet(picture.type());
			renderBinary(picture.get());
		}
	}

	public static void uploadPicture(Long id, Blob picture) {
		User user = User.findById(id);
		user.profilePicture = picture;
		user.save();
		index();
	}

	public static void getThumbnail(Long id) {
		User user = User.findById(id);
		Blob thumbnail = user.profileThumbnail;
		if (thumbnail.exists()) {
			response.setContentTypeIfNotSet(thumbnail.type());
			renderBinary(thumbnail.get());
		}
	}

	public static void uploadThumbnail(Long id, Blob thumbnail) {
		User user = User.findById(id);
		user.profileThumbnail = thumbnail;
		user.save();
		index();
	}
}