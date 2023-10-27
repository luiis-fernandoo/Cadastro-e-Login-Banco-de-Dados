package com.example.cadastro_banco_de_dados.Activities.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.cadastro_banco_de_dados.Activities.Helpers.FeedEntry;
import com.example.cadastro_banco_de_dados.Activities.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final User user;
    private FeedEntry.DBHelpers db;

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
}
