package org.magnum.mobilecloud.video.model;

import java.util.Collection;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * An interface for a repository that can store UserVideoRating
 * objects and allow them to be searched by videoId and user.
 */
//@Repository
public interface UserRatingRepository extends MongoRepository<UserVideoRating, String>{


    // Find all videos with a matching videoId and username (e.g., Video.name)
    public Collection<UserVideoRating> findByVideoIdAndUser(
            String videoId,
            String user);
}
