package edu.sjsu.android.kanbanboard.dataprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ContentProvider extends android.content.ContentProvider {
    private KanbanBoardDB database;

    public ContentProvider() {
    }

    @Override
    public boolean onCreate() {
        database = new KanbanBoardDB(getContext());
        return true;
    }

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "register",1);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "login", 2);
        uriMatcher.addURI("edu.sjsu.android.kanbanboard.dataprovider", "todo", 3);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       return database.delete(uri,selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = database.insert(uri,values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        sortOrder = sortOrder == null ? "_id" : sortOrder;
        
        int uriType = uriMatcher.match(uri);
        switch(uriType) {
            case 2:
                String username = selectionArgs[0];
                String password = selectionArgs[1];
                return database.getUserInfo(username, password);
            case 3:
                Cursor allToDo = database.getAllToDo(sortOrder);
                return allToDo;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return  database.update(uri, values, selection, null);
    }
}