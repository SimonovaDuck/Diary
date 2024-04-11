package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class enter_in_activity extends Activity {

	private Button button;
	private TextView text_noacc;
	private TextView create_account_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_in);

		// Находим кнопки и текстовые поля по их идентификаторам
		button = findViewById(R.id.create_account_button);

		text_noacc = findViewById(R.id.text_noacc);
		create_account_button = findViewById(R.id.create_account_button);

		// Устанавливаем обработчик нажатия на кнопку create_account_button
		create_account_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Создаем новый интент для перехода на активность registration_activity
				Intent intent = new Intent(enter_in_activity.this, registration_activity.class);
				// Запускаем активность с помощью созданного интента
				startActivity(intent);
			}
		});

	}
}
