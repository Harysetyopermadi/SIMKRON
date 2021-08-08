package com.harysetyopermadi.simkron.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.harysetyopermadi.simkron.JudulnyaYa;
import com.harysetyopermadi.simkron.R;
import com.harysetyopermadi.simkron.VideoAdapter;
import com.harysetyopermadi.simkron.YouTubeVideos;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    Vector<JudulnyaYa> judulnyaYas=new Vector<JudulnyaYa>();
    TextView judulnya;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ImageSlider imageSlider=root.findViewById(R.id.slider);
        recyclerView =root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        judulnya=root.findViewById(R.id.judul);

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/k-gBoTJoqoQ\" frameborder=\"0\" allowfullscreen></iframe>","Judul1"));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lcLqAdXwtqA\" frameborder=\"0\" allowfullscreen></iframe>","Judul2") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/DD6cT18QFGY\" frameborder=\"0\" allowfullscreen></iframe>","Judul3") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/gJipODtpncY\" frameborder=\"0\" allowfullscreen></iframe>","Judul4") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/k-gBoTJoqoQ\" frameborder=\"0\" allowfullscreen></iframe>","Judul5") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);



        recyclerView.setAdapter(videoAdapter);


        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("https://i.ibb.co/4m1cJ4b/IMG-20200817-WA0026.jpg","Team PHP2d UNIS"));
        slideModels.add(new SlideModel("https://i.ibb.co/ZVgyfs0/IMG-20200825-WA0013.jpg","Team PHP2d UNIS"));
        slideModels.add(new SlideModel("https://i.ibb.co/sHyh2p4/IMG-20200825-WA0009.jpg","Team PHP2d UNIS"));
        slideModels.add(new SlideModel("https://i.ibb.co/CQpXTbd/IMG-20200825-WA0022.jpg","Team PHP2d UNIS"));
        slideModels.add(new SlideModel("https://i.ibb.co/qmSwDz0/Whats-App-Image-2020-11-02-at-11-54-12.jpg","Team PHP2d UNIS"));
        slideModels.add(new SlideModel("https://i.ibb.co/xhJ4K33/Whats-App-Image-2020-11-02-at-11-50-25.jpg","Team PHP2d UNIS"));
        imageSlider.setImageList(slideModels,true);



        return root;
    }


}