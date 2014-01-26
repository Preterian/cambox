package controllers;

import static play.data.Form.form;

import java.io.File;

import javax.persistence.NoResultException;

import dao.UserDao;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;

public class UserController extends Controller {

	static Form<User> editUserForm = form(User.class);

	public static Result editUser() {
		String email = session("email");
		return ok(editbox.render(getUserByEmail(email), editUserForm));
	}

	private static User getUserByEmail(String email) {
		return UserDao.findUserByEmail(email);
	}

	public static Result submit() {
		editUserForm = form(User.class).bindFromRequest();
		MultipartFormData body = request().body().asMultipartFormData();
		String email = session("email");
		if (editUserForm.hasErrors()) {
			System.err.println("Some errors occured while editing");
			System.err.println(editUserForm.toString());			
			return badRequest(editbox.render(getUserByEmail(email), editUserForm));
		} else {
			try {
				User tmpUser = UserDao.findUserByEmail(email);				
				if (tmpUser != null) {
					System.err.println("Editing user!!!!!!!!!!!!");
					tmpUser.setUsername(editUserForm.get().getUsername());
					tmpUser.setEmail(editUserForm.get().getEmail());
					tmpUser.setPassword(editUserForm.get().getPassword());
					tmpUser.setName(editUserForm.get().getName());
					tmpUser.setSurname(editUserForm.get().getSurname());
					tmpUser.setAge(editUserForm.get().getAge());
					tmpUser.setGender(editUserForm.get().getGender());
					tmpUser.setCountry(editUserForm.get().getCountry());
					tmpUser.setCity(editUserForm.get().getCity());
					
/*
					FilePart filePart = body.getFile("picture");

					if (filePart != null) {
						String fileName = filePart.getFilename();

						String path = "public/users/".concat(email).concat(
								File.separator);

						File file = filePart.getFile();
						File theDir = new File(path);
						if (!theDir.exists()) {
							theDir.mkdirs();
						}

						path = path.concat(File.separator).concat(fileName);
						file.renameTo(new File(path));

					} else {
						flash("error", "Missing file");
					}*/
					
					UserDao.updateUser(tmpUser);

					return redirect(routes.Application.myBox());

				} else {
					System.err.println("Uer already exist!!!!!!!!!!!!");
					return ok();
				}
			} catch (NoResultException e) {
				System.err.println("Uer already exist!!!!!!!!!!!!");
				return ok();
			}

		}
	}

}
