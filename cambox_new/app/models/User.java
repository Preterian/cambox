package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dao.UserDao;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "User.findByName", query = "SELECT c FROM User c WHERE c.username = :username"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT c FROM User c WHERE c.email = :email")})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private int userId;

	@Column(name = "name", nullable = true)
	private String name;

	@Required
	@Column(name = "surname", nullable = true)
	private String surname;

	@Column(name = "age", nullable = true)
	private int age;

	@Column(name = "country", nullable = true)
	private String country;

	@Column(name = "city", nullable = true)
	private String city;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "question", nullable = true)
	private String question;

	@Required
	@Column(name = "email", nullable = true)
	private String email;

	@Required
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "registrated", nullable = true)
	private Date registrationDate;

	@OneToMany(mappedBy = "user")
	private Set<Like> likes;

	@OneToMany(mappedBy = "user")
	private Set<Dislike> dislikes;

	@OneToMany(mappedBy = "user")
	private Set<Comment> comments;

	@OneToMany(mappedBy = "userUploader", fetch = FetchType.LAZY)
	private Set<Video> videos;

	public Set<Video> getVideos() {
		return videos;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", updatable = true, nullable = true)}, inverseJoinColumns = {@JoinColumn(name = "role_id", updatable = true, nullable = true)})
	private Set<Role> roleSet = new HashSet<Role>();

	public User() {

	}

	public User(String name, String surname, int age, String country,
			String city, String gender, String username, String question,
			String email, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.country = country;
		this.city = city;
		this.gender = gender;
		this.username = username;
		this.question = question;
		this.email = email;
		this.password = password;
		this.registrationDate = new Date();
	}
	
	public User(String username, String name, String surname, String email, String password){
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	public Set<Dislike> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<Dislike> dislikes) {
		this.dislikes = dislikes;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getNameAndSurname() {
		return name + " " + surname;
	}

	public static User authenticate(String email, String password) {	
		User user = UserDao.findUserByEmail(email);
		if(user.getPassword().equals(password)){
			return user;
		}
		return null;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}