package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class enteryes_activity extends Activity {

	private FirebaseAuth mAuth;
	private EditText logindt, passwordt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enteryes);
		mAuth = FirebaseAuth.getInstance();
	}

	public void onClickReg(View view){
		Intent intent = new Intent (this, registration_activity.class);
		startActivity(intent);
	}

	public void onClickEnter(View view){
		logindt = findViewById(R.id.rectangle_12);
		passwordt = findViewById(R.id.rectangle_13);
		String login = logindt.getText().toString();
		String password = passwordt.getText().toString();
		if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)) {
			// Если поля пустые, выводим сообщение пользователю
			Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show();
		} else {
			// Если поля не пустые, производим аутентификацию пользователя
			authenticateUser(login, password);
		}
	}

	private void authenticateUser(String login, String password) {
		mAuth.signInWithEmailAndPassword(login, password)
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						// Вход выполнен успешно
						// Сохраняем информацию о входе пользователя
						saveLoginStatus(true);
						// Здесь вы можете перейти на другую активность или выполнить другие действия
						Intent intent = new Intent(enteryes_activity.this, menu_activity.class);
						startActivity(intent);
					} else {
						// Вход не выполнен
						Toast.makeText(enteryes_activity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void saveLoginStatus(boolean isLoggedIn) {
		SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isLoggedIn", isLoggedIn);
		editor.apply();
	}
}
