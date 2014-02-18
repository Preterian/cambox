package controllers;

import static play.data.Form.form;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;

import com.google.common.primitives.Ints;

import dao.CommentDao;
import dao.LikeDao;
import dao.UserDao;
import dao.VideoDao;
import exceptions.VideoNotFoundException;
import models.*;
import views.*;
import play.*;
import play.libs.*;
import play.api.templates.Html;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;

public class VideoController extends Controller {

	final static Form<Video> uploadVideoForm = form(Video.class);
	static Form<Comment> commentsForm = form(Comment.class);
	static String videoID = null;	

	public static Result video(String video_id) {
		// checking if id is correct
		videoID = video_id;
		long id = 0;
		try {
			id = Long.parseLong(video_id);
		} catch (NumberFormatException e) {
			return badRequest(notFound.render(video_id));
		}
		try {
			Video videoObj = getVideoById(id);
			incViews(videoObj);
			String phrase = videoObj.getName();
			return ok(video.render(videoObj,
					CommentDao.getCommentsOfVideo(videoObj), commentsForm, VideoDao.getVideosForSearchPhrase(phrase)));
			// return ok(video.render(getVideoById(id)));
		} catch (VideoNotFoundException e) {
			return badRequest(notFound.render(video_id));
		}
	}

	public static Result index() {
		return ok(upload.render(uploadVideoForm));
	}

	// allowed only to authorized users
	@Security.Authenticated(Secured.class)
	public static Result addComment(int idd) throws VideoNotFoundException {

		final Form<Comment> commentForm = form(Comment.class).bindFromRequest();
		if (commentForm.hasErrors()) {
			System.err.println("Some errors occured while signUp");
			System.err.println(commentForm.toString());
			return badRequest();
		} else {
			System.err.println("Adding Comments!!!!!!!!!!");
			Video videoObj = getVideoById(idd);
			Comment comment = new Comment(commentForm.get().getComment(),
					UserDao.findUserByEmail(session("email")), videoObj);

			videoObj.getComments().add(comment);

			CommentDao.addCommentToVideo(comment);

			String phrase = videoObj.getName();
			return ok(video.render(videoObj,
					CommentDao.getCommentsOfVideo(videoObj), commentsForm, VideoDao.getVideosForSearchPhrase(phrase)));
		}
	}

	// allowed only to authorized users
	@Security.Authenticated(Secured.class)
	public static Result uploadVideo() {
		String currentUserEmail = session().get("email");

		Form<Video> uploadVideoForm = form(Video.class).bindFromRequest();

		if (uploadVideoForm.hasErrors()) {
			System.err.println("Some errors occured while uploadVideo");
			return badRequest(upload.render(uploadVideoForm));
		} else {

			MultipartFormData body = request().body().asMultipartFormData();

			FilePart filePart = body.getFile("video");

			if (filePart != null) {
				String fileName = filePart.getFilename();
				String contentType = filePart.getContentType();

				String category = uploadVideoForm.get().getCategory();
				String title = uploadVideoForm.get().getName();
				String path = "public/users/".concat(currentUserEmail)
						.concat("/").concat("videos/").concat(category);
				
				File file = filePart.getFile();
				File theDir = new File(path);
				if (!theDir.exists()) {
					theDir.mkdirs();
				}
				
				path = path.concat("/").concat(title).concat("_")
						.concat(fileName);
				file.renameTo(new File(path));

				String imgPath = genImgPath(path, title.concat("_").concat(fileName));
				new DecodeAndCaptureFrames(path, title, imgPath);
			
				path = path.replace("public/", "");
				Video video = new Video(title, category, uploadVideoForm.get()
						.getDescription(), 0, path, new Date());
			
				video.setBigPreviewUrl(imgPath);
				VideoDao.saveVideoByEmail(video, currentUserEmail);

				return ok(mybox.render(UserDao
						.findUserByEmail(currentUserEmail)));

			} else {
				flash("error", "Missing file");
				return redirect(routes.Application.index());
			}

		}

	}
	
	private static String genImgPath(String fileNamePath, String fileName){
		int start = fileNamePath.indexOf('/');
		 int a = fileNamePath.lastIndexOf('/');
   	  	 String imgPath = "";
   	  	 for(int i = start; i < a; i++){
   	  		 imgPath += fileNamePath.charAt(i);
   	  	 }
   	  	 
   	  	 return imgPath.concat("/").concat(fileName).concat(".png"); 
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
		System.err.println("USSSSER" + currentUserEmail);
		User user = UserDao.findUserByEmail(currentUserEmail);
		System.err.println(user.getUsername());
		videos = VideoDao.getVideoByUser(user);

		/*
		 * try { videos.add(getVideoById(1)); videos.add(getVideoById(2));
		 * videos.add(getVideoById(3)); videos.add(getVideoById(1));
		 * videos.add(getVideoById(2)); videos.add(getVideoById(3));
		 * videos.add(getVideoById(1)); videos.add(getVideoById(2));
		 * videos.add(getVideoById(3));
		 * 
		 * // Тестування Бази JPA.withTransaction(new play.libs.F.Callback0() {
		 * 
		 * @Override public void invoke() throws Throwable { // Video video =
		 * new Video("a", "a", "a", 3, 2, "a", // "a","a"); //
		 * VideoDao.saveVideo(video, "olko");
		 * 
		 * // Video video = VideoDao.findVideoByName("a"); //
		 * VideoDao.deteleVideo(video);
		 * 
		 * // List<Video> list = VideoDao.getVideosByCategories("a"); // for(int
		 * i=0;i<list.size();i++){ // VideoDao.deteleVideo(list.get(i)); // }
		 * 
		 * // LikeDao.makeLike("olko", "b"); // LikeDao.makeDisLike("olko",
		 * "b");
		 * 
		 * // Video video = VideoDao.findVideoByName("a"); // List<Like> likes =
		 * LikeDao.findLikesByVideoId(video); //
		 * LikeDao.deleteLike(likes.get(0));
		 * 
		 * // Video video = VideoDao.findVideoByName("a"); // List<Like> likes =
		 * LikeDao.findLikesByVideoId(video); //
		 * LikeDao.deleteLike(likes.get(0));
		 * 
		 * } });
		 * 
		 * } catch (VideoNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		/*
		 * User user2 = UserDao.findUserByName("lola"); user2.setAge(10000);
		 * user2 = UserDao.updateUser(user2);
		 * 
		 * videos.get(0).setName("revolution"); videos.get(0).setViews(10);
		 */

		// VideoDao.updateVideo(videos.get(0));

		return videos;
	}

