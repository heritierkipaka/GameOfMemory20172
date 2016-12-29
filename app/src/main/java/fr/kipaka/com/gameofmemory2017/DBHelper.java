package fr.kipaka.com.gameofmemory2017;

/***
 * Created by M0297357 on 26/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DBHelper extends SQLiteOpenHelper {

    static final String COMPTEUR_COLUMN_ID = "id";
    static final String COMPTEUR_COLUMN_NAME = "name";
    static final String COMPTEUR_COLUMN_SCORE = "score";

    static final String COMPTEUR_COLUMN_TURNS = "turns";
    static final String COMPTEUR_COLUMN_DURATION = "duration";


    private static final String DATABASE_NAME = "Compteur.db";
    private static final String COMPTEUR_TABLE_NAME = "scores";
    private HashMap hp;


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + COMPTEUR_TABLE_NAME + " " +
                        "(" + COMPTEUR_COLUMN_ID + " integer primary key, " + COMPTEUR_COLUMN_NAME + " text, " + COMPTEUR_COLUMN_SCORE + " integer, " + COMPTEUR_COLUMN_TURNS + " integer, " + COMPTEUR_COLUMN_DURATION + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMPTEUR_TABLE_NAME);
        onCreate(db);
    }

    boolean insertScore(String name, Integer score, Integer turns, Integer duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPTEUR_COLUMN_NAME, name);
        contentValues.put(COMPTEUR_COLUMN_SCORE, score);
        contentValues.put(COMPTEUR_COLUMN_TURNS, turns);
        contentValues.put(COMPTEUR_COLUMN_DURATION, duration);

        db.insert(COMPTEUR_TABLE_NAME, null, contentValues);
        return true;
    }

    Cursor getScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + COMPTEUR_TABLE_NAME + " where " + COMPTEUR_COLUMN_ID + "=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, COMPTEUR_TABLE_NAME);
    }

    boolean updateScore(Integer id, String name, Integer score, Integer turns, Integer duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPTEUR_COLUMN_NAME, name);
        contentValues.put(COMPTEUR_COLUMN_SCORE, score);
        contentValues.put(COMPTEUR_COLUMN_TURNS, turns);
        contentValues.put(COMPTEUR_COLUMN_DURATION, duration);
        db.update(COMPTEUR_TABLE_NAME, contentValues, COMPTEUR_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    Integer deleteScore(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COMPTEUR_TABLE_NAME,
                COMPTEUR_COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

    List<Map<String, Object>> getAllScores() {
        List<Map<String, Object>> rows = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COMPTEUR_TABLE_NAME + " ORDER BY " + COMPTEUR_COLUMN_SCORE + " DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Map<String, Object> row = new HashMap<>();

            row.put(COMPTEUR_COLUMN_NAME, res.getString(res.getColumnIndex(COMPTEUR_COLUMN_NAME)));
            row.put(COMPTEUR_COLUMN_SCORE, res.getInt(res.getColumnIndex(COMPTEUR_COLUMN_SCORE)));
            row.put(COMPTEUR_COLUMN_TURNS, res.getInt(res.getColumnIndex(COMPTEUR_COLUMN_TURNS)));
            row.put(COMPTEUR_COLUMN_DURATION, res.getInt(res.getColumnIndex(COMPTEUR_COLUMN_DURATION)));

            rows.add(row);
            res.moveToNext();
        }
        return rows;
    }


}