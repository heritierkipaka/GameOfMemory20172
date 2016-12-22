package fr.kipaka.com.gameofmemory2017;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Created by M0297357 on 22/12/2016.
 */


public class Music {

    public static boolean OFF = false;

    public static void playCorrent() {
        if (!OFF) {
            MediaPlayer mp = MediaPlayer.create(R.raw.correct_answer);
            mp.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start();
        }
    }

    public static void playBackgroundMusic() {
        // TODO
    }

    public static void showStar() {
        if (!OFF) {
            MediaPlayer mp = MediaPlayer.create(R.raw.star);
            mp.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start();
        }
    }
}