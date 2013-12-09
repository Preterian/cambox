package controllers;
import play.mvc.*;
import play.data.*;
import play.data.validation.Constraints.Required;
import static play.data.Form.*;
import views.html.*;
import models.*;

public class Login extends Controller {
	// class for validation
	public static class LoginHelper {
		@Required
		public String email;
		@Required
		public String password;

		public String validate() {
			if (User.authenticate(email, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}
	}
	
	// Login page
    public static Result blank() {
    	return ok(login.render(form(LoginHelper.class)));
    }
    
 // Login page
    public static Result logOut() {
    	session().clear();
    	return redirect(routes.Application.index());
    }
    
    // this method uses validate() of LoginHelper class
    public static Result authenticate() {
        Form<LoginHelper> loginForm = form(Login.LoginHelper.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(routes.Application.index());
        }
    }
}