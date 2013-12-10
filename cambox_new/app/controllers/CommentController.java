package controllers;

import static play.data.Form.form;
import models.Comment;
import dao.CommentDao;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class CommentController extends Controller {
	
	public static Result addComment() {
		final Form<Comment> commentForm = form(Comment.class).bindFromRequest();
		if (commentForm.hasErrors()) {
			System.err.println("Some errors occured while signUp");
			System.err.println(commentForm.toString());
			return badRequest();
		} else {			
			Comment comment = new Comment(commentForm.get().getComment(),
					commentForm.get().getUser(), commentForm.get().getVideo());
			CommentDao.addCommentToVideo(comment);
			return ok();
		}
	}

}
