package fr.kipaka.com.gameofmemory2017;
/**
 * Created by M0297357 on 22/12/2016.
 */
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SimpleCursorAdapter;

public class HighScoresToolbar extends  ListActivity {

    private PersistentStore store;
    private SimpleCursorAdapter adapter;
    private static final String[] SCORE_FIELDS = new String[] {"best_score" };
    private static final int[] SCORE_VIEWS = new int[] {R.id.high_score_moves };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_header);

        store = new PersistentStore(this);
        adapter = new SimpleCursorAdapter(this, R.layout.score_item, store.getScores(), SCORE_FIELDS, SCORE_VIEWS);
        getListView().addHeaderView(getLayoutInflater().inflate(R.layout.score_header, null));
        setListAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /** @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";

        switch (item.getItemId()) {

            case R.id.save:
                msg = getString(R.string.mail);
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
    }**/



}