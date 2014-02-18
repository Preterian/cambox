package controllers;

import static play.data.Form.form;

import java.util.List;

import notifiers.Mails;
import dao.UserDao;
import dao.VideoDao;
import models.*;
import play.*;
import play.api.templates.Html;
import play.mvc.*;
import play.data.Form;
import play.data.validation.Constraints.Required;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render(VideoDao.getAllVideos()));
		//return ok(index.render(session("email")));
	}

	// allowed only to authorized users
	@Security.Authenticated(Secured.class)
	public static Result myBox() {
		String email = session("email");
		
		Mails.welcome(getUserByEmail(email));
		return ok(mybox.render(getUserByEmail(email)));
	}

	// TODO replace with real user from db
	private static User getUserByEmail(String email) {
		return UserDao.findUserByEmail(email);
	}
	
	public static Result search(){
		Form<SearchHelper> videoSearchForm = null;
		videoSearchForm = form(SearchHelper.class).bindFromRequest();
		if (videoSearchForm.hasErrors()) {
				System.err.println("Some errors occured while searching");
				System.err.println(videoSearchForm.toString());
		}else {
			List<Video> videos = VideoDao.getVideosForSearchPhrase(videoSearchForm.get().searchPhrase);			
			return ok(search.render(videoSearchForm.get().searchPhrase, videos));
		}		
		String email = session("email");
		return ok();
	}
	
	public static class SearchHelper{
		@Required
		public String searchPhrase;		
	}
	
}
