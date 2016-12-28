package fr.kipaka.com.gameofmemory2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.kipaka.com.gameofmemory2017.R.id.retour;


public class HighScoresToolbar extends AppCompatActivity {

    DBHelper mydb;
    Toolbar mToolbar;
    Toolbar mToolbarBottom;

    //Les items du menu d'en bas les reseaux sociaux
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("High Scores");
        mToolbar.setSubtitle("Best Gamers");
        mToolbar.setLogo(R.drawable.ic_launcher);
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
                Toast.makeText(HighScoresToolbar.this, "Développeur KIPAKA Mubwala AND TALEB Nassim!", Toast.LENGTH_SHORT).show();
            }
        });
        //FIN - Les items du menu d'en bas les reseaux sociaux


        mydb = new DBHelper(this);
        List<Map<String, Object>> rows = mydb.getAllScores();
        ArrayAdapter<Map<String, Object>> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, rows);

        ListView obj = (ListView) findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayScores.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });


    }

    //affichage du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.item100:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(), DisplayScores.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;

            case retour:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


}