	/*
	 * Returns Video object by it's id after incrementing it's views amount
	 * 
	 * @param long id - video's id
	 * 
	 * @return video by it's id
	 */
	private static Video getVideoById(long id) throws VideoNotFoundException {
		Video video = VideoDao.findVideoByID(safeLongToInt(id));
		if (video != null) {
			return video;
		} else
			throw new VideoNotFoundException();
	}

	private static void incViews(Video video) {
		// incrementing and updating vieas amount
		int views = video.getViews();
		views++;
		video.setViews(views);
		VideoDao.updateVideo(video);
	}

	private static int safeLongToInt(long l) {
		return Ints.checkedCast(l);
	}

	// TODO Need to replace incViews with makeVideo Like
	public static Result likeVideo() {
		try {
			System.err.println("Trying to make Like for Video!!!");

			long id = 0;
			try {
				id = Long.parseLong(videoID);
			} catch (NumberFormatException e) {
				return badRequest(notFound.render(videoID));
			}

			Video videoObj = getVideoById(id);
			incViews(videoObj);

			// LikeDao.makeLike(UserDao.findUserByEmail(session("email")).getUsername(),
			// getVideoById(Long.parseLong(videoID)).getName() );
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VideoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok();
	}

	// TODO Need to replace incViews with makeVideo Like
	public static Result disLikeVideo() {
		System.err.println("Trying to make disLike for Video!!!");
		try {
		

			long id = 0;
			try {
				id = Long.parseLong(videoID);
			} catch (NumberFormatException e) {
				return badRequest(notFound.render(videoID));
			}

			Video videoObj = getVideoById(id);
			incViews(videoObj);

			// LikeDao.makeLike(UserDao.findUserByEmail(session("email")).getUsername(),
			// getVideoById(Long.parseLong(videoID)).getName() );
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VideoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok();
	}

	public static Result addVideoToUsersCollection(String link) throws NumberFormatException, VideoNotFoundException{
		StringBuilder id = new StringBuilder();
		int len = link.length()-1;
		char c = link.charAt(len);
		for(int i = len-1; c!= '/'; i--){			
			id.append(c);
			c = link.charAt(i);
		}
		
		System.err.println("======"+ link);		
		id= id.reverse();
				
		System.err.println("======"+ id);
		Video video = getVideoById(Long.parseLong(id.toString()));		
		UserDao.addVideoToUser(video, UserDao.findUserByEmail(session("email")));
		System.err.println("need to add video to user");
		System.err.println("=============" + video.getName());
		return ok();
	}

	public static Result editVideo(){			
		return ok(editvideo.render(genVideo(), editVideoForm));		
	}
	
	
	
	public static Result submitDelete(){		
		Video videoObj = genVideo();
		System.err.println("===============Trying to delete video" + videoObj.getName());
		/*if(videoObj.getComments().size()>=0){
			for(Comment com : videoObj.getComments()){
				CommentDao.deleteComment(com);
			}
		}*/
		VideoDao.deteleVideo(videoObj);
		return Application.index();
	}
	
	
	public static Result submitEdit(){
		System.err.println("===============Trying to edit video");
		editVideoForm = form(Video.class).bindFromRequest();
		
		Video videoObj = genVideo();
		
		if (editVideoForm.hasErrors()) {
			System.err.println("Some errors occured while editing");
			System.err.println(editVideoForm.toString());			
			return badRequest(editvideo.render(videoObj, editVideoForm));
		} else {
			 videoObj.setName( editVideoForm.get().getName() );
			 videoObj.setDescription( editVideoForm.get().getDescription());
			 
			 VideoDao.updateVideo(videoObj);
		}
		return ok(editvideo.render(genVideo(), editVideoForm));
	}
	
	static Form<Video> editVideoForm = form(Video.class);
	
	
	private static Video genVideo(){
		try {			
			long id = 0;
			try {
				id = Long.parseLong(videoID);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
						
			return getVideoById(id); 
		} catch (NumberFormatException e) {			
			e.printStackTrace();
		} catch (VideoNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
