package controllers;
import controllers.Login.LoginHelper;
import dao.UserDao;
import play.mvc.*;
import play.data.*;
import play.data.validation.Constraints.Required;
import static play.data.Form.*;
import views.html.*;
import models.*;

public class SignUp extends Controller {
    
    /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> signupForm = form(User.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok(signUp.render(signupForm));
    }
    
   
    
    public static Result submit() {
    	 Form<User> signUpForm = form(User.class).bindFromRequest();
         if(signUpForm.hasErrors()) {
        	 System.err.println("Some errors occured while signUp");
        	 System.err.println(signUpForm.toString());
             return badRequest(signUp.render(signUpForm));
         } else {        	 
        	 System.err.println("Registering");
             User user = new User(signUpForm.get().getUsername(), signUpForm.get().getName(), signUpForm.get().getSurname(), signUpForm.get().getEmail(), signUpForm.get().getPassword());
             System.out.println(user.getUsername());
             UserDao.saveUser(user);
             return redirect(routes.Application.index());
         }
       
    }
  
}