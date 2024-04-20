
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		zad
	 *	@date 		Monday 01st of April 2024 01:00:29 PM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.diary;

	import android.annotation.SuppressLint;
	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.ImageView;
	import android.widget.TextView;

public class zad_activity extends Activity {

	
	private View _bg__zad;

	private View rectangle_5;

	private View rectangle_6;

	private View rectangle_7;

	private View rectangle_1;
	private TextView diary;


	private View foot;

	private View rectangle_11;

	private TextView _06_02_24;

	private View rectangle_11_ek1;

	private TextView ________________________ek1;
	private TextView _04_02_24;

	private View rectangle_10;
	private TextView ________________________ek2;
	private TextView _01_03_24;

	private View rectangle_9;
	private TextView _04_03_24;
	private TextView ______________________;

	public ImageView stat;

	public ImageView create_new_text;

	@SuppressLint("MissingInflatedId")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.zad);

		
		_bg__zad = (View) findViewById(R.id._bg__zad);
		rectangle_5 = (View) findViewById(R.id.rectangle_5);
		rectangle_6 = (View) findViewById(R.id.rectangle_6);
		rectangle_7 = (View) findViewById(R.id.rectangle_7);
		rectangle_1 = (View) findViewById(R.id.rectangle_1);
		diary = (TextView) findViewById(R.id.diary);
		foot = (View) findViewById(R.id.foot);
		rectangle_11 = (View) findViewById(R.id.rectangle_11);
		_06_02_24 = (TextView) findViewById(R.id._06_02_24);
		rectangle_11_ek1 = (View) findViewById(R.id.rectangle_11_ek1);
		_04_02_24 = (TextView) findViewById(R.id._04_02_24);
		rectangle_10 = (View) findViewById(R.id.rectangle_10);
		_01_03_24 = (TextView) findViewById(R.id._01_03_24);
		rectangle_9 = (View) findViewById(R.id.rectangle_9);
		_04_03_24 = (TextView) findViewById(R.id._04_03_24);


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
	
	