package vn.app.sendsms.database;

/**
 * Created by hoangnv on 12/28/16.
 */

public class DBDefinition {
    //database version
    public static final int DATABASE_VERSION = 1;

    //database name
    public static final String DATABASE_NAME = "sendsms.db";

    //dinh nghia ten bang va cac cot cho tung bang
    public static final String TABLE_HISTORY="history";
    public static final String COLUMN_HISTORY_ID = "history_id";
    public static final String COLUMN_HISTORY_FROM = "history_from";
    public static final String COLUMN_HISTORY_TO = "history_to";
    public static final String COLUMN_HISTORY_CONTENT = "history_content";
    public static final String COLUMN_HISTORY_TIME = "history_time";
}
