package com.example.player1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private TextView textView;
    private Button buttonStart;
    private Button buttonStop;
    private Toast toast;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private final String DATA_STREAM = "http://radio.ufaley.su:8000/stream.mp3"; // ссылка на аудио поток
    View.OnClickListener startListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

                releaseMediaPlayer();
                textView.setText("Начало вещания");
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(DATA_STREAM);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnPreparedListener(this);

                mediaPlayer.prepareAsync();
                mediaPlayer.start();
            } catch (IOException exception) {
                textView.setText("Источник не найден");
            }

        }
    };
    View.OnClickListener stopListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mediaPlayer != null) {
            textView.setText("Конец вещания");
            mediaPlayer.stop();
            releaseMediaPlayer();}
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        textView = findViewById(R.id.textView);
        textView.setText("Привет");
        buttonStart.setOnClickListener(startListerner);
        buttonStop.setOnClickListener(stopListerner);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

        @Override
    public void onCompletion(MediaPlayer mp) {
        toast = Toast.makeText(this, "Отключение медиа-плейера", Toast.LENGTH_SHORT); // инициализация тоста
        toast.show(); // демонстрация тоста на экране

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start(); // старт медиа-плейера
        toast = Toast.makeText(this, "Старт медиа-плейера", Toast.LENGTH_SHORT); // инициализация тоста
        toast.show(); // демонстрация тоста на экране

    }
// метод освобождения используемых проигрывателем ресурсов
    private void releaseMediaPlayer() {
        toast=Toast.makeText(this,"Очистка ресурсов плейера", Toast.LENGTH_LONG);
        toast.show();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}
