package mx.edu.utng.lajosefa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import mx.edu.utng.lajosefa.R;

public class IntroActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {
    String apiYouTube = "AIzaSyDkhBN5m-oX6Pm8XuLk9CS-X_UD1au69BM";
    YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(apiYouTube, this);
    }


        @Override
        public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer
        youTubePlayer, boolean b){
            if (!b) {
                youTubePlayer.cueVideo("He2jjRC41y0");
            }
        }

        @Override
        public void onInitializationFailure (YouTubePlayer.Provider
        provider, YouTubeInitializationResult youTubeInitializationResult){
            if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog(this, 1).show();
            } else {
                String error = "Error al inicializar YouTube" + youTubeInitializationResult.toString();
                Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
            }
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            getYoutubePlayerProvider().initialize(apiYouTube, this);
        }
    }

    protected YouTubePlayer.Provider getYoutubePlayerProvider() {
        return youTubePlayerView;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }



}
