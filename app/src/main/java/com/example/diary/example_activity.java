package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class example_activity extends Activity {

	private View _bg__example;
	private View foot;
	private View rectangle_9;
	private TextView _04_03_24;
	private View rectangle_6;
	private View rectangle_7;
	private View rectangle_1;
	private TextView diary;
	private EditText editText;
	private ScrollView scrollView;

	public ImageView stat;

	public ImageView create_new_text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example);

		foot = findViewById(R.id.foot);
		rectangle_9 = findViewById(R.id.rectangle_9);
		_04_03_24 = findViewById(R.id._04_03_24);
		rectangle_6 = findViewById(R.id.rectangle_6);
		rectangle_7 = findViewById(R.id.rectangle_7);
		rectangle_1 = findViewById(R.id.rectangle_1);
		diary = findViewById(R.id.diary);
		editText = findViewById(R.id.user_input);
		scrollView = findViewById(R.id.example);

		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);

	}


	public void onClickNotes(View view){
		Intent intent = new Intent (this, menu_activity.class);
		startActivity(intent);
	}
	public void onClickZads(View view){
		Intent intent = new Intent (this,zad_activity.class);
		startActivity(intent);
	}
	public void onClickWonts(View view){
		Intent intent = new Intent (this,wonts_activity.class);
		startActivity(intent);
	}

//save доделать
//	public void onClickNewNote(View view){
//		Intent intent = new Intent (this,note_activity.class);
//		startActivity(intent);
//	}
// delete доделать
//	public void onClickStatistic(View view){
//		Intent intent = new Intent (this,emotions_activity.class);
//		startActivity(intent);
//	}

}
