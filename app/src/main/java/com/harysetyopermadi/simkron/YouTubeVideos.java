package com.harysetyopermadi.simkron;

public class YouTubeVideos {
   private String videoUrl;
   private String judultxt;



    public YouTubeVideos(String videoUrl,String judultxt) {
        this.videoUrl = videoUrl;
        this.judultxt=judultxt;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public String setJudultxt() {
        return judultxt;
    }


}
