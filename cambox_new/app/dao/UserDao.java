package dao;

import javax.persistence.Query;

import models.User;
import models.Video;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

public class UserDao {

	static boolean result = false;
	static User tempUser = null;
	
	@Transactional
	public static boolean saveUser(final User user) {
	
		JPA.withTransaction(new play.libs.F.Callback0() {		
		@Override
		public void invoke() throws Throwable {
			Query userQuery = JPA.em().createNamedQuery("User.findByName")
					.setParameter("username", user.getUsername());

			if (userQuery.getMaxResults() > 0)
				result =  false;
			JPA.em().persist(user);
			result = true;	
		}		
		});
		return result;
	}

	@Transactional
	public static void addVideoToUser(Video video, User user) {
		user.getVideos().add(video);
		JPA.em().persist(user);
	}

	@Transactional
	public static User findUserByEmail(final String email) {
		
		JPA.withTransaction(new play.libs.F.Callback0() {		
			@Override
			public void invoke() throws Throwable {
		
		Query userQuery = JPA.em().createNamedQuery("User.findByEmail")
				.setParameter("email", email);
		if (userQuery.getMaxResults() > 0) {
			tempUser = (User) userQuery.getSingleResult();
			//return tempUser;
			 
		}
			}		
		});
		return tempUser;
	}

	@Transactional
	public static void deleteUser(User user) {
		JPA.em().remove(user);
	}

}
