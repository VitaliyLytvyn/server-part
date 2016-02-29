package org.magnum.mobilecloud.video.model;

import java.util.Collection;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * An interface for a repository that can store Video
 * objects and allow them to be searched by title.
 */
//@Repository
public interface VideoRepository extends MongoRepository<Video, String>{

    // Find all videos with a matching title (e.g., Video.name)
    public Collection<Video> findByTitle(String title);

}