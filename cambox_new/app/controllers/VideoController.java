package controllers;

import java.util.ArrayList;
import java.util.List;

import dao.LikeDao;
import dao.VideoDao;
import exceptions.VideoNotFoundException;
import models.*;
import views.*;
import play.*;
import play.api.templates.Html;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class VideoController extends Controller {

	public static Result video(String video_id) {
		// checking if id is correct
		long id = 0;
		try {
			id = Long.parseLong(video_id);
		} catch (NumberFormatException e) {
			return badRequest(notFound.render(video_id));
		}
		try {
			return ok(video.render(getVideoById(id)));
		} catch (VideoNotFoundException e) {
			return badRequest(notFound.render(video_id));
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result collection() {
		String currentUserEmail = session().get("email");
		List<Video> videos = getVideoCollection(currentUserEmail);
		return ok(collection.render(videos));
	}
	// TODO get all video of current logged user

	@Transactional
	public static List<Video> getVideoCollection(String currentUserEmail) {
		// VideoDao videoDao = new VideoDao();
		List<Video> videos = new ArrayList<Video>();
		try {
			videos.add(getVideoById(1));
			videos.add(getVideoById(2));
			videos.add(getVideoById(3));
			videos.add(getVideoById(1));
			videos.add(getVideoById(2));
			videos.add(getVideoById(3));
			videos.add(getVideoById(1));
			videos.add(getVideoById(2));
			videos.add(getVideoById(3));

			// Тестування Бази
			JPA.withTransaction(new play.libs.F.Callback0() {
				@Override
				public void invoke() throws Throwable {
					// Video video = new Video("a", "a", "a", 3, 2, "a",
					// "a","a");
					// VideoDao.saveVideo(video, "olko");

					// Video video = VideoDao.findVideoByName("a");
					// VideoDao.deteleVideo(video);

					// List<Video> list = VideoDao.getVideosByCategories("a");
					// for(int i=0;i<list.size();i++){
					// VideoDao.deteleVideo(list.get(i));
					// }

					// LikeDao.makeLike("olko", "b");
					// LikeDao.makeDisLike("olko", "b");

					// Video video = VideoDao.findVideoByName("a");
					// List<Like> likes = LikeDao.findLikesByVideoId(video);
					// LikeDao.deleteLike(likes.get(0));

					// Video video = VideoDao.findVideoByName("a");
					// List<Like> likes = LikeDao.findLikesByVideoId(video);
					// LikeDao.deleteLike(likes.get(0));
					
					

				}
			});

		} catch (VideoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return videos;
	}
	// TODO replace with real video from db
	private static Video getVideoById(long id) throws VideoNotFoundException {
		if (id == 1) {
			Video video = new Video("Yosemite HD", "Funny", "This whole ",
					34535, 345, "videos/1.mp4", "videos/1.jpg",
					"videos/big1.jpg");
			video.setVideoId((int) id);
			return video;
		}

		else if (id == 2) {
			Video video = new Video("Yosemite HD", "Funny", "This whole ",
					34535, 345, "videos/2.mp4", "videos/2.jpg",
					"videos/big2.jpg");
			video.setVideoId((int) id);
			return video;
		} else if (id == 3) {
			Video video = new Video("Yosemite HD", "Funny", "This who", 34535,
					345, "videos/3.mp4", "videos/3.jpg", "videos/big3.jpg");
			video.setVideoId((int) id);
			return video;
		}

		else
			throw new VideoNotFoundException();
	}
}
