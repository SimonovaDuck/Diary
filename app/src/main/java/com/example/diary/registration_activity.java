package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class registration_activity extends Activity {

	private View _bg___registration;
	private View rectangle_12;
	private View rectangle_13;
	private View rectangle_14;
	private TextView reg;
	private TextView quest;
	private TextView login;
	private TextView password;
	private TextView email;
	public TextView ent;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		_bg___registration = findViewById(R.id._bg___registration);
		rectangle_12 = findViewById(R.id.rectangle_12);
		rectangle_13 = findViewById(R.id.rectangle_13);
		rectangle_14 = findViewById(R.id.rectangle_14);
		reg = findViewById(R.id.reg);
		quest = findViewById(R.id.quest);
		login = findViewById(R.id.login);
		password = findViewById(R.id.password);
		email = findViewById(R.id.email);
		ent = findViewById(R.id.ent);



	}

	public void onClickEnt(View view){
		Intent intent = new Intent (this,enteryes_activity.class);
		startActivity(intent);
	}

	public void onClickregistration(View view){
		Intent intent = new Intent (this,enteryes_activity.class);
		startActivity(intent);
	}
}
