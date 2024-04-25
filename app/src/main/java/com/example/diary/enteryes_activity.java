package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class enteryes_activity extends Activity {


	public TextView ent;
	private FirebaseFirestore db;
	private EditText logindt, passwordt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enteryes);
		db = FirebaseFirestore.getInstance();


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
		authenticateUser(login, password);
	}

	private void authenticateUser(String login, String password) {
		CollectionReference dbusers = db.collection("users");
		Log.d("Authentication", "Attempting to find user with login: " + login);
		dbusers.whereEqualTo("login", login).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
			@Override
			public void onSuccess(QuerySnapshot querySnapshot) {
				if (!querySnapshot.isEmpty()) {
					// Пользователь с таким логином найден
					// Проверяем пароль
					for (DocumentSnapshot document : querySnapshot.getDocuments()) {
						String storedPassword = document.getString("password");
						if (storedPassword.equals(password)) {
							// Пароль совпадает, вход выполнен успешно
							// Здесь вы можете перейти на другую активность или выполнить другие действия
							Intent intent = new Intent(enteryes_activity.this, menu_activity.class);
							startActivity(intent);
							return;
						}
					}
					// Пароль не совпадает
					Toast.makeText(enteryes_activity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
				} else {
					// Пользователь с таким логином не найден
					Toast.makeText(enteryes_activity.this, "Пользователь с таким логином не найден", Toast.LENGTH_SHORT).show();
				}
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				// Ошибка при выполнении запроса к базе данных
				Toast.makeText(enteryes_activity.this, "Ошибка при попытке входа: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
