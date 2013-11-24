package controllers;
import play.mvc.*;
import play.data.*;
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
        return TODO;
    }
  
}