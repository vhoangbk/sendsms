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

        String create_table_message = "CREATE TABLE " + DBDefinition.TABLE_MESSAGE + " ("
                + DBDefinition.COLUMN_MESSAGE_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBDefinition.COLUMN_MESSAGE_ID_SERVER + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_MESSAGE_CONTENT_SMS + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_MESSAGE_DATE_SENT + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_MESSAGE_NUMBER_RECEIVER + " TEXT NOT NULL, "
                + DBDefinition.COLUMN_MESSAGE_SEND_FLAG + " INTEGER)";

        db.execSQL(create_table_message);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sqlDropTable = "DROP TABLE IF EXISTS ";

        db.execSQL(sqlDropTable + DBDefinition.TABLE_MESSAGE);

        onCreate(db);
    }

}