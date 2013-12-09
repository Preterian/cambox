package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

/* this class adding possibility to check if user is logged in 
 * by adding annotation @Security.Authenticated(Secured.class) before method
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Login.blank());
    }
}