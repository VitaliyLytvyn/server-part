package org.magnum.mobilecloud.video.model;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for a repository that can store UserVideoRating
 * objects and allow them to be searched by videoId and user.
 */
@Repository
public interface UserRatingRepository extends CrudRepository<UserVideoRating, Long>{


    // Find all videos with a matching videoId and username (e.g., Video.name)
    public Collection<UserVideoRating> findByVideoIdAndUser(long videoId, String user);
}
