package fr.kipaka.com.gameofmemory2017;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/*** Created by M0297357 on 22/12/2016.
 *  créer 1 matrice telle que la ligne = 4, col = 4x4 = 4 aura 16 cellules, chaque cellule sera affecté
 *  au premier certain nombre DC (attribué = fonction aléatoire dans = 16 / 2, aura ainsi 8 paires)
 puis attribuer id à la matrice suivie par la formule id = 100 * col + ligne (cet ID nombre fixe pour chaque cellule)
 tout en cliquant sur chaque cellule d'image (DC chaque cellule sera initialement fixé le même chiffre),
 il passera l'id pour calculer ensuite quelle rangée et col image
 rangée et col retiré de la cellule DC avec des nombres aléatoires initiaux,
 mis en arrière-plan de la cellule selon l'ArrayList ci-dessus.
 Maintenant, est quand vous avez besoin d'ouvrir 2 case 2 case pour vérifier que
 chacun aléatoire = k, si = puis il a fallu, et k =,
 puis le remettre à l'arrière-plan d'origine.*/
public class GameToolbar extends AppCompatActivity {

    private static final String PREF_SAVED_GAME = "saved_game";
    private static final Object lock = new Object();
    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    public Toolbar mToolbar;
    public TableLayout mainTable;
    public UpdateCardsHandler handler;
    int turns;
    private Context context;
    private Drawable backImage;
    private int [] [] cards;
    private List<Drawable> images;
    private Cards firstCard;
    private Cards seconedCard;
    private ButtonListener buttonListener;
    private DBHelper dbHelper;
    /**
     * Nombre de paires de carte restantes
     */
    private int countPairOfCards;

    /**
     * Demarage du jeu
     */
    private long durationStart;

    private CountDownTimer countDownTimer;



    //affichage du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_info:
                Toast.makeText(this,"Cliquez sur spinner et selectionner une taille de grille, puis trouvez les paires !",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.retour:
                finish();
                return true;

            case R.id.action_website:
                Toast.makeText(this,"KIPAKA Héritier",Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //fin pour le menu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        setContentView(R.layout.activity_game);



        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Start the game !");
        mToolbar.setSubtitle("Let's go !");

        handler = new UpdateCardsHandler();
        loadImages();
        backImage =  getResources().getDrawable(R.drawable.icon);
        buttonListener = new ButtonListener();

        mainTable = (TableLayout)findViewById(R.id.TableLayout03);


        context  = mainTable.getContext();

        Spinner s = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            //Selectionne la taille de la grille
            @Override
            public void onItemSelected(
                    android.widget.AdapterView<?> arg0,
                    View arg1, int pos, long arg3){

                ((Spinner) findViewById(R.id.Spinner01)).setSelection(0);

                int x,y;

                switch (pos) {
                    case 1:
                        x=2;y=3;
                        break;
                    case 2:
                        x = 3;
                        y = 4;
                        break;
                    case 3:
                        x = 4;
                        y = 4;
                        break;
                    case 4:
                        x = 4;
                        y = 5;
                        break;
                    default:
                        return;
                }
                newGame(x,y);
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }
    //generer une nouvelle partie de jeu
    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;

        //Nombre de paires = (X * Y) / 2
        countPairOfCards = (ROW_COUNT * COL_COUNT) / 2;

        durationStart = System.currentTimeMillis();
        final TextView _tv = (TextView) findViewById( R.id.textViewTimer );
        findViewById(R.id.textViewTimer).setVisibility(View.VISIBLE);


        final long DURATION_MAX = (ROW_COUNT * COL_COUNT) * 10000;

        //Si le chronomètre est en cours alors l'annuler
        if(countDownTimer != null){
            countDownTimer.cancel();
        }

        //Initialisation du chronomètre
        countDownTimer = new CountDownTimer(DURATION_MAX, 1000) {

            public void onTick(long millisUntilFinished) {
               // _tv.setText("Timer : " +new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));
                _tv.setText(new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));

            }

            public void onFinish() {
                _tv.setText("GAME OVER!");
                TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
                tr.removeAllViews();
            }
        }.start();



        cards = new int [COL_COUNT] [ROW_COUNT];

        //enleve le menu principal
        mainTable.removeView(findViewById(R.id.TableRow01));
        mainTable.removeView(findViewById(R.id.linearLayout));
        mainTable.removeView(findViewById(R.id.ImageView01));
        mainTable.removeView(findViewById(R.id.LinearLayout01));

        TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
        tr.removeAllViews();

        //REcréer un nouvel affichage
        mainTable = new TableLayout(context);
        tr.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard=null;
        // chargement des cartes
        loadCards();
        //affiche le score
        turns=0;
        ((TextView)findViewById(R.id.tv1)).setText("Essais: "+turns);


    }
    //chargement des images du Drawable
    private void loadImages() {
        images = new ArrayList<>();


        images.add(getResources().getDrawable(R.drawable.card1));
        images.add(getResources().getDrawable(R.drawable.card2));
        images.add(getResources().getDrawable(R.drawable.card3));
        images.add(getResources().getDrawable(R.drawable.card4));
        images.add(getResources().getDrawable(R.drawable.card5));
        images.add(getResources().getDrawable(R.drawable.card6));
        images.add(getResources().getDrawable(R.drawable.card7));
        images.add(getResources().getDrawable(R.drawable.card8));
        images.add(getResources().getDrawable(R.drawable.card9));
        images.add(getResources().getDrawable(R.drawable.card10));
        images.add(getResources().getDrawable(R.drawable.card11));
        images.add(getResources().getDrawable(R.drawable.card12));
        images.add(getResources().getDrawable(R.drawable.card13));


    }


