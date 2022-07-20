package edu.sjsu.android.kanbanboard.dataprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

public class KanbanBoardDB extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "KanbanBoardDatabase";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "todoList";
    protected static final String ID = "_id";
    protected static final String TITLE = "title";
    protected static final String DESCRIPTION = "description";
    protected static final String DATE = "date";

    protected static final String TABLE_NAME_2 = "Credential";
    protected static final String ID_2 = "_id";
    protected static final String EMAIL = "email";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "register",1);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "login", 2);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "todo", 3);
    }

    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT NOT NULL, "
                    + DESCRIPTION + " TEXT NOT NULL, "
                    + DATE + " TEXT NOT NULL);";

    static final String CREATE_TABLE_2 =
            " CREATE TABLE " + TABLE_NAME_2 +
                    " (" + ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EMAIL + " TEXT NOT NULL, "
                    + USERNAME + " TEXT NOT NULL, "
                    + PASSWORD + " TEXT NOT NULL);";


    public KanbanBoardDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_2);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(sqLiteDatabase);
    }


    public long insert( Uri uri,ContentValues contentValues) {

        SQLiteDatabase database = getWritableDatabase();

        int uriType = uriMatcher.match(uri);
        switch(uriType) {
            case 1:
                return database.insert(TABLE_NAME_2, null, contentValues);

            case 3:
                return database.insert(TABLE_NAME,null,contentValues);
        }
        return -1;
    }

    public int delete (Uri uri, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase database = getWritableDatabase();
        return database.update(TABLE_NAME, values, selection, null);

    }

    public Cursor getAllToDo(String orderBy) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME, new String[]{ID, TITLE, DESCRIPTION, DATE},
                null, null, null, null, orderBy);
    }


    public Cursor getUserInfo(String username, String password ) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from Credential where username = ? and password = ?",new String[]{username,password});

        return cursor;
    }

}
