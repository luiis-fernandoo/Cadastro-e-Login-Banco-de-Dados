package com.example.cadastro_banco_de_dados.Activities.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.cadastro_banco_de_dados.Activities.Helpers.FeedEntry;
import com.example.cadastro_banco_de_dados.Activities.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final User user;
    private FeedEntry.DBHelpers db;
    private static final String TAG = "HomeLog";

    public UserDAO(Context ctx, User user) {
        this.user = user;
        this.db = new FeedEntry.DBHelpers(ctx);
    }

    public void cadastroUser(){


        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_EMAIL, this.user.getEmail());
        values.put(FeedEntry.COLUMN_NAME_NAME, this.user.getName());
        values.put(FeedEntry.COLUMN_NAME_PASSWORD, this.user.getPassword());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = dbLite.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public boolean loginUser(){

        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String[] projection = {
                FeedEntry.COLUMN_NAME_EMAIL,
                FeedEntry.COLUMN_NAME_PASSWORD
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_EMAIL + " = ? AND " + FeedEntry.COLUMN_NAME_PASSWORD + " = ?;";
        String[] selectionArgs = {this.user.getEmail(), this.user.getPassword() };

        Cursor cursor = dbLite.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );
        Log.d(TAG, "Número de linhas no cursor: " + cursor.getCount()); // Adicione este log

        if (cursor.getCount() == 1){
            return true;
        }else{
            return false;
        }

    }

    public List welcome(){

        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String[] projection = {
                FeedEntry.COLUMN_NAME_NAME,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_EMAIL + " = ? AND " + FeedEntry.COLUMN_NAME_PASSWORD + " = ?;";
        String[] selectionArgs = {this.user.getEmail(), this.user.getPassword() };

        Cursor cursor = dbLite.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );

        List nameUser = new ArrayList<>();
        while(cursor.moveToNext()) {
            long name = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_NAME));
            nameUser.add(name);
        }
        return nameUser;
    }

    public User obterUsuarioPorEmail(){

        SQLiteDatabase dbLite = this.db.getReadableDatabase();

        String sql = "Select * From user Where email = ?;";

        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail()});

        Log.d(TAG, "Número de linhas no cursor: " + cursor.getCount());

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }else{
            Log.d(TAG, "Cursor vazio ou nulo!"); // Adicione este log
        }

        this.user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        this.user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
        this.user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));

        return this.user;
    }

    public boolean update(){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",this.user.getName());
        cv.put("password", this.user.getPassword());
        cv.put("email", this.user.getEmail());

        long ret = dbLite.update("user", cv,"email = ?", new String[]{this.user.getEmail()} );
        if (ret > 0){
            return true;
        }
        return false;
    }

    public boolean delete(){

            SQLiteDatabase dbLite = this.db.getWritableDatabase();
            long ret = dbLite.delete("user","email = ?", new String[]{this.user.getEmail()} );

            if (ret > 0){
                return true;
            }
            return false;
    }
}
