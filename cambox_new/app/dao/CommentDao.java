package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Comment;
import models.Like;
import models.User;
import models.Video;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class CommentDao {

	static List<Comment> commentList = null;
	
	
	@Transactional
	public static void addCommentToVideo(final Comment comment) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				JPA.em().persist(comment);
			}
		});
	}
	
	
	@Transactional
	public static List<Comment> getCommentsOfVideo(final Video video) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Query commentQuery = JPA.em()
						.createNamedQuery("Comment.findCommentsByVideoId")
						.setParameter("video", video);
				
				if (commentQuery.getMaxResults() > 0) {
					commentList = commentQuery.getResultList();
				}
			}
		});
		return commentList;
	}

	@Transactional
	public static void deleteComment(Comment comment) {
		JPA.em().remove(comment);
	}

}
