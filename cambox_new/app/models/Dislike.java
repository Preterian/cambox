package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "dislikes")
@NamedQueries({@NamedQuery(name = "Dislike.findDisLikesByVideoId", query = "SELECT c FROM Dislike c WHERE c.video = :video")})
public class Dislike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dislike_id", nullable = false)
	private int like_id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "video_id")
	private Video video;

	Dislike() {

	}

	public Dislike(User user, Video video) {
		super();
		this.user = user;
		this.video = video;
	}

	public int getLike_id() {
		return like_id;
	}

	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}