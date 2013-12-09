package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import models.Comment;
import models.Like;
import models.Video;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class CommentDao {

	@Transactional
	public static void addCommentToVideo(Comment comment) {
		JPA.em().persist(comment);
	}

	@Transactional
	public static List<Comment> getCommentsOfVideo(Video video) {
		Query commentQuery = JPA.em()
				.createNamedQuery("Comment.findCommentsByVideoId")
				.setParameter("video", video);
		List<Comment> commentList = new ArrayList<>();
		if (commentQuery.getMaxResults() > 0) {
			commentList = commentQuery.getResultList();
		}
		return commentList;
	}

	@Transactional
	public static void deleteComment(Comment comment) {
		JPA.em().remove(comment);
	}

}
