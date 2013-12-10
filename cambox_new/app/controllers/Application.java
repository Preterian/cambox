package controllers;

import dao.UserDao;
import models.*;
import play.*;
import play.api.templates.Html;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	// allowed only to authorized users
	@Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(index.render(session("email")));
	}

	// allowed only to authorized users
	@Security.Authenticated(Secured.class)
	public static Result myBox() {
		String email = session("email");
		return ok(mybox.render(getUserByEmail(email)));
	}

	// TODO replace with real user from db
	private static User getUserByEmail(String email) {
		return UserDao.findUserByEmail(email);
	}
		
}
