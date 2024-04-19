package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class enteryes_activity extends Activity {


	public TextView ent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enteryes);

	}
	public void onClickReg(View view){
		Intent intent = new Intent (this, registration_activity.class);
		startActivity(intent);
	}



	public void onClickEnter(View view){
		Intent intent = new Intent (this, menu_activity.class);
		startActivity(intent);
	}
}
