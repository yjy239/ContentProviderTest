package com.example.practiseforcontentprovider;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button button;
	private Button button1;
	private Button button2;
	private Button button3;
	private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.add);
        button1 = (Button)findViewById(R.id.delete);
        button2 = (Button)findViewById(R.id.update);
        button3 = (Button)findViewById(R.id.query);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }
    
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add:
			Uri uri = Uri.parse("content://com.example.practiseforcontentprovider/book");
			ContentValues values = new ContentValues();
			values.put("name", "kings");
			values.put("author", "aa");
			values.put("pages", 1040);
			values.put("prices", 12.56);
			Uri newuri = getContentResolver().insert(uri, values);
			newId = newuri.getPathSegments().get(1);
			Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.query:
			Uri quri = Uri.parse("content://com.example.practiseforcontentprovider/book");
			Cursor cursor = getContentResolver().query(quri, null, null, null, null);
			if(cursor != null){
				Log.e("cursor", "notnull");
				while(cursor.moveToNext()){
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String author = cursor.getString(cursor.getColumnIndex("author"));
					int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					double prices = cursor.getDouble(cursor.getColumnIndex("prices"));
					Log.e("name", ""+name);
					Log.e("author", ""+author);
					Log.e("pages", ""+pages);
					Log.e("prices", ""+prices);
				}
				cursor.close();
			}
			break;
			
		case R.id.update:
			Uri uri2 = Uri.parse("content://com.example.practiseforcontentprovider/book/"+ newId);
			ContentValues values2 = new ContentValues();
			values2.put("name", "Sword");
			values2.put("author", "bb");
			values2.put("pages", 1500);
			values2.put("prices", 24.05);
			getContentResolver().update(uri2, values2, null, null);
			Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.delete:
			Uri uri3 = Uri.parse("content://com.example.practiseforcontentprovider/book/"+ newId);
			getContentResolver().delete(uri3, null, null);
			Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
    
}
