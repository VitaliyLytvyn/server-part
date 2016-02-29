package org.magnum.mobilecloud.video.controller;

import com.google.common.collect.Lists;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Skyver on 26.08.2015.
 */

@Controller
public class MyController {

    private VideoFileManager videoDataMgr;

    @Autowired
    private VideoRepository videos;

    @Autowired
    private UserRatingRepository ratingRepository;


    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody
    Collection<Video> getVideoList(){

        System.out.println("IN " + "getVideoList()");
        return Lists.newArrayList(videos.findAll());
    }

    @RequestMapping (value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
    public @ResponseBody
    Video addVideo(@RequestBody Video v, Principal p){

        System.out.println("IN " + "addVideo()");
        v.setOwner(p.getName());

        Collection<Video> cl = videos.findByTitle(v.getTitle());
        for(Video vv : cl){
            if(vv.equals(v)){
                cl.remove(vv);
            }
        }

        videos.save(v);

        return v;
    }

    @RequestMapping (value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.POST)
    public @ResponseBody
    VideoStatus setVideoData(
            @PathVariable(VideoSvcApi.ID_PARAMETER) String id,
            @RequestParam(VideoSvcApi.DATA_PARAMETER)MultipartFile videoData,
            HttpServletResponse response,
            Principal p
    ) throws IOException {

        System.out.println("IN " + "setVideoData()");
        if(!videos.exists(id)){
            response.sendError(404);
            return null;
        }
        else if (!videos.findOne(id).getOwner().equals(p.getName())){
            response.sendError(403);
            return null;
        }

        InputStream in = videoData.getInputStream();
        videoDataMgr =  new VideoFileManager();

        videoDataMgr.saveVideoData(videos.findOne(id), in);
        System.out.println("return Video uploaded: ");
        return new VideoStatus(VideoStatus.VideoState.READY);
    }

    @RequestMapping (value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.GET)
    public
    void getData(
            @PathVariable(VideoSvcApi.ID_PARAMETER) String id,
            HttpServletResponse response) throws IOException {

        System.out.println("IN " + "getData()");
        videoDataMgr = new VideoFileManager();

        if(! videos.exists(id) ||
                ! videoDataMgr.hasVideoData(videos.findOne(id))){
            response.sendError(404);
            return;
        }

        response.setContentType(videos.findOne(id).getContentType());

        videoDataMgr.copyVideoData(videos.findOne(id), response.getOutputStream());

    }

    @RequestMapping (value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Video getVideoById(
            @PathVariable(VideoSvcApi.ID_PARAMETER) String id,
            HttpServletResponse response) throws IOException {

        System.out.println("IN " + "getVideoById()");
        if(! videos.exists(id)){
            response.sendError(404);
            return null;
        }

        return videos.findOne(id);

    }

    @RequestMapping (value = VideoSvcApi.VIDEO_SVC_PATH+"/{id}/rating/{rating}", method = RequestMethod.POST)
    public @ResponseBody
    AverageVideoRating rateVideo(
            @PathVariable("id") String id,
            @PathVariable("rating") int setRate,
            HttpServletResponse response,
            Principal p) throws IOException {

        System.out.println("IN " + "rateVideo()");
        System.out.println("RATING  ENTERED ID "+id +" rate: "+ setRate);


        if(! videos.exists(id)){
            response.sendError(404);
            return null;
        }

        Video vd = videos.findOne(id);

        Collection<UserVideoRating> cl = null;
        cl = ratingRepository.findByVideoIdAndUser(id, p.getName());

        if(cl != null && !cl.isEmpty()){
            for(UserVideoRating usrRt : cl){

                double oldRating = usrRt.getRating();
                vd.setSumOfAllGrades(vd.getSumOfAllGrades() - oldRating);
                vd.setLikes(vd.getLikes() - 1);

                updatedRate(id, setRate);

                usrRt.setRating(setRate);

                ratingRepository.save(usrRt);//////////!!!!!!!!!!!!!!
            }
        }
        else {
            updatedRate(id, setRate);
            UserVideoRating usRt = new UserVideoRating(id, setRate, p.getName());

            ratingRepository.save(usRt);////////!!!!!!!!!!!!
        }

        videos.save(vd);///////////!!!!!!!!!!!

        return new AverageVideoRating(vd.getStarRating(), id, (int)vd.getLikes());
    }

    @RequestMapping (value = VideoSvcApi.VIDEO_SVC_PATH+"/{id}/rating", method = RequestMethod.GET)
    public @ResponseBody
    AverageVideoRating getVideoRating(
            @PathVariable("id") String id,
            HttpServletResponse response ) throws IOException{

        System.out.println("IN " + "getVideoRating()");
        if(! videos.exists(id)){
            response.sendError(404);
            return null;
        }

        Video vd = videos.findOne(id);
        System.out.println("GET RATING  RETURN Rating: "+vd.getStarRating()+" Likes: "+vd.getLikes());
        return new AverageVideoRating(vd.getStarRating(), id, (int)vd.getLikes());

    }


    private  void updatedRate(String id, double setRate){

        Video vd = videos.findOne(id);
        long mCount =  vd.getLikes() + 1;
        vd.setLikes(mCount);
        double sumOfGrades = vd.getSumOfAllGrades() + setRate;
        vd.setSumOfAllGrades(sumOfGrades);

        vd.setStarRating(sumOfGrades / mCount);
    }



}
