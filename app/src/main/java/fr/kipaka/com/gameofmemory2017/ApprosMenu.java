package fr.kipaka.com.gameofmemory2017;
/*** Created by M0297357 on 22/12/2016.*/
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class ApprosMenu extends AppCompatActivity {

    private Toolbar mToolbar;
    protected Toolbar mToolbarBottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appros);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("À propos");
        mToolbar.setSubtitle("Projet Android");
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
                   /* case R.id.action_whatsapp:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;*/
                }

                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApprosMenu.this, "Développeur KIPAKA Mubwala AND TALEB Nassim!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appros, menu);
        return true;
    }


}

