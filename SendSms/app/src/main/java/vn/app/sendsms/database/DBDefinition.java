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
    public static final String TABLE_MESSAGE ="message";
    public static final String COLUMN_MESSAGE_ID = "message_id_server";
    public static final String COLUMN_MESSAGE_ID_SERVER = "message_id";
    public static final String COLUMN_MESSAGE_CONTENT_SMS = "message_content_sms";
    public static final String COLUMN_MESSAGE_DATE_SENT = "message_date_send";
    public static final String COLUMN_MESSAGE_NUMBER_RECEIVER = "message_number_receiver";
    public static final String COLUMN_MESSAGE_SEND_FLAG = "message_send_flag";
}
