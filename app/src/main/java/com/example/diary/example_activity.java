package com.example.diary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

	}
}
