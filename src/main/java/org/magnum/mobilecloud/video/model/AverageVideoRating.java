package org.magnum.mobilecloud.video.model;

public class AverageVideoRating {

	private final double rating;

	private final String videoId;

	private final int totalRatings;

	public AverageVideoRating(double rating, String videoId, int totalRatings) {
		super();
		this.rating = rating;
		this.videoId = videoId;
		this.totalRatings = totalRatings;
	}

	public double getRating() {
		return rating;
	}

	public String getVideoId() {
		return videoId;
	}

	public int getTotalRatings() {
		return totalRatings;
	}

}
