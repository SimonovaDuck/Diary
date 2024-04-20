
	 

	

package com.example.diary;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.ImageView;
	import android.widget.TextView;

public class menu_activity extends Activity {

	
	private TextView _04_03_24;
	public View rectangle_5;

	public View rectangle_6;
	public View rectangle_7;

	private View rectangle_1;
	private TextView diary;

	private View foot;

	public TextView __1;

	public ImageView stat;

	public ImageView create_new_text;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);


		_04_03_24 = (TextView) findViewById(R.id._04_03_24);
		rectangle_5 = (View) findViewById(R.id.rectangle_5);
		rectangle_6 = (View) findViewById(R.id.rectangle_6);
		rectangle_7 = (View) findViewById(R.id.rectangle_7);
		rectangle_1 = (View) findViewById(R.id.rectangle_1);
		diary = (TextView) findViewById(R.id.diary);
		foot = (View) findViewById(R.id.foot);

		__1 = (TextView) findViewById(R.id.__1);

		create_new_text = (ImageView) findViewById(R.id.create_new_text);
		stat = (ImageView) findViewById(R.id.stat);
	
	}

	public void onClickRealNote(View view){
		Intent intent = new Intent (this, note_ex_activity.class);
		startActivity(intent);
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
	
	