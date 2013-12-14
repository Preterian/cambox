package controllers;

import java.io.File;
import java.util.Date;

import javax.persistence.NoResultException;

import controllers.Login.LoginHelper;
import dao.UserDao;
import dao.VideoDao;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.*;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;
import static play.data.Form.*;
import views.html.*;
import models.*;

public class SignUp extends Controller {

	/**
	 * Defines a form wrapping the User class.
	 */
	final static Form<User> signupForm = form(User.class);
	static String message = null;

	/**
	 * Display a blank form.
	 */
	public static Result blank() {
		return ok(signUp.render(message, signupForm));
	}

	public static Result submit() {
		final Form<User> signUpForm = form(User.class).bindFromRequest();
		MultipartFormData body = request().body().asMultipartFormData();
		if (signUpForm.hasErrors()) {
			System.err.println("Some errors occured while signUp");
			System.err.println(signUpForm.toString());
			return badRequest(signUp.render(message, signUpForm));
		} else {
			try {
				final String email = signUpForm.get().getEmail();
				User tmpUser = UserDao.findUserByEmail(email);
				if (tmpUser == null) {
					System.err.println("User not found");
					System.err.println("Registering");

					FilePart filePart = body.getFile("picture");
					
					if (filePart != null) {
						String fileName = filePart.getFilename();

						String path = "public/users/".concat(email)
								.concat(File.separator);

						File file = filePart.getFile();
						File theDir = new File(path);
						if (!theDir.exists()) {
							theDir.mkdirs();
						}

						path = path.concat(File.separator).concat(fileName);
						file.renameTo(new File(path));

					} else {
						flash("error", "Missing file");						
					}

					User user = new User(signUpForm.get().getUsername(),
							signUpForm.get().getName(), signUpForm.get()
									.getSurname(), email, signUpForm.get()
									.getPassword());
					System.out.println(user.getUsername());
					UserDao.saveUser(user);

					return redirect(routes.Application.myBox());

				} else {
					System.err.println("Uer already exist!!!!!!!!!!!!ElSE");
					//flash("success", "Uer already exist!");
					message = "User already exist!";
					return forbidden(signUp.render(message, signupForm));
				}
			} catch (NoResultException e) {
				System.err.println("Uer already exist!!!!!!!!!!!!CATVH");
				message = e.toString();
				return forbidden(signUp.render(message, signupForm));
			}

		}

	}

}