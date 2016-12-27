package vn.app.sendsms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hoangnv on 12/28/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    Context _context;

    public DatabaseHandler(Context context) {

        super(context, DBDefinition.DATABASE_NAME, null,DBDefinition.DATABASE_VERSION);

        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_history = "CREATE TABLE " + DBDefinition.TABLE_HISTORY + " ("
                + DBDefinition.COLUMN_HISTORY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBDefinition.COLUMN_HISTORY_FROM + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_HISTORY_TO + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_HISTORY_CONTENT + " TEXT, "
                + DBDefinition.COLUMN_HISTORY_TIME + " TEXT)";

        db.execSQL(create_table_history);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sqlDropTable = "DROP TABLE IF EXISTS ";

        db.execSQL(sqlDropTable + DBDefinition.TABLE_HISTORY);

        onCreate(db);
    }

}