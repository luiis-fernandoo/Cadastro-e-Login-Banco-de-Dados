package com.example.cadastro_banco_de_dados.Activities.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PASSWORD = "password";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER," +
                    FeedEntry.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_PASSWORD + "TEXT," +
                    FeedEntry.COLUMN_NAME_NAME + " TEXT )";


    public static class DBHelpers extends SQLiteOpenHelper {


        public DBHelpers(@Nullable Context context) {
            super(context, "appCadastro.db", null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

    }
}
