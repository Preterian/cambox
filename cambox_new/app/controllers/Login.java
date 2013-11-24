package controllers;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;

import models.*;

public class Login extends Controller {
    

    public static Result blank() {
        return ok(login.render());
    }
    
    public static Result submit() {
        return TODO;
    }
  
}