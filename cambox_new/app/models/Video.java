package models;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "video")
@NamedQueries({
		@NamedQuery(name = "Video.findByName", query = "SELECT c FROM Video c WHERE c.name = :name"),
		@NamedQuery(name = "Video.findByID", query = "SELECT c FROM Video c WHERE c.videoId = :videoId"),
		@NamedQuery(name = "Video.findByCategory", query = "SELECT c FROM Video c WHERE c.category = :category")})
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id", nullable = false)
	private int videoId;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "category", nullable = true)
	private String category;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "views", nullable = true)
	private int views;

	@Column(name = "timesLiked", nullable = true)
	private int timesLiked;

	@Column(name = "timesDisLiked", nullable = true)
	private int timesDisLiked;

	@Column(name = "rate", nullable = true)
	private int rate;

	@Column(name = "url", nullable = true)
	private String url;

	@Column(name = "previewUrl", nullable = true)
	private String previewUrl;

	@Column(name = "bigPreviewUrl", nullable = true)
	private String bigPreviewUrl;

	@Column(name = "smallPreviewUrl", nullable = true)
	private String smallPreviewUrl;

	@Column(name = "uploadDate", nullable = true)
	private Date uploadDate;

	@OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
	private Set<Comment> comments;

	@OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
	private Set<Like> likes;

	@OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
	private Set<Dislike> dislikes;
	
	@ManyToOne
    @JoinColumn(name="user_id")
	private User userUploader;

	

	public Video() {

	}

	public Video(String name, String category, String description, int views,
			int rate, String url, Date uploadDate) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.views = views;
		this.rate = rate;
		this.url = url;
		this.uploadDate = uploadDate;
	}

	public Video(String name, String category, String description, int views,
			String url, Date uploadDate) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.views = views;
		this.url = url;
		this.uploadDate = uploadDate;
	}

	public Video(String name, String category, String description, int views,
			int rate, String url, String smallPreviewUrl, String bigPreviewUrl) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.views = views;
		this.rate = rate;
		this.url = url;
		this.smallPreviewUrl = smallPreviewUrl;
		this.bigPreviewUrl = bigPreviewUrl;
		this.uploadDate = new Date();
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public int getTimesLiked() {
		return timesLiked;
	}

	public void setTimesLiked(int timesLiked) {
		this.timesLiked = timesLiked;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	public int getTimesDisLiked() {
		return timesDisLiked;
	}

	public void setTimesDisLiked(int timesDisLiked) {
		this.timesDisLiked = timesDisLiked;
	}

	public Set<Dislike> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<Dislike> dislikes) {
		this.dislikes = dislikes;
	}

	public String getBigPreviewUrl() {
		return bigPreviewUrl;
	}

	public void setBigPreviewUrl(String bigPreviewUrl) {
		this.bigPreviewUrl = bigPreviewUrl;
	}

	public String getSmallPreviewUrl() {
		return smallPreviewUrl;
	}

	public void setSmallPreviewUrl(String smallPreviewUrl) {
		this.smallPreviewUrl = smallPreviewUrl;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public User getUserUploader() {
		return userUploader;
	}

	public void setUserUploader(User userUploader) {
		this.userUploader = userUploader;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
