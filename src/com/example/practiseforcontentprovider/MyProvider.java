package com.example.practiseforcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider{
	
	public static final int BOOK_DIR = 0;
	public static final int BOOK_ITEM = 1;
	public static final int CATEGORY_DIR = 2;
	public static final int CATEGORY_ITEM = 3;
	
	public static final String AUTHORITY = "com.example.practiseforcontentprovider";
	
	private static UriMatcher uriMatcher;
	private MyDataBaseHelper dbHelper;
	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
		uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
		uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
		uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int deleteRow = 0;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			deleteRow = db.delete("Book", selection, selectionArgs);
			break;
			
		case BOOK_ITEM:
			String bookid = uri.getPathSegments().get(1);
			deleteRow = db.delete("Book", "id = ?", new String[]{bookid});
			break;
			
		case CATEGORY_DIR:
			deleteRow = db.delete("Category", selection, selectionArgs);
			break;
			
		case CATEGORY_ITEM:
			String categoryid = uri.getPathSegments().get(1);
			deleteRow = db.delete("Category", "id = ?", new String[]{categoryid});
			break;
		default:
			break;
		}
		return deleteRow;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			return "vnd.android.cursor.dir/vnd.com.example.practiseforcontentprovider.book";
			
		case BOOK_ITEM:
			return "vnd.android.cursor.item/vnd.com.example.practiseforcontentprovider.book";
			
		case CATEGORY_DIR:
			return "vnd.android.cursor.dir/vnd.com.example.practiseforcontentprovider.category";
			
		case CATEGORY_ITEM:
			return "vnd.android.cursor.item/vnd.com.example.practiseforcontentprovider.category";
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri uriReturn = null;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
		case BOOK_ITEM:
			long newBookId = db.insert("Book", null, values);
			uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
			break;
			
		case CATEGORY_DIR:
		case CATEGORY_ITEM:
			long newCategoryId = db.insert("Category", null, values);
			uriReturn = Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryId);
			break;
		default:
			break;
		}
		return uriReturn;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper = new MyDataBaseHelper(getContext(), "BookStore.db", null, 3);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			cursor = db.query("Book", projection, selection, selectionArgs,null,null, sortOrder);
			break;
			
		case BOOK_ITEM:
			String bookId = uri.getPathSegments().get(1);
			cursor = db.query("Book", projection, "id = ?", new String[]{bookId}, null, null, sortOrder);
			break;
			
		case CATEGORY_DIR:
			cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
			break;
			
		case CATEGORY_ITEM:
			String categoryId = uri.getPathSegments().get(1);
			cursor = db.query("Category", projection, "id = ?", new String[]{categoryId}, null, null, sortOrder);
			break;

		default:
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int updateRow = 0;
		switch (uriMatcher.match(uri)) {
		case BOOK_DIR:
			updateRow = db.update("Book", values, selection, selectionArgs);
			break;
			
		case BOOK_ITEM:
			String bookid = uri.getPathSegments().get(1);
			updateRow = db.update("Book", values, "id = ?", new String[]{bookid});
			break;
			
		case CATEGORY_DIR:
			updateRow = db.update("Category", values, selection, selectionArgs);
			break;
			
		case CATEGORY_ITEM:
			String categoryid = uri.getPathSegments().get(1);
			updateRow = db.update("Category", values, "id = ?", new String[]{categoryid});
			break;
		default:
			break;
		}
		return 0;
	}

}
