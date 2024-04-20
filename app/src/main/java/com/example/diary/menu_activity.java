
	 

	

package com.example.diary;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.TextView;

public class menu_activity extends Activity {

	
	private TextView _04_03_24;
	private View rectangle_5;

	private View rectangle_6;
	private View rectangle_7;

	private View rectangle_1;
	private TextView diary;

	private View foot;

	public TextView __1;

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
	
	}

	public void onClickRealNote(View view){
		Intent intent = new Intent (this,note_example_activity.class);
		startActivity(intent);
	}
}
	
	