package org.magnum.mobilecloud.video.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

/**
 * A simple object to represent a video and its URL for viewing.
 * 
 * You must annotate this object to make it a JPA entity.
 *
 * Feel free to modify this with whatever other metadata that you want, such as
 * the
 * 
 * @author jules, mitchell
 */
@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;
	private long duration;
	private String location;
	private String subject;
	private String contentType;
	private String url;
	private long likes;
	private double starRating;

	// We don't want to bother unmarshalling or marshalling
	// any owner data in the JSON. Why? We definitely don't
	// want the client trying to tell us who the owner is.
	// We also might want to keep the owner secret.
	@JsonIgnore
	private String owner;
	@JsonIgnore
	private double sumOfAllGrades;

	public Video() {
	}

	public Video(String owner, String name, String url, long duration,
			long likes, Set<UserVideoRating> likedBy) {
		super();
		this.owner = owner;
		this.title = name;
		this.url = url;
		this.duration = duration;
		this.likes = likes;

	}

	public String getTitle() {return title;	}

	public void setTitle(String title) {this.title = title;}

	public String getUrl() {return url;	}

	public void setUrl(String url) {this.url = url;}

	public long getDuration() {return duration;}

	public void setDuration(long duration) {this.duration = duration;}

	public long getId() {return id;}

	public void setId(long id) {this.id = id;}

	public String getOwner() {return owner;}

	////////////////check if needed @JsonIgnore  //////////////////
	@JsonIgnore
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	//public Set<UserVideoRating> getLikedBy() {return likedBy;}

	//public void setLikedBy(Set<UserVideoRating> likedBy) {this.likedBy = likedBy;}

	public long getLikes() {return likes;}

	public void setLikes(long likes) {this.likes = likes;}

	public double getStarRating() {return starRating;}

	public void setStarRating(double starRating) {this.starRating = starRating;}

	public double getSumOfAllGrades() {return sumOfAllGrades;}

	public void setSumOfAllGrades(double sumOfAllGrades) {this.sumOfAllGrades = sumOfAllGrades;}

	/**
	 * Two Videos will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(title, url, duration, owner);
	}

	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Video) {
			Video other = (Video) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(title, other.title)
					&& Objects.equal(url, other.url)
					&& Objects.equal(owner, other.owner)
					&& duration == other.duration;
		} else {
			return false;
		}
	}

}
