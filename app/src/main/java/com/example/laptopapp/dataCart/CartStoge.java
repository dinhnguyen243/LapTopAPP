package com.example.laptopapp.dataCart;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.Model.Laptop;

import java.util.ArrayList;
public class CartStoge extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cartstorage2.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cartdata";
    private static final String Key_ID = "key_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String IMG = "product_img";
    private static final String PRODUCTNAME = "product_name";
    private static final String QUANLITY = "quanlity";
    private static final String PRICE = "price";
    private static final String USER_ID = "user_id";

    public CartStoge(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s TEXT,%s TEXT, %s TEXT)", TABLE_NAME,Key_ID,USER_ID, PRODUCT_ID, PRODUCTNAME, IMG ,QUANLITY,PRICE);

        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_table);
        onCreate(db);
    }

    public long addtocart(Laptop laptop, String user_id)  {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_ID,user_id);
            values.put(PRODUCT_ID,laptop.getProduct_id());
            values.put(PRODUCTNAME, laptop.getProduct_name());
            values.put(IMG, laptop.getImg().get(0).toString());
            values.put(PRICE,String.valueOf(laptop.getPrice_outcome()));
            values.put(QUANLITY,"1");
            result = db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {

        }

        return result;
    }

    public ArrayList<Cart> getallcart(String userid) {

        ArrayList<Cart> List = new ArrayList<>();
        String query = "SELECT * FROM cartdata WHERE user_id = '"+userid+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Cart cart = new Cart();
            cart.setCart_id(cursor.getInt(0));
            cart.setUser_id(cursor.getString(1));
            cart.setProduct_id(cursor.getString(2));
            cart.setProduct_name(cursor.getString(3));
            cart.setImg(cursor.getString(4));
            cart.setQuality(cursor.getString(5));
            cart.setPrice(cursor.getString(6));
            List.add(cart);
            cursor.moveToNext();
        }
        return List;
    }

    public long deleteCart(String ID,String User_ID ) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            result = db.delete(TABLE_NAME, PRODUCT_ID + " = ?" +" and "+ USER_ID + "= ?", new String[]{String.valueOf(ID),String.valueOf(User_ID)});
            System.out.println("xoa ok");
            db.close();
        } catch (Exception e) {

        }
        return result;
    }
    public long deleteAllCart(String User_ID ) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            result = db.delete(TABLE_NAME, USER_ID + "= ?", new String[]{String.valueOf(User_ID)});
            System.out.println("xoa ok");
            db.close();
        } catch (Exception e) {

        }
        return result;
    }
    public long updateCart(String user_id,String ID,String quality) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_ID,user_id);
            values.put(PRODUCT_ID,ID );
            values.put(QUANLITY, quality);
            result= db.update(TABLE_NAME, values, PRODUCT_ID + " = ?" + " and " + USER_ID + " = ?"  , new String[]{String.valueOf(ID),user_id});
            db.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;

    }

}
