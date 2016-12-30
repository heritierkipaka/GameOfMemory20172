package fr.kipaka.com.gameofmemory2017;

/***
 * Created by M0297357 on 26/12/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayScores extends AppCompatActivity {
    TextView turns;
    TextView name;
    Integer id_To_Update = 0;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        turns = (TextView) findViewById(R.id.editTextTurns);


        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getScore(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.SCORE_COL_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.SCORE_COL_SCORE));
                String id = rs.getString(rs.getColumnIndex(DBHelper.SCORE_COL_ID));

                if (!rs.isClosed()) {
                    rs.close();
                }


                name.setText(nam);
                name.setFocusable(false);
                name.setClickable(false);

                turns.setText(phon);
                turns.setFocusable(false);
                turns.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.menu_display_gamer, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_display, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.Edit_Scores:

                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                turns.setEnabled(true);
                turns.setFocusableInTouchMode(true);
                turns.setClickable(true);
                return true;

            case R.id.Delete_Scores:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("deleteScore")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteScore(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateScore(id_To_Update, name.getText().toString(), Integer.valueOf(turns.getText().toString()), Integer.valueOf(-111), Integer.valueOf(-222))) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertScore(name.getText().toString(), Integer.valueOf(turns.getText().toString()),  Integer.valueOf(-111), Integer.valueOf(-222))) {
                    Toast.makeText(getApplicationContext(), "Score du gamers ajouté",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Score du gamers pas ajouté",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}