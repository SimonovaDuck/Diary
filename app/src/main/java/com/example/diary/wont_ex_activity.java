
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		wont_ex
	 *	@date 		Monday 01st of April 2024 01:01:20 PM
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
import android.widget.TextView;
import android.widget.ImageView;

public class wont_ex_activity extends Activity {


	private View foot;

	private View rectangle_9;
	private TextView _04_03_24;

	private View rectangle_5;


	private View rectangle_6;

	private View rectangle_7;

	private View rectangle_1;
	private TextView diary;

	private View rectangle_16;

	private ImageView vector_ek5;

	private ImageView vector_ek7;

	private ImageView vector_ek8;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.wont_ex);

		
		foot = (View) findViewById(R.id.foot);
		rectangle_9 = (View) findViewById(R.id.rectangle_9);
		_04_03_24 = (TextView) findViewById(R.id._04_03_24);
		rectangle_5 = (View) findViewById(R.id.rectangle_5);
		rectangle_6 = (View) findViewById(R.id.rectangle_6);
		rectangle_7 = (View) findViewById(R.id.rectangle_7);
		rectangle_1 = (View) findViewById(R.id.rectangle_1);
		diary = (TextView) findViewById(R.id.diary);
		rectangle_16 = (View) findViewById(R.id.rectangle_16);
		vector_ek5 = (ImageView) findViewById(R.id.vector_ek5);
		vector_ek7 = (ImageView) findViewById(R.id.vector_ek7);
		vector_ek8 = (ImageView) findViewById(R.id.vector_ek8);

		
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
	
	