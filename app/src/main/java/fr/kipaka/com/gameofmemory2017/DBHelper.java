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
    private static final String DATABASE_NAME = "Compteur.db";
    private static final String COMPTEUR_TABLE_NAME = "gamers";
    private HashMap hp;


    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table gamers " +
                        "(id integer primary key, name text,score integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS gamers");
        onCreate(db);
    }

    boolean insertGamers(String name, Integer score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score", score);
        db.insert("gamers", null, contentValues);
        return true;
    }

    Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from gamers where id=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, COMPTEUR_TABLE_NAME);
    }

    boolean updateContact(Integer id, String name, Integer score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score", score);
        db.update("gamers", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("gamers",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

   List<Map<String, Object>> getAllScores() {
        List<Map<String, Object>> rows = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from gamers ORDER BY score DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Map<String, Object> row = new HashMap<>();

            row.put(COMPTEUR_COLUMN_NAME, res.getString(res.getColumnIndex(COMPTEUR_COLUMN_NAME)));
            row.put(COMPTEUR_COLUMN_SCORE, res.getInt(res.getColumnIndex(COMPTEUR_COLUMN_SCORE)));

            rows.add(row);
            res.moveToNext();
        }
        return rows;
    }


}