package vn.app.sendsms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by hoangnv on 12/28/16.
 */

public abstract class Entity<T>

{
    public DatabaseHandler _dbhandler;
    protected SQLiteDatabase _db;
    protected Context _context;
    protected String _tblname;

    public Entity(String tblName, Context mContext) {
        this._tblname = tblName;
        this._dbhandler = new DatabaseHandler(mContext);
        this._context = mContext;
    }

    public abstract long add(T obj);
    /**
     * insert ban ghi vao CSDL
     *
     * @param values
     * @return id cua ban ghi moi duoc them (-1 neu ko them duoc)
     */
    public long add(ContentValues values) {
        long id = -1;
        try {
            if (null != _db)
                _db = null;
            _db = _dbhandler.getWritableDatabase();
            id = _db.insert(_tblname, null, values);
            _db.close();
            return id;
        } catch (Exception e) {
            return id;
        }
    }

    public abstract boolean update(T obj);
    /**
     * update ban ghi
     *
     * @param values
     * @param where
     * @return
     */
    public boolean update(ContentValues values, String where) {
        try {
            if (null != _db)
                _db = null;
            _db = _dbhandler.getWritableDatabase();
            _db.update(_tblname, values, where, null);
            _db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public abstract int delete(T obj);
    /**
     * xoa ban ghi trong co so du lieu
     *
     * @param where: dieu kien xoa
     * @return so ban ghi da xoa (0 neu ko xoa duoc)
     */
    public int delete(String where) {
        int count = 0;
        try {
            if (null != _db)
                _db = null;
            _db = _dbhandler.getWritableDatabase();
            count = _db.delete(_tblname, where, null);
            _db.close();
            return count;
        } catch (Exception e) {
            return count;
        }
    }
    /**
     * xoa tat ca du lieu cua bang
     * @return
     */
    public int deleteAll(){
        int count = 0;
        try {
            if (null != _db)
                _db = null;
            _db = _dbhandler.getWritableDatabase();
            count = _db.delete(_tblname, null, null);
            _db.close();
            return count;
        } catch (Exception e) {
            return count;
        }
    }
    /**
     * thuc hien query
     *
     * @param sql
     * @return
     */
    public Cursor query(String sql){
        try {
            if (null != _db)
                _db = null;
            _db = _dbhandler.getWritableDatabase();
            return _db.rawQuery(sql, null);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * select voi dieu kien where
     *
     * @param where
     * @return
     */
    public Cursor select(String where){
        String sql;
        if(where!=null){
            sql = "SELECT * FROM "+_tblname+" where "+where;

        }else{
            sql = "SELECT * FROM "+_tblname;
        }
        return query(sql);
    }

    public abstract T getById(int id);

    public abstract ArrayList<T> getAll();

    public ArrayList<T> search() {
        return new ArrayList<T>();
    }

}
