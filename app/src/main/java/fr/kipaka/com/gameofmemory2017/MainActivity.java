package fr.kipaka.com.gameofmemory2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MY_TAG = "Message";
    private static long time;
    Toolbar mToolbarBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //La toolbar d'en haut avec les Nom et image du jeu
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Game Of Memory!");
        mToolbar.setSubtitle("Menu Principal");
        mToolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mToolbar);

        //Les items du menu d'en bas les reseaux sociaux
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
                Toast.makeText(MainActivity.this, "Développeur: KIPAKA and TALEB", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Les items du menu d'en haut
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_info:
                Toast.makeText(this, "Commencer le jeu en cliquant sur (Memory Game)", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.retour:
                finish();
                return true;

            case R.id.action_website:
                Toast.makeText(this, "KIPAKA et TALEB", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showGameToolbar(View view) {

        Intent intent = new Intent(this, GameToolbar.class);
        startActivity(intent);
    }

    public void showHighScoresToolbar(View view) {

        Intent intent = new Intent(this, HighScoresToolbar.class);
        startActivity(intent);
    }

    public void showApprosMenu(View view) {
        Intent intent = new Intent(this, ApprosMenu.class);
        startActivity(intent);
    }

    public void showMusicMenu(View view) {
        Intent intent = new Intent(this, MusicMenu.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MY_TAG, "OnCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(MY_TAG, "OnPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(MY_TAG, "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(MY_TAG, "OnResume");
    }

    @Override
    public void onBackPressed() {

        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "Appuyez longtemps pour quitter Game Of Memory", Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }
}


