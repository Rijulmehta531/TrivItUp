package com.example.trivitup;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private Button enableSoundButton;
    private boolean isSoundEnabled = true;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        enableSoundButton = findViewById(R.id.enableSoundButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound_tracks);

        enableSoundButton.setText(isSoundEnabled ? "Sound Enabled" : "Sound Disabled");

        enableSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSoundEnabled = !isSoundEnabled;

                playClickSound();

                enableSoundButton.setText(isSoundEnabled ? "Sound Enabled" : "Sound Disabled");

                // Save the sound state to SharedPreferences
                SoundPreferenceUtil.setSoundEnabled(SettingActivity.this, isSoundEnabled);
            }
        });

    }

    private void playClickSound() {
        if (mediaPlayer != null) {
            if (isSoundEnabled) {
                // Play the sound only if it is not already playing
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.setVolume(1.0f, 1.0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            } else {
                // Pause the sound immediately when disabling
                mediaPlayer.setLooping(false);
                mediaPlayer.pause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the sound state from SharedPreferences
        isSoundEnabled = SoundPreferenceUtil.isSoundEnabled(this);

        // Update the button text based on the state
        enableSoundButton.setText(isSoundEnabled ? "Sound Enabled" : "Sound Disabled");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the sound state to SharedPreferences
        SoundPreferenceUtil.setSoundEnabled(this, isSoundEnabled);

        // Stop the sound if the activity is paused
        stopClickSound();
    }

    private void stopClickSound() {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(false);
            mediaPlayer.pause();
        }
    }
}
