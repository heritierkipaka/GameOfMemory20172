package fr.kipaka.com.gameofmemory2017;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MusicMenu extends AppCompatActivity {

    Button start, pause, stop;
    MediaPlayer mp;
    Toolbar mToolbarBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Game Of Memory!");
        mToolbar.setSubtitle("Menu Principal");
        mToolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mToolbar);


        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch (menuItem.getItemId()) {
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case R.id.action_google_plus:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case R.id.action_linkedin:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.linkedin.com"));
                        break;
                }

                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MusicMenu.this, "Développeur KIPAKA Mubwala AND TALEB Nassim!", Toast.LENGTH_SHORT).show();
            }
        });

        start = (Button) findViewById(R.id.button1);
        pause = (Button) findViewById(R.id.button2);
        stop = (Button) findViewById(R.id.button3);

        //creating media player


        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music);
                Toast.makeText(getApplicationContext(), "Starting sound", Toast.LENGTH_SHORT).show();

                mp.start();
            }
        });

        /**
         * Play button click event* plays a song* pauses a song* */
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // check for already playing
                if (mp.isPlaying()) {
                    if (mp != null) {
                        Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                        mp.pause();
                    } else {
                        // Resume song
                        if (mp != null) {
                            Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                            mp.start();
                        }


                    }
                }
            }
        });

        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null && mp.isPlaying()) {//If music is playing already
                    Toast.makeText(getApplicationContext(), "Stopping sound", Toast.LENGTH_SHORT).show();
                    mp.stop();
                }
            }


        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }

    //Les items du menu d'en haut
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_info:
                Toast.makeText(this, "Commencer le jeu en cliquant sur (Memory Game)", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.retour:
                finish();
                return true;

            case R.id.action_website:
                Toast.makeText(this, "KIPAKA Héritier", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

