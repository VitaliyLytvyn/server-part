package org.magnum.mobilecloud.video.model;

import javax.persistence.*;

import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;

// You might want to annotate this with Jpa annotations, add an id field,
// and store it in the database...
//
// There are also plenty of other solutions that do not require
// persisting instances of this...

//////@Entity
public class UserVideoRating {

	@Id
	/////@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private String videoId;

	private double rating;

	private String user;

	public UserVideoRating() {
	}

	public UserVideoRating(String videoId, double rating, String user) {
		super();
		this.videoId = videoId;
		this.rating = rating;
		this.user = user;
	}

	public String getId() {return id;}

	public void setId(String id) {this.id = id;}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Two Ratings will generate the same hashcode if they have exactly the same
	 * values for their videoId, rating, user.
	 *
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(videoId, rating, user);
	}

	/**
	 * Two Ratings are considered equal if they have exactly the same values for
	 * their videoId, rating, user.
	 *
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserVideoRating) {
			UserVideoRating other = (UserVideoRating) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(videoId, other.videoId)
					&& Objects.equal(user, other.user)
					&& user == other.user;
		} else {
			return false;
		}
	}

}
