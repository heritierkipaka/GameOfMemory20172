package fr.kipaka.com.gameofmemory2017;
/**
 * Created by M0297357 on 22/12/2016.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HighScoresToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("High Scores  !");
        mToolbar.setSubtitle("by Game Of Memory !");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";

        switch (item.getItemId()) {

            case R.id.save:
                msg = getString(R.string.action_save);
                break;

            case R.id.mail:
                msg = getString(R.string.mail);
                break;

            case R.id.camera:
                msg = getString(R.string.camera);
                break;

            case R.id.settings:
                msg = getString(R.string.settings);
                break;

            case R.id.web_search:
                msg = getString(R.string.web_search);
                break;

            case R.id.help:
                msg = getString(R.string.help);
                break;
        }

        Toast.makeText(this, msg + " clicked !", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

}