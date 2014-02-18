package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;





import models.User;
import models.Video;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class VideoDao {

	static Video tempVideo = null;
	static List<Video> videoList = null;

	@Transactional
	public static List<Video> getAllVideos() {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Query videoQuery = JPA.em().createNamedQuery(
						"Video.findAllVideo");
				videoList = videoQuery.getResultList();
			}
		});
		return videoList;
	}

	@Transactional
	public static List<Video> getVideosForSearchPhrase(final String searchPhrase) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {			
				
				Query videoQuery = JPA.em()
						.createNamedQuery("Video.findVideoLike")
						.setParameter("name", searchPhrase).setParameter("description", searchPhrase);
				videoList = videoQuery.getResultList();
			}
		});
		return videoList;
	}
	
	@Transactional
	public static List<Video> getVideosByCategories(final String category) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Query videoQuery = JPA.em()
						.createNamedQuery("Video.findByCategory")
						.setParameter("category", category);
				videoList = videoQuery.getResultList();
			}
		});
		return videoList;
	}

	@Transactional
	public static List<Video> getVideoByUser(User user) {
		List<Video> outputList = new ArrayList<>();
		outputList.addAll(user.getVideos());
		return outputList;
	}

	@Transactional
	public static Video findVideoByName(String name) {
		Video tempVideo = null;
		Query videoQuery = JPA.em().createNamedQuery("Video.findByName")
				.setParameter("name", name);
		if (videoQuery.getResultList().size() > 0)
			tempVideo = (Video) videoQuery.getSingleResult();
		return tempVideo;
	}

	@Transactional
	public static Video findVideoByID(final int id) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Query videoQuery = JPA.em().createNamedQuery("Video.findByID")
						.setParameter("videoId", id);
				if (videoQuery.getResultList().size() > 0)
					tempVideo = (Video) videoQuery.getSingleResult();
			}
		});
		return tempVideo;
	}

	@Transactional
	public static void saveVideo(Video video, String username) {
		Query userQuery = JPA.em().createNamedQuery("User.findByName")
				.setParameter("username", username);
		User tempUser = (User) userQuery.getSingleResult();
		video.setUserUploader(tempUser);
		JPA.em().persist(video);
	}

	@Transactional
	public static void saveVideoByEmail(final Video video, final String email) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Query userQuery = JPA.em().createNamedQuery("User.findByEmail")
						.setParameter("email", email);
				User tempUser = (User) userQuery.getSingleResult();
				video.setUserUploader(tempUser);
				JPA.em().persist(video);
			}
		});
	}

	@Transactional
	public static void deteleVideo(final Video video) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				JPA.em().remove(JPA.em().contains(video) ? video : JPA.em().merge(video));
			}
		});
	}

	@Transactional
	public static Video updateVideo(final Video video) {
		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				tempVideo = JPA.em().merge(video);
			}
		});
		return tempVideo;
	}

}
