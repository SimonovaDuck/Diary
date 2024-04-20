
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		emotions
	 *	@date 		Monday 01st of April 2024 12:59:19 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.diary;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.ImageView;
	import android.widget.TextView;

public class emotions_activity extends Activity {


	private View foot;

	private View rectangle_5;

	private View rectangle_6;

	private View rectangle_7;

	private View rectangle_1;
	private TextView diary;

	private TextView num_of_day;
	private TextView text_emotions;

	public ImageView stat;

	public ImageView create_new_text;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.emotions);

		foot = (View) findViewById(R.id.foot);

		rectangle_5 = (View) findViewById(R.id.rectangle_5);
		rectangle_6 = (View) findViewById(R.id.rectangle_6);

		rectangle_7 = (View) findViewById(R.id.rectangle_7);
		rectangle_1 = (View) findViewById(R.id.rectangle_1);

		diary = (TextView) findViewById(R.id.diary);
		num_of_day = (TextView) findViewById(R.id.num_of_day);
		text_emotions = (TextView) findViewById(R.id.text_emotions);

		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);
	
		
		//custom code goes here
	
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

	public void onClickNewNote(View view){
		Intent intent = new Intent (this,note_activity.class);
		startActivity(intent);
	}

	public void onClickStatistic(View view){
		Intent intent = new Intent (this,emotions_activity.class);
		startActivity(intent);
	}
}
	
	