package vn.app.sendsms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import vn.app.sendsms.model.Message;

/**
 * Created by hoangnv on 12/28/16.
 */

public class MessageEntity extends Entity<Message>{

    public MessageEntity(Context mContext) {
        super(DBDefinition.TABLE_MESSAGE, mContext);

    }

    @Override
    public long add(Message obj) {
        ContentValues values = new ContentValues();
        if (obj.getId() != 0) {
            values.put(DBDefinition.COLUMN_MESSAGE_ID, obj.getId());
        }
        values.put(DBDefinition.COLUMN_MESSAGE_ID_SERVER, obj.getIdServer());
        values.put(DBDefinition.COLUMN_MESSAGE_CONTENT_SMS, obj.getContentSms());
        values.put(DBDefinition.COLUMN_MESSAGE_DATA_SENT, obj.getDateSend());
        values.put(DBDefinition.COLUMN_MESSAGE_NUMBER_RECEIVER, obj.getNumberReceiver());
        return super.add(values);
    }

    @Override
    public boolean update(Message obj) {
        ContentValues values = new ContentValues();
        values.put(DBDefinition.COLUMN_MESSAGE_ID_SERVER, obj.getIdServer());
        values.put(DBDefinition.COLUMN_MESSAGE_CONTENT_SMS, obj.getContentSms());
        values.put(DBDefinition.COLUMN_MESSAGE_DATA_SENT, obj.getDateSend());
        values.put(DBDefinition.COLUMN_MESSAGE_NUMBER_RECEIVER, obj.getNumberReceiver());
        String where = DBDefinition.COLUMN_MESSAGE_ID + "=" + obj.getId();
        return super.update(values, where);
    }

    @Override
    public int delete(Message obj) {
        String where = DBDefinition.COLUMN_MESSAGE_ID + "="	+ obj.getId();
        return super.delete(where);
    }

    @Override
    public Message getById(int id) {
        Message obj = new Message();
        Cursor mCursor = super.select(DBDefinition.COLUMN_MESSAGE_ID +"="+id);
        if (mCursor != null && mCursor.moveToFirst()) {
            obj.setId(mCursor.getInt(0));
            obj.setIdServer(mCursor.getString(1));
            obj.setContentSms(mCursor.getString(2));
            obj.setDateSend(mCursor.getLong(3));
            obj.setNumberReceiver(mCursor.getString(4));
        } else {
            obj = null;
        }
        mCursor.close();
        return obj;
    }

    @Override
    public ArrayList<Message> getAll() {
        ArrayList<Message> listObj = new ArrayList<Message>();

        Cursor mCursor = super.select(null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    int id = mCursor.getInt(0);
                    String idServer = mCursor.getString(1);
                    String contentSms = mCursor.getString(2);
                    long dataSend = mCursor.getLong(3);
                    String number = mCursor.getString(4);

                    Message message = new Message(idServer, contentSms, dataSend, number);
                    message.setId(id);

                    listObj.add(message);
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }


        return listObj;
    }

}
