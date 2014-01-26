package dao;

import java.util.List;

import javax.persistence.Query;

import models.Dislike;
import models.Like;
import models.User;
import models.Video;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class LikeDao {

	private static boolean result = false;
	@Transactional
	public static boolean makeLike(final String user, final String video) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Like newLike = null;
				User tempUser = null;
				Video tempVideo = null;
				Query userQuery = JPA.em().createNamedQuery("User.findByName")
						.setParameter("username", user);
				Query videoQuery = JPA.em()
						.createNamedQuery("Video.findByName")
						.setParameter("name", video);
				if (userQuery.getResultList().size() > 0
						&& videoQuery.getResultList().size() > 0) {
					tempUser = (User) userQuery.getSingleResult();
					tempVideo = (Video) videoQuery.getSingleResult();
				}
				if ((tempUser != null) && (tempVideo != null)) {
					// tempVideo.setTimesLiked(tempVideo.getTimesLiked() + 1);
					Query likeQuery = JPA.em()
							.createNamedQuery("Like.findLikesByVideoId")
							.setParameter("video", tempVideo);
					List<Like> checkList = likeQuery.getResultList();
					if (checkList.size() > 0) {
						newLike = checkList.get(0);
					}
					if (newLike == null) {
						newLike = new Like(tempUser, tempVideo);
						JPA.em().persist(newLike);
						result = true;
					} else {
						JPA.em().remove(newLike);
						result = false;
					}
				} else {
					result = false;
				}
			}
		});
		return result;
	}

	@Transactional
	public static boolean makeDisLike(String user, String video) {
		Dislike dislike = null;
		User tempUser = null;
		Video tempVideo = null;
		Query userQuery = JPA.em().createNamedQuery("User.findByName")
				.setParameter("username", user);
		Query videoQuery = JPA.em().createNamedQuery("Video.findByName")
				.setParameter("name", video);
		if (userQuery.getMaxResults() > 0 && videoQuery.getMaxResults() > 0) {
			tempUser = (User) userQuery.getSingleResult();
			tempVideo = (Video) videoQuery.getSingleResult();
		}
		if ((tempUser != null) && (tempVideo != null)) {
			// tempVideo.setTimesLiked(tempVideo.getTimesLiked() + 1);
			Query dislikeQuery = JPA.em()
					.createNamedQuery("Dislike.findDisLikesByVideoId")
					.setParameter("video", tempVideo);
			List<Dislike> checkList = dislikeQuery.getResultList();
			if (checkList.size() > 0) {
				dislike = checkList.get(0);
			}
			if (dislike == null) {
				dislike = new Dislike(tempUser, tempVideo);
				JPA.em().persist(dislike);
				return true;
			} else {
				JPA.em().remove(dislike);
				return false;
			}
		} else {
			return false;
		}
	}

	@Transactional
	public static List<Like> findLikesByVideoId(Video video) {
		Query likeQuery = JPA.em().createNamedQuery("Like.findLikesByVideoId")
				.setParameter("video", video);
		List<Like> likes = likeQuery.getResultList();
		return likes;
	}

	@Transactional
	public static List<Dislike> findDisLikesByVideoId(Video video) {
		Query dislikeQuery = JPA.em()
				.createNamedQuery("Dislike.findDisLikesByVideoId")
				.setParameter("video", video);
		List<Dislike> dislikes = dislikeQuery.getResultList();
		return dislikes;
	}

	@Transactional
	public static void deleteLike(Like like) {
		JPA.em().remove(like);
	}

}
