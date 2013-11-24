package controllers;

import play.*;
import play.api.templates.Html;
import play.mvc.*;
import views.html.*;

public class Video extends Controller {

	public static Result video(String video_id) {
		//checking if id is correct
		long id = 0;
		try { 
	        id = Long.parseLong(video_id); 
	    } catch(NumberFormatException e) { 
	        return badRequest("Video not found!"); 
	    }
	     return ok(video.render(id));

	}

}
