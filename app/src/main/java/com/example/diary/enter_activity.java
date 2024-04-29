package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class enter_activity extends Activity {

	private static final int SPLASH_DELAY = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Проверяем состояние входа пользователя
		if (isUserLoggedIn()) {
			// Пользователь уже вошел, перенаправляем его на основную активность
			Intent intent = new Intent(this, menu_activity.class);
			startActivity(intent);
			finish(); // Закрываем текущую активность, чтобы нельзя было вернуться назад
		} else {
			// Пользователь не вошел, отображаем активность входа
			setContentView(R.layout.enter);
			View entering = findViewById(R.id.entering);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(enter_activity.this, enteryes_activity.class);
					startActivity(intent);
					finish(); // Закрываем текущую активность, чтобы нельзя было вернуться назад
				}
			}, SPLASH_DELAY);
		}
	}

	// Метод для проверки состояния входа пользователя
	private boolean isUserLoggedIn() {
		SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
		return sharedPreferences.getBoolean("isLoggedIn", false);
	}
}
