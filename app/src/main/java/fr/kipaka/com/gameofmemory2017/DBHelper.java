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

    static final String SCORE_COL_ID = "id";
    static final String SCORE_COL_NAME = "name";
    static final String SCORE_COL_SCORE = "score";

    static final String SCORE_COL_TURNS = "turns";
    static final String SCORE_COL_DURATION = "duration";


    private static final String DATABASE_NAME = "Scores.db";
    private static final String SCORE_TAB_NAME = "scores";
    private HashMap hp;


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + SCORE_TAB_NAME + " " +
                        "(" + SCORE_COL_ID + " integer primary key, " + SCORE_COL_NAME + " text, " + SCORE_COL_SCORE + " integer, " + SCORE_COL_TURNS + " integer, " + SCORE_COL_DURATION + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCORE_TAB_NAME);
        onCreate(db);
    }

    boolean insertScore(String name, Integer score, Integer turns, Integer duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORE_COL_NAME, name);
        contentValues.put(SCORE_COL_SCORE, score);
        contentValues.put(SCORE_COL_TURNS, turns);
        contentValues.put(SCORE_COL_DURATION, duration);

        db.insert(SCORE_TAB_NAME, null, contentValues);
        return true;
    }

    Cursor getScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + SCORE_TAB_NAME + " where " + SCORE_COL_ID + "=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, SCORE_TAB_NAME);
    }

    boolean updateScore(Integer id, String name, Integer score, Integer turns, Integer duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORE_COL_NAME, name);
        contentValues.put(SCORE_COL_SCORE, score);
        contentValues.put(SCORE_COL_TURNS, turns);
        contentValues.put(SCORE_COL_DURATION, duration);
        db.update(SCORE_TAB_NAME, contentValues, SCORE_COL_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    Integer deleteScore(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCORE_TAB_NAME,
                SCORE_COL_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

    List<Map<String, Object>> getAllScores() {
        List<Map<String, Object>> rows = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + SCORE_TAB_NAME + " ORDER BY " + SCORE_COL_SCORE + " DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Map<String, Object> row = new HashMap<>();

            row.put(SCORE_COL_ID, res.getInt(res.getColumnIndex(SCORE_COL_ID)));
            row.put(SCORE_COL_NAME, res.getString(res.getColumnIndex(SCORE_COL_NAME)));
            row.put(SCORE_COL_SCORE, res.getInt(res.getColumnIndex(SCORE_COL_SCORE)));
            row.put(SCORE_COL_TURNS, res.getInt(res.getColumnIndex(SCORE_COL_TURNS)));
            row.put(SCORE_COL_DURATION, res.getInt(res.getColumnIndex(SCORE_COL_DURATION)));

            rows.add(row);
            res.moveToNext();
        }
        return rows;
    }


}