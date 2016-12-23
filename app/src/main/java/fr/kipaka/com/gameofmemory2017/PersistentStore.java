package fr.kipaka.com.gameofmemory2017;

/**
 * Created by M0297357 on 23/12/2016.
 */
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;

public class PersistentStore extends SQLiteOpenHelper {

    static final String[] HIGH_SCORE_FIELDS = new String[] { "ROWID as _id", "MIN(moves) as best_score" };

    public PersistentStore(Context context) {
        super(context, "memory.sqlite", null, 1);
    }

    public void addScore(String levelset, int level, int moves) {
        ContentValues values = new ContentValues();
        values.put("moves", moves);
        getWritableDatabase().insert("scores", null, values);
    }

    public Cursor getScores() {
        return getReadableDatabase().query(
                "scores",
                HIGH_SCORE_FIELDS,
                null,
                null,
                "moves, moves", // GROUP BY
                null,
                null
        );
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores (moves INT, scored_at DATETIME DEFAULT (DATETIME('now')))");
    }

    public void onOpen(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
    }
}
