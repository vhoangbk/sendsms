package vn.app.sendsms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import vn.app.sendsms.model.History;

/**
 * Created by hoangnv on 12/28/16.
 */

public class HistoryEntity extends Entity<History>{

    public HistoryEntity(Context mContext) {
        super(DBDefinition.TABLE_HISTORY, mContext);

    }

    @Override
    public long add(History obj) {
        ContentValues values = new ContentValues();
        values.put(DBDefinition.COLUMN_HISTORY_ID, obj.getId());
        values.put(DBDefinition.COLUMN_HISTORY_FROM, obj.getFrom());
        values.put(DBDefinition.COLUMN_HISTORY_TO, obj.getTo());
        values.put(DBDefinition.COLUMN_HISTORY_CONTENT, obj.getContent());
        values.put(DBDefinition.COLUMN_HISTORY_TIME, obj.getTime());
        return super.add(values);
    }

    @Override
    public boolean update(History obj) {
        ContentValues values = new ContentValues();
        values.put(DBDefinition.COLUMN_HISTORY_FROM, obj.getFrom());
        values.put(DBDefinition.COLUMN_HISTORY_TO, obj.getTo());
        values.put(DBDefinition.COLUMN_HISTORY_CONTENT, obj.getContent());
        values.put(DBDefinition.COLUMN_HISTORY_TIME, obj.getTime());
        String where = DBDefinition.COLUMN_HISTORY_ID + "=" + obj.getId();
        return super.update(values, where);
    }

    @Override
    public int delete(History obj) {
        String where = DBDefinition.COLUMN_HISTORY_ID + "="	+ obj.getId();
        return super.delete(where);
    }

    @Override
    public History getById(int id) {
        History obj = new History();
        Cursor mCursor = super.select(DBDefinition.COLUMN_HISTORY_ID+"="+id);
        if (mCursor != null && mCursor.moveToFirst()) {
            obj.setId(mCursor.getInt(0));
            obj.setFrom(mCursor.getString(1));
            obj.setTo(mCursor.getString(2));
            obj.setContent(mCursor.getString(3));
            obj.setTime(mCursor.getString(4));
        } else {
            obj = null;
        }
        mCursor.close();
        return obj;
    }

    @Override
    public ArrayList<History> getAll() {
        ArrayList<History> listObj = new ArrayList<History>();

        Cursor mCursor = super.select(null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            do {
                int id = mCursor.getInt(0);
                String from = mCursor.getString(1);
                String to = mCursor.getString(2);
                String content = mCursor.getString(3);
                String time = mCursor.getString(4);

                History history = new History(id, from, to, content, time);

                listObj.add(history);
            } while (mCursor.moveToNext());
        }
        mCursor.close();

        return listObj;
    }

}
