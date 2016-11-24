package com.example.adeilham.onlineradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button_radio, button_audio;
    MediaPlayer media_player, media_player2;
    boolean prepared = false;
    boolean started = false;

    String radio_stream = "http://stream.radioreklama.bg:80/radio1rock128";
    String file_stream = "http://10.151.32.32/Kajian%20Tematik/Kajian%20Syar'i%20-%20Alternatif%20Permodalan%20dalam%20Islam%20%5bSerial%20Mp3%5d/085%20Alternatif%20Permodalan%20dalam%20Islam%2001%20-%20Ust.%20Dr.%20Arifin%20Badri,%20Lc.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_radio = (Button) findViewById(R.id.buttonRadio);
        button_radio.setEnabled(false);
        button_radio.setText("Connecting to Server...");

        button_audio = (Button) findViewById(R.id.buttonAudio);
        button_audio.setEnabled(false);
        button_audio.setText("Loading...");

        media_player = new MediaPlayer();
        media_player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        media_player2 = new MediaPlayer();
        media_player2.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        new PlayerTask().execute(radio_stream);
        new AudioTask().execute(file_stream);


        button_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started){
                    started = false;
                    media_player.pause();
                    button_radio.setText("Play..");
                }
                else {
                    started = true;
                    media_player.start();
                    button_radio.setText("Pause..");
                }
            }
        });

        button_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started){
                    started = false;
                    media_player2.pause();
                    button_audio.setText("Play..");
                }
                else {
                    started = true;
                    media_player2.start();
                    button_audio.setText("Pause..");
                }
            }
        });
    }


    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            try {
                media_player.setDataSource(params[0]);
                media_player.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            button_radio.setEnabled(true);
            button_radio.setText("Play..");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started){
            media_player.pause();
            media_player2.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started){
            media_player.start();
            media_player2.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prepared){
            media_player.release();
            media_player2.release();
        }
    }


    //Audio Streaming

    class AudioTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                media_player2.setDataSource(params[0]);
                media_player2.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            button_audio.setEnabled(true);
            button_audio.setText("Play..");
        }
    }




}