    //genre les cartes dans les cases
    private void loadCards(){
        try{
            int size = ROW_COUNT*COL_COUNT;

            Log.i("loadCards() >>> ","size=" + size);

            ArrayList<Integer> list = new ArrayList<>();

            for(int i=0;i<size;i++){
                list.add(Integer.valueOf(i));
            }

            // la fonction aleatoire
            Random r = new Random();

            for(int i=size-1;i>=0;i--){
                int t=0;

                if(i>0){
                    t = r.nextInt(i);
                }
// la division par 2 (sur le dernier size) met les cartes en doubles !! car deux cartes ont le mm nombre dans la matrice
                t = list.remove(t);
                cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);

                Log.i("loadCards()", "card["+(i%COL_COUNT)+
                        "]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        }
        catch (Exception e) {
            Log.e("loadCards()", e+"");
        }

    }

    //crée les boutons sur chaque case du tableau "Table Row"
    private TableRow createRow(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x,y));
        }
        return row;
    }
    //Mets un ecran par defaut sur chaque case du tableau (arriere de la carte)
    private View createImageButton(int x, int y){
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveGame();
    }

    public String getSavedGame() {
        return getPreferences(MODE_PRIVATE).getString(PREF_SAVED_GAME, null);
    }

    protected void saveGame() {
        d("Saving Game");
        getPreferences(MODE_PRIVATE).edit().
                putString(PREF_SAVED_GAME, null).
                apply();
    }

    protected void d(String message) {
        Log.d("MemoryGame", message);
    }

    //classe qui regarde ou l'utilisateur clique
    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            synchronized (lock) {
                if(firstCard!=null && seconedCard!=null){
                    return;
                }
                int id = v.getId();
                int x = id/100;
                int y = id%100;
                turnCard((Button)v,x,y);
            }

        }

        //affiche l'image qui est sur la case choisie (en enlevant l'image de fond)
        private void turnCard(Button button,int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if(firstCard==null){
                firstCard = new Cards(button,x,y);
            }
            else{

                if(firstCard.x == x && firstCard.y == y){
                    return; //Quand l'user retourne la meme
                }

                seconedCard = new Cards(button,x,y);

                //incrementation du score
                turns++;
                ((TextView)findViewById(R.id.tv1)).setText("Essais: "+turns);


                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try{
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 1300);
            }


        }

    }

    class UpdateCardsHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }
        //si deux cartes identiques alors les deux deviennent invisible
        void checkCards(){
            if(cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]){
                firstCard.button.setVisibility(View.INVISIBLE);
                seconedCard.button.setVisibility(View.INVISIBLE);
                countPairOfCards --;
                Log.i("RRRRRRR", "  " +countPairOfCards);
                if(countPairOfCards <= 0){
                    onTurnAllCards();
                }
            }
            else {
                //sinon on remet l'image de fond
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
            }
            //on remet à zéro les images
            firstCard=null;
            seconedCard=null;
        }
    }


    /**
     * Instructions à effectuer une fois toutes les cartes retournées
     *
     * TODO ajouter le facteur temps dans le calcul du score
     */
    private void onTurnAllCards() {
        Log.i("onTurnAllCards()", " Gagné !!! " +countPairOfCards);

        Log.i("onTurnAllCards()","size=" + countPairOfCards);

        long durationEnd = System.currentTimeMillis();

        final long duration = durationEnd - durationStart;

        final int durationAsSec = (int)duration/1000;

        Log.i("onTurnAllCards()","duration     =" + duration);
        Log.i("onTurnAllCards()","durationAsSec=" + durationAsSec);


        //Calcul du score du joueur
        final int score = calculateScore(duration, turns, ROW_COUNT, COL_COUNT);

        final EditText txtUrl = new EditText(this);

// Set the default text to a link of the Queen
        txtUrl.setHint("Votre nom");

        //Annuler le chronomètre
        countDownTimer.cancel();

        findViewById(R.id.textViewTimer).setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Gagné!!!");
        builder.setMessage("Vous avez réussi " + turns + " essais \n Votre score est : " + score);
        builder.setView(txtUrl);
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = txtUrl.getText().toString();

                if(name == null || name.equals("")){
                    name = "Anonyme";
                }

                Log.i("loadCards()", "name =" + name);

                dbHelper.insertScore(name, score, turns, durationAsSec);

                Toast.makeText(GameToolbar.this, " Gagné !!!! en " + turns + " essais \net "+durationAsSec+"sec.\n Votre score est : " + score, Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();

    }

    /**
     * Calcul du score du joueur
     *
     * doit prondre en compte trois facteurs
     *
     * 1) le temps passé
     * 2) le nombre d'essais
     * 3) la le nombre de cases ( X * Y )
     *
     * @param duration
     * @param turns
     * @param nbRows
     * @param nbCols
     * @return
     */
    private int calculateScore(double duration, int turns, int nbRows, int nbCols) {

        double complexiteXY = Math.pow(nbRows*nbCols, 2);

        return (int)( (double)(100.0/((double)turns + duration) ) * (double)100000 * complexiteXY);
    }


    //** La classe des Cartes X Y **/
    public class Cards {

        int x;
        int y;
        Button button;

        Cards(Button button, int x, int y) {
            this.x = x;
            this.y = y;
            this.button = button;
        }

    }


}
