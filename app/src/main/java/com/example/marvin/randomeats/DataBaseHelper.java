package com.example.marvin.randomeats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Edgar on 6/5/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.favoriteRestaurant.DATABASE_VERSION);
        this.context = context;

    }

    private Context context;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Contract.favoriteRestaurant.TABLE_NAME;

    public String[] favoriteRestaurant = {
            Contract.favoriteRestaurant._ID,
            Contract.favoriteRestaurant.NAME,
            Contract.favoriteRestaurant.IMAGE,
            Contract.favoriteRestaurant.URL,
            Contract.favoriteRestaurant.PHONE,
            Contract.favoriteRestaurant.ADDRESS,
    };

    public static final String CREATE_TABLE = "CREATE TABLE " +
            Contract.favoriteRestaurant.TABLE_NAME + " (" +
            Contract.favoriteRestaurant._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Contract.favoriteRestaurant.NAME + " TEXT NOT NULL, " +
            Contract.favoriteRestaurant.IMAGE + " TEXT NOT NULL, " +
            Contract.favoriteRestaurant.URL + " TEXT NOT NULL, " +
            Contract.favoriteRestaurant.PHONE + " TEXT NOT NULL, " +
            Contract.favoriteRestaurant.ADDRESS + " TEXT NOT NULL)";


    public void insertRestaurant(Business business) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Contract.favoriteRestaurant.NAME, business.name);
        cv.put(Contract.favoriteRestaurant.IMAGE, business.image_url);
        cv.put(Contract.favoriteRestaurant.URL, business.url);
        cv.put(Contract.favoriteRestaurant.PHONE, business.display_phone);
        cv.put(Contract.favoriteRestaurant.ADDRESS, business.location.toString());
        db.insert(Contract.favoriteRestaurant.TABLE_NAME, null, cv);
        db.close();
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
    }

    public Cursor getAllRestaurants() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contract.favoriteRestaurant.TABLE_NAME, favoriteRestaurant, null, null, null, null, null, null);
    }

    public void deleteRestaurant(String phone) {
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(phone)};
        db.delete(Contract.favoriteRestaurant.TABLE_NAME, Contract.favoriteRestaurant.NAME + " ==?", ids);
        Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    public void addRows(List<Business> businesses){
        for (Business business: businesses){
            insertRestaurant(business);
        }
    }

    public Cursor getRowById(long id){
        SQLiteDatabase db = getWritableDatabase();
        String[] ids = {String.valueOf(id)};
        return db.query(Contract.favoriteRestaurant.TABLE_NAME, favoriteRestaurant, Contract.favoriteRestaurant._ID + " ==?", ids, null, null, null);
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + Contract.favoriteRestaurant.TABLE_NAME);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//            Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}

//}
